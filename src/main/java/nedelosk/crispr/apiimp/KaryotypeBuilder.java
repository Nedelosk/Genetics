package nedelosk.crispr.apiimp;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IGeneKey;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.gene.IKaryotypeBuilder;
import nedelosk.crispr.apiimp.alleles.AlleleTemplateBuilder;

public class KaryotypeBuilder implements IKaryotypeBuilder {
	private Set<IGeneKey> genes = new HashSet<>();
	private BiFunction<IKaryotype, Allele[], IAlleleTemplateBuilder> templateFactory = AlleleTemplateBuilder::new;

	@Override
	public IKaryotypeBuilder register(IGeneKey gene) {
		this.genes.add(gene);
		return this;
	}

	@Override
	public IKaryotypeBuilder setFactory(BiFunction<IKaryotype, Allele[], IAlleleTemplateBuilder> templateFactory) {
		this.templateFactory = templateFactory;
		return this;
	}

	@Override
	public IKaryotype build() {
		return new Karyotype(genes, templateFactory);
	}
}
