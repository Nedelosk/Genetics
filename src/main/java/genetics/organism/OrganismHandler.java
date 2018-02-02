package genetics.organism;

import java.util.Optional;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import genetics.api.definition.IIndividualDefinition;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismHandler;

public class OrganismHandler<I extends IIndividual> implements IOrganismHandler<I> {
	private static final String INDIVIDUAL_KEY = "Individual";
	protected final IIndividualDefinition<I, ?> definition;
	protected final ItemStack stack;

	public OrganismHandler(IIndividualDefinition<I, ?> definition, ItemStack stack) {
		this.definition = definition;
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
		return Optional.of(definition.getRoot().create(tagCompound));
	}

	@Override
	public boolean setIndividual(ItemStack itemStack, I individual) {
		itemStack.setTagInfo(INDIVIDUAL_KEY, individual.writeToNBT(new NBTTagCompound()));
		return true;
	}
}
