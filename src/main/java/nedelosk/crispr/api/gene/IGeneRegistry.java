package nedelosk.crispr.api.gene;

import java.util.Optional;
import java.util.function.Function;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticDefinitionBuilder;
import nedelosk.crispr.api.IGeneticRoot;
import nedelosk.crispr.api.individual.IIndividual;

public interface IGeneRegistry {
	IGeneBuilder addGene(String name);

	Optional<IGeneBuilder> getGene(String name);

	IKaryotypeBuilder createKaryotype(IGeneType templateType);

	<I extends IIndividual, R extends IGeneticRoot<I, ?>> IGeneticDefinitionBuilder<I, R> createDefinition(String name, IKaryotype karyotype, Function<IGeneticDefinition<I, R>, R> rootFactory);

	<I extends IIndividual, R extends IGeneticRoot<I, ?>> Optional<IGeneticDefinitionBuilder<I, R>> getDefinition(String name);

	<T extends Enum<T> & IGeneType> IKaryotype createKaryotype(Class<? extends T> enumClass);

}
