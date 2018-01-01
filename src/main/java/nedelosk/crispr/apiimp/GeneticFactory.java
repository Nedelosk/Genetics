package nedelosk.crispr.apiimp;

import net.minecraft.item.ItemStack;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticFactory;
import nedelosk.crispr.api.IGeneticRoot;
import nedelosk.crispr.api.ITemplateContainer;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.individual.IGeneticType;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.individual.IIndividualHandler;
import nedelosk.crispr.apiimp.alleles.AlleleTemplateBuilder;
import nedelosk.crispr.apiimp.individual.IndividualHandler;

public enum GeneticFactory implements IGeneticFactory {
	INSTANCE;

	@Override
	public IAlleleTemplateBuilder createTemplate(ITemplateContainer container) {
		return new AlleleTemplateBuilder(container.getKaryotype(), container.getDefaultTemplate().alleles());
	}

	@Override
	public IAlleleTemplateBuilder createTemplate(ITemplateContainer container, Allele[] alleles) {
		return new AlleleTemplateBuilder(container.getKaryotype(), alleles);
	}

	@Override
	public <I extends IIndividual> IIndividualHandler<I> createIndividualHandler(ItemStack itemStack, IGeneticType type, IGeneticDefinition<I, IGeneticRoot> definition) {
		return new IndividualHandler<>(itemStack, () -> definition, () -> type);
	}
}
