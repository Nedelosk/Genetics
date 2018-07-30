package genetics.api.events;

import java.lang.reflect.Type;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.IGenericEvent;

import genetics.api.root.IIndividualRoot;
import genetics.api.root.IRootDefinition;

public class RootEvent<R extends IIndividualRoot> extends Event implements IGenericEvent<R> {
	private final IRootDefinition<R> definition;

	private RootEvent(IRootDefinition<R> definition) {
		this.definition = definition;
	}

	@Override
	public Type getGenericType() {
		return definition.getClass();
	}

	public IRootDefinition<R> getDefinition() {
		return definition;
	}

	public static class CreationEvent<R extends IIndividualRoot> extends RootEvent<R> {
		public CreationEvent(IRootDefinition<R> definition) {
			super(definition);
		}
	}
}
