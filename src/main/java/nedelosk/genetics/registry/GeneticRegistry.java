package nedelosk.genetics.registry;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import nedelosk.genetics.Karyotype;
import nedelosk.genetics.api.definition.IGeneticDefinition;
import nedelosk.genetics.api.definition.IGeneticDefinitionBuilder;
import nedelosk.genetics.api.definition.IGeneticRoot;
import nedelosk.genetics.api.gene.IGeneBuilder;
import nedelosk.genetics.api.gene.IGeneType;
import nedelosk.genetics.api.gene.IKaryotype;
import nedelosk.genetics.api.gene.IKaryotypeBuilder;
import nedelosk.genetics.api.individual.IIndividual;
import nedelosk.genetics.api.registry.IGeneticRegistry;
import nedelosk.genetics.definition.GeneticDefinitionBuilder;
import nedelosk.genetics.gene.Gene;

public class GeneticRegistry implements IGeneticRegistry {
	private final HashMap<String, Gene.Builder> geneBuilders = new HashMap<>();
	private final HashMap<String, GeneticDefinitionBuilder> definitionBuilders = new HashMap<>();

	@Override
	public IGeneBuilder addGene(String name) {
		Gene.Builder registry = new Gene.Builder(name);
		geneBuilders.put(name, registry);
		return registry;
	}

	public Optional<IGeneBuilder> getGene(String name) {
		return Optional.ofNullable(geneBuilders.get(name));
	}

	@Override
	public IKaryotypeBuilder createKaryotype(IGeneType templateType) {
		return new Karyotype.Builder(templateType);
	}

	@Override
	public <I extends IIndividual, R extends IGeneticRoot<I, ?>> IGeneticDefinitionBuilder<I, R> createDefinition(String name, IKaryotype karyotype, Function<IGeneticDefinition<I, R>, R> rootFactory) {
		GeneticDefinitionBuilder<I, R> definitionBuilder = new GeneticDefinitionBuilder<>(name, karyotype, rootFactory);
		definitionBuilders.put(name, definitionBuilder);
		return definitionBuilder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I extends IIndividual, R extends IGeneticRoot<I, ?>> Optional<IGeneticDefinitionBuilder<I, R>> getDefinition(String name) {
		return Optional.ofNullable((IGeneticDefinitionBuilder<I, R>) definitionBuilders.get(name));
	}

	@Override
	public <T extends Enum<T> & IGeneType> IKaryotype createKaryotype(Class<? extends T> enumClass) {
		T[] types = enumClass.getEnumConstants();
		if (types.length <= 0) {
			throw new IllegalArgumentException("The given enum class must contain at least one enum constant.");
		}
		IKaryotypeBuilder builder = new Karyotype.Builder(types[0]);
		for (int i = 1; i < types.length; i++) {
			IGeneType type = types[i];
			builder.add(type);
		}
		return builder.build();
	}

	public GeneticSystem createSystem() {
		GeneticSystem geneticSystem = new GeneticSystem();
		geneBuilders.values().forEach(r -> {
			Set<IGeneType> types = r.getTypes();
			geneticSystem.registerGene(r.createGene(), types.toArray(new IGeneType[types.size()]));
		});
		definitionBuilders.values().forEach(b -> geneticSystem.registerDefinition(b.create()));
		return geneticSystem;
	}
}