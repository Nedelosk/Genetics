package genetics.plugins;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

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
import genetics.root.KaryotypeFactory;
import genetics.root.RootManager;

import joptsimple.internal.Strings;

public class PluginManager {
	private static final Comparator<IGeneticPlugin> PLUGIN_COMPARATOR = (firstPlugin, secondPlugin) -> {
		EventPriority first = firstPlugin.getClass().getAnnotation(GeneticPlugin.class).priority();
		EventPriority second = secondPlugin.getClass().getAnnotation(GeneticPlugin.class).priority();
		if (first.equals(second)) {
			return 0;
		}
		return first.ordinal() > second.ordinal() ? 1 : -1;
	};
	private static List<IGeneticPlugin> plugins = Collections.emptyList();
	@Nullable
	private static String activePlugin = null;

	private PluginManager() {
	}

	public static void create(FMLPreInitializationEvent event) {
		plugins = PluginUtil.getPlugins(event.getAsmData());
		plugins.sort(PLUGIN_COMPARATOR);
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
		handlePlugins(p -> p.postRootCreation(rootManager));
		handlePlugins(p -> p.onFinishRegistration(rootManager, GeneticsAPI.apiInstance));
		ApiInstance.INSTANCE.setRootRegistry(rootManager.createRegistry());
	}

	private static void handlePlugins(Consumer<IGeneticPlugin> pluginConsumer) {
		plugins.forEach(p -> {
			setActivePlugin(p.getClass().getAnnotation(GeneticPlugin.class).modId());
			pluginConsumer.accept(p);
			setActivePlugin(null);
		});
	}

	public static void setActivePlugin(@Nullable String activePlugin) {
		PluginManager.activePlugin = activePlugin;
	}

	public static String getCurrentModId() {
		if (activePlugin == null || Strings.isNullOrEmpty(activePlugin)) {
			return Genetics.MOD_ID;
		}
		return activePlugin;
	}
}
