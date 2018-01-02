package nedelosk.genetics.api.registry;

import java.util.Optional;
import java.util.function.Function;

import nedelosk.genetics.api.definition.IGeneticDefinition;
import nedelosk.genetics.api.definition.IGeneticDefinitionBuilder;
import nedelosk.genetics.api.definition.IGeneticRoot;
import nedelosk.genetics.api.gene.IGeneBuilder;
import nedelosk.genetics.api.gene.IGeneType;
import nedelosk.genetics.api.gene.IKaryotype;
import nedelosk.genetics.api.gene.IKaryotypeBuilder;
import nedelosk.genetics.api.individual.IIndividual;

public interface IGeneticRegistry {
	IGeneBuilder addGene(String name);

	Optional<IGeneBuilder> getGene(String name);

	IKaryotypeBuilder createKaryotype(IGeneType templateType);

	<I extends IIndividual, R extends IGeneticRoot<I, ?>> IGeneticDefinitionBuilder<I, R> createDefinition(String name, IKaryotype karyotype, Function<IGeneticDefinition<I, R>, R> rootFactory);

	<I extends IIndividual, R extends IGeneticRoot<I, ?>> Optional<IGeneticDefinitionBuilder<I, R>> getDefinition(String name);

	<T extends Enum<T> & IGeneType> IKaryotype createKaryotype(Class<? extends T> enumClass);

}
