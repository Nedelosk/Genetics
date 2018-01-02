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

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.definition.IGeneticDefinition;
import genetics.api.definition.IGeneticRoot;
import genetics.api.definition.IGeneticTypes;
import genetics.api.definition.ITemplateContainer;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGeneticHandler;
import genetics.api.individual.IGeneticType;
import genetics.api.individual.IGenome;
import genetics.api.individual.IIndividual;
import genetics.api.translators.IBlockTranslator;
import genetics.api.translators.IGeneticTranslator;
import genetics.api.translators.IItemTranslator;

public class GeneticDefinition<I extends IIndividual, R extends IGeneticRoot<I, ?>> implements IGeneticDefinition<I, R> {
	private final IGeneticTypes<I> types;
	private final IGeneticTranslator<I> translator;
	private final ITemplateContainer templateContainer;
	private final IKaryotype karyotype;
	private final String uid;
	private final R root;

	GeneticDefinition(IGeneticTypes<I> types, IGeneticTranslator<I> translator, ITemplateContainer templateContainer, String uid, Function<IGeneticDefinition<I, R>, R> rootFactory) {
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
	public R root() {
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
	public ItemStack getMember(I individual, IGeneticType type) {
		return types.getMember(individual, type);
	}

	@Override
	public Optional<I> getMember(ItemStack itemStack) {
		return types.getMember(itemStack);
	}

	@Override
	public Optional<IGeneticType> getType(ItemStack itemStack) {
		return types.getType(itemStack);
	}

	@Override
	public Optional<IGeneticHandler<I>> getHandler(IGeneticType type) {
		return types.getHandler(type);
	}

	@Override
	public Collection<IGeneticType> getTypes() {
		return types.getTypes();
	}

	@Override
	public Collection<IGeneticHandler<I>> getHandlers() {
		return types.getHandlers();
	}

	@Override
	public IAlleleTemplate getDefaultTemplate() {
		return templateContainer.getDefaultTemplate();
	}

	@Override
	public IAlleleTemplateBuilder createTemplate() {
		return templateContainer.createTemplate();
	}

	@Override
	public IAlleleTemplateBuilder createTemplate(IAllele[] alleles) {
		return templateContainer.createTemplate(alleles);
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
