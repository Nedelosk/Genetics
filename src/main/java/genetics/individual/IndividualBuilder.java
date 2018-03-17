package genetics.individual;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.definition.IIndividualDefinition;
import genetics.api.definition.IIndividualRoot;
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IGenome;
import genetics.api.individual.IGenomeWrapper;
import genetics.api.individual.IIndividual;
import genetics.api.individual.IIndividualBuilder;

public final class IndividualBuilder<I extends IIndividual> implements IIndividualBuilder<I> {
	private final IIndividualDefinition<I, IIndividualRoot<I, IGenomeWrapper>> definition;
	private final IAlleleTemplateBuilder activeBuilder;
	private final IAlleleTemplateBuilder inactiveBuilder;
	private final I creationIndividual;

	@SuppressWarnings("unchecked")
	public IndividualBuilder(I individual) {
		this.definition = (IIndividualDefinition<I, IIndividualRoot<I, IGenomeWrapper>>) individual.getDefinition();
		IGenome genome = individual.getGenome();
		IKaryotype karyotype = definition.getKaryotype();
		this.activeBuilder = karyotype.createTemplate(genome.getActiveAlleles());
		this.inactiveBuilder = karyotype.createTemplate(genome.getInactiveAlleles());
		this.creationIndividual = individual;
	}

	@Override
	public void setAllele(IChromosomeType type, IAllele<?> allele, boolean active) {
		IAlleleTemplateBuilder builder = active ? activeBuilder : inactiveBuilder;
		builder.set(type, allele);
	}

	@Override
	public void setAllele(IChromosomeType type, IAlleleKey key, boolean active) {
		IAlleleTemplateBuilder builder = active ? activeBuilder : inactiveBuilder;
		builder.set(type, key);
	}

	@Override
	public IIndividualDefinition<I, IIndividualRoot<I, IGenomeWrapper>> getDefinition() {
		return definition;
	}

	@Override
	public I getCreationIndividual() {
		return creationIndividual;
	}

	@Override
	public I build() {
		IAlleleTemplate activeTemplate = activeBuilder.build();
		IAlleleTemplate inactiveTemplate = inactiveBuilder.build();
		I individual = definition.getRoot().create(activeTemplate.toGenome(inactiveTemplate));
		individual.onBuild(creationIndividual);
		return individual;
	}
}
