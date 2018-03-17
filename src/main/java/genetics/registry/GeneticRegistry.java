package genetics.registry;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import genetics.api.definition.IIndividualDefinition;
import genetics.api.definition.IIndividualRoot;
import genetics.api.definition.IOptionalDefinition;
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IGeneBuilder;
import genetics.api.gene.IKaryotype;
import genetics.api.gene.IKaryotypeBuilder;
import genetics.api.individual.IGenomeWrapper;
import genetics.api.individual.IIndividual;
import genetics.api.registry.IGeneticRegistry;

import genetics.Karyotype;
import genetics.definition.IndividualDefinitionBuilder;
import genetics.gene.Gene;
import genetics.individual.OptionalDefinition;

public class GeneticRegistry implements IGeneticRegistry {
	private final HashMap<String, Gene.Builder> geneBuilders = new HashMap<>();
	private final HashMap<String, OptionalDefinition> definitionBuilders = new HashMap<>();

	@Override
	public IGeneBuilder addGene(String name) {
		if (geneBuilders.containsKey(name)) {
			return geneBuilders.get(name);
		}
		Gene.Builder registry = new Gene.Builder(name);
		geneBuilders.put(name, registry);
		return registry;
	}

	public Optional<IGeneBuilder> getGene(String name) {
		return Optional.ofNullable(geneBuilders.get(name));
	}

	@Override
	public IKaryotypeBuilder createKaryotype(IChromosomeType templateType, String identifier) {
		return new Karyotype.Builder(templateType, identifier);
	}

	@Override
	public <T extends Enum<T> & IChromosomeType> IKaryotype createKaryotype(Class<? extends T> enumClass, String identifier) {
		T[] types = enumClass.getEnumConstants();
		if (types.length <= 0) {
			throw new IllegalArgumentException("The given enum class must contain at least one enum constant.");
		}
		IKaryotypeBuilder builder = new Karyotype.Builder(types[0], identifier);
		for (int i = 1; i < types.length; i++) {
			IChromosomeType type = types[i];
			builder.add(type);
		}
		return builder.build();
	}

	@Override
	public <I extends IIndividual, R extends IIndividualRoot<I, IGenomeWrapper>> IOptionalDefinition<I, R> createDefinition(String uid, IKaryotype karyotype, Function<IIndividualDefinition<I, R>, R> rootFactory) {
		OptionalDefinition<I, R> definition = new OptionalDefinition<>(uid, new IndividualDefinitionBuilder<>(uid, karyotype, rootFactory));
		definitionBuilders.put(uid, definition);
		return definition;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I extends IIndividual, R extends IIndividualRoot<I, IGenomeWrapper>> IOptionalDefinition<I, R> getDefinition(String uid) {
		IOptionalDefinition<I, R> definition = (IOptionalDefinition<I, R>) definitionBuilders.get(uid);
		if (definition == null) {
			definition = new OptionalDefinition<>(uid);
		}
		return definition;
	}

	public GeneticSystem createSystem() {
		GeneticSystem geneticSystem = new GeneticSystem();
		geneBuilders.values().forEach(r -> {
			Set<IChromosomeType> types = r.getTypes();
			geneticSystem.registerGene(r.createGene(), types.toArray(new IChromosomeType[types.size()]));
		});
		definitionBuilders.values().forEach(b -> {
			b.build();
			Optional<IIndividualDefinition> definition = b.maybeDefinition();
			definition.ifPresent(geneticSystem::registerDefinition);
		});
		return geneticSystem;
	}
}
