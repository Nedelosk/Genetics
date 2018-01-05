package genetics.definition;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import genetics.api.IBlockTranslator;
import genetics.api.IItemTranslator;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.definition.IOrganismDefinition;
import genetics.api.definition.IOrganismRoot;
import genetics.api.definition.IGeneticTranslator;
import genetics.api.definition.IOrganismTypes;
import genetics.api.definition.ITemplateContainer;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IOrganism;
import genetics.api.individual.IOrganismHandler;
import genetics.api.individual.IOrganismType;
import genetics.api.individual.IGenome;

public class OrganismDefinition<I extends IOrganism, R extends IOrganismRoot<I, ?>> implements IOrganismDefinition<I, R> {
	private final IOrganismTypes<I> types;
	private final IGeneticTranslator<I> translator;
	private final ITemplateContainer templateContainer;
	private final IKaryotype karyotype;
	private final String uid;
	private final R root;

	OrganismDefinition(IOrganismTypes<I> types, IGeneticTranslator<I> translator, ITemplateContainer templateContainer, String uid, Function<IOrganismDefinition<I, R>, R> rootFactory) {
		this.types = types;
		this.translator = translator;
		this.uid = uid;
		this.karyotype = templateContainer.getKaryotype();
		this.templateContainer = templateContainer;
		this.root = rootFactory.apply(this);
	}

	@Override
	public String getUID() {
		return uid;
	}

	@Override
	public R getRoot() {
		return root;
	}

	@Override
	public Optional<IItemTranslator<I>> getTranslator(Item translatorKey) {
		return translator.getTranslator(translatorKey);
	}

	@Override
	public Optional<IBlockTranslator<I>> getTranslator(Block translatorKey) {
		return translator.getTranslator(translatorKey);
	}

	@Override
	public Optional<I> translateMember(IBlockState objectToTranslate) {
		return translator.translateMember(objectToTranslate);
	}

	@Override
	public Optional<I> translateMember(ItemStack objectToTranslate) {
		return translator.translateMember(objectToTranslate);
	}

	@Override
	public ItemStack getGeneticEquivalent(IBlockState objectToTranslate) {
		return translator.getGeneticEquivalent(objectToTranslate);
	}

	@Override
	public ItemStack getGeneticEquivalent(ItemStack objectToTranslate) {
		return translator.getGeneticEquivalent(objectToTranslate);
	}

	@Override
	public ItemStack createStack(I individual, IOrganismType type) {
		return types.createStack(individual, type);
	}

	@Override
	public Optional<I> createIndividual(ItemStack itemStack) {
		return types.createIndividual(itemStack);
	}

	@Override
	public Optional<IOrganismType> getType(ItemStack itemStack) {
		return types.getType(itemStack);
	}

	@Override
	public Optional<IOrganismHandler<I>> getHandler(IOrganismType type) {
		return types.getHandler(type);
	}

	@Override
	public Collection<IOrganismType> getTypes() {
		return types.getTypes();
	}

	@Override
	public Collection<IOrganismHandler<I>> getHandlers() {
		return types.getHandlers();
	}

	@Override
	public IAlleleTemplate getDefaultTemplate() {
		return karyotype.getDefaultTemplate();
	}

	@Override
	public IGenome getDefaultGenome() {
		return karyotype.getDefaultGenome();
	}

	@Override
	public IAlleleTemplateBuilder createTemplate() {
		return karyotype.createTemplate();
	}

	@Override
	public IAlleleTemplateBuilder createTemplate(IAllele[] alleles) {
		return karyotype.createTemplate(alleles);
	}

	@Nullable
	@Override
	public IAllele[] getTemplate(String identifier) {
		return templateContainer.getTemplate(identifier);
	}

	@Override
	public IAllele[] getRandomTemplate(Random rand) {
		return templateContainer.getRandomTemplate(rand);
	}

	@Override
	public Map<String, IAllele[]> getGenomeTemplates() {
		return templateContainer.getGenomeTemplates();
	}

	@Override
	public IKaryotype getKaryotype() {
		return karyotype;
	}

	@Override
	public String getIdentifier() {
		return karyotype.getIdentifier();
	}

	@Override
	public IGeneType[] getGeneTypes() {
		return karyotype.getGeneTypes();
	}

	@Override
	public boolean contains(IGeneType type) {
		return karyotype.contains(type);
	}

	@Override
	public IGeneType getTemplateType() {
		return karyotype.getTemplateType();
	}

	@Override
	public IChromosome[] templateAsChromosomes(IAllele[] templateActive, @Nullable IAllele[] templateInactive) {
		return karyotype.templateAsChromosomes(templateActive, templateInactive);
	}

	@Override
	public IGenome templateAsGenome(IAllele[] templateActive, @Nullable IAllele[] templateInactive) {
		return karyotype.templateAsGenome(templateActive, templateInactive);
	}
}
