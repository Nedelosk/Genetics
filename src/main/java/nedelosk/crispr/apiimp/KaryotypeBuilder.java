package nedelosk.crispr.apiimp;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.gene.IKaryotypeBuilder;
import nedelosk.crispr.apiimp.alleles.AlleleTemplateBuilder;

public class KaryotypeBuilder implements IKaryotypeBuilder {
	private final Set<IGeneType> geneTypes = new HashSet<>();
	private final IGeneType templateType;
	private BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory = AlleleTemplateBuilder::new;
	private final HashMap<String, IAllele[]> templates = new HashMap<>();

	public KaryotypeBuilder(IGeneType templateType) {
		this.templateType = templateType;
	}

	@Override
	public IKaryotypeBuilder register(IGeneType gene) {
		this.geneTypes.add(gene);
		return this;
	}

	@Override
	public IKaryotypeBuilder setFactory(BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory) {
		this.templateFactory = templateFactory;
		return this;
	}

	@Override
	public void registerTemplate(IAllele[] template) {
		Preconditions.checkNotNull(template, "Tried to register null template");
		Preconditions.checkArgument(template.length > 0, "Tried to register empty template");

		registerTemplate(template[templateType.getIndex()].getRegistryName().toString(), template);
	}

	@Override
	public void registerTemplate(String identifier, IAllele[] template) {
		templates.put(identifier, template);
	}

	@Override
	public IKaryotype build() {
		return new Karyotype(geneTypes, templateFactory, templateType, templates);
	}
}
