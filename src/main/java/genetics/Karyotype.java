package genetics;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import genetics.api.alleles.IAllele;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.gene.IKaryotypeBuilder;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;

import genetics.individual.Chromosome;
import genetics.individual.Genome;

public class Karyotype implements IKaryotype {
	private final IGeneType[] geneTypes;
	private final IGeneType templateType;
	private final String identifier;

	private Karyotype(String identifier, Set<IGeneType> geneTypes, IGeneType templateType) {
		this.identifier = identifier;
		this.templateType = templateType;
		this.geneTypes = new IGeneType[geneTypes.size()];
		for (IGeneType key : geneTypes) {
			this.geneTypes[key.getIndex()] = key;
		}
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

		public Builder(IGeneType templateType, String identifier) {
			this.templateType = templateType;
			this.identifier = identifier;
			add(templateType);
		}

		@Override
		public IKaryotypeBuilder add(IGeneType type) {
			this.geneTypes.add(type);
			return this;
		}

		@Override
		public IKaryotype build() {
			return new Karyotype(identifier, geneTypes, templateType);
		}
	}
}
