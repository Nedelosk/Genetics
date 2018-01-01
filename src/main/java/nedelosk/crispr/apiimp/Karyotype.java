package nedelosk.crispr.apiimp;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Set;

import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IChromosome;
import nedelosk.crispr.api.individual.IGenome;
import nedelosk.crispr.apiimp.individual.Chromosome;
import nedelosk.crispr.apiimp.individual.Genome;

public class Karyotype implements IKaryotype {
	private final IGeneType[] geneTypes;
	private final IGeneType templateType;

	Karyotype(Set<IGeneType> geneTypes, IGeneType templateType) {
		this.templateType = templateType;
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
		return new Genome(templateAsChromosomes(templateActive, templateInactive));
	}
}
