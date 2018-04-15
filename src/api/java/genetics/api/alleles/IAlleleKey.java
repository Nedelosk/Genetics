package genetics.api.alleles;

/**
 * The IAlleleKey is used to associate it with a specified {@link IAllele}. Every {@link IAlleleKey} can only be used for
 * one specified {@link IAllele}, but the specified {@link IAllele} can have more than one IAlleleKey.
 * <p>
 * You can register the key at the {@link IAlleleRegistry}.
 */
public interface IAlleleKey {
}
