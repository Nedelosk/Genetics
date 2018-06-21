package genetics.root;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import genetics.api.alleles.IAlleleSpecies;
import genetics.api.individual.IChromosomeType;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismType;
import genetics.api.root.IDisplayHelper;
import genetics.api.root.IIndividualRoot;

@SideOnly(Side.CLIENT)
public class DisplayHelper<I extends IIndividual> implements IDisplayHelper<I> {
	private final IIndividualRoot<I> root;
	private final Table<IOrganismType, String, ItemStack> iconStacks = HashBasedTable.create();

	public DisplayHelper(IIndividualRoot<I> root) {
		this.root = root;
	}

	@Override
	public String getShortName(IChromosomeType chromosomeType) {
		return I18n.format(getUnlocalizedShortName(chromosomeType));
	}

	@Override
	public String getUnlocalizedShortName(IChromosomeType chromosomeType) {
		return "chromosome." + chromosomeType.getName() + ".short";
	}

	@Override
	public String getLocalizedName(IChromosomeType chromosomeType) {
		return I18n.format(getUnlocalizedName(chromosomeType));
	}

	@Override
	public String getUnlocalizedName(IChromosomeType chromosomeType) {
		return "chromosome." + chromosomeType.getName();
	}

	@Override
	public ItemStack getDisplayStack(IAlleleSpecies species, IOrganismType type) {
		String registryName = species.getRegistryName().toString();
		ItemStack stack = iconStacks.get(type, registryName);
		if(stack == null){
			stack = root.createStack(species, type);
			iconStacks.put(type, registryName, stack);
		}
		return stack;
	}

	@Override
	public ItemStack getDisplayStack(IAlleleSpecies species) {
		return getDisplayStack(species, root.getTypes().getDefaultType());
	}
}
