package nedelosk.crispr.apiimp;

import java.util.Arrays;
import java.util.Set;

import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;

public class Karyotype implements IKaryotype {
	private final IGeneType[] geneTypes;
	private final IGeneType templateType;

	Karyotype(Set<IGeneType> geneTypes, IGeneType templateType) {
		this.templateType = templateType;
		this.geneTypes = new IGeneType[geneTypes.size()];
		for (IGeneType key : geneTypes) {
			this.geneTypes[key.getIndex()] = key;
		}
	}

	@Override
	public IGeneType[] getGeneTypes() {
		return geneTypes;
	}

	@Override
	public IGeneType getTemplateType() {
		return templateType;
	}

	@Override
	public boolean contains(IGeneType type) {
		return Arrays.asList(geneTypes).contains(type);
	}
}
