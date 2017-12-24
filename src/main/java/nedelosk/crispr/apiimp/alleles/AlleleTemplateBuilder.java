package nedelosk.crispr.apiimp.alleles;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IGene;

public class AlleleTemplateBuilder implements IAlleleTemplateBuilder {
	public final Allele[] alleles;
	public final IGeneticDefinition root;

	public AlleleTemplateBuilder(IGeneticDefinition root, Allele[] alleles) {
		this.alleles = alleles;
		this.root = root;
	}

	@Override
	public <V, G extends IGene<V>> IAlleleTemplateBuilder set(G chromosomeType, Allele<V> allele) {
		return null;
	}

	@Override
	public <V, G extends IGene<V>> IAlleleTemplateBuilder set(G chromosomeType, V value) {
		return null;
	}

	@Override
	public IGeneticDefinition getRoot() {
		return root;
	}

	@Override
	public int size() {
		return alleles.length;
	}

	@Override
	public IAlleleTemplate build() {
		return new AlleleTemplate(alleles, root);
	}
}
