package genetics.plugins;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import genetics.api.GeneticPlugin;
import genetics.api.IGeneticPlugin;

import genetics.Log;

public class PluginUtil {
	private PluginUtil() {

	}

	static Map<IGeneticPlugin, ModContainer> getPlugins(ASMDataTable asmDataTable) {
		return getInstances(asmDataTable, GeneticPlugin.class, IGeneticPlugin.class);
	}

	private static <T> Map<T, ModContainer> getInstances(ASMDataTable asmDataTable, Class annotationClass, Class<T> instanceClass) {
		String annotationClassName = annotationClass.getCanonicalName();
		Set<ASMDataTable.ASMData> abmData = asmDataTable.getAll(annotationClassName);
		Map<String, ModContainer> containerByID = new HashMap<>();
		Loader.instance().getActiveModList().forEach(modContainer -> containerByID.put(modContainer.getModId(), modContainer));
		Map<T, ModContainer> instances = new HashMap<>();
		for (ASMDataTable.ASMData asmData : abmData) {
			try {
				Class<?> asmClass = Class.forName(asmData.getClassName());
				Class<? extends T> asmInstanceClass = asmClass.asSubclass(instanceClass);
				T instance = asmInstanceClass.newInstance();
				GeneticPlugin plugin = asmInstanceClass.getAnnotation(GeneticPlugin.class);
				if (plugin != null) {
					ModContainer container = containerByID.get(plugin.modId());
					if (container != null) {
						instances.put(instance, container);
					}
				}
			} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
				Log.error("Failed to load: {}", asmData.getClassName(), e);
			}
		}
		return instances;
	}
}