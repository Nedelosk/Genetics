package genetics.organism;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;

import genetics.api.alleles.IAllele;
import genetics.api.individual.IChromosomeType;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganism;
import genetics.api.organism.IOrganismType;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IRootDefinition;

import genetics.Genetics;
import genetics.individual.GeneticSaveHandler;

public class Organism<I extends IIndividual> implements IOrganism<I> {
	private final ItemStack container;
	private final IRootDefinition<? extends IIndividualRoot<I>> definition;
	private final Supplier<IOrganismType> typeSupplier;

	public Organism(ItemStack container, IRootDefinition<? extends IIndividualRoot<I>> geneticDefinitionSupplier, Supplier<IOrganismType> typeSupplier) {
		this.container = container;
		this.definition = geneticDefinitionSupplier;
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
		return definition.get();
	}

	@Override
	public IOrganismType getType() {
		return typeSupplier.get();
	}

	@Override
	public IAllele getAllele(IChromosomeType chromosomeType, boolean active) {
		IAllele allele = GeneticSaveHandler.INSTANCE.getAlleleDirectly(container, getType(), chromosomeType, active);
		if (allele == null) {
			allele = GeneticSaveHandler.INSTANCE.getAllele(container, getType(), chromosomeType, active);
		}
		return allele;
	}

	@Override
	public Optional<IAllele> getAlleleDirectly(IChromosomeType type, boolean active) {
		return Optional.ofNullable(GeneticSaveHandler.INSTANCE.getAlleleDirectly(container, getType(), type, active));
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
