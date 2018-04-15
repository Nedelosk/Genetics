package genetics.alleles;

import java.util.Arrays;
import java.util.Optional;

import net.minecraft.util.ResourceLocation;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;
import genetics.api.alleles.IAlleleRegistry;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IGene;
import genetics.api.gene.IKaryotype;

import genetics.ApiInstance;

public final class AlleleTemplateBuilder implements IAlleleTemplateBuilder {
	private final IAllele[] alleles;
	private final IKaryotype karyotype;

	public AlleleTemplateBuilder(IKaryotype karyotype, IAllele[] alleles) {
		this.alleles = Arrays.copyOf(alleles, alleles.length);
		this.karyotype = karyotype;
	}

	@Override
	public IAlleleTemplateBuilder set(IChromosomeType chromosomeType, IAllele<?> allele) {
		if (!karyotype.contains(chromosomeType)) {
			throw new IllegalArgumentException("The given chromosome type is not valid for the karyotype of this template.");
		}
		Optional<IGene> optionalGene = ApiInstance.INSTANCE.getGeneRegistry().getGene(chromosomeType);
		if (!optionalGene.isPresent()) {
			throw new IllegalArgumentException("Chromosome key is not registered.");

		}
		IGene gene = optionalGene.get();
		if (!gene.isValidAllele(allele)) {
			throw new IllegalArgumentException("The given allele is not a valid allele for the gene of the given chromosome key.");
		}
		alleles[chromosomeType.getIndex()] = allele;
		return this;
	}

	@Override
	public IAlleleTemplateBuilder set(IChromosomeType chromosomeType, IAlleleKey alleleKey) {
		if (!karyotype.contains(chromosomeType)) {
			throw new IllegalArgumentException("The given chromosome type is not valid for the karyotype of this template.");
		}
		Optional<IGene> optionalGene = ApiInstance.INSTANCE.getGeneRegistry().getGene(chromosomeType);
		if (!optionalGene.isPresent()) {
			throw new IllegalArgumentException("Gene key is not registered.");
		}
		IAlleleRegistry alleleRegistry = ApiInstance.INSTANCE.getAlleleRegistry();
		Optional<IAllele> allele = alleleRegistry.getAllele(alleleKey);
		allele.ifPresent(a -> alleles[chromosomeType.getIndex()] = a);
		return this;
	}

	@Override
	public IAlleleTemplateBuilder set(IChromosomeType chromosomeType, ResourceLocation registryName) {
		if (!karyotype.contains(chromosomeType)) {
			throw new IllegalArgumentException("The given chromosome type is not valid for the karyotype of this template.");
		}
		Optional<IGene> optionalGene = ApiInstance.INSTANCE.getGeneRegistry().getGene(chromosomeType);
		if (!optionalGene.isPresent()) {
			throw new IllegalArgumentException("Chromosome key is not registered.");

		}
		IAlleleRegistry alleleRegistry = ApiInstance.INSTANCE.getAlleleRegistry();
		Optional<IAllele> allele = alleleRegistry.getAllele(registryName);
		allele.ifPresent(a -> alleles[chromosomeType.getIndex()] = a);
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
