package nedelosk.crispr.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraftforge.fml.common.discovery.ASMDataTable;

import forestry.core.utils.Log;

import nedelosk.crispr.api.GeneticPlugin;
import nedelosk.crispr.api.IGeneticPlugin;

public class PluginUtil {
	private PluginUtil() {

	}

	public static List<IGeneticPlugin> getPlugins(ASMDataTable asmDataTable) {
		return getInstances(asmDataTable, GeneticPlugin.class, IGeneticPlugin.class);
	}

	private static <T> List<T> getInstances(ASMDataTable asmDataTable, Class annotationClass, Class<T> instanceClass) {
		String annotationClassName = annotationClass.getCanonicalName();
		Set<ASMDataTable.ASMData> asmDatas = asmDataTable.getAll(annotationClassName);
		List<T> instances = new ArrayList<>();
		for (ASMDataTable.ASMData asmData : asmDatas) {
			try {
				Class<?> asmClass = Class.forName(asmData.getClassName());
				Class<? extends T> asmInstanceClass = asmClass.asSubclass(instanceClass);
				T instance = asmInstanceClass.newInstance();
				instances.add(instance);
			} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
				Log.error("Failed to load: {}", asmData.getClassName(), e);
			}
		}
		return instances;
	}
}