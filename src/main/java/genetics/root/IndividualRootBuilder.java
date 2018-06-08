package genetics.root;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import net.minecraftforge.common.MinecraftForge;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.events.RootCreationEvent;
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismHandler;
import genetics.api.organism.IOrganismType;
import genetics.api.organism.IOrganismTypes;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IIndividualRootBuilder;
import genetics.api.root.IIndividualRootFactory;
import genetics.api.root.translator.IBlockTranslator;
import genetics.api.root.translator.IIndividualTranslator;
import genetics.api.root.translator.IItemTranslator;

import genetics.ApiInstance;
import genetics.individual.RootDefinition;
import genetics.organism.OrganismTypes;

public class IndividualRootBuilder<I extends IIndividual> implements IIndividualRootBuilder<I> {
	private final Map<IOrganismType, IOrganismHandler<I>> types = new HashMap<>();
	private final IIndividualRootFactory<I, IIndividualRoot<I>> rootFactory;
	private final String uid;
	private final IKaryotype karyotype;
	private final HashMap<String, IAllele[]> templates = new HashMap<>();
	private final Map<Item, IItemTranslator<I>> itemTranslators = new HashMap<>();
	private final Map<Block, IBlockTranslator<I>> blockTranslators = new HashMap<>();
	private final RootDefinition<IIndividualRoot<I>> definition;
	private BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IIndividualTranslator<I>> translatorFactory = IndividualTranslator::new;
	private Function<Map<IOrganismType, IOrganismHandler<I>>, IOrganismTypes<I>> typesFactory = OrganismTypes::new;

	public IndividualRootBuilder(String uid, IKaryotype karyotype, IIndividualRootFactory<I, IIndividualRoot<I>> rootFactory) {
		this.uid = uid;
		this.karyotype = karyotype;
		this.rootFactory = rootFactory;
		this.definition = ApiInstance.INSTANCE.getRoot(uid);
	}

	@Override
	public IIndividualRootBuilder<I> registerType(IOrganismType type, IOrganismHandler<I> handler) {
		types.put(type, handler);
		return this;
	}

	@Override
	public IIndividualRootBuilder<I> registerTranslator(Block translatorKey, IBlockTranslator<I> translator) {
		blockTranslators.put(translatorKey, translator);
		return this;
	}

	@Override
	public IIndividualRootBuilder<I> registerTranslator(Item translatorKey, IItemTranslator<I> translator) {
		itemTranslators.put(translatorKey, translator);
		return this;
	}

	@Override
	public IIndividualRootBuilder<I> registerTemplate(IAllele[] template) {
		Preconditions.checkNotNull(template, "Tried to register null template");
		Preconditions.checkArgument(template.length > 0, "Tried to register empty template");

		IChromosomeType templateType = karyotype.getTemplateType();
		IAllele templateAllele = template[templateType.getIndex()];
		String identifier = templateAllele.getRegistryName().toString();
		templates.put(identifier, template);

		return this;
	}

	@Override
	public IIndividualRootBuilder<I> registerTemplate(IAlleleTemplate template) {
		return registerTemplate(template.alleles());
	}

	@Override
	public IIndividualRootBuilder<I> setTranslator(BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IIndividualTranslator<I>> translatorFactory) {
		this.translatorFactory = translatorFactory;
		return this;
	}

	@Override
	public IIndividualRootBuilder<I> setTypes(Function<Map<IOrganismType, IOrganismHandler<I>>, IOrganismTypes<I>> typesFactory) {
		this.typesFactory = typesFactory;
		return this;
	}

	@Override
	public IKaryotype getKaryotype() {
		return karyotype;
	}

	public void create() {
		IIndividualTranslator<I> translator = translatorFactory.apply(itemTranslators, blockTranslators);
		IOrganismTypes<I> typesInstance = typesFactory.apply(this.types);
		definition.setRoot(rootFactory.createRoot(typesInstance, translator, new TemplateContainer(karyotype, ImmutableMap.copyOf(templates)), karyotype));
		MinecraftForge.EVENT_BUS.register(new RootCreationEvent<>(definition));
	}

	@SuppressWarnings("unchecked")
	public <R extends IIndividualRoot<I>> RootDefinition<R> getDefinition() {
		return (RootDefinition<R>) definition;
	}
}
