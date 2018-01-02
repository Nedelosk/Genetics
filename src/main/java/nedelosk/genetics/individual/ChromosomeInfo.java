package nedelosk.genetics.individual;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;

import nedelosk.genetics.api.gene.IGeneType;
import nedelosk.genetics.api.individual.IChromosome;

public class ChromosomeInfo {
	public final IGeneType geneKey;
	@Nullable
	public IChromosome<?> chromosome;
	@Nullable
	public ResourceLocation activeSpeciesUid;
	@Nullable
	public ResourceLocation inactiveSpeciesUid;

	public ChromosomeInfo(IGeneType geneKey) {
		this.geneKey = geneKey;
	}

	public ChromosomeInfo setChromosome(@Nullable IChromosome<?> chromosome) {
		this.chromosome = chromosome;
		return this;
	}

	public void setSpeciesInfo(@Nullable ResourceLocation activeSpeciesUid, @Nullable ResourceLocation inactiveSpeciesUid) {
		this.activeSpeciesUid = activeSpeciesUid;
		this.inactiveSpeciesUid = inactiveSpeciesUid;
	}
}
