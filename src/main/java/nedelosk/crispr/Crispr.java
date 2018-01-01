package nedelosk.crispr;

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

import forestry.core.capabilities.NullStorage;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.gene.IGeneTemplate;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.individual.IGeneticType;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.individual.IIndividualHandler;
import nedelosk.crispr.apiimp.AlleleRegistry;
import nedelosk.crispr.apiimp.GeneticSystem;
import nedelosk.crispr.apiimp.individual.GeneticSaveHandler;
import nedelosk.crispr.apiimp.individual.SaveFormat;
import nedelosk.crispr.plugins.PluginManager;

@Mod(modid = Crispr.MOD_ID, name = Crispr.NAME, version = Crispr.VERSION)
public class Crispr {
	public static final String MOD_ID = "crispr";
	public static final String NAME = "CRISPR API";
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
			public IGeneticDefinition<IIndividual> getDefinition() {
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
			public Allele getAllele() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IGeneType getType() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IGeneticDefinition<?> getDescription() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}
		});

		PluginManager.create(event);

		PluginManager.initPlugins();
	}

	/*@EventHandler
	public void init(FMLInitializationEvent event) {

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}*/

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {
		GeneticSaveHandler.setWriteFormat(SaveFormat.BINARY);
	}

	public class NullStorage <T> implements Capability.IStorage<T> {
		public NullStorage() { /* compiled code */ }

		@Nullable
		public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
			/* compiled code */
			return null;
		}

		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) { /* compiled code */ }
	}
}
