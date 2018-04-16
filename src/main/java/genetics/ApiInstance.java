package genetics;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import genetics.api.IGeneticApiInstance;
import genetics.api.IGeneticFactory;
import genetics.api.IGeneticSaveHandler;
import genetics.api.alleles.IAlleleRegistry;
import genetics.api.gene.IGeneRegistry;
import genetics.api.root.IRootRegistry;

import genetics.alleles.AlleleRegistry;
import genetics.classification.ClassificationRegistry;
import genetics.individual.GeneticSaveHandler;

public enum ApiInstance implements IGeneticApiInstance {
	INSTANCE;

	private static final String ERROR_MESSAGE = "A method of the genetic api was called before the api reached the state at that the value of the method is present.";

	@Nullable
	public ClassificationRegistry classificationRegistry;
	@Nullable
	public AlleleRegistry alleleRegistry;
	@Nullable
	private IGeneRegistry geneRegistry;
	@Nullable
	private IRootRegistry rootRegistry;

	@Override
	public ClassificationRegistry getClassificationRegistry() {
		Preconditions.checkState(classificationRegistry != null, ERROR_MESSAGE);
		return classificationRegistry;
	}

	public void setClassificationRegistry(@Nullable ClassificationRegistry classificationRegistry) {
		this.classificationRegistry = classificationRegistry;
	}

	@Override
	public IAlleleRegistry getAlleleRegistry() {
		Preconditions.checkState(alleleRegistry != null, ERROR_MESSAGE);
		return alleleRegistry;
	}

	public void setAlleleRegistry(@Nullable AlleleRegistry alleleRegistry) {
		this.alleleRegistry = alleleRegistry;
	}

	@Override
	public IGeneRegistry getGeneRegistry() {
		Preconditions.checkState(geneRegistry != null, ERROR_MESSAGE);
		return geneRegistry;
	}

	public void setGeneRegistry(@Nullable IGeneRegistry geneRegistry) {
		this.geneRegistry = geneRegistry;
	}

	@Override
	public IRootRegistry getRootRegistry() {
		Preconditions.checkState(rootRegistry != null, ERROR_MESSAGE);
		return rootRegistry;
	}

	public void setRootRegistry(@Nullable IRootRegistry rootRegistry) {
		this.rootRegistry = rootRegistry;
	}

	@Override
	public IGeneticFactory getGeneticFactory() {
		return GeneticFactory.INSTANCE;
	}

	@Override
	public IGeneticSaveHandler getSaveHandler() {
		return GeneticSaveHandler.INSTANCE;
	}

	@Override
	public boolean isModPresent() {
		return true;
	}
}
