package genetics.api.individual;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.alleles.IAllele;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;

public interface IGenome {

	IChromosome[] getChromosomes();

	<V> IAllele<V> getActiveAllele(IGeneType geneType);

	<V> IAllele<V> getInactiveAllele(IGeneType geneType);

	<V> IChromosome<V> getChromosome(IGeneType geneType);

	IKaryotype getKaryotype();

	NBTTagCompound writeToNBT(NBTTagCompound compound);
}
