package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.ITemplateContainer;

public interface IGeneType {
	int getIndex();

	default IKaryotype getKaryotype() {
		return getContainer().getKaryotype();
	}

	ITemplateContainer getContainer();
}
