package genetics;

import javax.annotation.Nullable;
import java.util.Optional;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import genetics.api.GeneticsAPI;
import genetics.api.IGeneTemplate;
import genetics.api.alleles.IAllele;
import genetics.api.gene.IChromosomeType;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganism;
import genetics.api.organism.IOrganismType;
import genetics.api.root.IIndividualRoot;

import genetics.individual.GeneticSaveHandler;
import genetics.individual.SaveFormat;
import genetics.plugins.PluginManager;
import genetics.utils.WorldEventHandler;

@Mod(modid = Genetics.MOD_ID, name = Genetics.NAME, version = Genetics.VERSION)
public class Genetics {
	public static final String MOD_ID = "geneticsapi";
	public static final String NAME = "Genetics";
	public static final String VERSION = "@VERSION@";

	/**
	 * Capability for {@link IOrganism}.
	 */
	@CapabilityInject(IOrganism.class)
	public static Capability<IOrganism> ORGANISM;
	@CapabilityInject(IGeneTemplate.class)
	public static Capability<IGeneTemplate> GENE_TEMPLATE;

	public Genetics() {
		GeneticsAPI.apiInstance = ApiInstance.INSTANCE;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		CapabilityManager.INSTANCE.register(IOrganism.class, new NullStorage<>(), () -> new IOrganism<IIndividual>() {
			@Override
			public Optional<IIndividual> getIndividual() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public boolean setIndividual(IIndividual individual) {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IIndividualRoot<IIndividual> getRoot() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IOrganismType getType() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IAllele<?> getAllele(IChromosomeType type, boolean active) {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public Optional<IAllele> getAlleleDirectly(IChromosomeType type, boolean active) {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}
		});
		CapabilityManager.INSTANCE.register(IGeneTemplate.class, new NullStorage<>(), () -> new IGeneTemplate() {
			@Override
			public Optional<IAllele> getAllele() {
				return Optional.empty();
			}

			@Override
			public Optional<IChromosomeType> getType() {
				return Optional.empty();
			}

			@Override
			public Optional<IIndividualRoot> getRoot() {
				return Optional.empty();
			}

			@Override
			public void setAllele(@Nullable IAllele allele, @Nullable IChromosomeType type) {
			}
		});

		PluginManager.create(event);

		PluginManager.initPlugins();
		MinecraftForge.EVENT_BUS.register(new WorldEventHandler());
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {
		GeneticSaveHandler.setWriteFormat(SaveFormat.BINARY);
	}

	public class NullStorage<T> implements Capability.IStorage<T> {
		@Nullable
		public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
			/* compiled code */
			return null;
		}

		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) { /* compiled code */ }
	}
}
