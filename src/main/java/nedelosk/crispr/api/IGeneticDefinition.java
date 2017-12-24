package nedelosk.crispr.api;

import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IGeneticStat;
import nedelosk.crispr.api.gene.IGenome;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IGeneticIndividual;
import nedelosk.crispr.api.translators.IGeneticTranslator;

public interface IGeneticDefinition<I extends IGeneticIndividual> extends IKaryotype{

	String getName();

	I getDefaultMember();

	IGeneticStat createStat(IGenome genome);

	IAlleleTemplate getDefaultTemplate();

	IAlleleTemplateBuilder createTemplate();

	IAlleleTemplateBuilder createTemplate(Allele[] alleles);

	IGeneticTypes<I> types();

	IGeneticTransformer<I> transformer();

	IGeneticTranslator<I> translator();
}
