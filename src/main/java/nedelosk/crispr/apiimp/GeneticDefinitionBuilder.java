package nedelosk.crispr.apiimp;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticDefinitionBuilder;
import nedelosk.crispr.api.IGeneticRoot;
import nedelosk.crispr.api.IGeneticTransformer;
import nedelosk.crispr.api.ITemplateContainer;
import nedelosk.crispr.api.gene.IGeneticStat;
import nedelosk.crispr.api.individual.IGeneticHandler;
import nedelosk.crispr.api.individual.IGeneticType;
import nedelosk.crispr.api.individual.IGenome;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.translators.IBlockTranslator;
import nedelosk.crispr.api.translators.IGeneticTranslator;
import nedelosk.crispr.api.translators.IItemTranslator;

public class GeneticDefinitionBuilder<I extends IIndividual, R extends IGeneticRoot<I, ?>> implements IGeneticDefinitionBuilder<I, R> {
	private final Map<IGeneticType, IGeneticHandler<I>> types = new HashMap<>();
	private final ITemplateContainer templateContainer;
	private final Function<IGeneticDefinition<I, R>, R> rootFactory;
	private final String name;
	private final Map<Item, IItemTranslator<I>> itemTranslators = new HashMap<>();
	private final Map<Block, IBlockTranslator<I>> blockTranslators = new HashMap<>();
	private Supplier<IGeneticTransformer<I>> transformerFactory = GeneticTransformer::new;
	private BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IGeneticTranslator<I>> translatorFactory = GeneticTranslator::new;
	private Function<Map<IGeneticType, IGeneticHandler<I>>, IGeneticTypes<I>> typesFactory = GeneticTypes::new;
	private Function<IGenome, IGeneticStat> statFactory = (g) -> (IGeneticStat) () -> g;

	public GeneticDefinitionBuilder(String name, ITemplateContainer templateContainer, Function<IGeneticDefinition<I, R>, R> rootFactory) {
		this.name = name;
		this.templateContainer = templateContainer;
		this.rootFactory = rootFactory;
	}

	@Override
	public IGeneticDefinitionBuilder<I, R> registerType(IGeneticType type, IGeneticHandler<I> handler) {
		types.put(type, handler);
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I, R> registerTranslator(Block translatorKey, IBlockTranslator<I> translator) {
		blockTranslators.put(translatorKey, translator);
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I, R> registerTranslator(Item translatorKey, IItemTranslator<I> translator) {
		itemTranslators.put(translatorKey, translator);
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I, R> setTransformer(Supplier<IGeneticTransformer<I>> transformerFactory) {
		this.transformerFactory = transformerFactory;
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I, R> setTranslator(BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IGeneticTranslator<I>> translatorFactory) {
		this.translatorFactory = translatorFactory;
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I, R> setTypes(Function<Map<IGeneticType, IGeneticHandler<I>>, IGeneticTypes<I>> typesFactory) {
		this.typesFactory = typesFactory;
		return this;
	}

	@Override
	public IGeneticDefinitionBuilder<I, R> setStat(Function<IGenome, IGeneticStat> statFactory) {
		this.statFactory = statFactory;
		return this;
	}

	@Override
	public IGeneticDefinition<I, R> build() {
		IGeneticTranslator<I> translator = translatorFactory.apply(itemTranslators, blockTranslators);
		IGeneticTransformer<I> transformer = transformerFactory.get();
		IGeneticTypes<I> types = typesFactory.apply(this.types);
		return new GeneticDefinition<>(types, translator, transformer, statFactory, templateContainer, name, rootFactory);
	}
}
