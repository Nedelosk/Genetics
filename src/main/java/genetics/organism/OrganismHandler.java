package genetics.organism;

import java.util.Optional;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismHandler;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IRootDefinition;

public class OrganismHandler<I extends IIndividual> implements IOrganismHandler<I> {
	private static final String INDIVIDUAL_KEY = "Individual";
	private final IRootDefinition<IIndividualRoot<I>> optionalRoot;
	private final ItemStack stack;

	public OrganismHandler(IRootDefinition<IIndividualRoot<I>> optionalRoot, ItemStack stack) {
		this.optionalRoot = optionalRoot;
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
		if (tagCompound == null || !optionalRoot.isPresent()) {
			return Optional.empty();
		}
		IIndividualRoot<I> root = this.optionalRoot.get();
		return Optional.of(root.create(tagCompound));
	}

	@Override
	public boolean setIndividual(ItemStack itemStack, I individual) {
		itemStack.setTagInfo(INDIVIDUAL_KEY, individual.writeToNBT(new NBTTagCompound()));
		return true;
	}
}
