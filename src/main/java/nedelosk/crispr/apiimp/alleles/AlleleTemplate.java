package nedelosk.crispr.apiimp.alleles;

import javax.annotation.Nullable;

import nedelosk.crispr.api.IGeneticRoot;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IGeneticStat;
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
	public <I extends IIndividual> I toIndividual(IGeneticRoot<I, IGeneticStat> root, @Nullable IAlleleTemplate inactiveTemplate) {
		return root.templateAsIndividual(alleles, inactiveTemplate == null ? null : inactiveTemplate.alleles());
	}

	@Override
	public IGenome toGenome(@Nullable IAlleleTemplate inactiveTemplate) {
		return karyotype.templateAsGenome(alleles, inactiveTemplate == null ? null : inactiveTemplate.alleles());
	}

	@Override
	public IChromosome[] toChromosomes(@Nullable IAlleleTemplate inactiveTemplate) {
		return karyotype.templateAsChromosomes(alleles, inactiveTemplate == null ? null : inactiveTemplate.alleles());
	}
}
