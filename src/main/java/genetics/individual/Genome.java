package genetics.individual;

import com.google.common.base.MoreObjects;

import java.util.Arrays;
import java.util.Optional;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.GeneticsAPI;
import genetics.api.alleles.IAllele;
import genetics.api.gene.IGene;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;

public final class Genome implements IGenome {
	private final IChromosome[] chromosomes;
	private final IKaryotype karyotype;

	public Genome(IKaryotype karyotype, NBTTagCompound compound) {
		this.karyotype = karyotype;
		this.chromosomes = GeneticSaveHandler.INSTANCE.readTag(karyotype, compound);
	}

	public Genome(IKaryotype karyotype, IChromosome[] chromosomes) {
		this.karyotype = karyotype;
		checkChromosomes(chromosomes);
		this.chromosomes = chromosomes;
	}

	@SuppressWarnings("all")
	private void checkChromosomes(IChromosome[] chromosomes) {
		if (chromosomes.length != karyotype.getGeneTypes().length) {
			String message = String.format("Tried to create a genome for '%s' from an invalid chromosome template.\n%s", karyotype.getIdentifier(), chromosomesToString(chromosomes));
			throw new IllegalArgumentException(message);
		}

		IGeneType[] geneTypes = karyotype.getGeneTypes();
		for (int i = 0; i < geneTypes.length; i++) {
			IGeneType geneType = geneTypes[i];
			Optional<IGene> optionalGene = GeneticsAPI.geneticSystem.getGene(geneType);
			if (!optionalGene.isPresent()) {
				String message = String.format("Tried to create a genome for '%s' from an invalid chromosome template. " +
					"Unregistered gene type '%s'.\n%s", karyotype.getIdentifier(), geneType.getName(), chromosomesToString(chromosomes));
				throw new IllegalArgumentException(message);
			}
			IGene gene = optionalGene.get();
			IChromosome chromosome = chromosomes[i];
			if (chromosome == null) {
				String message = String.format("Tried to create a genome for '%s' from an invalid chromosome template. " +
					"Missing chromosome '%s'.\n%s", karyotype.getIdentifier(), geneType.getName(), chromosomesToString(chromosomes));
				throw new IllegalArgumentException(message);
			}


			IAllele primary = chromosome.getActiveAllele();
			if (primary == null) {
				String message = String.format("Tried to create a genome for '%s' from an invalid chromosome template. " +
					"Missing active allele for '%s'.\n%s", karyotype.getIdentifier(), geneType.getName(), chromosomesToString(chromosomes));
				throw new IllegalArgumentException(message);
			}

			IAllele secondary = chromosome.getInactiveAllele();
			if (secondary == null) {
				String message = String.format("Tried to create a genome for '%s' from an invalid chromosome template. " +
					"Missing inactive allele for '%s'.\n%s", karyotype.getIdentifier(), geneType.getName(), chromosomesToString(chromosomes));
				throw new IllegalArgumentException(message);
			}

			if (!gene.isValidAllele(primary)) {
				String message = String.format("Tried to create a genome for '%s' from an invalid chromosome template. " +
					"Incorrect type for active allele '%s'.\n%s.", karyotype.getIdentifier(), geneType.getName(), chromosomesToString(chromosomes));
				throw new IllegalArgumentException(message);
			}

			if (!gene.isValidAllele(secondary)) {
				String message = String.format("Tried to create a genome for '%s' from an invalid chromosome template. " +
					"Incorrect type for inaktive allele '%s'.\n%s.", karyotype.getIdentifier(), geneType.getName(), chromosomesToString(chromosomes));
				throw new IllegalArgumentException(message);
			}
		}
	}

	private String chromosomesToString(IChromosome[] chromosomes) {
		StringBuilder stringBuilder = new StringBuilder();
		IGeneType[] geneTypes = karyotype.getGeneTypes();
		for (int i = 0; i < chromosomes.length; i++) {
			IGeneType geneType = geneTypes[i];
			IChromosome chromosome = chromosomes[i];
			stringBuilder.append(geneType.getName()).append(": ").append(chromosome).append("\n");
		}

		return stringBuilder.toString();
	}

	// / INFORMATION RETRIEVAL
	@Override
	public IChromosome[] getChromosomes() {
		return Arrays.copyOf(chromosomes, chromosomes.length);
	}

	@Override
	public IAllele getActiveAllele(IGeneType geneType) {
		IChromosome chromosome = getChromosome(geneType);
		return chromosome.getActiveAllele();
	}

	@Override
	public IAllele getInactiveAllele(IGeneType geneType) {
		IChromosome chromosome = getChromosome(geneType);
		return chromosome.getInactiveAllele();
	}

	@Override
	public <V> V getActiveValue(IGeneType geneType, Class<? extends V> valueClass) {
		Object value = getActiveAllele(geneType).getValue();
		if(valueClass.isInstance(valueClass)){
			return valueClass.cast(value);
		}
		throw new IllegalArgumentException();
	}

	@Override
	public <V> V getInactiveValue(IGeneType geneType, Class<? extends V> valueClass) {
		Object value = getInactiveAllele(geneType).getValue();
		if(valueClass.isInstance(valueClass)){
			return valueClass.cast(value);
		}
		throw new IllegalArgumentException();
	}

	@Override
	public IChromosome getChromosome(IGeneType geneType) {
		return chromosomes[geneType.getIndex()];
	}

	@Override
	public IKaryotype getKaryotype() {
		return karyotype;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return GeneticSaveHandler.INSTANCE.writeTag(chromosomes, karyotype, compound);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IGenome)) {
			return false;
		}
		IGenome other = (IGenome) obj;

		IChromosome[] genetics = other.getChromosomes();
		if (chromosomes.length != genetics.length) {
			return false;
		}

		for (int i = 0; i < chromosomes.length; i++) {
			IChromosome chromosome = chromosomes[i];
			IChromosome otherChromosome = genetics[i];
			if (chromosome == null && otherChromosome == null) {
				continue;
			}
			if (chromosome == null || otherChromosome == null) {
				return false;
			}

			if (!chromosome.getActiveAllele().equals(otherChromosome.getActiveAllele())) {
				return false;
			}
			if (!chromosome.getInactiveAllele().equals(otherChromosome.getInactiveAllele())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		MoreObjects.ToStringHelper toStringHelper = MoreObjects.toStringHelper(this);
		int i = 0;
		for (IChromosome chromosome : chromosomes) {
			toStringHelper.add(String.valueOf(i++), chromosome);
		}
		return toStringHelper.toString();
	}
}
