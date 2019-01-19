package genetics.api.individual;

import genetics.api.alleles.IAlleleSpecies;
import genetics.api.root.components.ComponentKey;
import genetics.api.root.components.IRootComponentBuilder;

public interface IGeneticDefinition extends ITemplateProvider {

	default <B extends IRootComponentBuilder> void onComponent(ComponentKey<?, B> key, B builder){
	}

	/**
	 * @return A copy of the genome that the individual contains.
	 */
	IGenome getGenome();

	IAlleleSpecies getSpecies();

	/**
	 * @return Creates a instance of the {@link IIndividual} that contains the {@link #getGenome()}.
	 */
	IIndividual createIndividual();
}
