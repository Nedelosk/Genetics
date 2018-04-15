package genetics.api.mutations;

import java.util.Collection;
import java.util.List;

import genetics.api.alleles.IAllele;
import genetics.api.gene.IChromosomeType;
import genetics.api.individual.IIndividual;
import genetics.api.root.IIndividualRoot;

public interface IMutationRoot<I extends IIndividual, M extends IMutation> extends IIndividualRoot<I> {
	/**
	 * Use to register mutations.
	 */
	void registerMutation(M mutation);

	/**
	 * @return All registered mutations.
	 */
	List<M> getMutations(boolean shuffle);

	/**
	 * @param other Allele to match mutations against.
	 * @return All registered mutations the given allele is part of.
	 */
	List<M> getCombinations(IAllele other);

	/**
	 * @param other Allele to match mutations against.
	 * @return All registered mutations the give allele is resolute of.
	 */
	List<M> getResultantMutations(IAllele other);

	/**
	 * @return all possible mutations that result from breeding two species
	 */
	List<M> getCombinations(IAllele parentFirst, IAllele parentSecond, boolean shuffle);

	Collection<M> getPaths(IAllele result, IChromosomeType geneType);
}
