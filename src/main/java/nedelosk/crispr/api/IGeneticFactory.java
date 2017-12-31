package nedelosk.crispr.api;

import net.minecraft.item.ItemStack;

import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IGeneticType;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.individual.IIndividualHandler;

public interface IGeneticFactory {

	IAlleleTemplateBuilder createTemplate(IKaryotype karyotype);

	IAlleleTemplateBuilder createTemplate(IKaryotype karyotype, Allele[] alleles);

	<I extends IIndividual> IIndividualHandler<I> createIndividualHandler(ItemStack itemStack, IGeneticType type, IGeneticDefinition<I> definition);
}
