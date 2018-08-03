package genetics.root;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.individual.IChromosomeType;
import genetics.api.individual.IIndividual;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IIndividualRootBuilder;
import genetics.api.root.IIndividualRootFactory;
import genetics.api.root.IRootManager;

public class RootManager implements IRootManager {
	private final HashMap<String, IndividualRootBuilder> rootBuilders = new HashMap<>();

	@Override
	public <I extends IIndividual> IIndividualRootBuilder<I> createRoot(String uid, IChromosomeType speciesType, Function<IAlleleTemplateBuilder, IAlleleTemplate> defaultTemplate, IIndividualRootFactory<I, IIndividualRoot<I>> rootFactory) {
		IndividualRootBuilder<I> builder = new IndividualRootBuilder<>(uid, speciesType, defaultTemplate, rootFactory);
		rootBuilders.put(uid, builder);
		return builder;
	}

	@Override
	public <I extends IIndividual, T extends Enum<T> & IChromosomeType> IIndividualRootBuilder<I> createRoot(String uid, Class<? extends T> enumClass, Function<IAlleleTemplateBuilder, IAlleleTemplate> defaultTemplate, IIndividualRootFactory<I, IIndividualRoot<I>> rootFactory) {
		T[] types = enumClass.getEnumConstants();
		if (types.length <= 0) {
			throw new IllegalArgumentException("The given enum class must contain at least one enum constant.");
		}
		IndividualRootBuilder<I> builder = new IndividualRootBuilder<>(uid, types[0], defaultTemplate, rootFactory);
		for (int i = 1; i < types.length; i++) {
			IChromosomeType type = types[i];
			builder.addChromosome(type);
		}
		rootBuilders.put(uid, builder);
		return builder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I extends IIndividual> Optional<IIndividualRootBuilder<I>> getRoot(String uid) {
		return Optional.ofNullable((IndividualRootBuilder<I>) rootBuilders.get(uid));
	}

	public Map<String, IndividualRootBuilder> getRootBuilders() {
		return rootBuilders;
	}
}
