package nedelosk.crispr.api.gene;

public interface IGeneType {
	int getIndex();

	void setKaryotype(IKaryotype karyotype);

	IKaryotype getKaryotype();
}
