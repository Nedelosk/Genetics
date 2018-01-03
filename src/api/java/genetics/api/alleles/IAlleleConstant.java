package genetics.api.alleles;

public interface IAlleleConstant<V> extends IAlleleData<V> {
	boolean isDefault();

	IAlleleKey getKey();

	String getName();
}
