package genetics;

import javax.annotation.Nullable;
import java.util.Optional;

import genetics.api.IGeneticApiInstance;
import genetics.api.IGeneticFactory;
import genetics.api.IGeneticSaveHandler;
import genetics.api.registry.IAlleleRegistry;
import genetics.api.registry.IGeneticRegistry;
import genetics.api.registry.IGeneticSystem;

public enum ApiInstance implements IGeneticApiInstance {
	INSTANCE;

	@Nullable
	private IAlleleRegistry alleleRegistry;
	@Nullable
	private IGeneticRegistry geneticRegistry;
	@Nullable
	private IGeneticSystem geneticSystem;
	@Nullable
	private IGeneticFactory geneticFactory;
	@Nullable
	private IGeneticSaveHandler saveHandler;

	@Override
	public Optional<IAlleleRegistry> getAlleleRegistry() {
		return Optional.ofNullable(alleleRegistry);
	}

	public void setAlleleRegistry(@Nullable IAlleleRegistry alleleRegistry) {
		this.alleleRegistry = alleleRegistry;
	}

	@Override
	public Optional<IGeneticRegistry> getGeneticRegistry() {
		return Optional.ofNullable(geneticRegistry);
	}

	public void setGeneticRegistry(@Nullable IGeneticRegistry geneticRegistry) {
		this.geneticRegistry = geneticRegistry;
	}

	@Override
	public Optional<IGeneticSystem> getGeneticSystem() {
		return Optional.ofNullable(geneticSystem);
	}

	public void setGeneticSystem(@Nullable IGeneticSystem geneticSystem) {
		this.geneticSystem = geneticSystem;
	}

	@Override
	public Optional<IGeneticFactory> getGeneticFactory() {
		return Optional.ofNullable(geneticFactory);
	}

	public void setGeneticFactory(@Nullable IGeneticFactory geneticFactory) {
		this.geneticFactory = geneticFactory;
	}

	@Override
	public Optional<IGeneticSaveHandler> getSaveHandler() {
		return Optional.ofNullable(saveHandler);
	}

	public void setSaveHandler(@Nullable IGeneticSaveHandler saveHandler) {
		this.saveHandler = saveHandler;
	}
}
