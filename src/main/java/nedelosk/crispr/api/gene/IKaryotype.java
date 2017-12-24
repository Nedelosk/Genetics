package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;

public interface IKaryotype {
	IGeneKey[] getKeys();

	default <V> boolean contains(IGene<V> gene) {
		return CrisprAPI.registry.getKeys(gene).stream().anyMatch(this::contains);
	}

	boolean contains(IGeneKey key);

	IAlleleTemplate getDefaultTemplate();

	IAlleleTemplateBuilder createTemplate();

	IAlleleTemplateBuilder createTemplate(Allele[] alleles);
}
