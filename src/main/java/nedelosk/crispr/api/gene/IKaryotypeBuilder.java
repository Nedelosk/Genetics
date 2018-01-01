package nedelosk.crispr.api.gene;

public interface IKaryotypeBuilder {

	IKaryotypeBuilder add(IGeneType type);

	IKaryotype build();
}
