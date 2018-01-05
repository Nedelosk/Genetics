package genetics;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import genetics.api.GeneticsAPI;
import genetics.api.alleles.Allele;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.gene.IGene;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.gene.IKaryotypeBuilder;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;

import genetics.alleles.AlleleTemplateBuilder;
import genetics.individual.Chromosome;
import genetics.individual.Genome;

@Immutable
public class Karyotype implements IKaryotype {
	private final IGeneType[] geneTypes;
	private final IGeneType templateType;
	private final String identifier;
	private final IAlleleTemplate defaultTemplate;
	private final IGenome defaultGenome;
	private final BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory;

	private Karyotype(String identifier, Set<IGeneType> geneTypes, IGeneType templateType, BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory) {
		this.identifier = identifier;
		this.templateType = templateType;
		this.geneTypes = new IGeneType[geneTypes.size()];
		this.templateFactory = templateFactory;
		for (IGeneType key : geneTypes) {
			this.geneTypes[key.getIndex()] = key;
		}
		IAllele[] alleles = new Allele[geneTypes.size()];
		for (IGeneType key : geneTypes) {
			Optional<IGene> optional = GeneticsAPI.geneticSystem.getGene(key);
			optional.ifPresent(g -> alleles[key.getIndex()] = g.getDefaultAllele());
		}
		this.defaultTemplate = createTemplate(alleles).build();
		this.defaultGenome = getDefaultTemplate().toGenome();
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public IGeneType[] getGeneTypes() {
		return geneTypes;
	}

	@Override
	public boolean contains(IGeneType type) {
		return Arrays.asList(geneTypes).contains(type);
	}

	@Override
	public IGeneType getTemplateType() {
		return templateType;
	}

	@Override
	public IAlleleTemplate getDefaultTemplate() {
		return defaultTemplate;
	}

	@Override
	public IGenome getDefaultGenome() {
		return defaultGenome;
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
	public IChromosome[] templateAsChromosomes(IAllele[] templateActive, @Nullable IAllele[] templateInactive) {
		Chromosome[] chromosomes = new Chromosome[geneTypes.length];
		for (int i = 0; i < geneTypes.length; i++) {
			if (templateInactive == null) {
				chromosomes[i] = Chromosome.create(templateActive[i], geneTypes[i]);
			} else {
				chromosomes[i] = Chromosome.create(templateActive[i], templateInactive[i], geneTypes[i]);
			}
		}

		return chromosomes;
	}

	@Override
	public IGenome templateAsGenome(IAllele[] templateActive, @Nullable IAllele[] templateInactive) {
		return new Genome(this, templateAsChromosomes(templateActive, templateInactive));
	}

	public static class Builder implements IKaryotypeBuilder {
		private final Set<IGeneType> geneTypes = new HashSet<>();
		private final IGeneType templateType;
		private final String identifier;
		private BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory = AlleleTemplateBuilder::new;

		public Builder(IGeneType templateType, String identifier) {
			this.templateType = templateType;
			this.identifier = identifier;
			add(templateType);
		}

		@Override
		public Builder setTemplateFactory(BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory) {
			this.templateFactory = templateFactory;
			return this;
		}

		@Override
		public IKaryotypeBuilder add(IGeneType type) {
			this.geneTypes.add(type);
			return this;
		}

		@Override
		public IKaryotype build() {
			return new Karyotype(identifier, geneTypes, templateType, templateFactory);
		}
	}
}
