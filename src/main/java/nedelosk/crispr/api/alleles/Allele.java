package nedelosk.crispr.api.alleles;

import net.minecraft.client.resources.I18n;

import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * A default implementation for a simple allele that only contains a value.
 */
public final class Allele<V> extends IForgeRegistryEntry.Impl<IAllele<?>> implements IAllele<V> {
	private final V value;
	private final boolean dominant;
	private final String unlocalizedName;

	public Allele(V value, boolean dominant, String unlocalizedName) {
		this.value = value;
		this.dominant = dominant;
		this.unlocalizedName = unlocalizedName;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public boolean isDominant() {
		return dominant;
	}

	@Override
	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	@Override
	public String getLocalizedName() {
		return I18n.format(unlocalizedName);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IAllele)) {
			return false;
		}
		IAllele otherAllele = (IAllele) obj;
		return value.equals(otherAllele.getValue()) && dominant == otherAllele.isDominant();
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + value.hashCode();
		hash = hash * 31 + Boolean.hashCode(dominant);
		return hash;
	}
}
