package nedelosk.genetics;

import net.minecraft.item.ItemStack;

import nedelosk.genetics.alleles.AlleleTemplateBuilder;
import nedelosk.genetics.api.IGeneticFactory;
import nedelosk.genetics.api.alleles.IAllele;
import nedelosk.genetics.api.alleles.IAlleleTemplateBuilder;
import nedelosk.genetics.api.definition.IGeneticDefinition;
import nedelosk.genetics.api.definition.IGeneticRoot;
import nedelosk.genetics.api.individual.IGeneticType;
import nedelosk.genetics.api.individual.IIndividual;
import nedelosk.genetics.api.individual.IIndividualHandler;
import nedelosk.genetics.individual.IndividualHandler;

public enum GeneticFactory implements IGeneticFactory {
	INSTANCE;

	@Override
	public IAlleleTemplateBuilder createTemplate(IGeneticDefinition definition) {
		return new AlleleTemplateBuilder(definition, definition.getDefaultTemplate().alleles());
	}

	@Override
	public IAlleleTemplateBuilder createTemplate(IGeneticDefinition definition, IAllele[] alleles) {
		return new AlleleTemplateBuilder(definition, alleles);
	}

	@Override
	public <I extends IIndividual> IIndividualHandler<I> createIndividualHandler(ItemStack itemStack, IGeneticType type, IGeneticDefinition<I, IGeneticRoot> definition) {
		return new IndividualHandler<>(itemStack, () -> definition, () -> type);
	}
}
