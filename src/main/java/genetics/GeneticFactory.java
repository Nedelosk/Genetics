package genetics;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import genetics.api.IGeneticFactory;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.definition.IGeneticDefinition;
import genetics.api.definition.IGeneticRoot;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGeneticType;
import genetics.api.individual.IGenome;
import genetics.api.individual.IIndividual;
import genetics.api.items.IGeneTemplate;
import genetics.api.items.IIndividualHandler;

import genetics.alleles.AlleleTemplate;
import genetics.alleles.AlleleTemplateBuilder;
import genetics.individual.Chromosome;
import genetics.individual.Genome;
import genetics.items.GeneTemplate;
import genetics.items.IndividualHandler;

public enum GeneticFactory implements IGeneticFactory {
	INSTANCE;

	@Override
	public IAlleleTemplateBuilder createTemplateBuilder(IGeneticDefinition definition) {
		return new AlleleTemplateBuilder(definition, definition.getDefaultTemplate().alleles());
	}

	@Override
	public IAlleleTemplateBuilder createTemplateBuilder(IGeneticDefinition definition, IAllele[] alleles) {
		return new AlleleTemplateBuilder(definition, alleles);
	}

	@Override
	public IAlleleTemplate createTemplate(IGeneticDefinition definition, IAllele[] alleles) {
		return new AlleleTemplate(Arrays.copyOf(alleles, alleles.length), definition);
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
	public <I extends IIndividual> IIndividualHandler<I> createIndividualHandler(ItemStack itemStack, IGeneticType type, IGeneticDefinition<I, IGeneticRoot> definition) {
		return new IndividualHandler<>(itemStack, () -> definition, () -> type);
	}

	@Override
	public IGeneTemplate createGeneTemplate() {
		return new GeneTemplate();
	}
}
