package genetics.plugins;

import com.google.common.collect.ImmutableSortedMap;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Consumer;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

import genetics.api.GeneticPlugin;
import genetics.api.GeneticsAPI;
import genetics.api.IGeneticPlugin;

import genetics.ApiInstance;
import genetics.Genetics;
import genetics.alleles.AlleleRegistry;
import genetics.classification.ClassificationRegistry;
import genetics.gene.GeneFactory;
import genetics.root.IndividualRootBuilder;
import genetics.root.KaryotypeFactory;
import genetics.root.RootManager;

public class PluginManager {
	private static final Comparator<IGeneticPlugin> PLUGIN_COMPARATOR = (firstPlugin, secondPlugin) -> {
		EventPriority first = firstPlugin.getClass().getAnnotation(GeneticPlugin.class).priority();
		EventPriority second = secondPlugin.getClass().getAnnotation(GeneticPlugin.class).priority();
		if (first.equals(second)) {
			return firstPlugin.hashCode() - secondPlugin.hashCode();
		}
		return first.ordinal() > second.ordinal() ? 1 : -1;
	};
	private static ImmutableSortedMap<IGeneticPlugin, ModContainer> plugins;
	/* The modID of the current active plugin*/
	@Nullable
	private static ModContainer activeContainer = null;

	private PluginManager() {
	}

	public static void create(FMLPreInitializationEvent event) {
		ImmutableSortedMap.Builder<IGeneticPlugin, ModContainer> builder = new ImmutableSortedMap.Builder<>(PLUGIN_COMPARATOR);
		builder.putAll(PluginUtil.getPlugins(event.getAsmData()));
		plugins = builder.build();
	}

	public static void initPlugins() {
		//
		ClassificationRegistry classificationRegistry = new ClassificationRegistry();
		ApiInstance.INSTANCE.setClassificationRegistry(classificationRegistry);
		handlePlugins(p -> p.registerClassifications(classificationRegistry));
		//
		handlePlugins(p -> p.registerSimple(RegistryHelper.INSTANCE));
		//register all alleles
		AlleleRegistry alleleRegistry = new AlleleRegistry();
		ApiInstance.INSTANCE.setAlleleRegistry(alleleRegistry);
		RegistryHelper.INSTANCE.onRegisterAlleles(alleleRegistry);
		handlePlugins(p -> p.registerAlleles(alleleRegistry));
		//
		GeneFactory geneFactory = new GeneFactory();
		RegistryHelper.INSTANCE.onRegister(geneFactory);
		handlePlugins(p -> p.registerGenes(geneFactory));
		ApiInstance.INSTANCE.setGeneRegistry(geneFactory.createRegistry());
		//
		KaryotypeFactory karyotypeFactory = new KaryotypeFactory();
		handlePlugins(p -> p.createKaryotype(karyotypeFactory));
		//
		RootManager rootManager = new RootManager();
		handlePlugins(p -> p.createRoot(rootManager));
		handlePlugins(p -> p.initRoots(rootManager));
		Map<String, IndividualRootBuilder> rootBuilders = rootManager.getRootBuilders();
		for (IndividualRootBuilder builder : rootBuilders.values()) {
			builder.create();
		}
		handlePlugins(p -> p.onFinishRegistration(rootManager, GeneticsAPI.apiInstance));
	}

	private static void handlePlugins(Consumer<IGeneticPlugin> pluginConsumer) {
		Loader loader = Loader.instance();
		ModContainer oldContainer = loader.activeModContainer();
		plugins.forEach((plugin, container) -> {
			loader.setActiveModContainer(container);
			setActiveContainer(container);
			pluginConsumer.accept(plugin);
			setActiveContainer(null);
		});
		loader.setActiveModContainer(oldContainer);
	}

	static void setActiveContainer(@Nullable ModContainer activeContainer) {
		PluginManager.activeContainer = activeContainer;
	}

	public static String getCurrentModId() {
		if (activeContainer == null) {
			return Genetics.MOD_ID;
		}
		return activeContainer.getModId();
	}

	public static ModContainer getActiveContainer() {
		if (activeContainer == null) {
			throw new IllegalStateException();
		}
		return activeContainer;
	}
}
