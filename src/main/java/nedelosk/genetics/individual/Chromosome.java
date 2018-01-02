package nedelosk.genetics.individual;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Optional;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import nedelosk.genetics.api.GeneticsAPI;
import nedelosk.genetics.api.alleles.IAllele;
import nedelosk.genetics.api.definition.ITemplateContainer;
import nedelosk.genetics.api.gene.IGene;
import nedelosk.genetics.api.gene.IGeneType;
import nedelosk.genetics.api.individual.IChromosome;

@SuppressWarnings("unchecked")
@Immutable
public class Chromosome<V> implements IChromosome<V> {

	private static final String ACTIVE_ALLELE_TAG = "UID0";
	private static final String INACTIVE_ALLELE_TAG = "UID1";
	private final IAllele<V> active;
	private final IAllele<V> inactive;
	private final IGeneType type;

	private Chromosome(IAllele<V> allele, IGeneType type) {
		this.active = inactive = allele;
		this.type = type;
	}

	private Chromosome(IAllele<V> active, IAllele<V> inactive, IGeneType type) {
		this.active = active;
		this.inactive = inactive;
		this.type = type;
	}

	public static Chromosome create(@Nullable ResourceLocation primarySpeciesUid, @Nullable ResourceLocation secondarySpeciesUid, IGeneType geneType, NBTTagCompound nbt) {
		IAllele primary = GeneticsAPI.alleleRegistry.getAllele(nbt.getString(ACTIVE_ALLELE_TAG)).orElse(null);
		IAllele secondary = GeneticsAPI.alleleRegistry.getAllele(nbt.getString(INACTIVE_ALLELE_TAG)).orElse(null);
		return create(primarySpeciesUid, secondarySpeciesUid, geneType, primary, secondary);
	}

	public static <V> Chromosome<V> create(@Nullable ResourceLocation primaryTemplateIdentifier, @Nullable ResourceLocation secondaryTemplateIdentifier, IGeneType type, @Nullable IAllele<V> firstAllele, @Nullable IAllele<V> secondAllele) {
		return create(getStringOrNull(primaryTemplateIdentifier), getStringOrNull(secondaryTemplateIdentifier), type, firstAllele, secondAllele);
	}

	public static <V> Chromosome<V> create(@Nullable String primaryTemplateIdentifier, @Nullable String secondaryTemplateIdentifier, IGeneType type, @Nullable IAllele<V> firstAllele, @Nullable IAllele<V> secondAllele) {
		firstAllele = validateAllele(primaryTemplateIdentifier, type, firstAllele);
		secondAllele = validateAllele(secondaryTemplateIdentifier, type, secondAllele);

		return new Chromosome<>(firstAllele, secondAllele, type);
	}

	@Nullable
	private static String getStringOrNull(@Nullable ResourceLocation location) {
		return location != null ? location.toString() : null;
	}

	private static <V> IAllele<V> validateAllele(@Nullable String templateIdentifier, IGeneType type, @Nullable IAllele<V> allele) {
		Optional<IGene> optional = GeneticsAPI.geneticSystem.getGene(type);
		if (!optional.isPresent()) {
			return getDefaultAllele(null, templateIdentifier, type);
		}
		IGene gene = optional.get();
		if (allele == null || gene.isValidAllele(allele)) {
			return getDefaultAllele(gene, templateIdentifier, type);
		}
		return allele;
	}

	private static <V> IAllele<V> getDefaultAllele(@Nullable IGene gene, @Nullable String templateIdentifier, IGeneType type) {
		ITemplateContainer container = type.getDefinition();
		if (gene == null) {
			return container.getDefaultTemplate().get(type);
		}
		IAllele[] template = null;

		if (templateIdentifier != null) {
			template = container.getTemplate(templateIdentifier);
		}

		if (template == null) {
			return (IAllele<V>) gene.getDefaultAllele();
		}

		return template[type.getIndex()];
	}

	public static Chromosome create(IAllele allele, IGeneType geneType) {
		return new Chromosome(allele, geneType);
	}

	static Optional<IAllele<?>> getActiveAllele(NBTTagCompound chromosomeNBT) {
		String alleleUid = chromosomeNBT.getString(Chromosome.ACTIVE_ALLELE_TAG);
		return GeneticsAPI.alleleRegistry.getAllele(alleleUid);
	}

	static Optional<IAllele<?>> getInactiveAllele(NBTTagCompound chromosomeNBT) {
		String alleleUid = chromosomeNBT.getString(Chromosome.ACTIVE_ALLELE_TAG);
		return GeneticsAPI.alleleRegistry.getAllele(alleleUid);
	}

	@Override
	public IGeneType getGeneKey() {
		return type;
	}

	@Override
	public IAllele<V> getActiveAllele() {
		return active;
	}

	@Override
	public IAllele<V> getInactiveAllele() {
		return inactive;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setString(ACTIVE_ALLELE_TAG, active.getRegistryName().toString());
		nbttagcompound.setString(INACTIVE_ALLELE_TAG, inactive.getRegistryName().toString());
		return nbttagcompound;
	}

	@Override
	public IChromosome inheritChromosome(Random rand, IChromosome other) {
		IAllele firstChoice;
		if (rand.nextBoolean()) {
			firstChoice = getActiveAllele();
		} else {
			firstChoice = getInactiveAllele();
		}

		IAllele secondChoice;
		if (rand.nextBoolean()) {
			secondChoice = other.getActiveAllele();
		} else {
			secondChoice = other.getInactiveAllele();
		}

		if (rand.nextBoolean()) {
			return Chromosome.create(firstChoice, secondChoice, type);
		} else {
			return Chromosome.create(secondChoice, firstChoice, type);
		}
	}

	public static Chromosome create(IAllele firstAllele, IAllele secondAllele, IGeneType geneType) {
		firstAllele = getActiveAllele(firstAllele, secondAllele);
		secondAllele = getInactiveAllele(firstAllele, secondAllele);
		return new Chromosome(firstAllele, secondAllele, geneType);
	}

	private static IAllele getActiveAllele(IAllele firstAllele, IAllele secondAllele) {
		if (firstAllele.isDominant()) {
			return firstAllele;
		}
		if (secondAllele.isDominant()) {
			return secondAllele;
		}
		// Leaves only the case of both being recessive
		return firstAllele;
	}

	private static IAllele getInactiveAllele(IAllele firstAllele, IAllele secondAllele) {
		if (!secondAllele.isDominant()) {
			return secondAllele;
		}
		if (!firstAllele.isDominant()) {
			return firstAllele;
		}
		// Leaves only the case of both being dominant
		return secondAllele;
	}

	@Override
	public String toString() {
		return "{" + active + ", " + inactive + "}";
	}
}
