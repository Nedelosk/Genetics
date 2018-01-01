package nedelosk.crispr.alleles;

import java.util.Optional;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleKey;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;

public final class AlleleTemplateBuilder implements IAlleleTemplateBuilder {
	public final IAllele[] alleles;
	public final IKaryotype karyotype;

	public AlleleTemplateBuilder(IKaryotype karyotype, IAllele[] alleles) {
		this.alleles = alleles;
		this.karyotype = karyotype;
	}

	@Override
	public IAlleleTemplateBuilder set(IGeneType geneKey, IAllele<?> allele) {
		if (!karyotype.contains(geneKey)) {
			throw new IllegalArgumentException("Gene key is not valid for the karyotype of this template.");
		}
		Optional<IGene> optionalGene = CrisprAPI.geneticSystem.getGene(geneKey);
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
		Optional<IGene> optionalGene = CrisprAPI.geneticSystem.getGene(geneKey);
		if (!optionalGene.isPresent()) {
			throw new IllegalArgumentException("Gene key is not registered.");
		}
		IGene gene = optionalGene.get();
		Optional<IAllele<?>> allele = CrisprAPI.alleleRegistry.getAllele(alleleKey);
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
