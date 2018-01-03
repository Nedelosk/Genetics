package genetics.definition;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import net.minecraft.item.ItemStack;

import genetics.api.definition.IGeneticTypes;
import genetics.api.individual.IGeneticHandler;
import genetics.api.individual.IGeneticType;
import genetics.api.individual.IIndividual;

public class GeneticTypes<I extends IIndividual> implements IGeneticTypes<I> {
	private final Map<IGeneticType, IGeneticHandler<I>> types;

	GeneticTypes(Map<IGeneticType, IGeneticHandler<I>> types) {
		this.types = types;
	}

	@Override
	public ItemStack createStack(I individual, IGeneticType type) {
		IGeneticHandler<I> handler = types.get(type);
		if (handler == null) {
			return ItemStack.EMPTY;
		}
		return handler.createStack(individual);
	}

	@Override
	public Optional<I> createIndividual(ItemStack itemStack) {
		Optional<IGeneticType> optional = getType(itemStack);
		if (!optional.isPresent()) {
			return Optional.empty();
		}
		IGeneticHandler<I> handler = types.get(optional.get());
		if (handler == null) {
			return Optional.empty();
		}
		return Optional.of(handler.createIndividual(itemStack));
	}

	@Override
	public Optional<IGeneticType> getType(ItemStack itemStack) {
		for (Map.Entry<IGeneticType, IGeneticHandler<I>> entry : types.entrySet()) {
			IGeneticHandler handler = entry.getValue();
			if (itemStack.getItem() == handler.getItem()) {
				return Optional.of(entry.getKey());
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<IGeneticHandler<I>> getHandler(IGeneticType type) {
		return Optional.ofNullable(types.get(type));
	}

	@Override
	public Collection<IGeneticType> getTypes() {
		return types.keySet();
	}

	@Override
	public Collection<IGeneticHandler<I>> getHandlers() {
		return types.values();
	}
}
