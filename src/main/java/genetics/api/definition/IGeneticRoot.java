package genetics.api.definition;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.alleles.IAllele;
import genetics.api.gene.IGeneticStat;
import genetics.api.individual.IGenome;
import genetics.api.individual.IIndividual;

public interface IGeneticRoot<I extends IIndividual, S extends IGeneticStat> {

	IGeneticDefinition<I, IGeneticRoot<I, S>> getDefinition();

	I create(NBTTagCompound compound);

	I create(IGenome genome);

	I create(IGenome genome, IGenome mate);

	default I templateAsIndividual(IAllele[] template) {
		return templateAsIndividual(template, null);
	}

	I templateAsIndividual(IAllele[] templateActive, @Nullable IAllele[] templateInactive);

	I getDefaultMember();

	S createStat(IGenome genome);
}
