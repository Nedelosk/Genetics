package genetics.api.root;

import genetics.api.gene.IKaryotype;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismTypes;
import genetics.api.root.translator.IIndividualTranslator;

public interface IIndividualRootFactory<I extends IIndividual, R extends IIndividualRoot<I>> {
	/**
	 * Creates a new root.
	 * <p>
	 * Used by {@link IIndividualRootBuilder} to create the root object.
	 */
	R createRoot(IOrganismTypes<I> types, IIndividualTranslator<I> translator, ITemplateContainer templates, IKaryotype karyotype);
}
