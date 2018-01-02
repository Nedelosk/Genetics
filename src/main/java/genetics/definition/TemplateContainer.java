package genetics.definition;

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;

import genetics.api.GeneticsAPI;
import genetics.api.alleles.Allele;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.definition.ITemplateContainer;
import genetics.api.gene.IGene;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;

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
				Optional<IGene> optional = GeneticsAPI.geneticSystem.getGene(key);
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
