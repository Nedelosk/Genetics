package genetics.root;

import java.util.Map;
import java.util.Optional;

import net.minecraft.item.ItemStack;

import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismTypes;
import genetics.api.root.EmptyRootDefinition;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IIndividualRootHelper;
import genetics.api.root.IRootDefinition;

import genetics.ApiInstance;

public enum IndividualRootHelper implements IIndividualRootHelper {
	INSTANCE;
	@Override
	public IRootDefinition getSpeciesRoot(ItemStack stack) {
		if (stack.isEmpty()) {
			return EmptyRootDefinition.empty();
		}

		Map<String, IRootDefinition> definitions = ApiInstance.INSTANCE.getRoots();
		for (IRootDefinition definition : definitions.values()) {
		/*	if(!definition.isPresent()){
				continue;
			}
			IIndividualRoot root = definition.get();
			IOrganismTypes types = root.getTypes();
			if (definition.isMember(stack)) {
				return definition;
			}*/
		}
		return EmptyRootDefinition.empty();
	}

	@Override
	public IRootDefinition getSpeciesRoot(Class<? extends IIndividual> individualClass) {
		return null;
	}

	@Override
	public IRootDefinition getSpeciesRoot(IIndividual individual) {

		return null;
	}

	/*@Override
	@Nullable
	public ISpeciesRoot getSpeciesRoot(Class<? extends IIndividual> individualClass) {
		for (ISpeciesRoot root : rootMap.values()) {
			if (root.getMemberClass().isAssignableFrom(individualClass)) {
				return root;
			}
		}
		return null;
	}

	@Override
	public ISpeciesRoot getSpeciesRoot(IIndividual individual) {
		return individual.getGenome().getSpeciesRoot();
	}*/

	@Override
	public boolean isIndividual(ItemStack stack) {
		return getSpeciesRoot(stack).isPresent();
	}

	@Override
	public Optional<? extends IIndividual> getIndividual(ItemStack stack) {
		IRootDefinition<?> rootDefinition = getSpeciesRoot(stack);
		if (!rootDefinition.isPresent()) {
			return Optional.empty();
		}
		IIndividualRoot<?> root = rootDefinition.get();
		IOrganismTypes<? extends IIndividual> types = root.getTypes();
		return types.createIndividual(stack);
	}
}
