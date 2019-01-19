package genetics.organism;

import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismHandler;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IRootDefinition;

public class OrganismHandler<I extends IIndividual> implements IOrganismHandler<I> {
	private static final String INDIVIDUAL_KEY = "Individual";
	private final IRootDefinition<? extends IIndividualRoot<I>> optionalRoot;
	private final Supplier<ItemStack> stack;

	public OrganismHandler(IRootDefinition<? extends IIndividualRoot<I>> optionalRoot, Supplier<ItemStack> stack) {
		this.optionalRoot = optionalRoot;
		this.stack = stack;
	}

	@Override
	public ItemStack createStack(I individual) {
		ItemStack itemStack = stack.get();
		itemStack.setTagInfo(INDIVIDUAL_KEY, individual.writeToNBT(new NBTTagCompound()));
		return itemStack;
	}

	@Override
	public Optional<I> createIndividual(ItemStack itemStack) {
		NBTTagCompound tagCompound = itemStack.getChildTag(INDIVIDUAL_KEY);
		if (tagCompound == null || !optionalRoot.isRootPresent()) {
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

	@Override
	public void setIndividualData(ItemStack itemStack, NBTTagCompound compound) {
		itemStack.setTagInfo(INDIVIDUAL_KEY, compound);
	}

	@Override
	public NBTTagCompound getIndividualData(ItemStack itemStack) {
		return itemStack.getChildTag(INDIVIDUAL_KEY);
	}
}
