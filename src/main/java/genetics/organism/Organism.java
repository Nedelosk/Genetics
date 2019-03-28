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
import net.minecraftforge.common.util.LazyOptional;

public class Organism<I extends IIndividual> implements IOrganism<I> {
	private final LazyOptional<IOrganism> holder = LazyOptional.of(() -> this);
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
	public boolean isEmpty() {
		return false;
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
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing facing) {
		return Genetics.ORGANISM.orEmpty(cap, holder);
	}
}
