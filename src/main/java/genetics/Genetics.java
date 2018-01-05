package genetics;

import javax.annotation.Nullable;
import java.util.Optional;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import genetics.api.GeneticsAPI;
import genetics.api.alleles.IAllele;
import genetics.api.definition.IOrganismDefinition;
import genetics.api.definition.IOrganismRoot;
import genetics.api.gene.IGeneType;
import genetics.api.individual.IOrganism;
import genetics.api.individual.IOrganismType;
import genetics.api.items.IGeneTemplate;
import genetics.api.items.IIndividualHandler;

import genetics.individual.GeneticSaveHandler;
import genetics.individual.SaveFormat;
import genetics.plugins.PluginManager;
import genetics.registry.AlleleRegistry;
import genetics.registry.GeneticSystem;

@Mod(modid = Genetics.MOD_ID, name = Genetics.NAME, version = Genetics.VERSION)
public class Genetics {
	public static final String MOD_ID = "geneticsapi";
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
		CapabilityManager.INSTANCE.register(IIndividualHandler.class, new NullStorage<>(), () -> new IIndividualHandler<IOrganism>() {
			@Override
			public Optional<IOrganism> getIndividual() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IOrganismDefinition<IOrganism, IOrganismRoot> getDefinition() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IOrganismType getType() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IAllele<?> getAlleleDirectly(IGeneType type, boolean active) {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}
		});
		CapabilityManager.INSTANCE.register(IGeneTemplate.class, new NullStorage<>(), () -> new IGeneTemplate() {
			@Override
			public Optional<IAllele<?>> getAllele() {
				return Optional.empty();
			}

			@Override
			public Optional<IGeneType> getType() {
				return Optional.empty();
			}

			@Override
			public Optional<IOrganismDefinition> getDescription() {
				return Optional.empty();
			}

			@Override
			public void setAllele(@Nullable IAllele<?> allele, @Nullable IGeneType type) {
			}
		});
		GeneticsAPI.saveHandler = GeneticSaveHandler.INSTANCE;
		GeneticsAPI.geneticFactory = GeneticFactory.INSTANCE;

		PluginManager.create(event);

		PluginManager.initPlugins();
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
