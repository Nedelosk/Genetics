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
import genetics.api.definition.IOrganismDefinition;
import genetics.api.definition.IOrganismRoot;
import genetics.api.gene.IGeneType;
import genetics.api.individual.IOrganism;
import genetics.api.individual.IOrganismType;
import genetics.api.items.IIndividualHandler;

import genetics.Genetics;
import genetics.individual.GeneticSaveHandler;

public class IndividualHandler<I extends IOrganism> implements IIndividualHandler<I>, ICapabilityProvider {
	private final ItemStack container;
	private final Supplier<IOrganismDefinition<I, IOrganismRoot>> definitionSupplier;
	private final Supplier<IOrganismType> typeSupplier;

	public IndividualHandler(ItemStack container, Supplier<IOrganismDefinition<I, IOrganismRoot>> geneticDefinitionSupplier, Supplier<IOrganismType> typeSupplier) {
		this.container = container;
		this.definitionSupplier = geneticDefinitionSupplier;
		this.typeSupplier = typeSupplier;
	}

	@Override
	public Optional<I> getIndividual() {
		return getDefinition().createIndividual(container);
	}

	@Override
	public IOrganismDefinition<I, IOrganismRoot> getDefinition() {
		return definitionSupplier.get();
	}

	@Override
	public IOrganismType getType() {
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
