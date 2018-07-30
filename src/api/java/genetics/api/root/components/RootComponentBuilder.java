package genetics.api.root.components;

import genetics.api.individual.IIndividual;
import genetics.api.root.IIndividualRoot;

public abstract class RootComponentBuilder<C extends IRootComponent, I extends IIndividual> implements IRootComponentBuilder<C> {

	protected final IIndividualRoot<I> root;

	public RootComponentBuilder(IIndividualRoot<I> root) {
		this.root = root;
	}

	@Override
	public IIndividualRoot<I> getRoot() {
		return root;
	}
}