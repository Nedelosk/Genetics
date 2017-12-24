package nedelosk.crispr.apiimp;

import java.util.function.Function;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticTransformer;
import nedelosk.crispr.api.gene.IGeneticStat;
import nedelosk.crispr.api.gene.IGenome;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.individual.IIndividualFactory;
import nedelosk.crispr.api.translators.IGeneticTranslator;

public class GeneticDefinition<I extends IIndividual> implements IGeneticDefinition<I> {
	private final IGeneticTypes<I> types;
	private final IGeneticTranslator<I> translator;
	private final IGeneticTransformer<I> transformer;
	private final IKaryotype karyotype;
	private final String name;
	private final Function<IGenome, IGeneticStat> statFactory;
	private final IIndividualFactory<I> factory;

	GeneticDefinition(IGeneticTypes<I> types, IGeneticTranslator<I> translator, IGeneticTransformer<I> transformer, Function<IGenome, IGeneticStat> statFactory, IKaryotype karyotype, String name, IIndividualFactory<I> factory) {
		this.types = types;
		this.translator = translator;
		this.transformer = transformer;
		this.statFactory = statFactory;
		this.name = name;
		this.karyotype = karyotype;
		this.factory = factory;
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
	public IIndividualFactory factory() {
		return factory;
	}

	@Override
	public IKaryotype karyotype() {
		return karyotype;
	}

	@Override
	public IGeneticStat createStat(IGenome genome) {
		return statFactory.apply(genome);
	}

	@Override
	public IGeneticTypes<I> types() {
		return types;
	}

	@Override
	public IGeneticTransformer<I> transformer() {
		return transformer;
	}

	@Override
	public IGeneticTranslator<I> translator() {
		return translator;
	}
}