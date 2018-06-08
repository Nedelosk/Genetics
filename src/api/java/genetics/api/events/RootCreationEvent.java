package genetics.api.events;

import net.minecraftforge.fml.common.eventhandler.Event;

import genetics.api.root.IIndividualRoot;
import genetics.api.root.IRootDefinition;

public class RootCreationEvent<R extends IIndividualRoot> extends Event {
	private final IRootDefinition<R> definition;

	public RootCreationEvent(IRootDefinition<R> definition) {
		this.definition = definition;
	}

	public IRootDefinition<R> getDefinition() {
		return definition;
	}
}
