package genetics.api.root;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import genetics.api.GeneticsAPI;
import genetics.api.IGeneticFactory;
import genetics.api.alleles.IAllele;
import genetics.api.individual.IGenome;
import genetics.api.individual.IIndividual;
import genetics.api.individual.IKaryotype;
import genetics.api.organism.IOrganismType;
import genetics.api.organism.IOrganismTypes;
import genetics.api.root.components.ComponentKey;
import genetics.api.root.components.IRootComponent;
import genetics.api.root.components.IRootComponentBuilder;
import genetics.api.root.translator.IIndividualTranslator;

/**
 * Abstract implementation of the {@link IIndividualRoot} interface.
 *
 * @param <I> The type of the individual that this root provides.
 */
public abstract class IndividualRoot<I extends IIndividual> implements IIndividualRoot<I> {
	protected final IRootDefinition definition;
	protected final IOrganismTypes<I> types;
	protected final IIndividualTranslator<I> translator;
	protected final ITemplateContainer templates;
	protected final IKaryotype karyotype;
	private final ImmutableList<I> individualTemplates;
	private final I defaultMember;
	private final Map<ComponentKey, IRootComponent> componentByName;
	@Nullable
	@SideOnly(Side.CLIENT)
	private IDisplayHelper<I> displayHelper;

	public IndividualRoot(IOrganismTypes<I> types, IIndividualTranslator<I> translator, ITemplateContainer templates, IKaryotype karyotype, Function<IIndividualRoot<I>, Map<ComponentKey, IRootComponent>> components) {
		this.definition = GeneticsAPI.apiInstance.getRoot(getUID());
		this.types = types;
		this.translator = translator;
		this.templates = templates;
		this.karyotype = karyotype;
		this.defaultMember = create(karyotype.getDefaultGenome());
		ImmutableList.Builder<I> templateBuilder = new ImmutableList.Builder<>();
		for (IAllele[] template : templates.getTemplates()) {
			templateBuilder.add(templateAsIndividual(template));
		}
		this.individualTemplates = templateBuilder.build();
		this.componentByName = ImmutableMap.copyOf(components.apply(this));
	}

	@Override
	public I templateAsIndividual(IAllele[] templateActive, @Nullable IAllele[] templateInactive) {
		IGenome genome = karyotype.templateAsGenome(templateActive, templateInactive);
		return create(genome);
	}

	@Override
	public I getDefaultMember() {
		return defaultMember;
	}

	@Override
	public List<I> getIndividualTemplates() {
		return individualTemplates;
	}

	@Override
	public Optional<I> create(String templateIdentifier) {
		IAllele[] template = templates.getTemplate(templateIdentifier);
		if (template.length == 0) {
			return Optional.empty();
		}
		return Optional.of(create(karyotype.templateAsGenome(template)));
	}

	@Override
	public ItemStack createStack(IAllele allele, IOrganismType type) {
		Optional<I> optional = create(allele.getRegistryName().toString());
		return optional.map(i -> types.createStack(i, type)).orElse(ItemStack.EMPTY);
	}

	@Override
	public ITemplateContainer getTemplates() {
		return templates;
	}

	@Override
	public IKaryotype getKaryotype() {
		return karyotype;
	}

	@Override
	public IIndividualTranslator<I> getTranslator() {
		return translator;
	}

	@Override
	public IOrganismTypes<I> getTypes() {
		return types;
	}

	@Override
	public IRootDefinition getDefinition() {
		return definition;
	}

	@Override
	public <C extends IRootComponent, B extends IRootComponentBuilder<C>> Optional<C> getComponent(ComponentKey<C, B> key) {
		Class<C> componentClass = key.getComponentClass();
		IRootComponent component = componentByName.get(key);
		if (component == null || !componentClass.isInstance(component)) {
			return Optional.empty();
		}
		return Optional.of(componentClass.cast(component));
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public IDisplayHelper getDisplayHelper() {
		if (displayHelper == null) {
			IGeneticFactory geneticFactory = GeneticsAPI.apiInstance.getGeneticFactory();
			displayHelper = geneticFactory.createDisplayHelper(this);
		}
		return displayHelper;
	}
}
