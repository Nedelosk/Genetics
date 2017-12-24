package nedelosk.crispr.apiimp;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneKey;
import nedelosk.crispr.api.gene.IKaryotype;

public class Karyotype implements IKaryotype {
	private final IGeneKey[] genes;
	private final BiFunction<IKaryotype, Allele[], IAlleleTemplateBuilder> templateFactory;

	Karyotype(Set<IGeneKey> keys, BiFunction<IKaryotype, Allele[], IAlleleTemplateBuilder> templateFactory) {
		this.templateFactory = templateFactory;
		this.genes = new IGeneKey[keys.size()];
		for (IGeneKey key : keys) {
			this.genes[key.getIndex()] = key;
		}
	}

	@Override
	public IGeneKey[] getKeys() {
		return genes;
	}

	@Override
	public boolean contains(IGeneKey key) {
		return Arrays.asList(genes).contains(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IAlleleTemplate getDefaultTemplate() {
		Allele[] alleles = new Allele[genes.length];
		for (IGeneKey key : genes) {
			Optional<IGene> optional = CrisprAPI.registry.getGene(key);
			optional.ifPresent(g -> alleles[key.getIndex()] = g.getDefaultAllele());
		}
		return templateFactory.apply(this, alleles).build();
	}

	@Override
	public IAlleleTemplateBuilder createTemplate() {
		return getDefaultTemplate().createBuilder();
	}

	@Override
	public IAlleleTemplateBuilder createTemplate(Allele[] alleles) {
		return templateFactory.apply(this, alleles);
	}
}
