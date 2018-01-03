package genetics.items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import genetics.api.alleles.IAllele;
import genetics.api.definition.IGeneticDefinition;
import genetics.api.definition.IGeneticRoot;
import genetics.api.gene.IGeneType;
import genetics.api.individual.IGeneticType;
import genetics.api.individual.IIndividual;
import genetics.api.items.IIndividualHandler;

import genetics.Genetics;
import genetics.individual.GeneticSaveHandler;

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
		IAllele allele = GeneticSaveHandler.INSTANCE.getAlleleDirectly(container, type, active);
		if (allele == null) {
			allele = GeneticSaveHandler.INSTANCE.getAllele(container, type, active);
		}
		return allele;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == Genetics.INDIVIDUAL_HANDLER;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == Genetics.INDIVIDUAL_HANDLER ? Genetics.INDIVIDUAL_HANDLER.cast(this) : null;
	}
}
