package nedelosk.crispr.apiimp.gene;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticDefinitionBuilder;
import nedelosk.crispr.api.IGeneticRoot;
import nedelosk.crispr.api.gene.IGeneBuilder;
import nedelosk.crispr.api.gene.IGeneRegistry;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.gene.IKaryotypeBuilder;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.apiimp.GeneticDefinitionBuilder;
import nedelosk.crispr.apiimp.GeneticSystem;
import nedelosk.crispr.apiimp.KaryotypeBuilder;
import nedelosk.crispr.apiimp.alleles.GeneBuilder;

public class GeneRegistry implements IGeneRegistry {
	private final HashMap<String, GeneBuilder> geneBuilders = new HashMap<>();
	private final HashMap<String, IGeneticDefinition> definitions = new HashMap<>();
	private final HashMap<String, IGeneticDefinitionBuilder> definitionBuilders = new HashMap<>();

	@Override
	public IGeneBuilder addGene(String name) {
		GeneBuilder registry = new GeneBuilder(name);
		geneBuilders.put(name, registry);
		return registry;
	}

	public Optional<IGeneBuilder> getGene(String name) {
		return Optional.ofNullable(geneBuilders.get(name));
	}

	@Override
	public IKaryotypeBuilder createKaryotype(IGeneType templateType) {
		return new KaryotypeBuilder(templateType);
	}

	@Override
	public <I extends IIndividual, R extends IGeneticRoot<I, ?>> IGeneticDefinitionBuilder<I, R> createDefinition(String name, IKaryotype karyotype, Function<IGeneticDefinition<I, R>, R> rootFactory) {
		IGeneticDefinitionBuilder<I, R> definitionBuilder = new GeneticDefinitionBuilder<>(name, karyotype, rootFactory);
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
		IKaryotypeBuilder builder = new KaryotypeBuilder(types[0]);
		for (int i = 1; i < types.length; i++) {
			IGeneType type = types[i];
			builder.add(type);
		}
		return builder.build();
	}

	public GeneticSystem createGeneticSystem() {
		GeneticSystem geneticSystem = new GeneticSystem();
		geneBuilders.values().forEach(r -> {
			Set<IGeneType> types = r.getTypes();
			geneticSystem.registerGene(r.createGene(), types.toArray(new IGeneType[types.size()]));
		});
		definitions.values().forEach(geneticSystem::registerDefinition);
		return geneticSystem;
	}
}
