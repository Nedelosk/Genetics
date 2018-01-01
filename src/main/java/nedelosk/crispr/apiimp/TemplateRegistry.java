package nedelosk.crispr.apiimp;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.function.BiFunction;

import nedelosk.crispr.api.ITemplateContainer;
import nedelosk.crispr.api.ITemplateRegistry;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.apiimp.alleles.AlleleTemplateBuilder;

public class TemplateRegistry implements ITemplateRegistry {
	private final IKaryotype karyotype;
	private BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory = AlleleTemplateBuilder::new;
	private final BiFunction<IKaryotype, IAllele[], String> templateNameFactory;
	private final HashMap<String, IAllele[]> templates = new HashMap<>();

	public TemplateRegistry(IKaryotype karyotype, BiFunction<IKaryotype, IAllele[], String> templateNameFactory) {
		this.karyotype = karyotype;
		this.templateNameFactory = templateNameFactory;
	}


	public TemplateRegistry(IKaryotype karyotype) {
		this.karyotype = karyotype;
		this.templateNameFactory = (k, a) -> a[karyotype.getTemplateType().getIndex()].getRegistryName().toString();
	}

	@Override
	public ITemplateRegistry setFactory(BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory) {
		this.templateFactory = templateFactory;
		return this;
	}

	@Override
	public void registerTemplate(IAllele[] template) {
		Preconditions.checkNotNull(template, "Tried to register null template");
		Preconditions.checkArgument(template.length > 0, "Tried to register empty template");

		registerTemplate(templateNameFactory.apply(karyotype, template), template);
	}

	@Override
	public void registerTemplate(String identifier, IAllele[] template) {
		Preconditions.checkNotNull(template, "Tried to register null template");
		Preconditions.checkArgument(template.length > 0, "Tried to register empty template");

		templates.put(identifier, template);
	}

	@Override
	public void registerTemplate(IAlleleTemplate template) {
		registerTemplate(template.alleles());
	}

	@Override
	public void registerTemplate(String identifier, IAlleleTemplate template) {
		registerTemplate(identifier, template.alleles());
	}

	@Override
	public ITemplateContainer create() {
		return new TemplateContainer(karyotype, ImmutableMap.copyOf(templates), templateFactory);
	}
}
