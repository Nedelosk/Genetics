package genetics.api.mutation;

import genetics.api.root.components.IRootComponentBuilder;

public interface IMutationRegistry<M extends IMutation> extends IRootComponentBuilder<IMutationContainer<M>> {
	boolean registerMutation(M mutation);
}
