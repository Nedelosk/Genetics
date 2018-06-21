package genetics.api.root;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import genetics.api.alleles.IAlleleSpecies;
import genetics.api.individual.IChromosomeType;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismType;

@SideOnly(Side.CLIENT)
public interface IDisplayHelper<I extends IIndividual> {

	String getShortName(IChromosomeType type);

	String getUnlocalizedShortName(IChromosomeType type);

	String getLocalizedName(IChromosomeType type);

	String getUnlocalizedName(IChromosomeType type);

	/**
	 * Retrieves a stack that can and should only be used on the client side in a gui.
	 *
	 * @return A empty stack, if the species was not registered before the creation of this handler or if the species is
	 * 			not a species of the {@link IIndividualRoot}.
	 */
	ItemStack getDisplayStack(IAlleleSpecies species, IOrganismType type);

	ItemStack getDisplayStack(IAlleleSpecies species);
}
