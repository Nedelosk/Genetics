package genetics.definition;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import net.minecraft.item.ItemStack;

import genetics.api.definition.IOrganismTypes;
import genetics.api.individual.IOrganismHandler;
import genetics.api.individual.IOrganismType;
import genetics.api.individual.IOrganism;

public class GeneticTypes<I extends IOrganism> implements IOrganismTypes<I> {
	private final Map<IOrganismType, IOrganismHandler<I>> types;

	GeneticTypes(Map<IOrganismType, IOrganismHandler<I>> types) {
		this.types = types;
	}

	@Override
	public ItemStack createStack(I individual, IOrganismType type) {
		IOrganismHandler<I> handler = types.get(type);
		if (handler == null) {
			return ItemStack.EMPTY;
		}
		return handler.createStack(individual);
	}

	@Override
	public Optional<I> createIndividual(ItemStack itemStack) {
		Optional<IOrganismType> optional = getType(itemStack);
		if (!optional.isPresent()) {
			return Optional.empty();
		}
		IOrganismHandler<I> handler = types.get(optional.get());
		if (handler == null) {
			return Optional.empty();
		}
		return Optional.of(handler.createIndividual(itemStack));
	}

	@Override
	public Optional<IOrganismType> getType(ItemStack itemStack) {
		for (Map.Entry<IOrganismType, IOrganismHandler<I>> entry : types.entrySet()) {
			IOrganismHandler handler = entry.getValue();
			if (itemStack.getItem() == handler.getItem()) {
				return Optional.of(entry.getKey());
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<IOrganismHandler<I>> getHandler(IOrganismType type) {
		return Optional.ofNullable(types.get(type));
	}

	@Override
	public Collection<IOrganismType> getTypes() {
		return types.keySet();
	}

	@Override
	public Collection<IOrganismHandler<I>> getHandlers() {
		return types.values();
	}
}
