package nedelosk.crispr.apiimp;

import java.util.Collection;
import java.util.Optional;

import net.minecraft.item.ItemStack;

import nedelosk.crispr.api.individual.IGeneticHandler;
import nedelosk.crispr.api.individual.IGeneticType;
import nedelosk.crispr.api.individual.IIndividual;

public interface IGeneticTypes<I extends IIndividual> {
	ItemStack getMember(I individual, IGeneticType type);

	Optional<I> getMember(ItemStack itemStack);

	Optional<IGeneticType> getType(ItemStack itemStack);

	Optional<IGeneticHandler<I>> getHandler(IGeneticType type);

	Collection<IGeneticType> getTypes();

	Collection<IGeneticHandler<I>> getHandlers();
}
