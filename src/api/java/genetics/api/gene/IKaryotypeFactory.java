package genetics.api.gene;

public interface IKaryotypeFactory {
	/**
	 * Creates a IKaryotypeBuilder.
	 *
	 * @param templateType The template type of the karyotype
	 * @return A IKaryotypeBuilder that can be used to build {@link IKaryotype}.
	 */
	IKaryotypeBuilder createKaryotype(IChromosomeType templateType, String identifier);

	/**
	 * Creates a IKaryotype
	 *
	 * @param enumClass A enum that implements {@link IChromosomeType}.
	 */
	<T extends Enum<T> & IChromosomeType> IKaryotype createKaryotype(Class<? extends T> enumClass, String identifier);
}
