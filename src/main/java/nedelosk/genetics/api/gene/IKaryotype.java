package nedelosk.genetics.api.gene;

import javax.annotation.Nullable;

import nedelosk.genetics.api.GeneticsAPI;
import nedelosk.genetics.api.alleles.IAllele;
import nedelosk.genetics.api.individual.IChromosome;
import nedelosk.genetics.api.individual.IGenome;

public interface IKaryotype {
	IGeneType[] getGeneTypes();

	default <V> boolean contains(IGene gene) {
		return GeneticsAPI.geneticSystem.getTypes(gene).stream().anyMatch(this::contains);
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
