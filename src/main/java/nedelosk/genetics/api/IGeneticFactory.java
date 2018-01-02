package nedelosk.genetics.api;

import net.minecraft.item.ItemStack;

import nedelosk.genetics.api.alleles.IAllele;
import nedelosk.genetics.api.alleles.IAlleleTemplateBuilder;
import nedelosk.genetics.api.definition.IGeneticDefinition;
import nedelosk.genetics.api.definition.IGeneticRoot;
import nedelosk.genetics.api.individual.IGeneticType;
import nedelosk.genetics.api.individual.IIndividual;
import nedelosk.genetics.api.individual.IIndividualHandler;

public interface IGeneticFactory {

	IAlleleTemplateBuilder createTemplate(IGeneticDefinition definition);

	IAlleleTemplateBuilder createTemplate(IGeneticDefinition definition, IAllele[] alleles);

	<I extends IIndividual> IIndividualHandler<I> createIndividualHandler(ItemStack itemStack, IGeneticType type, IGeneticDefinition<I, IGeneticRoot> definition);
}
