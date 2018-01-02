package genetics.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.definition.IGeneticDefinition;
import genetics.api.definition.IGeneticRoot;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGeneticType;
import genetics.api.individual.IGenome;
import genetics.api.individual.IIndividual;
import genetics.api.individual.IIndividualHandler;

public interface IGeneticFactory {

	IAlleleTemplateBuilder createTemplate(IGeneticDefinition definition);

	IAlleleTemplateBuilder createTemplate(IGeneticDefinition definition, IAllele[] alleles);

	IGenome createGenome(IKaryotype karyotype, NBTTagCompound compound);

	IGenome createGenome(IKaryotype karyotype, IChromosome[] chromosomes);

	<I extends IIndividual> IIndividualHandler<I> createIndividualHandler(ItemStack itemStack, IGeneticType type, IGeneticDefinition<I, IGeneticRoot> definition);
}
