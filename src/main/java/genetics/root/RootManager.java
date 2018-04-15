package genetics.root;

import java.util.HashMap;
import java.util.Optional;

import genetics.api.gene.IKaryotype;
import genetics.api.individual.IIndividual;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IIndividualRootBuilder;
import genetics.api.root.IIndividualRootFactory;
import genetics.api.root.IRootManager;

public class RootManager implements IRootManager {
	private final HashMap<String, IndividualRootBuilder> definitionBuilders = new HashMap<>();

	@Override
	public <I extends IIndividual, R extends IIndividualRoot<I>> IIndividualRootBuilder<I, R> createRoot(String name, IKaryotype karyotype, IIndividualRootFactory<I, R> rootFactory) {
		IndividualRootBuilder<I, R> builder = new IndividualRootBuilder<>(name, karyotype, rootFactory);
		definitionBuilders.put(name, builder);
		return builder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I extends IIndividual, R extends IIndividualRoot<I>> Optional<IIndividualRootBuilder<I, R>> getRoot(String uid) {
		return Optional.ofNullable((IndividualRootBuilder<I, R>) definitionBuilders.get(uid));
	}

	public RootRegistry createRegistry() {
		return new RootRegistry(definitionBuilders);
	}
}
