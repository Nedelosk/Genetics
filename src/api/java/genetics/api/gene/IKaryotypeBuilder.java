package genetics.api.gene;

import java.util.function.BiFunction;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.registry.IGeneticRegistry;

/**
 * A builder to create a karyotype manual.
 * <p>
 * You can create an instance of this with {@link IGeneticRegistry#createKaryotype(IGeneType, String)} or you can
 * create a {@link IKaryotype} directly with {@link IGeneticRegistry#createKaryotype(Class, String)} if you have a enum
 * that contains your {@link IGeneType}s.
 */
public interface IKaryotypeBuilder {

	/**
	 * Adds a type
	 */
	IKaryotypeBuilder add(IGeneType type);

	/**
	 * Sets the function that is used to create a template builder.
	 */
	IKaryotypeBuilder setTemplateFactory(BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory);

	/**
	 * Creates the {@link IKaryotype}.
	 */
	IKaryotype build();
}
