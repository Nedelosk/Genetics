package genetics.api;

import java.util.Optional;

import genetics.api.registry.IAlleleRegistry;
import genetics.api.registry.IGeneticRegistry;
import genetics.api.registry.IGeneticSystem;

public class DummyApiInstance implements IGeneticApiInstance {
	@Override
	public Optional<IAlleleRegistry> getAlleleRegistry() {
		return Optional.empty();
	}

	@Override
	public Optional<IGeneticRegistry> getGeneticRegistry() {
		return Optional.empty();
	}

	@Override
	public Optional<IGeneticSystem> getGeneticSystem() {
		return Optional.empty();
	}

	@Override
	public Optional<IGeneticFactory> getGeneticFactory() {
		return Optional.empty();
	}

	@Override
	public Optional<IGeneticSaveHandler> getSaveHandler() {
		return Optional.empty();
	}
}
