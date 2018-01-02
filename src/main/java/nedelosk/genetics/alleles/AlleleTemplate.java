package nedelosk.genetics.alleles;

import javax.annotation.Nullable;

import nedelosk.genetics.api.alleles.IAllele;
import nedelosk.genetics.api.alleles.IAlleleTemplate;
import nedelosk.genetics.api.alleles.IAlleleTemplateBuilder;
import nedelosk.genetics.api.definition.IGeneticRoot;
import nedelosk.genetics.api.gene.IGeneType;
import nedelosk.genetics.api.gene.IGeneticStat;
import nedelosk.genetics.api.gene.IKaryotype;
import nedelosk.genetics.api.individual.IChromosome;
import nedelosk.genetics.api.individual.IGenome;
import nedelosk.genetics.api.individual.IIndividual;

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
