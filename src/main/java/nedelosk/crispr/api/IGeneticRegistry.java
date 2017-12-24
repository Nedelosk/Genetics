package nedelosk.crispr.api;

import javax.annotation.Nullable;
import java.util.Collection;

public interface IGeneticRegistry {
	void registerRoot(IGeneticDefinition root);

	Collection<IGeneticDefinition> getRoots();

	@Nullable
	IGeneticDefinition getRoot(String name);

	IGeneticFactory factory();
}
