package nedelosk.crispr.definition;

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.definition.ITemplateContainer;
import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;

public class TemplateContainer implements ITemplateContainer {
	private final IKaryotype karyotype;
	private final ImmutableMap<String, IAllele[]> templates;
	private final BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory;
	@Nullable
	private IAlleleTemplate defaultTemplate = null;

	public TemplateContainer(IKaryotype karyotype, ImmutableMap<String, IAllele[]> templates, BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory) {
		this.karyotype = karyotype;
		this.templates = templates;
		this.templateFactory = templateFactory;
	}


	@Override
	public IAlleleTemplate getDefaultTemplate() {
		if (defaultTemplate == null) {
			IGeneType[] geneTypes = karyotype.getGeneTypes();
			IAllele[] alleles = new Allele[geneTypes.length];
			for (IGeneType key : geneTypes) {
				Optional<IGene> optional = CrisprAPI.geneticSystem.getGene(key);
				optional.ifPresent(g -> alleles[key.getIndex()] = g.getDefaultAllele());
			}
			defaultTemplate = templateFactory.apply(karyotype, alleles).build();
		}
		return defaultTemplate;
	}

	@Override
	public IAlleleTemplateBuilder createTemplate() {
		return getDefaultTemplate().createBuilder();
	}

	@Override
	public IAlleleTemplateBuilder createTemplate(IAllele[] alleles) {
		return templateFactory.apply(karyotype, alleles);
	}

	@Override
	public Map<String, IAllele[]> getGenomeTemplates() {
		return templates;
	}

	@Override
	public IAllele[] getRandomTemplate(Random rand) {
		Collection<IAllele[]> templates = this.templates.values();
		int size = templates.size();
		IAllele[][] templatesArray = templates.toArray(new IAllele[size][]);
		return templatesArray[rand.nextInt(size)];
	}

	@Override
	public IAllele[] getTemplate(String identifier) {
		IAllele[] template = templates.get(identifier);
		if (template == null) {
			return null;
		}
		return Arrays.copyOf(template, template.length);
	}

	@Override
	public IKaryotype getKaryotype() {
		return karyotype;
	}
}
