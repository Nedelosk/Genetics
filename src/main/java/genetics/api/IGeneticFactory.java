package genetics.api;

import net.minecraft.item.ItemStack;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.definition.IGeneticDefinition;
import genetics.api.definition.IGeneticRoot;
import genetics.api.individual.IGeneticType;
import genetics.api.individual.IIndividual;
import genetics.api.individual.IIndividualHandler;

public interface IGeneticFactory {

	IAlleleTemplateBuilder createTemplate(IGeneticDefinition definition);

	IAlleleTemplateBuilder createTemplate(IGeneticDefinition definition, IAllele[] alleles);

	<I extends IIndividual> IIndividualHandler<I> createIndividualHandler(ItemStack itemStack, IGeneticType type, IGeneticDefinition<I, IGeneticRoot> definition);
}
