package cultivation.indivudual;

import java.util.Arrays;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.individual.IGeneticDefinition;
import genetics.api.individual.IGenome;
import genetics.api.individual.IKaryotype;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IIndividualRootBuilder;
import genetics.api.root.ITemplateRegistry;
import genetics.api.root.components.ComponentKey;
import genetics.api.root.components.ComponentKeys;
import genetics.api.root.components.IRootComponentBuilder;

public enum PlantDefinition implements IGeneticDefinition {
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

	public void init(ITemplateRegistry templateRegistry, IIndividualRootBuilder builder) {
		IKaryotype karyotype = builder.getKaryotype();
		IAlleleTemplateBuilder template = karyotype.createTemplate();
		template.set(PlantChromosomes.SPECIES, allele);
		createTemplate(template);

		this.templateAlleles = template.build().alleles();
		genome = karyotype.templateAsGenome(this.templateAlleles);
		templateRegistry.registerTemplate(this.templateAlleles);
	}

	public static void registerTemplates(ITemplateRegistry registry) {
		IKaryotype karyotype = registry.getRoot().getKaryotype();
		for (PlantDefinition definition : values()) {
			definition.registerTemplate(karyotype, registry);
		}
	}

	private void registerTemplate(IKaryotype karyotype, ITemplateRegistry registry) {
		IAlleleTemplateBuilder template = karyotype.createTemplate();
		template.set(PlantChromosomes.SPECIES, allele);
		createTemplate(template);

		this.templateAlleles = template.build().alleles();
		this.genome = karyotype.templateAsGenome(this.templateAlleles);
		registry.registerTemplate(this.templateAlleles);
	}

	@Override
	public <B extends IRootComponentBuilder> void onComponent(ComponentKey<?, B> key, B builder) {
		IIndividualRoot root = builder.getRoot();
		IKaryotype karyotype = root.getKaryotype();
		if (key == ComponentKeys.TEMPLATES) {
			ITemplateRegistry registry = (ITemplateRegistry) builder;
			IAlleleTemplateBuilder template = karyotype.createTemplate();
			template.set(PlantChromosomes.SPECIES, allele);
			createTemplate(template);

			this.templateAlleles = template.build().alleles();
			this.genome = karyotype.templateAsGenome(this.templateAlleles);
			registry.registerTemplate(this.templateAlleles);
		}
	}

	protected void createTemplate(IAlleleTemplateBuilder template) {
	}

	public static IAlleleTemplate createDefaultTemplate(IAlleleTemplateBuilder template) {
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
	public Plant createIndividual() {
		return new Plant(genome);
	}
}
