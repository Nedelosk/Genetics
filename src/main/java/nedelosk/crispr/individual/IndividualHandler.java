package nedelosk.crispr.individual;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import nedelosk.crispr.Crispr;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.definition.IGeneticDefinition;
import nedelosk.crispr.api.definition.IGeneticRoot;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.individual.IGeneticType;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.individual.IIndividualHandler;

public class IndividualHandler<I extends IIndividual> implements IIndividualHandler<I>, ICapabilityProvider {
	private final ItemStack container;
	private final Supplier<IGeneticDefinition<I, IGeneticRoot>> definitionSupplier;
	private final Supplier<IGeneticType> typeSupplier;

	public IndividualHandler(ItemStack container, Supplier<IGeneticDefinition<I, IGeneticRoot>> geneticDefinitionSupplier, Supplier<IGeneticType> typeSupplier) {
		this.container = container;
		this.definitionSupplier = geneticDefinitionSupplier;
		this.typeSupplier = typeSupplier;
	}

	@Override
	public Optional<I> getIndividual() {
		return getDefinition().getMember(container);
	}

	@Override
	public IGeneticDefinition<I, IGeneticRoot> getDefinition() {
		return definitionSupplier.get();
	}

	@Override
	public IGeneticType getType() {
		return typeSupplier.get();
	}

	@Override
	public IAllele<?> getAlleleDirectly(IGeneType type, boolean active) {
		IAllele allele = GeneticSaveHandler.INSTANCE.getAlleleDirectly(type, active, container);
		if (allele == null) {
			allele = GeneticSaveHandler.INSTANCE.getAllele(container, type, active);
		}
		return allele;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == Crispr.INDIVIDUAL_HANDLER;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == Crispr.INDIVIDUAL_HANDLER ? Crispr.INDIVIDUAL_HANDLER.cast(this) : null;
	}
}
