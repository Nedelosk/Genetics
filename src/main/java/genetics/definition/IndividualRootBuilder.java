package genetics.definition;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
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

import genetics.individual.OptionalRoot;
import genetics.organism.OrganismTypes;

public class IndividualRootBuilder<I extends IIndividual, R extends IIndividualRoot<I>> implements IIndividualRootBuilder<I, R> {
	private final Map<IOrganismType, IOrganismHandler<I>> types = new HashMap<>();
	private final IIndividualRootFactory<I, R> rootFactory;
	private final String uid;
	private final IKaryotype karyotype;
	private final HashMap<String, IAllele[]> templates = new HashMap<>();
	private final Map<Item, IItemTranslator<I>> itemTranslators = new HashMap<>();
	private final Map<Block, IBlockTranslator<I>> blockTranslators = new HashMap<>();
	private final OptionalRoot<R> optional;
	private BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IIndividualTranslator<I>> translatorFactory = IndividualTranslator::new;
	private Function<Map<IOrganismType, IOrganismHandler<I>>, IOrganismTypes<I>> typesFactory = OrganismTypes::new;

	public IndividualRootBuilder(String uid, IKaryotype karyotype, IIndividualRootFactory<I, R> rootFactory) {
		this.uid = uid;
		this.karyotype = karyotype;
		this.rootFactory = rootFactory;
		this.optional = new OptionalRoot<>();
	}

	@Override
	public IIndividualRootBuilder<I, R> registerType(IOrganismType type, IOrganismHandler<I> handler) {
		types.put(type, handler);
		return this;
	}

	@Override
	public IIndividualRootBuilder<I, R> registerTranslator(Block translatorKey, IBlockTranslator<I> translator) {
		blockTranslators.put(translatorKey, translator);
		return this;
	}

	@Override
	public IIndividualRootBuilder<I, R> registerTranslator(Item translatorKey, IItemTranslator<I> translator) {
		itemTranslators.put(translatorKey, translator);
		return this;
	}

	@Override
	public IIndividualRootBuilder<I, R> registerTemplate(IAllele[] template) {
		Preconditions.checkNotNull(template, "Tried to register null template");
		Preconditions.checkArgument(template.length > 0, "Tried to register empty template");

		registerTemplate(template[this.karyotype.getTemplateType().getIndex()].getRegistryName().toString(), template);
		return this;
	}

	@Override
	public IIndividualRootBuilder<I, R> registerTemplate(String identifier, IAllele[] template) {
		Preconditions.checkNotNull(template, "Tried to register null template");
		Preconditions.checkArgument(template.length > 0, "Tried to register empty template");

		templates.put(identifier, template);
		return this;
	}

	@Override
	public IIndividualRootBuilder<I, R> registerTemplate(IAlleleTemplate template) {
		return registerTemplate(template.alleles());
	}

	@Override
	public IIndividualRootBuilder<I, R> registerTemplate(String identifier, IAlleleTemplate template) {
		return registerTemplate(identifier, template.alleles());
	}

	@Override
	public IIndividualRootBuilder<I, R> setTranslator(BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IIndividualTranslator<I>> translatorFactory) {
		this.translatorFactory = translatorFactory;
		return this;
	}

	@Override
	public IIndividualRootBuilder<I, R> setTypes(Function<Map<IOrganismType, IOrganismHandler<I>>, IOrganismTypes<I>> typesFactory) {
		this.typesFactory = typesFactory;
		return this;
	}

	@Override
	public IKaryotype getKaryotype() {
		return karyotype;
	}

	public OptionalRoot<R> create() {
		IIndividualTranslator<I> translator = translatorFactory.apply(itemTranslators, blockTranslators);
		IOrganismTypes<I> typesInstance = typesFactory.apply(this.types);
		optional.setRoot(rootFactory.createRoot(typesInstance, translator, new TemplateContainer(karyotype, ImmutableMap.copyOf(templates)), karyotype));
		return optional;
	}

	public OptionalRoot<R> getOptional() {
		return optional;
	}
}
