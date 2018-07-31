package genetics.api.organism;

import java.util.Optional;

import net.minecraft.item.ItemStack;

import genetics.api.GeneticsAPI;
import genetics.api.IGeneticFactory;
import genetics.api.individual.IIndividual;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IRootDefinition;

import genetics.Genetics;

public class OrganismHelper {

	private OrganismHelper() {
	}

	public static <I extends IIndividual> IOrganism<I> createOrganism(ItemStack itemStack, IOrganismType type, IRootDefinition<? extends IIndividualRoot<I>> root) {
		IGeneticFactory geneticFactory = GeneticsAPI.apiInstance.getGeneticFactory();
		return geneticFactory.createOrganism(itemStack, type, root);
	}

	@SuppressWarnings("unchecked")
	public static <I extends IIndividual> IOrganism<I> getOrganism(ItemStack itemStack) {
		return (IOrganism<I>) itemStack.getCapability(Genetics.ORGANISM, null);
	}

	@SuppressWarnings("unchecked")
	public static <I extends IIndividual> boolean setIndividual(ItemStack itemStack, I individual) {
		IOrganism<I> organism = itemStack.getCapability(Genetics.ORGANISM, null);
		return organism != null && organism.setIndividual(individual);
	}

	public static IOrganismHandler getOrganismHandler(IIndividualRoot<IIndividual> root, IOrganismType type) {
		Optional<IOrganismHandler<IIndividual>> optionalHandler = root.getTypes().getHandler(type);
		if (!optionalHandler.isPresent()) {
			throw new IllegalArgumentException(String.format("No organism handler was registered for the organism type '%s'", type.getName()));
		}
		return optionalHandler.get();
	}
}
