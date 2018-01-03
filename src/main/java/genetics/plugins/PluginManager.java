package genetics.plugins;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

import genetics.api.GeneticPlugin;
import genetics.api.GeneticsAPI;
import genetics.api.IGeneticPlugin;

import genetics.Genetics;
import genetics.registry.AlleleRegistry;
import genetics.registry.GeneticRegistry;
import genetics.registry.GeneticSystem;

public class PluginManager {
	private static final Comparator<IGeneticPlugin> PLUGIN_COMPARATOR = (firstPlugin, secondPlugin) -> {
		EventPriority first = firstPlugin.getClass().getAnnotation(GeneticPlugin.class).priority();
		EventPriority second = secondPlugin.getClass().getAnnotation(GeneticPlugin.class).priority();
		if (first == second) {
			return 0;
		}
		return first.ordinal() > second.ordinal() ? 1 : -1;
	};
	private static List<IGeneticPlugin> plugins = Collections.emptyList();

	private PluginManager() {
	}

	public static void create(FMLPreInitializationEvent event) {
		plugins = PluginUtil.getPlugins(event.getAsmData());
		plugins.sort(PLUGIN_COMPARATOR);
	}

	public static void initPlugins() {
		//
		plugins.forEach(p -> p.registerSimple(RegistryHelper.INSTANCE));
		//register all alleles
		AlleleRegistry alleleRegistry = new AlleleRegistry();
		GeneticsAPI.alleleRegistry = Genetics.alleleRegistry = alleleRegistry;
		RegistryHelper.INSTANCE.onRegisterAlleles(alleleRegistry);
		plugins.forEach(p -> p.registerAlleles(alleleRegistry));
		//
		GeneticRegistry registry = new GeneticRegistry();
		GeneticsAPI.geneticRegistry = registry;
		RegistryHelper.INSTANCE.onRegister(registry);
		plugins.forEach(p -> p.register(registry));
		GeneticSystem system = Genetics.system = registry.createSystem();
		//
		GeneticsAPI.geneticSystem = system;
		plugins.forEach(p -> p.onFinishRegistration(system));
	}
}
