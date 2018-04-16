package genetics.api.classification;

import genetics.api.alleles.IAllele;

public interface IBranchDefinition {
	IAllele[] getTemplate();

	IClassification getBranch();
}
