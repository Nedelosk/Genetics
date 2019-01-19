package genetics.api.root;

import java.util.Optional;

import net.minecraft.item.ItemStack;

import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.individual.IIndividual;

public interface IIndividualRootHelper {
	/**
	 * Retrieve a matching {@link IRootDefinition} for the given itemstack.
	 *
	 * @param stack An itemstack possibly containing NBT data which can be converted by a species root.
	 * @return {@link IRootDefinition} if found, empty otherwise.
	 */
	IRootDefinition getSpeciesRoot(ItemStack stack);

	/**
	 * Retrieve a matching {@link IRootDefinition} for the given {@link IIndividual}-class.
	 *
	 * @param individualClass Class extending {@link IIndividual}.
	 * @return {@link IRootDefinition} if found, null otherwise.
	 */
	IRootDefinition getSpeciesRoot(Class<? extends IIndividual> individualClass);

	/**
	 * Retrieve a matching {@link IRootDefinition} for the given {@link IIndividual}
	 */
	IRootDefinition getSpeciesRoot(IIndividual individual);

	boolean isIndividual(ItemStack stack);

	Optional<? extends IIndividual> getIndividual(ItemStack stack);

	IAlleleTemplateBuilder createTemplate(String uid);
}
