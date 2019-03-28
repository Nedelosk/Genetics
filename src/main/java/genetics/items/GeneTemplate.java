package genetics.items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import genetics.api.IGeneTemplate;
import genetics.api.alleles.IAllele;
import genetics.api.individual.IChromosomeType;
import genetics.api.root.IIndividualRoot;

import genetics.ApiInstance;
import genetics.Genetics;
import net.minecraftforge.common.util.LazyOptional;

public class GeneTemplate implements IGeneTemplate, ICapabilitySerializable<NBTTagCompound> {
	private static final String ALLELE_NBT_KEY = "Allele";
	private static final String TYPE_NBT_KEY = "Type";
	private static final String DEFINITION_NBT_KEY = "Definition";

	private final LazyOptional<IGeneTemplate> holder = LazyOptional.of(() -> this);

	@Nullable
	private IAllele allele;
	@Nullable
	private IChromosomeType type;
	@Nullable
	private IIndividualRoot root;

	@Override
	public Optional<IAllele> getAllele() {
		return Optional.ofNullable(allele);
	}

	@Override
	public Optional<IChromosomeType> getType() {
		return Optional.ofNullable(type);
	}

	@Override
	public Optional<IIndividualRoot> getRoot() {
		return Optional.ofNullable(root);
	}

	@Override
	public void setAllele(@Nullable IChromosomeType type, @Nullable IAllele allele) {
		this.allele = allele;
		this.type = type;
		if (type != null) {
			root = type.getRoot();
		} else {
			root = null;
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		if (allele != null) {
			compound.setString(ALLELE_NBT_KEY, allele.getRegistryName().toString());
		}
		if (type != null && root != null) {
			compound.setByte(TYPE_NBT_KEY, (byte) type.getIndex());
			compound.setString(DEFINITION_NBT_KEY, root.getUID());
		}
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		if (compound.hasKey(TYPE_NBT_KEY) && compound.hasKey(DEFINITION_NBT_KEY)) {
			ApiInstance.INSTANCE.getRoot(compound.getString(DEFINITION_NBT_KEY)).maybe().ifPresent(def -> {
				this.root = def;
				type = def.getKaryotype().getChromosomeTypes()[compound.getByte(TYPE_NBT_KEY)];
			});
		}
		if (compound.hasKey(ALLELE_NBT_KEY)) {
			allele = ApiInstance.INSTANCE.getAlleleRegistry().getAllele(compound.getString(ALLELE_NBT_KEY)).orElse(null);
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing facing) {
		return Genetics.GENE_TEMPLATE.orEmpty(cap, holder);
	}
}
