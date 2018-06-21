package genetics.api.events;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.IGenericEvent;

import genetics.api.GeneticsAPI;
import genetics.api.alleles.IAlleleRegistry;
import genetics.api.individual.IChromosomeType;
import genetics.api.mutation.IMutation;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IRootDefinition;

public class RootEvent extends Event {
	private RootEvent() {
	}

	public static class CreationEvent<R extends IIndividualRoot> extends RootEvent {
		private final IRootDefinition<R> definition;

		public CreationEvent(IRootDefinition<R> definition) {
			this.definition = definition;
		}

		public IRootDefinition<R> getDefinition() {
			return definition;
		}
	}

	public static class MutationEvent<M extends IMutation> extends RootEvent implements IGenericEvent<M> {

		private final List<M> mutations = new ArrayList<>();
		private final IIndividualRoot root;
		private final Class<? extends M> type;

		public MutationEvent(IIndividualRoot root, Class<? extends M> type) {
			this.root = root;
			this.type = type;
		}

		public boolean registerMutation(M mutation){
			IChromosomeType speciesType = root.getKaryotype().getSpeciesType();
			IAlleleRegistry alleleRegistry = GeneticsAPI.apiInstance.getAlleleRegistry();
			if (alleleRegistry.isBlacklisted(mutation.getTemplate()[speciesType.getIndex()].getRegistryName())) {
				return false;
			}
			if (alleleRegistry.isBlacklisted(mutation.getFirstAllele().getRegistryName())) {
				return false;
			}
			if (alleleRegistry.isBlacklisted(mutation.getSecondAllele().getRegistryName())) {
				return false;
			}

			return mutations.add(mutation);
		}

		public List<? extends M> getMutations() {
			return Collections.unmodifiableList(mutations);
		}

		@Override
		public Type getGenericType() {
			return type;
		}
	}
}
