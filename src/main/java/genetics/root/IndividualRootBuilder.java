package genetics.root;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraftforge.common.MinecraftForge;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.events.RootEvent;
import genetics.api.individual.IChromosomeType;
import genetics.api.individual.IIndividual;
import genetics.api.individual.IKaryotype;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IIndividualRootBuilder;
import genetics.api.root.IIndividualRootFactory;
import genetics.api.root.components.ComponentKey;
import genetics.api.root.components.ComponentKeys;
import genetics.api.root.components.IRootComponent;
import genetics.api.root.components.IRootComponentBuilder;
import genetics.api.root.components.IRootComponentFactory;

import genetics.ApiInstance;
import genetics.alleles.AlleleTemplateBuilder;
import genetics.individual.Karyotype;
import genetics.individual.RootDefinition;

public class IndividualRootBuilder<I extends IIndividual> implements IIndividualRootBuilder<I> {
	private final String uid;
	private final List<IChromosomeType> chromosomeTypes = new ArrayList<>();
	@Nullable
	private IChromosomeType speciesType;
	private final Multimap<ComponentKey, Consumer> componentListeners = HashMultimap.create();
	private final Map<ComponentKey, IRootComponentFactory> componentFactories = new HashMap<>();
	private final RootDefinition<IIndividualRoot<I>> definition;
	private BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory = AlleleTemplateBuilder::new;
	@Nullable
	private Function<IAlleleTemplateBuilder, IAlleleTemplate> defaultTemplate;
	@Nullable
	private IIndividualRootFactory<I, IIndividualRoot<I>> rootFactory;

	IndividualRootBuilder(String uid) {
		this.uid = uid;
		this.definition = ApiInstance.INSTANCE.getRoot(uid);
		addComponent(ComponentKeys.TEMPLATES);
		addComponent(ComponentKeys.TYPES);
	}

	@Override
	public IIndividualRootBuilder addChromosome(IChromosomeType type) {
		chromosomeTypes.add(type);
		if(speciesType == null){
			speciesType = type;
		}
		return this;
	}

	@Override
	public IIndividualRootBuilder addChromosome(IChromosomeType... types) {
		if(speciesType == null && types.length > 0){
			speciesType = types[0];
		}
		chromosomeTypes.addAll(Arrays.asList(types));
		return this;
	}

	@Override
	public IIndividualRootBuilder setSpeciesType(IChromosomeType speciesType) {
		this.speciesType = speciesType;
		if(!chromosomeTypes.contains(speciesType)){
			addChromosome(speciesType);
		}
		return this;
	}

	@Override
	public IIndividualRootBuilder setRootFactory(IIndividualRootFactory<I, IIndividualRoot<I>> rootFactory) {
		this.rootFactory = rootFactory;
		return this;
	}

	@Override
	public IIndividualRootBuilder setDefaultTemplate(Function<IAlleleTemplateBuilder, IAlleleTemplate> defaultTemplate) {
		this.defaultTemplate = defaultTemplate;
		return this;
	}

	@Override
	public IIndividualRootBuilder setTemplateFactory(BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory) {
		this.templateFactory = templateFactory;
		return this;
	}

	@SuppressWarnings("unchecked")
	public void create() {
		Preconditions.checkNotNull(speciesType);
		Preconditions.checkNotNull(templateFactory);
		Preconditions.checkNotNull(defaultTemplate);
		Preconditions.checkNotNull(rootFactory);
		IKaryotype karyotype = new Karyotype(uid, chromosomeTypes, speciesType, templateFactory, defaultTemplate);
		definition.setRoot(rootFactory.createRoot(karyotype, root -> {
			Map<ComponentKey, IRootComponentBuilder> builders = new HashMap<>();
			componentFactories.forEach((componentKey, factory) -> builders.put(componentKey, factory.create(root)));
			componentListeners.forEach((componentKey, consumer) -> consumer.accept(builders.get(componentKey)));
			ImmutableMap.Builder<ComponentKey, IRootComponent> components = new ImmutableMap.Builder<>();
			builders.forEach((componentKey, builder) -> components.put(componentKey, builder.create()));
			return components.build();
		}));
		MinecraftForge.EVENT_BUS.register(new RootEvent.CreationEvent<>(definition));
	}

	@SuppressWarnings("unchecked")
	public <R extends IIndividualRoot<I>> RootDefinition<R> getDefinition() {
		return (RootDefinition<R>) definition;
	}

	@Override
	public <C extends IRootComponent, B extends IRootComponentBuilder> void addComponent(ComponentKey<C, B> key) {
		IRootComponentFactory factory = RootComponentRegistry.INSTANCE.getFactory(key);
		if (factory == null) {
			throw new IllegalArgumentException(String.format("No component factory was registered for the component key '%s'.", key));
		}
		componentFactories.put(key, factory);
	}

	@Override
	public <C extends IRootComponent, B extends IRootComponentBuilder> void addComponent(ComponentKey<C, B> key, IRootComponentFactory<I, B> factory) {
		componentFactories.put(key, factory);
	}

	@Override
	public <C extends IRootComponent, B extends IRootComponentBuilder> void addListener(ComponentKey<C, B> key, Consumer<B> consumer) {
		if (!componentFactories.containsKey(key)) {
			throw new IllegalArgumentException(String.format("No component factory was added for the component key '%s'. Please call 'addComponent' before 'addListener'.", key));
		}
		componentListeners.put(key, consumer);
	}
}
