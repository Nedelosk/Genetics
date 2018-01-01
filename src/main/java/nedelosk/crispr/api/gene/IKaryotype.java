package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.CrisprAPI;

public interface IKaryotype {
	IGeneType[] getGeneTypes();

	default <V> boolean contains(IGene gene) {
		return CrisprAPI.geneticSystem.getTypes(gene).stream().anyMatch(this::contains);
	}

	boolean contains(IGeneType type);

	IGeneType getTemplateType();
}
