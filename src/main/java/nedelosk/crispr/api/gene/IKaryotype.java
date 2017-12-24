package nedelosk.crispr.api.gene;

import java.util.Collection;

public interface IKaryotype {
	Collection<IGene> getGenes();

	int getGeneIndex(IGene gene);
}
