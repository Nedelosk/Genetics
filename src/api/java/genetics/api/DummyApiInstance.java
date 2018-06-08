package genetics.api;

import net.minecraftforge.fml.common.Loader;

import genetics.api.alleles.IAlleleRegistry;
import genetics.api.classification.IClassificationRegistry;
import genetics.api.gene.IGeneRegistry;
import genetics.api.root.EmptyRootDefinition;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IRootDefinition;

public class DummyApiInstance implements IGeneticApiInstance {
	private static final String ERROR_MESSAGE = "The mod %s tried to access the genetics api without checking if the mod is present. Please report this to the mod author.";

	@Override
	public IClassificationRegistry getClassificationRegistry() {
		throw new IllegalStateException(String.format(ERROR_MESSAGE, Loader.instance().activeModContainer()));
	}

	@Override
	public IAlleleRegistry getAlleleRegistry() {
		throw new IllegalStateException(String.format(ERROR_MESSAGE, Loader.instance().activeModContainer()));
	}

	@Override
	public IGeneRegistry getGeneRegistry() {
		throw new IllegalStateException(String.format(ERROR_MESSAGE, Loader.instance().activeModContainer()));
	}

	@Override
	public IGeneticFactory getGeneticFactory() {
		throw new IllegalStateException(String.format(ERROR_MESSAGE, Loader.instance().activeModContainer()));
	}

	@Override
	public IGeneticSaveHandler getSaveHandler() {
		throw new IllegalStateException(String.format(ERROR_MESSAGE, Loader.instance().activeModContainer()));
	}

	@Override
	public <R extends IIndividualRoot> IRootDefinition<R> getRoot(String rootUID) {
		return EmptyRootDefinition.empty();
	}

	@Override
	public boolean isModPresent() {
		return false;
	}
}
