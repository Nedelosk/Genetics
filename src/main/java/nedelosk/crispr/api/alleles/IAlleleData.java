package nedelosk.crispr.api.alleles;

public interface IAlleleData<V> {
	V getValue();

	boolean isDominant();

	String getName();
}
