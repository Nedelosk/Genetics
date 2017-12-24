package nedelosk.crispr.apiimp;

import com.google.common.collect.ImmutableMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticTransformer;
import nedelosk.crispr.api.IGeneticTypes;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneticStat;
import nedelosk.crispr.api.gene.IGenome;
import nedelosk.crispr.api.individual.IGeneticIndividual;
import nedelosk.crispr.api.translators.IGeneticTranslator;
import nedelosk.crispr.apiimp.alleles.AlleleTemplate;
import nedelosk.crispr.apiimp.alleles.AlleleTemplateBuilder;

public class GeneticDefinition<I extends IGeneticIndividual> implements IGeneticDefinition<I> {
	private final IGeneticTypes<I> types;
	private final IGeneticTranslator<I> translator;
	private final IGeneticTransformer<I> transformer;
	private final Map<IGene, Integer> genes;
	private final String name;
	private final Function<IGenome, IGeneticStat> statFactory;

	public GeneticDefinition(IGeneticTypes<I> types, IGeneticTranslator<I> translator, IGeneticTransformer<I> transformer, Function<IGenome, IGeneticStat> statFactory, List<IGene> genes, String name) {
		this.types = types;
		this.translator = translator;
		this.transformer = transformer;
		this.statFactory = statFactory;
		this.name = name;
		ImmutableMap.Builder<IGene, Integer> geneBuilder = ImmutableMap.builder();
		for(int i = 0;i < genes.size();i++){
			geneBuilder.put(genes.get(i), i);
		}
		this.genes = geneBuilder.build();
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
	public Collection<IGene> getGenes() {
		return genes.keySet();
	}

	@Override
	public IAlleleTemplate getDefaultTemplate() {
		Allele[] alleles = genes.keySet().stream().map(IGene::getDefaultAllele).toArray(Allele[]::new);
		return new AlleleTemplate(alleles, this);
	}

	@Override
	public int getGeneIndex(IGene gene) {
		return genes.get(gene);
	}

	@Override
	public IGeneticStat createStat(IGenome genome) {
		return statFactory.apply(genome);
	}

	@Override
	public IAlleleTemplateBuilder createTemplate() {
		return getDefaultTemplate().createBuilder();
	}

	@Override
	public IAlleleTemplateBuilder createTemplate(Allele[] alleles) {
		return new AlleleTemplateBuilder(this, alleles);
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
