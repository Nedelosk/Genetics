package nedelosk.crispr.apiimp;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.BiFunction;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;

public class Karyotype implements IKaryotype {
	private final IGeneType[] geneTypes;
	private final IGeneType templateType;
	private final BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory;
	protected final HashMap<String, IAllele[]> templates;
	@Nullable
	private IAlleleTemplate defaultTemplate = null;

	Karyotype(Set<IGeneType> geneTypes, BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory, IGeneType templateType, HashMap<String, IAllele[]> templates) {
		this.templateFactory = templateFactory;
		this.templateType = templateType;
		this.templates = templates;
		this.geneTypes = new IGeneType[geneTypes.size()];
		for (IGeneType key : geneTypes) {
			this.geneTypes[key.getIndex()] = key;
		}
	}

	@Override
	public IGeneType[] getGeneTypes() {
		return geneTypes;
	}

	@Override
	public IGeneType getTemplateType() {
		return templateType;
	}

	@Override
	public boolean contains(IGeneType type) {
		return Arrays.asList(geneTypes).contains(type);
	}

	@Override
	public IAlleleTemplate getDefaultTemplate() {
		if (defaultTemplate == null) {
			IAllele[] alleles = new Allele[geneTypes.length];
			for (IGeneType key : geneTypes) {
				Optional<IGene> optional = CrisprAPI.geneticSystem.getGene(key);
				optional.ifPresent(g -> alleles[key.getIndex()] = g.getDefaultAllele());
			}
			defaultTemplate = templateFactory.apply(this, alleles).build();
		}
		return defaultTemplate;
	}

	@Override
	public IAlleleTemplateBuilder createTemplate() {
		return getDefaultTemplate().createBuilder();
	}

	@Override
	public IAlleleTemplateBuilder createTemplate(IAllele[] alleles) {
		return templateFactory.apply(this, alleles);
	}

	@Override
	public Map<String, IAllele[]> getGenomeTemplates() {
		return templates;
	}

	@Override
	public Allele[] getRandomTemplate(Random rand) {
		Collection<IAllele[]> templates = this.templates.values();
		int size = templates.size();
		Allele[][] templatesArray = templates.toArray(new Allele[size][]);
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
}
