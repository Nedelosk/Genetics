package genetics.definition;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

import genetics.api.definition.IDefinitionFactory;
import genetics.api.definition.IIndividualDefinition;
import genetics.api.definition.IIndividualDefinitionBuilder;
import genetics.api.definition.IIndividualRoot;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IIndividual;

public class DefinitionFactory implements IDefinitionFactory {
	private final HashMap<String, IndividualDefinitionBuilder> definitionBuilders = new HashMap<>();

	@Override
	public <I extends IIndividual, R extends IIndividualRoot<I>> IIndividualDefinitionBuilder<I, R> createDefinition(String uid, IKaryotype karyotype, Function<IIndividualDefinition<I, R>, R> rootFactory) {
		IndividualDefinitionBuilder<I, R> builder = new IndividualDefinitionBuilder<>(uid, karyotype, rootFactory);
		definitionBuilders.put(uid, builder);
		return builder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I extends IIndividual, R extends IIndividualRoot<I>> Optional<IIndividualDefinitionBuilder<I, R>> getDefinition(String uid) {
		return Optional.ofNullable((IndividualDefinitionBuilder<I, R>) definitionBuilders.get(uid));
	}

	public DefinitionRegistry createRegistry() {
		return new DefinitionRegistry(definitionBuilders);
	}
}
