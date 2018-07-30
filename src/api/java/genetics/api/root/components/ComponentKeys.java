package genetics.api.root.components;

import genetics.api.mutation.IMutationContainer;
import genetics.api.mutation.IMutationContainerBuilder;
import genetics.api.organism.IOrganismTypes;
import genetics.api.organism.IOrganismTypesBuilder;
import genetics.api.root.ITemplateContainer;
import genetics.api.root.ITemplateRegistry;
import genetics.api.root.translator.IIndividualTranslator;
import genetics.api.root.translator.IIndividualTranslatorBuilder;

public class ComponentKeys {
	public static final ComponentKey<ITemplateContainer, ITemplateRegistry> TEMPLATES = new ComponentKey<>("templates", ITemplateContainer.class);
	public static final ComponentKey<IOrganismTypes, IOrganismTypesBuilder> TYPES = new ComponentKey<>("types", IOrganismTypes.class);
	public static final ComponentKey<IIndividualTranslator, IIndividualTranslatorBuilder> TRANSLATORS = new ComponentKey<>("translators", IIndividualTranslator.class);
	public static final ComponentKey<IMutationContainer, IMutationContainerBuilder> MUTATIONS = new ComponentKey<>("mutations", IMutationContainer.class);
	public static final String RESEARCH_COMPONENT = "research";
}
