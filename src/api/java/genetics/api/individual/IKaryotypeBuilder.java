package genetics.api.individual;

import java.util.function.BiFunction;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplateBuilder;

/**
 * A builder to create a karyotype manual.
 * <p>
 * You can create an instance of this with {@link IKaryotypeFactory#createKaryotype(IChromosomeType, String)} or you can
 * create a {@link IKaryotype} directly with {@link IKaryotypeFactory#createKaryotype(Class, String)} if you have a enum
 * that contains your {@link IChromosomeType}s.
 */
public interface IKaryotypeBuilder {

	/**
	 * Adds a type
	 */
	IKaryotypeBuilder add(IChromosomeType type);

	/**
	 * Sets the function that is used to create a template builder.
	 */
	IKaryotypeBuilder setTemplateFactory(BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory);

	/**
	 * Creates the {@link IKaryotype}.
	 */
	IKaryotype build();
}
