package nedelosk.crispr.apiimp;

import java.util.HashSet;
import java.util.Set;

import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.gene.IKaryotypeBuilder;

public class KaryotypeBuilder implements IKaryotypeBuilder {
	private final Set<IGeneType> geneTypes = new HashSet<>();
	private final IGeneType templateType;

	public KaryotypeBuilder(IGeneType templateType) {
		this.templateType = templateType;
		add(templateType);
	}

	@Override
	public IKaryotypeBuilder add(IGeneType type) {
		this.geneTypes.add(type);
		return this;
	}

	@Override
	public IKaryotype build() {
		return new Karyotype(geneTypes, templateType);
	}
}
