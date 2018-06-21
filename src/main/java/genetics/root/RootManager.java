package genetics.root;

import java.util.HashMap;
import java.util.Optional;

import genetics.api.individual.IIndividual;
import genetics.api.individual.IKaryotype;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IIndividualRootBuilder;
import genetics.api.root.IIndividualRootFactory;
import genetics.api.root.IRootManager;

public class RootManager implements IRootManager {
	private final HashMap<String, IndividualRootBuilder> rootBuilders = new HashMap<>();

	@Override
	public <I extends IIndividual> IIndividualRootBuilder<I> createRoot(String uid, IKaryotype karyotype, IIndividualRootFactory<I, IIndividualRoot<I>> rootFactory) {
		IndividualRootBuilder<I> builder = new IndividualRootBuilder<>(uid, karyotype, rootFactory);
		rootBuilders.put(uid, builder);
		return builder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I extends IIndividual> Optional<IIndividualRootBuilder<I>> getRoot(String uid) {
		return Optional.ofNullable((IndividualRootBuilder<I>) rootBuilders.get(uid));
	}

	public HashMap<String, IndividualRootBuilder> getRootBuilders() {
		return rootBuilders;
	}
}
