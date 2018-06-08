package genetics;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import genetics.api.IGeneticApiInstance;
import genetics.api.IGeneticFactory;
import genetics.api.IGeneticSaveHandler;
import genetics.api.alleles.IAlleleRegistry;
import genetics.api.gene.IGeneRegistry;
import genetics.api.root.IIndividualRoot;

import genetics.alleles.AlleleRegistry;
import genetics.classification.ClassificationRegistry;
import genetics.individual.GeneticSaveHandler;
import genetics.individual.RootDefinition;

public enum ApiInstance implements IGeneticApiInstance {
	INSTANCE;

	private static final String ERROR_MESSAGE = "A method of the genetic api was called before the api reached the state at that the value of the method is present.";

	@Nullable
	public ClassificationRegistry classificationRegistry;
	@Nullable
	public AlleleRegistry alleleRegistry;
	@Nullable
	private IGeneRegistry geneRegistry;

	private Map<String, RootDefinition> rootDefinitionByUID = new HashMap<>();

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
	public IGeneticFactory getGeneticFactory() {
		return GeneticFactory.INSTANCE;
	}

	@Override
	public IGeneticSaveHandler getSaveHandler() {
		return GeneticSaveHandler.INSTANCE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R extends IIndividualRoot> RootDefinition<R> getRoot(String rootUID) {
		return rootDefinitionByUID.computeIfAbsent(rootUID, uid -> new RootDefinition<>());
	}

	@Override
	public boolean isModPresent() {
		return true;
	}
}
