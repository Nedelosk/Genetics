package genetics.api.individual;

import javax.annotation.Nullable;
import java.util.Optional;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.GeneticsAPI;
import genetics.api.gene.IGeneType;

/**
 * A simple abstract implementation of {@link IOrganism}.
 */
public abstract class Organism implements IOrganism {
	protected final IGenome genome;
	protected boolean isAnalyzed = false;
	@Nullable
	protected IGenome mate;

	public Organism(IGenome genome) {
		this.genome = genome;
	}

	public Organism(IGenome genome, @Nullable IGenome mate) {
		this.genome = genome;
		this.mate = mate;
	}

	public Organism(NBTTagCompound compound) {
		if (compound.hasKey("Genome")) {
			genome = GeneticsAPI.geneticFactory.createGenome(getDefinition().getKaryotype(), compound.getCompoundTag("Genome"));
		} else {
			genome = getDefinition().getDefaultGenome();
		}

		if (compound.hasKey("Mate")) {
			mate = GeneticsAPI.geneticFactory.createGenome(getDefinition().getKaryotype(), compound.getCompoundTag("Mate"));
		}

		isAnalyzed = compound.getBoolean("IsAnalyzed");
	}

	@Override
	public String getIdentifier() {
		return genome.getActiveAllele(getDefinition().getTemplateType()).getRegistryName().toString();
	}

	@Override
	public IGenome getGenome() {
		return genome;
	}

	@Override
	public void mate(@Nullable IGenome mate) {
		this.mate = mate;
	}

	@Override
	public Optional<IGenome> getMate() {
		return Optional.ofNullable(mate);
	}

	@Override
	public boolean isPureBred(IGeneType geneType) {
		return genome.getActiveAllele(geneType).equals(genome.getInactiveAllele(geneType));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("Genome", genome.writeToNBT(new NBTTagCompound()));
		if (mate != null) {
			compound.setTag("Mate", mate.writeToNBT(new NBTTagCompound()));
		}
		compound.setBoolean("IsAnalyzed", isAnalyzed);
		return compound;
	}

	@Override
	public boolean analyze() {
		if (isAnalyzed) {
			return false;
		}

		isAnalyzed = true;
		return true;
	}

	@Override
	public boolean isAnalyzed() {
		return isAnalyzed;
	}
}
