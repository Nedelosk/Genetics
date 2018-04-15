package genetics.definition;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import genetics.api.definition.EmptyOptionalDefinition;
import genetics.api.definition.IDefinitionRegistry;
import genetics.api.definition.IIndividualRoot;
import genetics.api.definition.IOptionalDefinition;
import genetics.api.individual.IIndividual;

public class DefinitionRegistry implements IDefinitionRegistry {
	private final HashMap<String, IOptionalDefinition> definitions = new HashMap<>();

	public DefinitionRegistry(Map<String, IndividualDefinitionBuilder> definitionBuilders) {
		for (Map.Entry<String, IndividualDefinitionBuilder> entry : definitionBuilders.entrySet()) {
			definitions.put(entry.getKey(), entry.getValue().create());
		}
	}

	@Override
	public Map<String, IOptionalDefinition> getDefinitions() {
		return Collections.unmodifiableMap(definitions);
	}

	@Override
	public <I extends IIndividual, R extends IIndividualRoot<I>> IOptionalDefinition<I, R> getDefinition(String uid) {
		@SuppressWarnings("unchecked")
		IOptionalDefinition<I, R> definition = (IOptionalDefinition<I, R>) definitions.get(uid);
		if (definition == null) {
			definition = EmptyOptionalDefinition.empty();
		}
		return definition;
	}
}
