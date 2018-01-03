package genetics.alleles;

import javax.annotation.Nullable;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.definition.IGeneticRoot;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;
import genetics.api.individual.IGenomeWrapper;
import genetics.api.individual.IIndividual;

public final class AlleleTemplate implements IAlleleTemplate {
	private final IAllele[] alleles;
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
	public <I extends IIndividual> I toIndividual(IGeneticRoot<I, IGenomeWrapper> root, @Nullable IAlleleTemplate inactiveTemplate) {
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
