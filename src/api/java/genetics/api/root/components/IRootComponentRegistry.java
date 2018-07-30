package genetics.api.root.components;

import javax.annotation.Nullable;

public interface IRootComponentRegistry {
	void registerFactory(ComponentKey key, IRootComponentFactory factory);

	@Nullable
	IRootComponentFactory getFactory(ComponentKey key);
}
