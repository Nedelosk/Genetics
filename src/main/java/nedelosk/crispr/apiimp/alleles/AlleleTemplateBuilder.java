package nedelosk.crispr.apiimp.alleles;

import java.util.Optional;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneKey;
import nedelosk.crispr.api.gene.IKaryotype;

public final class AlleleTemplateBuilder implements IAlleleTemplateBuilder {
	public final Allele[] alleles;
	public final IKaryotype karyotype;

	public AlleleTemplateBuilder(IKaryotype karyotype, Allele[] alleles) {
		this.alleles = alleles;
		this.karyotype = karyotype;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V, K extends IGeneKey> IAlleleTemplateBuilder set(K key, Allele<V> allele) {
		if (!karyotype.contains(key)) {
			throw new IllegalArgumentException("Gene key is not valid for the karyotype of this template.");
		}
		Optional<IGene> optionalGene = CrisprAPI.registry.getGene(key);
		if (!optionalGene.isPresent()) {
			throw new IllegalArgumentException("Gene key is not registered.");
		}
		IGene<V> gene = optionalGene.get();
		if (!gene.isValidAllele(allele)) {
			throw new IllegalArgumentException("The given allele is not a valid allele for the gene of the given gene key.");
		}
		alleles[key.getIndex()] = allele;
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V, K extends IGeneKey> IAlleleTemplateBuilder set(K key, V value) {
		if (!karyotype.contains(key)) {
			throw new IllegalArgumentException("Gene key is not valid for the karyotype of this template.");
		}
		Optional<IGene> optionalGene = CrisprAPI.registry.getGene(key);
		if (!optionalGene.isPresent()) {
			throw new IllegalArgumentException("Gene key is not registered.");
		}
		IGene<V> gene = optionalGene.get();

		Class<? extends V> valueClass = gene.getValueClass();
		if (!valueClass.isInstance(value)) {
			throw new IllegalArgumentException("The given value is not valid.");
		}
		Optional<Allele<V>> allele = gene.getAllele(value);
		allele.ifPresent(a -> alleles[key.getIndex()] = a);
		return this;
	}

	@Override
	public IKaryotype getKaryotype() {
		return karyotype;
	}

	@Override
	public int size() {
		return alleles.length;
	}

	@Override
	public IAlleleTemplate build() {
		return new AlleleTemplate(alleles, karyotype);
	}
}
