package nedelosk.crispr.api.gene;

import java.util.Optional;

import nedelosk.crispr.api.ITemplateRegistry;

public interface IGeneRegistry {
	IGeneBuilder addGene(String name);

	Optional<IGeneBuilder> getGene(String name);

	IKaryotypeBuilder createKaryotype(IGeneType templateType);

	ITemplateRegistry createRegistry(IKaryotype karyotype);

	Optional<ITemplateRegistry> getRegistry(IKaryotype karyotype);

	<T extends Enum<T> & IGeneType> IKaryotype createKaryotype(Class<? extends T> enumClass);

}
