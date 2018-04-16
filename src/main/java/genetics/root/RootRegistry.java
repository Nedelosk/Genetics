package genetics.root;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import genetics.api.individual.IIndividual;
import genetics.api.root.EmptyOptionalRoot;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IOptionalRoot;
import genetics.api.root.IRootRegistry;

public class RootRegistry implements IRootRegistry {
	private final HashMap<String, IOptionalRoot> definitions = new HashMap<>();

	public RootRegistry(Map<String, IndividualRootBuilder> definitionBuilders) {
		for (Map.Entry<String, IndividualRootBuilder> entry : definitionBuilders.entrySet()) {
			definitions.put(entry.getKey(), entry.getValue().create());
		}
	}

	@Override
	public Map<String, IOptionalRoot> getDefinitions() {
		return Collections.unmodifiableMap(definitions);
	}

	@Override
	public <I extends IIndividual, R extends IIndividualRoot<I>> IOptionalRoot<R> getRoot(String uid) {
		@SuppressWarnings("unchecked")
		IOptionalRoot<R> definition = (IOptionalRoot<R>) definitions.get(uid);
		if (definition == null) {
			definition = EmptyOptionalRoot.empty();
		}
		return definition;
	}
}
