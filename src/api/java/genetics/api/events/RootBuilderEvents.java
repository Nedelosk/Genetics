package genetics.api.events;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IGenericEvent;

import genetics.api.individual.IIndividual;
import genetics.api.individual.ISpeciesDefinition;
import genetics.api.root.IIndividualRootBuilder;
import genetics.api.root.components.ComponentKey;
import genetics.api.root.components.IRootComponentBuilder;

public class RootBuilderEvents<I extends IIndividual> extends Event {
	private final IIndividualRootBuilder<I> root;

	private RootBuilderEvents(IIndividualRootBuilder<I> root) {
		this.root = root;
	}

	public IIndividualRootBuilder<I> getRoot() {
		return root;
	}

	public static class GatherDefinitions<I extends IIndividual> extends RootBuilderEvents<I> {
		private final List<ISpeciesDefinition> definitions = new LinkedList<>();

		public GatherDefinitions(IIndividualRootBuilder<I> root) {
			super(root);
		}

		public void add(ISpeciesDefinition definition){
			this.definitions.add(definition);
		}

		public void add(ISpeciesDefinition... definitions){
			add(Arrays.asList(definitions));
		}

		public void add(Collection<ISpeciesDefinition> definitions){
			this.definitions.addAll(definitions);
		}

		public List<ISpeciesDefinition> getDefinitions() {
			return Collections.unmodifiableList(definitions);
		}
	}

	public static class BuildComponent<I extends IIndividual, B extends IRootComponentBuilder> extends RootBuilderEvents<I> implements IGenericEvent<B> {

		private final ComponentKey<?, B> key;
		private final B builder;

		public BuildComponent(IIndividualRootBuilder<I> root, ComponentKey<?, B> key, B builder) {
			super(root);
			this.key = key;
			this.builder = builder;
		}

		public B getBuilder() {
			return builder;
		}

		public ComponentKey<?, B> getKey() {
			return key;
		}

		@Override
		public Type getGenericType() {
			return key.getBuilderClass();
		}
	}
}
