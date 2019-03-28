package genetics;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import net.minecraft.nbt.INBTBase;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import genetics.api.GeneticsAPI;
import genetics.api.IGeneTemplate;
import genetics.api.alleles.IAllele;
import genetics.api.individual.IChromosomeType;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganism;
import genetics.api.organism.IOrganismType;
import genetics.api.root.IIndividualRoot;

import genetics.individual.GeneticSaveHandler;
import genetics.individual.SaveFormat;
import genetics.plugins.PluginManager;

@Mod(Genetics.MOD_ID)
public class Genetics {
	public static final String MOD_ID = "geneticsapi";

	/**
	 * Capability for {@link IOrganism}.
	 */
	@CapabilityInject(IOrganism.class)
	public static Capability<IOrganism> ORGANISM;
	@CapabilityInject(IGeneTemplate.class)
	public static Capability<IGeneTemplate> GENE_TEMPLATE;

	public Genetics() {
		GeneticsAPI.apiInstance = ApiInstance.INSTANCE;
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
	}

	public void preInit(FMLCommonSetupEvent event) {
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
			public boolean isEmpty() {
				return false;
			}

			@Override
			public IAllele getAllele(IChromosomeType type, boolean active) {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Override
			public Optional<IAllele> getAlleleDirectly(IChromosomeType type, boolean active) {
				throw new UnsupportedOperationException("Cannot use default implementation");
			}

			@Nonnull
			@Override
			public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing facing) {
				return LazyOptional.empty();
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
			public void setAllele(@Nullable IChromosomeType type, @Nullable IAllele allele) {
			}
		});

		PluginManager.create();

		PluginManager.initPlugins();
	}

	public void loadComplete(FMLLoadCompleteEvent event) {
		GeneticSaveHandler.setWriteFormat(SaveFormat.BINARY);
	}

	public class NullStorage<T> implements Capability.IStorage<T> {
		@Nullable
		public INBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
			/* compiled code */
			return null;
		}

		@Override
		public void readNBT(Capability<T> capability, T instance, EnumFacing side, INBTBase nbt) {
			/* compiled code */
		}
	}
}
