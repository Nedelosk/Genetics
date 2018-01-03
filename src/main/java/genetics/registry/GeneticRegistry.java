package genetics.registry;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import genetics.api.definition.IGeneticDefinition;
import genetics.api.definition.IGeneticDefinitionBuilder;
import genetics.api.definition.IGeneticRoot;
import genetics.api.gene.IGeneBuilder;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.gene.IKaryotypeBuilder;
import genetics.api.individual.IGenomeWrapper;
import genetics.api.individual.IIndividual;
import genetics.api.registry.IGeneticRegistry;

import genetics.Karyotype;
import genetics.definition.GeneticDefinitionBuilder;
import genetics.gene.Gene;

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
	public IKaryotypeBuilder createKaryotype(IGeneType templateType, String identifier) {
		return new Karyotype.Builder(templateType, identifier);
	}

	@Override
	public <T extends Enum<T> & IGeneType> IKaryotype createKaryotype(Class<? extends T> enumClass, String identifier) {
		T[] types = enumClass.getEnumConstants();
		if (types.length <= 0) {
			throw new IllegalArgumentException("The given enum class must contain at least one enum constant.");
		}
		IKaryotypeBuilder builder = new Karyotype.Builder(types[0], identifier);
		for (int i = 1; i < types.length; i++) {
			IGeneType type = types[i];
			builder.add(type);
		}
		return builder.build();
	}

	@Override
	public <I extends IIndividual, R extends IGeneticRoot<I, IGenomeWrapper>> IGeneticDefinitionBuilder<I> createDefinition(String uid, IKaryotype karyotype, Function<IGeneticDefinition<I, R>, R> rootFactory) {
		GeneticDefinitionBuilder<I, R> definitionBuilder = new GeneticDefinitionBuilder<>(uid, karyotype, rootFactory);
		definitionBuilders.put(uid, definitionBuilder);
		return definitionBuilder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I extends IIndividual> Optional<IGeneticDefinitionBuilder<I>> getDefinition(String uid) {
		return Optional.ofNullable((IGeneticDefinitionBuilder<I>) definitionBuilders.get(uid));
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