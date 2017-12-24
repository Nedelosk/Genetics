package nedelosk.crispr.apiimp.gene;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.resources.I18n;

import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.gene.IGene;

public class Gene<V> implements IGene<V> {
	private final Set<Allele<V>> alleles;
	private final Set<V> values;
	private final Class<? extends V> valueClass;
	private final Allele<V> defaultAllele;
	private final String name;

	public Gene(Set<Allele<V>> alleles, Class<? extends V> valueClass, Allele<V> defaultAllele, String name) {
		this.alleles = alleles;
		this.valueClass = valueClass;
		this.defaultAllele = defaultAllele;
		this.name = name;
		this.values = new HashSet<>();
		alleles.forEach(allele -> values.add(allele.get()));
	}

	@Override
	public Collection<Allele<V>> getValidAlleles() {
		return alleles;
	}

	@Override
	public Collection<V> getValidValues() {
		return values;
	}

	@Override
	public Class<? extends V> getValueClass() {
		return valueClass;
	}

	@Override
	public Allele<V> getDefaultAllele() {
		return defaultAllele;
	}

	@Override
	public String getShortName() {
		return I18n.format(getUnlocalizedShortName());
	}

	@Override
	public String getUnlocalizedShortName() {
		return "gene." + name + ".short.name";
	}

	@Override
	public String getLocalizedName() {
		return I18n.format(getUnlocalizedName());
	}

	@Override
	public String getUnlocalizedName() {
		return "gene." + name + ".name";
	}
}
