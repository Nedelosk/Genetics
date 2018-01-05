package genetics.definition;

import com.google.common.collect.ImmutableMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

import genetics.api.alleles.IAllele;
import genetics.api.definition.ITemplateContainer;
import genetics.api.gene.IKaryotype;

public class TemplateContainer implements ITemplateContainer {
	private final IKaryotype karyotype;
	private final ImmutableMap<String, IAllele[]> templates;

	public TemplateContainer(IKaryotype karyotype, ImmutableMap<String, IAllele[]> templates) {
		this.karyotype = karyotype;
		this.templates = templates;
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
