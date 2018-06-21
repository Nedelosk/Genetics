package cultivation.indivudual;

import java.util.Arrays;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.individual.IGenome;
import genetics.api.individual.IIndividual;
import genetics.api.individual.IKaryotype;
import genetics.api.individual.ITemplateProvider;
import genetics.api.root.IIndividualRootBuilder;

public enum PlantDefinition implements ITemplateProvider {
	WHEAT("wheat");

	private final IAllele allele;

	@SuppressWarnings("NullableProblems")
	private IAllele[] templateAlleles;
	@SuppressWarnings("NullableProblems")
	private IGenome genome;

	PlantDefinition(String name) {
		this(name, false);
	}

	PlantDefinition(String name, boolean dominant) {
		this.allele = new AllelePlantSpecies(name, dominant).setRegistryName(name);
	}

	public static void preInit(IIndividualRootBuilder builder){
		for(PlantDefinition definition : values()){
			definition.init(builder);
		}
	}

	private void init(IIndividualRootBuilder builder) {
		IKaryotype karyotype = builder.getKaryotype();
		IAlleleTemplateBuilder template = karyotype.createTemplate();
		template.set(PlantChromosomes.SPECIES, allele);
		createTemplate(template);

		this.templateAlleles = template.build().alleles();
		genome = karyotype.templateAsGenome(this.templateAlleles);
		builder.registerTemplate(this.templateAlleles);
	}

	protected void createTemplate(IAlleleTemplateBuilder template){
	}

	public static IAlleleTemplate getDefaultTemplate(IKaryotype karyotype){
		IAlleleTemplateBuilder template = karyotype.createEmptyTemplate();
		template.set(PlantChromosomes.SPECIES, PlantDefinition.WHEAT.getAllele());
		PlantDefinition.WHEAT.createTemplate(template);
		return template.build();
	}

	public IAllele getAllele() {
		return allele;
	}

	@Override
	public IAllele[] getTemplate() {
		return Arrays.copyOf(templateAlleles, templateAlleles.length);
	}

	@Override
	public IGenome getGenome() {
		return genome;
	}

	@Override
	public IIndividual createIndividual() {
		return new Plant(genome);
	}
}
