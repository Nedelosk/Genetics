package genetics.root;

import java.util.Map;
import java.util.Optional;

import net.minecraft.item.ItemStack;

import genetics.api.GeneticHelper;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganism;
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
			if(!definition.isRootPresent()){
				continue;
			}
			IIndividualRoot root = definition.get();
			if (root.isMember(stack)) {
				return definition;
			}
		}
		return EmptyRootDefinition.empty();
	}

	@Override
	public IRootDefinition getSpeciesRoot(Class<? extends IIndividual> individualClass) {
		Map<String, IRootDefinition> definitions = ApiInstance.INSTANCE.getRoots();
		for(IRootDefinition rootDefinition : definitions.values()){
			if(!rootDefinition.isRootPresent()) {
				continue;
			}
			IIndividualRoot<?> root = rootDefinition.get();
			if(root.getMemberClass().isAssignableFrom(individualClass)){
				return rootDefinition;
			}

		}
		return EmptyRootDefinition.empty();
	}

	@Override
	public IRootDefinition getSpeciesRoot(IIndividual individual) {
		return individual.getRoot().getDefinition();
	}

	@Override
	public boolean isIndividual(ItemStack stack) {
		return getSpeciesRoot(stack).isRootPresent();
	}

	@Override
	public Optional<? extends IIndividual> getIndividual(ItemStack stack) {
		IOrganism<? extends IIndividual> organism = GeneticHelper.getOrganism(stack);
		return organism.getIndividual();
	}

	@Override
	public IAlleleTemplateBuilder createTemplate(String uid) {
		ApiInstance.INSTANCE.getRoot(uid);
		return null;
	}
}
