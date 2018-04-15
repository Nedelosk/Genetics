package genetics.api.root;

import javax.annotation.Nullable;
import java.util.Optional;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import genetics.api.alleles.IAllele;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IGenome;
import genetics.api.individual.IGenomeWrapper;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismType;
import genetics.api.organism.IOrganismTypes;

/**
 * The IGeneticRoot offers several functions to create {@link IIndividual}s and to wrap the genome of a
 * {@link IIndividual}.
 *
 * The IGeneticDefinition is wraps every interface like the {@link IIndividualTranslator}, the {@link IKaryotype}, etc.
 * that are important for the handling of the individual. And it provides the custom implementation of the
 * {@link IIndividualRoot} interface that specifies the individual and can be used to create a instance of it.
 *
 * @param <I> The type of the individual that this root provides.
 */
public interface IIndividualRoot<I extends IIndividual> {

	/**
	 * Uses the information that the NBT-Data contains to create a {@link IIndividual}.
	 */
	I create(NBTTagCompound compound);

	/**
	 * Creates a {@link IIndividual} that contains this genome.
	 */
	I create(IGenome genome);

	/**
	 * Creates a {@link IIndividual} that contains the two genome.
	 */
	I create(IGenome genome, IGenome mate);

	Optional<I> create(String templateIdentifier);

	/**
	 * Creates a {@link IIndividual} that contains the alleles of the template in a genome.
	 *
	 * @param template The alleles of the genome.
	 */
	default I templateAsIndividual(IAllele[] template) {
		return templateAsIndividual(template, null);
	}

	/**
	 * Creates a {@link IIndividual} that contains the alleles of the two templates in a genome.
	 *
	 * @param templateActive   The active alleles of the genome.
	 * @param templateInactive The inactive alleles of the genome.
	 */
	I templateAsIndividual(IAllele[] templateActive, @Nullable IAllele[] templateInactive);

	/**
	 * A instance of an {@link IIndividual} that is used if a item has lost its generic data.
	 */
	I getDefaultMember();

	/**
	 * Creates a wrapper that can be used to give access to the values of the alleles that the genome contains.
	 */
	IGenomeWrapper createWrapper(IGenome genome);

	/**
	 * @return The string based unique identifier of this definition.
	 */
	String getUID();

	ItemStack createStack(IAllele allele, IOrganismType type);

	ITemplateContainer getTemplates();

	IKaryotype getKaryotype();

	IIndividualTranslator<I> getTranslator();

	IOrganismTypes<I> getTypes();
}
