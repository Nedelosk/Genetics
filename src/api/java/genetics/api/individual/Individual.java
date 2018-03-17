package genetics.api.individual;

import javax.annotation.Nullable;
import java.util.Optional;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.GeneticsAPI;
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IKaryotype;

import genetics.individual.IndividualBuilder;

/**
 * A simple abstract implementation of {@link IIndividual}.
 */
public abstract class Individual implements IIndividual {
	protected static final String GENOME_KEY = "Genome";
	protected static final String MATE_KEY = "Mate";
	protected static final String ANALYZED_KEY = "IsAnalyzed";

	protected final IGenome genome;
	protected boolean isAnalyzed = false;
	@Nullable
	protected IGenome mate;

	public Individual(IGenome genome) {
		this.genome = genome;
	}

	public Individual(IGenome genome, @Nullable IGenome mate) {
		this.genome = genome;
		this.mate = mate;
	}

	public Individual(NBTTagCompound compound) {
		IKaryotype karyotype = getDefinition().getKaryotype();
		if (compound.hasKey(GENOME_KEY)) {
			genome = GeneticsAPI.geneticFactory.createGenome(karyotype, compound.getCompoundTag(GENOME_KEY));
		} else {
			genome = karyotype.getDefaultGenome();
		}

		if (compound.hasKey(MATE_KEY)) {
			mate = GeneticsAPI.geneticFactory.createGenome(karyotype, compound.getCompoundTag(MATE_KEY));
		}

		isAnalyzed = compound.getBoolean(ANALYZED_KEY);
	}

	@Override
	public String getIdentifier() {
		return genome.getActiveAllele(getDefinition().getKaryotype().getTemplateType()).getRegistryName().toString();
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
	public boolean isPureBred(IChromosomeType geneType) {
		return genome.isPureBred(geneType);
	}

	@Override
	public boolean isGeneticEqual(IIndividual other) {
		return genome.isGeneticEqual(other.getGenome());
	}

	@Override
	public void onBuild(IIndividual otherIndividual) {
		if (otherIndividual.isAnalyzed()) {
			analyze();
		}
		Optional<IGenome> otherMate = otherIndividual.getMate();
		otherMate.ifPresent(this::mate);
	}

	@Override
	public IIndividualBuilder toBuilder() {
		return new IndividualBuilder<>(this);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag(GENOME_KEY, genome.writeToNBT(new NBTTagCompound()));
		if (mate != null) {
			compound.setTag(MATE_KEY, mate.writeToNBT(new NBTTagCompound()));
		}
		compound.setBoolean(ANALYZED_KEY, isAnalyzed);
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
