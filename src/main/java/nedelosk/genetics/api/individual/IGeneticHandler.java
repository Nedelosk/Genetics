package nedelosk.genetics.api.individual;

import net.minecraft.item.ItemStack;

public interface IGeneticHandler<I extends IIndividual> {

	boolean isMember(ItemStack itemStack);

	ItemStack getMember(I individual);

	I getMember(ItemStack itemStack);
}
