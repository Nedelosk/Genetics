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
	private final HashMap<String, IndividualRootBuilder> rootBuilders = new HashMap<>();

	@Override
	public <I extends IIndividual> IIndividualRootBuilder<I> createRoot(String name, IKaryotype karyotype, IIndividualRootFactory<I, IIndividualRoot<I>> rootFactory) {
		IndividualRootBuilder<I> builder = new IndividualRootBuilder<>(name, karyotype, rootFactory);
		rootBuilders.put(name, builder);
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
