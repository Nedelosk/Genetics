package nedelosk.crispr.apiimp;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticRoot;
import nedelosk.crispr.api.IGeneticTransformer;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.gene.IGeneticStat;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IChromosome;
import nedelosk.crispr.api.individual.IGeneticHandler;
import nedelosk.crispr.api.individual.IGeneticType;
import nedelosk.crispr.api.individual.IGenome;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.translators.IBlockTranslator;
import nedelosk.crispr.api.translators.IGeneticTranslator;
import nedelosk.crispr.api.translators.IItemTranslator;

public class GeneticDefinition<I extends IIndividual> implements IGeneticDefinition<I> {
	private final IGeneticTypes<I> types;
	private final IGeneticTranslator<I> translator;
	private final IGeneticTransformer<I> transformer;
	private final IKaryotype karyotype;
	private final String name;
	private final Function<IGenome, IGeneticStat> statFactory;
	private final IGeneticRoot<I, ?> root;

	GeneticDefinition(IGeneticTypes<I> types, IGeneticTranslator<I> translator, IGeneticTransformer<I> transformer, Function<IGenome, IGeneticStat> statFactory, IKaryotype karyotype, String name, Function<IGeneticDefinition<I>, IGeneticRoot<I, ?>> rootFactory) {
		this.types = types;
		this.translator = translator;
		this.transformer = transformer;
		this.statFactory = statFactory;
		this.name = name;
		this.karyotype = karyotype;
		this.root = rootFactory.apply(this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public I getDefaultMember() {
		return null;
	}

	@Override
	public IGeneticRoot root() {
		return root;
	}

	@Override
	public IKaryotype getKaryotype() {
		return karyotype;
	}

	@Override
	public IGeneticStat createStat(IGenome genome) {
		return statFactory.apply(genome);
	}

	@Override
	public I templateAsIndividual(IAllele[] templateActive, @Nullable IAllele[] templateInactive) {
		return transformer.templateAsIndividual(templateActive, templateInactive);
	}

	@Override
	public IChromosome[] templateAsChromosomes(IAllele[] templateActive, @Nullable IAllele[] templateInactive) {
		return transformer.templateAsChromosomes(templateActive, templateInactive);
	}

	@Override
	public IGenome templateAsGenome(IAllele[] templateActive, @Nullable IAllele[] templateInactive) {
		return transformer.templateAsGenome(templateActive, templateInactive);
	}

	@Override
	public Optional<IItemTranslator<I>> getTranslator(Item translatorKey) {
		return translator.getTranslator(translatorKey);
	}

	@Override
	public Optional<IBlockTranslator<I>> getTranslator(Block translatorKey) {
		return translator.getTranslator(translatorKey);
	}

	@Override
	public Optional<I> translateMember(IBlockState objectToTranslate) {
		return translator.translateMember(objectToTranslate);
	}

	@Override
	public Optional<I> translateMember(ItemStack objectToTranslate) {
		return translator.translateMember(objectToTranslate);
	}

	@Override
	public ItemStack getGeneticEquivalent(IBlockState objectToTranslate) {
		return translator.getGeneticEquivalent(objectToTranslate);
	}

	@Override
	public ItemStack getGeneticEquivalent(ItemStack objectToTranslate) {
		return translator.getGeneticEquivalent(objectToTranslate);
	}

	@Override
	public ItemStack getMember(I individual, IGeneticType type) {
		return types.getMember(individual, type);
	}

	@Override
	public Optional<I> getMember(ItemStack itemStack) {
		return types.getMember(itemStack);
	}

	@Override
	public Optional<IGeneticType> getType(ItemStack itemStack) {
		return types.getType(itemStack);
	}

	@Override
	public Optional<IGeneticHandler<I>> getHandler(IGeneticType type) {
		return types.getHandler(type);
	}

	@Override
	public Collection<IGeneticType> getTypes() {
		return types.getTypes();
	}

	@Override
	public Collection<IGeneticHandler<I>> getHandlers() {
		return types.getHandlers();
	}
}
