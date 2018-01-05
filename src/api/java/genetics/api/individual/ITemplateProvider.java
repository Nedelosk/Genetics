package genetics.api.individual;

import genetics.api.alleles.IAllele;

public interface ITemplateProvider {
	IAllele[] getTemplate();

	IGenome getGenome();

	IOrganism createIndividual();
}
