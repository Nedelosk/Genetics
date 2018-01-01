package nedelosk.crispr.api;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IIndividual;

public interface IGeneticSystem {
	Collection<IGeneticDefinition> getDefinitions();

	Optional<IGeneticDefinition> getDefinition(String name);

	<I extends IIndividual, R extends IGeneticRoot<I, ?>> IGeneticDefinitionBuilder<I, R> createDefinition(String name, ITemplateContainer templateContainer, Function<IGeneticDefinition<I, R>, R> rootFactory);

	void registerDefinition(IGeneticDefinition definition);

	void registerTemplates(ITemplateContainer container);

	Optional<ITemplateContainer> getTemplates(IKaryotype karyotype);

	void registerGene(IGene gene, IGeneType... types);

	Optional<IGene> getGene(IGeneType type);

	Collection<IGeneType> getTypes();

	Collection<IGeneType> getTypes(IGene gene);
}
