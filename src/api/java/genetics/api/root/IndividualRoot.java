package genetics.api.root;

import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

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
import genetics.api.root.translator.IIndividualTranslator;

/**
 * Abstract implementation of the {@link IIndividualRoot} interface.
 *
 * @param <I> The type of the individual that this root provides.
 */
public abstract class IndividualRoot<I extends IIndividual> implements IIndividualRoot<I> {
	protected final IOrganismTypes<I> types;
	protected final IIndividualTranslator<I> translator;
	protected final ITemplateContainer templates;
	protected final IKaryotype karyotype;
	private final ImmutableList<I> individualTemplates;
	protected final I defaultMember;
	@Nullable
	@SideOnly(Side.CLIENT)
	protected IDisplayHelper<I> displayHelper;

	public IndividualRoot(IOrganismTypes<I> types, IIndividualTranslator<I> translator, ITemplateContainer templates, IKaryotype karyotype) {
		this.types = types;
		this.translator = translator;
		this.templates = templates;
		this.karyotype = karyotype;
		this.defaultMember = create(karyotype.getDefaultGenome());
		ImmutableList.Builder<I> individualTemplates = new ImmutableList.Builder<>();
		for(IAllele[] template : templates.getTemplates()){
			individualTemplates.add(templateAsIndividual(template));
		}
		this.individualTemplates = individualTemplates.build();
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

	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	@Override
	public IDisplayHelper getDisplayHelper() {
		if(displayHelper == null){
			IGeneticFactory geneticFactory = GeneticsAPI.apiInstance.getGeneticFactory();
			displayHelper = geneticFactory.createDisplayHelper(this);
		}
		return displayHelper;
	}
}
