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
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.definition.IGeneticDefinition;
import genetics.api.definition.IGeneticDefinitionBuilder;
import genetics.api.definition.IGeneticRoot;
import genetics.api.definition.IGeneticTypes;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IGeneticHandler;
import genetics.api.individual.IGeneticType;
import genetics.api.individual.IIndividual;
import genetics.api.translators.IBlockTranslator;
import genetics.api.translators.IGeneticTranslator;
import genetics.api.translators.IItemTranslator;

import genetics.alleles.AlleleTemplateBuilder;

public class GeneticDefinitionBuilder<I extends IIndividual, R extends IGeneticRoot<I, ?>> implements IGeneticDefinitionBuilder<I> {
	private final Map<IGeneticType, IGeneticHandler<I>> types = new HashMap<>();
	private final Function<IGeneticDefinition<I, R>, R> rootFactory;
	private final String uid;
	private final IKaryotype karyotype;
	private final HashMap<String, IAllele[]> templates = new HashMap<>();
	private final Map<Item, IItemTranslator<I>> itemTranslators = new HashMap<>();
	private final Map<Block, IBlockTranslator<I>> blockTranslators = new HashMap<>();
	private BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IGeneticTranslator<I>> translatorFactory = GeneticTranslator::new;
	private Function<Map<IGeneticType, IGeneticHandler<I>>, IGeneticTypes<I>> typesFactory = GeneticTypes::new;
	private BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory = AlleleTemplateBuilder::new;
	private BiFunction<IKaryotype, IAllele[], String> templateNameFactory;

	public GeneticDefinitionBuilder(String uid, IKaryotype karyotype, Function<IGeneticDefinition<I, R>, R> rootFactory) {
		this.uid = uid;
		this.karyotype = karyotype;
		this.rootFactory = rootFactory;
		this.templateNameFactory = (k, a) -> a[this.karyotype.getTemplateType().getIndex()].getRegistryName().toString();
	}

	@Override
	public IGeneticDefinitionBuilder<I> registerType(IGeneticType type, IGeneticHandler<I> handler) {
		types.put(type, handler);
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I> registerTranslator(Block translatorKey, IBlockTranslator<I> translator) {
		blockTranslators.put(translatorKey, translator);
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I> registerTranslator(Item translatorKey, IItemTranslator<I> translator) {
		itemTranslators.put(translatorKey, translator);
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I> registerTemplate(IAllele[] template) {
		Preconditions.checkNotNull(template, "Tried to register null template");
		Preconditions.checkArgument(template.length > 0, "Tried to register empty template");

		registerTemplate(templateNameFactory.apply(karyotype, template), template);
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I> registerTemplate(String identifier, IAllele[] template) {
		Preconditions.checkNotNull(template, "Tried to register null template");
		Preconditions.checkArgument(template.length > 0, "Tried to register empty template");

		templates.put(identifier, template);
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I> registerTemplate(IAlleleTemplate template) {
		return registerTemplate(template.alleles());
	}

	@Override
	public IGeneticDefinitionBuilder<I> registerTemplate(String identifier, IAlleleTemplate template) {
		return registerTemplate(identifier, template.alleles());
	}

	@Override
	public IGeneticDefinitionBuilder<I> setTranslator(BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IGeneticTranslator<I>> translatorFactory) {
		this.translatorFactory = translatorFactory;
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I> setTypes(Function<Map<IGeneticType, IGeneticHandler<I>>, IGeneticTypes<I>> typesFactory) {
		this.typesFactory = typesFactory;
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I> setTemplateFactory(BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory) {
		this.templateFactory = templateFactory;
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I> setTemplateNameFactory(BiFunction<IKaryotype, IAllele[], String> templateNameFactory) {
		this.templateNameFactory = templateNameFactory;
		return this;
	}

	public IGeneticDefinition<I, R> create() {
		IGeneticTranslator<I> translator = translatorFactory.apply(itemTranslators, blockTranslators);
		IGeneticTypes<I> types = typesFactory.apply(this.types);
		return new GeneticDefinition<>(types, translator, new TemplateContainer(karyotype, ImmutableMap.copyOf(templates), templateFactory), uid, rootFactory);
	}
}
