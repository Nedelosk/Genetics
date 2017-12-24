package nedelosk.crispr.apiimp.alleles;

import javax.annotation.Nullable;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IChromosome;
import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGenome;
import nedelosk.crispr.api.individual.IGeneticIndividual;

public class AlleleTemplate implements IAlleleTemplate {
	public final Allele[] alleles;
	private final IGeneticDefinition definition;

	public AlleleTemplate(Allele[] alleles, IGeneticDefinition definition) {
		this.alleles = alleles;
		this.definition = definition;
	}

	@Nullable
	@Override
	public <V> Allele<V> get(IGene<V> gene) {
		return (Allele<V>) alleles[definition.getGeneIndex(gene)];
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
		return new AlleleTemplate(alleles, definition);
	}

	@Override
	public IAlleleTemplateBuilder createBuilder() {
		return new AlleleTemplateBuilder(definition, alleles);
	}

	@Override
	public IGeneticDefinition getDefinition() {
		return definition;
	}

	@Override
	public IGeneticIndividual toIndividual(@Nullable IAlleleTemplate inactiveTemplate) {
		return definition.transformer().templateAsIndividual(alleles);
	}

	@Override
	public IGenome toGenome(@Nullable IAlleleTemplate inactiveTemplate) {
		return definition.transformer().templateAsGenome(alleles);
	}

	@Override
	public IChromosome[] toChromosomes(@Nullable IAlleleTemplate inactiveTemplate) {
		return definition.transformer().templateAsChromosomes(alleles);
	}
}
