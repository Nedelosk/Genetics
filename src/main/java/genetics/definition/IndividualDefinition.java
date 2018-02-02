package genetics.definition;

import java.util.Optional;
import java.util.function.Function;

import net.minecraft.item.ItemStack;

import genetics.api.alleles.IAllele;
import genetics.api.definition.IIndividualDefinition;
import genetics.api.definition.IIndividualRoot;
import genetics.api.definition.IIndividualTranslator;
import genetics.api.definition.ITemplateContainer;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismType;
import genetics.api.organism.IOrganismTypes;

public class IndividualDefinition<I extends IIndividual, R extends IIndividualRoot<I, ?>> implements IIndividualDefinition<I, R> {
	private final IOrganismTypes<I> types;
	private final IIndividualTranslator<I> translator;
	private final ITemplateContainer templateContainer;
	private final IKaryotype karyotype;
	private final String uid;
	private final R root;

	IndividualDefinition(IOrganismTypes<I> types, IIndividualTranslator<I> translator, ITemplateContainer templateContainer, String uid, Function<IIndividualDefinition<I, R>, R> rootFactory) {
		this.types = types;
		this.translator = translator;
		this.uid = uid;
		this.karyotype = templateContainer.getKaryotype();
		this.templateContainer = templateContainer;
		this.root = rootFactory.apply(this);
	}

	@Override
	public String getUID() {
		return uid;
	}

	@Override
	public R getRoot() {
		return root;
	}

	@Override
	public ITemplateContainer getTemplates() {
		return templateContainer;
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
	public Optional<I> createIndividual(String templateIdentifier) {
		IAllele[] template = templateContainer.getTemplate(templateIdentifier);
		if (template == null) {
			return Optional.empty();
		}
		return Optional.of(root.create(karyotype.templateAsGenome(template)));
	}

	@Override
	public ItemStack createStack(IAllele allele, IOrganismType type) {
		Optional<I> optional = createIndividual(allele.getRegistryName().toString());
		return optional.map(i -> types.createStack(i, type)).orElse(ItemStack.EMPTY);
	}
}
