package genetics.api.definition;

import genetics.api.gene.IKaryotype;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismTypes;

public interface IIndividualRootFactory<I extends IIndividual> {
	IIndividualRoot<I> createRoot(IOrganismTypes<I> types, IIndividualTranslator<I> translator, ITemplateContainer templates, IKaryotype karyotype);
}
