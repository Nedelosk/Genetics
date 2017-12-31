package nedelosk.crispr.apiimp.alleles;

import javax.annotation.Nullable;

import nedelosk.crispr.api.IGeneticTransformer;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IChromosome;
import nedelosk.crispr.api.individual.IGenome;
import nedelosk.crispr.api.individual.IIndividual;

public final class AlleleTemplate implements IAlleleTemplate {
	public final IAllele[] alleles;
	private final IKaryotype karyotype;

	AlleleTemplate(IAllele[] alleles, IKaryotype karyotype) {
		this.alleles = alleles;
		this.karyotype = karyotype;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> IAllele<V> get(IGeneType type) {
		return (IAllele<V>) alleles[type.getIndex()];
	}

	@Override
	public IAllele[] alleles() {
		return alleles;
	}

	@Override
	public int size() {
		return alleles.length;
	}

	@Override
	public IAlleleTemplate copy() {
		return new AlleleTemplate(alleles, karyotype);
	}

	@Override
	public IAlleleTemplateBuilder createBuilder() {
		return new AlleleTemplateBuilder(karyotype, alleles);
	}

	@Override
	public IKaryotype getKaryotype() {
		return karyotype;
	}

	@Override
	public <I extends IIndividual> I toIndividual(IGeneticTransformer<I> transformer, @Nullable IAlleleTemplate inactiveTemplate) {
		return transformer.templateAsIndividual(alleles);
	}

	@Override
	public IGenome toGenome(IGeneticTransformer transformer, @Nullable IAlleleTemplate inactiveTemplate) {
		return transformer.templateAsGenome(alleles);
	}

	@Override
	public IChromosome[] toChromosomes(IGeneticTransformer transformer, @Nullable IAlleleTemplate inactiveTemplate) {
		return transformer.templateAsChromosomes(alleles);
	}
}
