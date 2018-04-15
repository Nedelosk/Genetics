package genetics.organism;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import genetics.api.alleles.IAllele;
import genetics.api.gene.IChromosomeType;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganism;
import genetics.api.organism.IOrganismType;
import genetics.api.root.IIndividualRoot;

import genetics.Genetics;
import genetics.individual.GeneticSaveHandler;

public class Organism<I extends IIndividual> implements IOrganism<I>, ICapabilityProvider {
	private final ItemStack container;
	private final Supplier<IIndividualRoot<I>> definitionSupplier;
	private final Supplier<IOrganismType> typeSupplier;

	public Organism(ItemStack container, Supplier<IIndividualRoot<I>> geneticDefinitionSupplier, Supplier<IOrganismType> typeSupplier) {
		this.container = container;
		this.definitionSupplier = geneticDefinitionSupplier;
		this.typeSupplier = typeSupplier;
	}

	@Override
	public Optional<I> getIndividual() {
		return getRoot().getTypes().createIndividual(container);
	}

	@Override
	public boolean setIndividual(I individual) {
		return getRoot().getTypes().setIndividual(container, individual);
	}

	@Override
	public IIndividualRoot<I> getRoot() {
		return definitionSupplier.get();
	}

	@Override
	public IOrganismType getType() {
		return typeSupplier.get();
	}

	@Override
	public IAllele getAllele(IChromosomeType type, boolean active) {
		IAllele allele = GeneticSaveHandler.INSTANCE.getAlleleDirectly(container, type, active);
		if (allele == null) {
			allele = GeneticSaveHandler.INSTANCE.getAllele(container, type, active);
		}
		return allele;
	}

	@Override
	public Optional<IAllele> getAlleleDirectly(IChromosomeType type, boolean active) {
		return Optional.ofNullable(GeneticSaveHandler.INSTANCE.getAlleleDirectly(container, type, active));
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == Genetics.ORGANISM;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == Genetics.ORGANISM ? Genetics.ORGANISM.cast(this) : null;
	}
}
