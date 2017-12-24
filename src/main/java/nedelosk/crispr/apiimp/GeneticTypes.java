package nedelosk.crispr.apiimp;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import net.minecraft.item.ItemStack;

import nedelosk.crispr.api.individual.IGeneticHandler;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.individual.IGeneticType;

public class GeneticTypes<I extends IIndividual> implements IGeneticTypes<I> {
	private final Map<IGeneticType, IGeneticHandler<I>> types;

	GeneticTypes(Map<IGeneticType, IGeneticHandler<I>> types) {
		this.types = types;
	}

	@Override
	public ItemStack getMember(I individual, IGeneticType type) {
		IGeneticHandler<I> handler = types.get(type);
		if(handler == null){
			return ItemStack.EMPTY;
		}
		return handler.getMember(individual);
	}

	@Override
	public Optional<I> getMember(ItemStack itemStack) {
		Optional<IGeneticType> optional = getType(itemStack);
		if(!optional.isPresent()){
			return Optional.empty();
		}
		IGeneticType type = optional.get();
		IGeneticHandler<I> handler = types.get(type);
		if(handler == null){
			return Optional.empty();
		}
		return Optional.of(handler.getMember(itemStack));
	}

	@Override
	public Optional<IGeneticType> getType(ItemStack itemStack) {
		for(Map.Entry<IGeneticType, IGeneticHandler<I>> entry : types.entrySet()){
			IGeneticHandler handler = entry.getValue();
			if(handler.isMember(itemStack)){
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
