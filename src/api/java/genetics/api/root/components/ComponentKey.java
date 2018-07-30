package genetics.api.root.components;

public class ComponentKey<C extends IRootComponent, B extends IRootComponentBuilder> {

	private final String name;
	private final Class<C> componentClass;

	public ComponentKey(String name, Class<C> componentClass) {
		this.name = name;
		this.componentClass = componentClass;
	}

	@SuppressWarnings("unchecked")
	public <R> R cast(C instance) {
		return (R) instance;
	}

	public Class<C> getComponentClass() {
		return componentClass;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return o == this || (o != null && o.toString().equals(name));
	}
}
