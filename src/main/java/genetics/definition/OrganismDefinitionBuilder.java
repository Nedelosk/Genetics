package genetics.definition;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import genetics.api.IBlockTranslator;
import genetics.api.IItemTranslator;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.definition.IOrganismDefinition;
import genetics.api.definition.IOrganismDefinitionBuilder;
import genetics.api.definition.IOrganismRoot;
import genetics.api.definition.IGeneticTranslator;
import genetics.api.definition.IOrganismTypes;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IOrganism;
import genetics.api.individual.IOrganismHandler;
import genetics.api.individual.IOrganismType;

public class OrganismDefinitionBuilder<I extends IOrganism, R extends IOrganismRoot<I, ?>> implements IOrganismDefinitionBuilder<I> {
	private final Map<IOrganismType, IOrganismHandler<I>> types = new HashMap<>();
	private final Function<IOrganismDefinition<I, R>, R> rootFactory;
	private final String uid;
	private final IKaryotype karyotype;
	private final HashMap<String, IAllele[]> templates = new HashMap<>();
	private final Map<Item, IItemTranslator<I>> itemTranslators = new HashMap<>();
	private final Map<Block, IBlockTranslator<I>> blockTranslators = new HashMap<>();
	private BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IGeneticTranslator<I>> translatorFactory = GeneticTranslator::new;
	private Function<Map<IOrganismType, IOrganismHandler<I>>, IOrganismTypes<I>> typesFactory = GeneticTypes::new;

	public OrganismDefinitionBuilder(String uid, IKaryotype karyotype, Function<IOrganismDefinition<I, R>, R> rootFactory) {
		this.uid = uid;
		this.karyotype = karyotype;
		this.rootFactory = rootFactory;
	}

	@Override
	public IOrganismDefinitionBuilder<I> registerType(IOrganismType type, IOrganismHandler<I> handler) {
		types.put(type, handler);
		return this;
	}

	@Override
	public IOrganismDefinitionBuilder<I> registerTranslator(Block translatorKey, IBlockTranslator<I> translator) {
		blockTranslators.put(translatorKey, translator);
		return this;
	}

	@Override
	public IOrganismDefinitionBuilder<I> registerTranslator(Item translatorKey, IItemTranslator<I> translator) {
		itemTranslators.put(translatorKey, translator);
		return this;
	}

	@Override
	public IOrganismDefinitionBuilder<I> registerTemplate(IAllele[] template) {
		Preconditions.checkNotNull(template, "Tried to register null template");
		Preconditions.checkArgument(template.length > 0, "Tried to register empty template");

		registerTemplate(template[this.karyotype.getTemplateType().getIndex()].getRegistryName().toString(), template);
		return this;
	}

	@Override
	public IOrganismDefinitionBuilder<I> registerTemplate(String identifier, IAllele[] template) {
		Preconditions.checkNotNull(template, "Tried to register null template");
		Preconditions.checkArgument(template.length > 0, "Tried to register empty template");

		templates.put(identifier, template);
		return this;
	}

	@Override
	public IOrganismDefinitionBuilder<I> registerTemplate(IAlleleTemplate template) {
		return registerTemplate(template.alleles());
	}

	@Override
	public IOrganismDefinitionBuilder<I> registerTemplate(String identifier, IAlleleTemplate template) {
		return registerTemplate(identifier, template.alleles());
	}

	@Override
	public IOrganismDefinitionBuilder<I> setTranslator(BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IGeneticTranslator<I>> translatorFactory) {
		this.translatorFactory = translatorFactory;
		return this;
	}

	@Override
	public IOrganismDefinitionBuilder<I> setTypes(Function<Map<IOrganismType, IOrganismHandler<I>>, IOrganismTypes<I>> typesFactory) {
		this.typesFactory = typesFactory;
		return this;
	}

	@Override
	public IKaryotype getKaryotype() {
		return karyotype;
	}

	@Override
	public IAlleleTemplate getDefaultTemplate() {
		return null;
	}

	public IOrganismDefinition<I, R> create() {
		IGeneticTranslator<I> translator = translatorFactory.apply(itemTranslators, blockTranslators);
		IOrganismTypes<I> types = typesFactory.apply(this.types);
		return new OrganismDefinition<>(types, translator, new TemplateContainer(karyotype, ImmutableMap.copyOf(templates)), uid, rootFactory);
	}
}
