package nedelosk.crispr.apiimp.alleles;

import javax.annotation.Nullable;

import nedelosk.crispr.api.IGeneticTransformer;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IChromosome;
import nedelosk.crispr.api.gene.IGeneKey;
import nedelosk.crispr.api.gene.IGenome;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IIndividual;

public final class AlleleTemplate implements IAlleleTemplate {
	public final Allele[] alleles;
	private final IKaryotype karyotype;

	AlleleTemplate(Allele[] alleles, IKaryotype karyotype) {
		this.alleles = alleles;
		this.karyotype = karyotype;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <V> Allele<V> get(IGeneKey key) {
		return (Allele<V>) alleles[key.getIndex()];
	}

	@Override
	public Allele[] alleles() {
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
