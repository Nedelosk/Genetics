package genetics.api.root.components;

import genetics.api.individual.IIndividual;
import genetics.api.root.IIndividualRoot;

public interface IRootComponentFactory<I extends IIndividual, B extends IRootComponentBuilder> {

	B create(IIndividualRoot<I> root);
}
