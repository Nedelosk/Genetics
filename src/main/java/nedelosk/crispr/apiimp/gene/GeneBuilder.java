package nedelosk.crispr.apiimp.gene;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneBuilder;
import nedelosk.crispr.api.gene.IGeneKey;

public class GeneBuilder<V> implements IGeneBuilder<V> {
	private final Class<? extends V> valueClass;
	private final Set<Allele<V>> alleles = new HashSet<>();
	@Nullable
	private Allele<V> defaultAllele;
	@Nullable
	private String name;

	GeneBuilder(Class<? extends V> valueClass) {
		this.valueClass = valueClass;
	}

	@Override
	public Allele<V> registerAllele(String unlocalizedName, V value, boolean dominant) {
		return new Allele<>(dominant, unlocalizedName, value);
	}

	@Override
	public void setDefaultAllele(Allele<V> allele) {
		this.defaultAllele = allele;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public final IGene<V> register(IGeneKey... keys) {
		Preconditions.checkNotNull(defaultAllele);
		Preconditions.checkNotNull(name);
		Gene<V> gene = new Gene<>(alleles, valueClass, defaultAllele, name);
		CrisprAPI.geneRegistry.registerGene(gene, keys);
		return gene;
	}
}
