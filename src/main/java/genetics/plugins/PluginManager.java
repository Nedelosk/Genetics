package genetics.plugins;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

import genetics.api.GeneticPlugin;
import genetics.api.GeneticsAPI;
import genetics.api.IGeneticPlugin;

import genetics.ApiInstance;
import genetics.alleles.AlleleRegistry;
import genetics.definition.DefinitionFactory;
import genetics.definition.KaryotypeFactory;
import genetics.gene.GeneFactory;

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
		ApiInstance.INSTANCE.setAlleleRegistry(alleleRegistry);
		RegistryHelper.INSTANCE.onRegisterAlleles(alleleRegistry);
		plugins.forEach(p -> p.registerAlleles(alleleRegistry));
		//
		GeneFactory geneFactory = new GeneFactory();
		RegistryHelper.INSTANCE.onRegister(geneFactory);
		plugins.forEach(p -> p.registerGenes(geneFactory));
		ApiInstance.INSTANCE.setGeneRegistry(geneFactory.createRegistry());
		//
		KaryotypeFactory karyotypeFactory = new KaryotypeFactory();
		plugins.forEach(p -> p.createKaryotype(karyotypeFactory));
		//
		DefinitionFactory definitionFactory = new DefinitionFactory();
		plugins.forEach(p -> p.onFinishRegistration(definitionFactory, GeneticsAPI.apiInstance));
		ApiInstance.INSTANCE.setDefinitionRegistry(definitionFactory.createRegistry());
	}
}
