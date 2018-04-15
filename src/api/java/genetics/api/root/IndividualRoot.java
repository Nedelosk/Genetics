package genetics.api.root;

import javax.annotation.Nullable;
import java.util.Optional;

import net.minecraft.item.ItemStack;

import genetics.api.alleles.IAllele;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IGenome;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismType;
import genetics.api.organism.IOrganismTypes;

public abstract class IndividualRoot<I extends IIndividual> implements IIndividualRoot<I> {
	private final IOrganismTypes<I> types;
	private final IIndividualTranslator<I> translator;
	private final ITemplateContainer templates;
	private final IKaryotype karyotype;

	public IndividualRoot(IOrganismTypes<I> types, IIndividualTranslator<I> translator, ITemplateContainer templates, IKaryotype karyotype) {
		this.types = types;
		this.translator = translator;
		this.templates = templates;
		this.karyotype = karyotype;
	}

	@Override
	public I templateAsIndividual(IAllele[] templateActive, @Nullable IAllele[] templateInactive) {
		IGenome genome = karyotype.templateAsGenome(templateActive, templateInactive);
		return create(genome);
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
}
