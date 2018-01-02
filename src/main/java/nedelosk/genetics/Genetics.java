package nedelosk.genetics;

import javax.annotation.Nullable;
import java.util.Optional;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import nedelosk.genetics.api.GeneticsAPI;
import nedelosk.genetics.api.alleles.IAllele;
import nedelosk.genetics.api.definition.IGeneticDefinition;
import nedelosk.genetics.api.definition.IGeneticRoot;
import nedelosk.genetics.api.gene.IGeneTemplate;
import nedelosk.genetics.api.gene.IGeneType;
import nedelosk.genetics.api.individual.IGeneticType;
import nedelosk.genetics.api.individual.IIndividual;
import nedelosk.genetics.api.individual.IIndividualHandler;
import nedelosk.genetics.individual.GeneticSaveHandler;
import nedelosk.genetics.individual.SaveFormat;
import nedelosk.genetics.plugins.PluginManager;
import nedelosk.genetics.registry.AlleleRegistry;
import nedelosk.genetics.registry.GeneticSystem;

@Mod(modid = Genetics.MOD_ID, name = Genetics.NAME, version = Genetics.VERSION)
public class Genetics {
	public static final String MOD_ID = "genetics";
	public static final String NAME = "Genetics";
	public static final String VERSION = "@VERSION@";

	public static GeneticSystem system;
	public static AlleleRegistry alleleRegistry;

	/**
	 * Capability for {@link IIndividualHandler}.
	 */
	@CapabilityInject(IIndividualHandler.class)
	public static Capability<IIndividualHandler> INDIVIDUAL_HANDLER;
	@CapabilityInject(IGeneTemplate.class)
	public static Capability<IGeneTemplate> GENE_TEMPLATE;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		CapabilityManager.INSTANCE.register(IIndividualHandler.class, new NullStorage<>(), () -> new IIndividualHandler<IIndividual>() {
			@Override
			public Optional<IIndividual> getIndividual() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IGeneticDefinition<IIndividual, IGeneticRoot> getDefinition() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IGeneticType getType() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IAllele<?> getAlleleDirectly(IGeneType type, boolean active) {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}
		});
		CapabilityManager.INSTANCE.register(IGeneTemplate.class, new NullStorage<>(), () -> new IGeneTemplate() {
			@Override
			public IAllele getAllele() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IGeneType getType() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IGeneticDefinition getDescription() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}
		});
		GeneticsAPI.saveHandler = GeneticSaveHandler.INSTANCE;

		PluginManager.create(event);

		PluginManager.initPlugins();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {
		GeneticSaveHandler.setWriteFormat(SaveFormat.BINARY);
	}

	public class NullStorage<T> implements Capability.IStorage<T> {
		public NullStorage() { /* compiled code */ }

		@Nullable
		public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
			/* compiled code */
			return null;
		}

		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) { /* compiled code */ }
	}
}
