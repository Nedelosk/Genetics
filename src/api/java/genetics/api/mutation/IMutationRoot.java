package genetics.api.mutation;

import genetics.api.individual.IIndividual;
import genetics.api.root.IIndividualRoot;

public interface IMutationRoot<I extends IIndividual, M extends IMutation> extends IIndividualRoot<I> {

	IMutationContainer<M> getMutations();
}
