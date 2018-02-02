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
import genetics.api.definition.IIndividualDefinition;
import genetics.api.definition.IIndividualRoot;
import genetics.api.gene.IGeneType;
import genetics.api.individual.IIndividual;
import genetics.api.items.IGeneTemplate;
import genetics.api.organism.IOrganism;
import genetics.api.organism.IOrganismType;

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
	 * Capability for {@link IOrganism}.
	 */
	@CapabilityInject(IOrganism.class)
	public static Capability<IOrganism> ORGANISM;
	@CapabilityInject(IGeneTemplate.class)
	public static Capability<IGeneTemplate> GENE_TEMPLATE;

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
			public IIndividualDefinition<IIndividual, IIndividualRoot<IIndividual, ?>> getDefinition() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IOrganismType getType() {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public IAllele<?> getAllele(IGeneType type, boolean active) {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public Optional<IAllele<?>> getAlleleDirectly(IGeneType type, boolean active) {
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
			public Optional<IIndividualDefinition> getDefinition() {
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
