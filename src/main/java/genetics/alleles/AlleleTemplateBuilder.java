package genetics.alleles;

import java.util.Arrays;
import java.util.Optional;

import genetics.api.GeneticsAPI;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.gene.IGene;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;

public final class AlleleTemplateBuilder implements IAlleleTemplateBuilder {
	private final IAllele[] alleles;
	private final IKaryotype karyotype;

	public AlleleTemplateBuilder(IKaryotype karyotype, IAllele[] alleles) {
		this.alleles = Arrays.copyOf(alleles, alleles.length);
		this.karyotype = karyotype;
	}

	@Override
	public IAlleleTemplateBuilder set(IGeneType geneKey, IAllele<?> allele) {
		if (!karyotype.contains(geneKey)) {
			throw new IllegalArgumentException("Gene key is not valid for the karyotype of this template.");
		}
		Optional<IGene> optionalGene = GeneticsAPI.geneticSystem.getGene(geneKey);
		if (!optionalGene.isPresent()) {
			throw new IllegalArgumentException("Gene key is not registered.");
		}
		IGene gene = optionalGene.get();
		if (!gene.isValidAllele(allele)) {
			throw new IllegalArgumentException("The given allele is not a valid allele for the gene of the given gene key.");
		}
		alleles[geneKey.getIndex()] = allele;
		return this;
	}

	@Override
	public IAlleleTemplateBuilder set(IGeneType geneKey, IAlleleKey alleleKey) {
		if (!karyotype.contains(geneKey)) {
			throw new IllegalArgumentException("Gene key is not valid for the karyotype of this template.");
		}
		Optional<IGene> optionalGene = GeneticsAPI.geneticSystem.getGene(geneKey);
		if (!optionalGene.isPresent()) {
			throw new IllegalArgumentException("Gene key is not registered.");
		}
		IGene gene = optionalGene.get();
		Optional<IAllele<?>> allele = GeneticsAPI.alleleRegistry.getAllele(alleleKey);
		allele.ifPresent(a -> alleles[geneKey.getIndex()] = a);
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
