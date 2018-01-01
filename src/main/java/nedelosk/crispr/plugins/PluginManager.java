package nedelosk.crispr.plugins;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

import nedelosk.crispr.Crispr;
import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.GeneticPlugin;
import nedelosk.crispr.api.IGeneticPlugin;
import nedelosk.crispr.apiimp.AlleleRegistry;
import nedelosk.crispr.apiimp.GeneticFactory;
import nedelosk.crispr.apiimp.GeneticSystem;
import nedelosk.crispr.apiimp.gene.GeneRegistry;

public class PluginManager {
	private static List<IGeneticPlugin> plugins = Collections.emptyList();
	private static final Comparator<IGeneticPlugin> PLUGIN_COMPARATOR = (firstPlugin, secondPlugin) -> {
		EventPriority first = firstPlugin.getClass().getAnnotation(GeneticPlugin.class).priority();
		EventPriority second = secondPlugin.getClass().getAnnotation(GeneticPlugin.class).priority();
		if (first == second) {
			return 0;
		}
		return first.ordinal() > second.ordinal() ? 1 : -1;
	};

	private PluginManager() {
	}

	public static void create(FMLPreInitializationEvent event) {
		plugins = PluginUtil.getPlugins(event.getAsmData());
		plugins.sort(PLUGIN_COMPARATOR);
	}

	public static void initPlugins() {
		//register all alleles
		AlleleRegistry alleleRegistry = new AlleleRegistry();
		CrisprAPI.alleleRegistry = Crispr.alleleRegistry = alleleRegistry;
		plugins.forEach(p -> p.registerAlleles(alleleRegistry));
		//
		GeneRegistry geneRegistry = new GeneRegistry();
		CrisprAPI.geneRegistry = geneRegistry;
		plugins.forEach(p -> p.registerGenes(geneRegistry, GeneticFactory.INSTANCE));
		GeneticSystem geneticSystem = Crispr.system = geneRegistry.createGeneticSystem();
		//
		CrisprAPI.geneticSystem = geneticSystem;
		plugins.forEach(p -> p.registerDefinitions(geneticSystem));
	}
}
