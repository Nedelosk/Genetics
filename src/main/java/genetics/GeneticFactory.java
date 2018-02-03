package genetics;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import genetics.api.IGeneTemplate;
import genetics.api.IGeneticFactory;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.definition.IIndividualDefinition;
import genetics.api.definition.IIndividualRoot;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganism;
import genetics.api.organism.IOrganismType;

import genetics.alleles.AlleleTemplate;
import genetics.alleles.AlleleTemplateBuilder;
import genetics.individual.Chromosome;
import genetics.individual.Genome;
import genetics.items.GeneTemplate;
import genetics.organism.Organism;

public enum GeneticFactory implements IGeneticFactory {
	INSTANCE;

	@Override
	public IAlleleTemplateBuilder createTemplateBuilder(IKaryotype karyotype) {
		return new AlleleTemplateBuilder(karyotype, karyotype.getDefaultTemplate().alleles());
	}

	@Override
	public IAlleleTemplateBuilder createTemplateBuilder(IKaryotype karyotype, IAllele[] alleles) {
		return new AlleleTemplateBuilder(karyotype, alleles);
	}

	@Override
	public IAlleleTemplate createTemplate(IKaryotype karyotype, IAllele[] alleles) {
		return new AlleleTemplate(Arrays.copyOf(alleles, alleles.length), karyotype);
	}

	@Override
	public IGenome createGenome(IKaryotype karyotype, NBTTagCompound compound) {
		return new Genome(karyotype, compound);
	}

	@Override
	public IGenome createGenome(IKaryotype karyotype, IChromosome[] chromosomes) {
		return new Genome(karyotype, chromosomes);
	}

	@Override
	public IChromosome createChromosome(IAllele allele, IGeneType type) {
		return Chromosome.create(allele, type);
	}

	@Override
	public IChromosome createChromosome(IAllele firstAllele, IAllele secondAllele, IGeneType type) {
		return Chromosome.create(firstAllele, secondAllele, type);
	}

	@Override
	public <I extends IIndividual> IOrganism<I> createOrganism(ItemStack itemStack, IOrganismType type, IIndividualDefinition<I, IIndividualRoot<I, ?>> definition) {
		return new Organism<>(itemStack, () -> definition, () -> type);
	}

	@Override
	public IGeneTemplate createGeneTemplate() {
		return new GeneTemplate();
	}
}
