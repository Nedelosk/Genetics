package nedelosk.crispr.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.minecraftforge.fml.common.eventhandler.EventPriority;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneticPlugin {
	EventPriority priority() default EventPriority.NORMAL;
}
