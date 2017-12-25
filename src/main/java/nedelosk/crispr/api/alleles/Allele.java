package nedelosk.crispr.api.alleles;

import java.util.function.Supplier;

import net.minecraft.client.resources.I18n;

import net.minecraftforge.registries.IForgeRegistryEntry;

public final class Allele<V> extends IForgeRegistryEntry.Impl<Allele<V>> implements Supplier<V> {
	private final boolean dominant;
	private final String unlocalizedName;
	private final V value;
	private final IAlleleKey key;

	public Allele(IAlleleKey key, boolean dominant, String unlocalizedName, V value) {
		this.key = key;
		this.dominant = dominant;
		this.unlocalizedName = unlocalizedName;
		this.value = value;
	}

	public boolean isDominant() {
		return dominant;
	}

	public String getLocalizedName() {
		return I18n.format(unlocalizedName);
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public IAlleleKey key() {
		return key;
	}

	@Override
	public V get() {
		return value;
	}
}
