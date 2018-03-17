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
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IGene;
import genetics.api.gene.IKaryotype;
import genetics.api.gene.IKaryotypeBuilder;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;

import genetics.alleles.AlleleTemplateBuilder;
import genetics.individual.Chromosome;
import genetics.individual.Genome;

@Immutable
public class Karyotype implements IKaryotype {
	private final IChromosomeType[] chromosomeTypes;
	private final IChromosomeType templateType;
	private final String identifier;
	private final IAlleleTemplate defaultTemplate;
	private final IGenome defaultGenome;
	private final BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory;

	private Karyotype(String identifier, Set<IChromosomeType> chromosomeTypes, IChromosomeType templateType, BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory) {
		this.identifier = identifier;
		this.templateType = templateType;
		this.chromosomeTypes = new IChromosomeType[chromosomeTypes.size()];
		this.templateFactory = templateFactory;
		for (IChromosomeType key : chromosomeTypes) {
			this.chromosomeTypes[key.getIndex()] = key;
		}
		IAllele[] alleles = new Allele[chromosomeTypes.size()];
		for (IChromosomeType key : chromosomeTypes) {
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
	public IChromosomeType[] getChromosomeTypes() {
		return chromosomeTypes;
	}

	@Override
	public boolean contains(IChromosomeType type) {
		return Arrays.asList(chromosomeTypes).contains(type);
	}

	@Override
	public IChromosomeType getTemplateType() {
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
		Chromosome[] chromosomes = new Chromosome[chromosomeTypes.length];
		for (int i = 0; i < chromosomeTypes.length; i++) {
			if (templateInactive == null) {
				chromosomes[i] = Chromosome.create(templateActive[i], chromosomeTypes[i]);
			} else {
				chromosomes[i] = Chromosome.create(templateActive[i], templateInactive[i], chromosomeTypes[i]);
			}
		}

		return chromosomes;
	}

	@Override
	public IGenome templateAsGenome(IAllele[] templateActive, @Nullable IAllele[] templateInactive) {
		return new Genome(this, templateAsChromosomes(templateActive, templateInactive));
	}

	public static class Builder implements IKaryotypeBuilder {
		private final Set<IChromosomeType> chromosomeTypes = new HashSet<>();
		private final IChromosomeType templateType;
		private final String identifier;
		private BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory = AlleleTemplateBuilder::new;

		public Builder(IChromosomeType templateType, String identifier) {
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
		public IKaryotypeBuilder add(IChromosomeType type) {
			this.chromosomeTypes.add(type);
			return this;
		}

		@Override
		public IKaryotype build() {
			return new Karyotype(identifier, chromosomeTypes, templateType, templateFactory);
		}
	}
}
