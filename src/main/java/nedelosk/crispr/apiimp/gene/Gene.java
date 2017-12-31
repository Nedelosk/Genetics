package nedelosk.crispr.apiimp.gene;

import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.Optional;

import net.minecraft.client.resources.I18n;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleKey;
import nedelosk.crispr.api.alleles.IAlleleNameFormatter;
import nedelosk.crispr.api.alleles.IAlleleRegistry;
import nedelosk.crispr.api.alleles.INamedAlleleValue;
import nedelosk.crispr.api.gene.IGene;

public class Gene implements IGene {
	private final ImmutableSet<IAlleleKey> keys;
	private final ImmutableSet<IAllele> alleles;
	private final Class<?> valueClass;
	private final IAllele defaultAllele;
	private final String name;
	private final IAlleleNameFormatter nameFormatter;

	public Gene(ImmutableSet<IAlleleKey> keys, Class<?> valueClass, IAlleleKey defaultKey, String name, IAlleleNameFormatter<?> nameFormatter) {
		this.valueClass = valueClass;
		this.name = name;
		this.keys = keys;
		IAlleleRegistry alleleRegistry = CrisprAPI.alleleRegistry;
		ImmutableSet.Builder<IAllele> builder = ImmutableSet.builder();
		keys.forEach(k -> alleleRegistry.getAllele(k).ifPresent(builder::add));
		this.alleles = builder.build();
		Optional<IAllele> optional = getAllele(defaultKey);
		if (!optional.isPresent()) {
			throw new RuntimeException();
		}
		this.defaultAllele = optional.get();
		this.nameFormatter = nameFormatter;
	}

	@Override
	public Collection<IAllele> getVariants() {
		return alleles;
	}

	@Override
	public Collection<IAlleleKey> getKeys() {
		return keys;
	}

	@Override
	public boolean isValidAllele(IAllele<?> allele) {
		return alleles.contains(allele);
	}

	@Override
	public Optional getValue(IAlleleKey key) {
		Optional<IAllele> allele = getAllele(key);
		return allele.map(IAllele::getValue);
	}

	@Override
	public Optional<IAllele> getAllele(IAlleleKey key) {
		Optional<IAllele<?>> optional = CrisprAPI.alleleRegistry.getAllele(key);
		if (!optional.isPresent()) {
			return Optional.empty();
		}
		IAllele<?> allele = optional.get();
		if (!valueClass.isInstance(allele.getValue())) {
			return Optional.empty();
		}
		return Optional.of(allele);
	}

	@Override
	public Class<?> getValueClass() {
		return valueClass;
	}

	@Override
	public IAllele<?> getDefaultAllele() {
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

	@Override
	public String getAlleleName(IAllele<?> allele) {
		String unlocalizedName = getUnlocalizedName(allele);
		return I18n.format("allele." + unlocalizedName + ".name");
	}

	private String getUnlocalizedName(IAllele<?> allele) {
		Object value = allele.getValue();
		if (value instanceof INamedAlleleValue) {
			return ((INamedAlleleValue) value).getName();
		}
		return nameFormatter.getName(allele, this);
	}
}
