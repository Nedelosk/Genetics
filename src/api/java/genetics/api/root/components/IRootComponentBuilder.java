package genetics.api.root.components;

import genetics.api.root.IIndividualRoot;

public interface IRootComponentBuilder<C extends IRootComponent> {
	C create();

	IIndividualRoot getRoot();
}
