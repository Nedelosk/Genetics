package nedelosk.genetics.api.gene;

public interface IKaryotypeBuilder {

	IKaryotypeBuilder add(IGeneType type);

	IKaryotype build();
}
