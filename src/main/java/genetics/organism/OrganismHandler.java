package genetics.organism;

import java.util.Optional;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismHandler;
import genetics.api.root.IIndividualRoot;

public class OrganismHandler<I extends IIndividual> implements IOrganismHandler<I> {
	private static final String INDIVIDUAL_KEY = "Individual";
	protected final IIndividualRoot<I> root;
	protected final ItemStack stack;

	public OrganismHandler(IIndividualRoot<I> root, ItemStack stack) {
		this.root = root;
		this.stack = stack;
	}

	@Override
	public ItemStack createStack(I individual) {
		ItemStack itemStack = stack.copy();
		itemStack.setTagInfo(INDIVIDUAL_KEY, individual.writeToNBT(new NBTTagCompound()));
		return itemStack;
	}

	@Override
	public Optional<I> createIndividual(ItemStack itemStack) {
		NBTTagCompound tagCompound = itemStack.getSubCompound(INDIVIDUAL_KEY);
		if (tagCompound == null) {
			return Optional.empty();
		}
		return Optional.of(root.create(tagCompound));
	}

	@Override
	public boolean setIndividual(ItemStack itemStack, I individual) {
		itemStack.setTagInfo(INDIVIDUAL_KEY, individual.writeToNBT(new NBTTagCompound()));
		return true;
	}
}
