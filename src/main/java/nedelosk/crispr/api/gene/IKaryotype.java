package nedelosk.crispr.api.gene;

import javax.annotation.Nullable;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.individual.IChromosome;
import nedelosk.crispr.api.individual.IGenome;

public interface IKaryotype {
	IGeneType[] getGeneTypes();

	default <V> boolean contains(IGene gene) {
		return CrisprAPI.geneticSystem.getTypes(gene).stream().anyMatch(this::contains);
	}

	boolean contains(IGeneType type);

	IGeneType getTemplateType();

	default IChromosome[] templateAsChromosomes(IAllele[] template) {
		return templateAsChromosomes(template, null);
	}

	IChromosome[] templateAsChromosomes(IAllele[] templateActive, @Nullable IAllele[] templateInactive);

	default IGenome templateAsGenome(IAllele[] template) {
		return templateAsGenome(template, null);
	}

	IGenome templateAsGenome(IAllele[] templateActive, @Nullable IAllele[] templateInactive);
}
