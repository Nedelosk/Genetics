package genetics.api.individual;

import genetics.api.root.components.ComponentKey;
import genetics.api.root.components.IRootComponentBuilder;

public interface IGeneticDefinition extends ITemplateProvider {

	<B extends IRootComponentBuilder> void onComponent(ComponentKey<?, B> key, B builder);

	/**
	 * @return A copy of the genome that the individual contains.
	 */
	IGenome getGenome();

	/**
	 * @return Creates a instance of the {@link IIndividual} that contains the {@link #getGenome()}.
	 */
	IIndividual createIndividual();
}
