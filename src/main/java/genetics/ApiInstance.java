package genetics;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;

import genetics.api.IGeneticApiInstance;
import genetics.api.IGeneticFactory;
import genetics.api.IGeneticSaveHandler;
import genetics.api.definition.IDefinitionRegistry;
import genetics.api.gene.IGeneRegistry;
import genetics.api.registry.IAlleleRegistry;

import genetics.alleles.AlleleRegistry;
import genetics.individual.GeneticSaveHandler;

public enum ApiInstance implements IGeneticApiInstance {
	INSTANCE;

	private static final String ERROR_MESSAGE = "A method of the genetic api was called before the api reached the state at that the value of the method is present.";

	@Nullable
	public AlleleRegistry alleleRegistry;
	@Nullable
	private IGeneRegistry geneRegistry;
	@Nullable
	private IDefinitionRegistry definitionRegistry;

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
	public IDefinitionRegistry getDefinitionRegistry() {
		Preconditions.checkState(definitionRegistry != null, ERROR_MESSAGE);
		return definitionRegistry;
	}

	public void setDefinitionRegistry(@Nullable IDefinitionRegistry definitionRegistry) {
		this.definitionRegistry = definitionRegistry;
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
