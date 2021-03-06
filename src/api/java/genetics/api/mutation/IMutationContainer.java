package genetics.api.mutation;

import java.util.Collection;
import java.util.List;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleSpecies;
import genetics.api.individual.IChromosomeType;
import genetics.api.root.components.IRootComponent;

public interface IMutationContainer<M extends IMutation> extends IRootComponent {

	/**
	 * @return All registered mutations.
	 */
	List<? extends M> getMutations(boolean shuffle);

	/**
	 * @param other Allele to match mutations against.
	 * @return All registered mutations the given allele is part of.
	 */
	List<? extends M> getCombinations(IAlleleSpecies other);

	/**
	 * @param other Allele to match mutations against.
	 * @return All registered mutations the give allele is resolute of.
	 */
	List<? extends M> getResultantMutations(IAllele other);

	/**
	 * @return all possible mutations that result from breeding two species
	 */
	List<? extends M> getCombinations(IAlleleSpecies parentFirst, IAlleleSpecies parentSecond, boolean shuffle);

	Collection<? extends M> getPaths(IAllele result, IChromosomeType geneType);
}
