package genetics.items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import genetics.api.GeneticsAPI;
import genetics.api.IGeneTemplate;
import genetics.api.alleles.IAllele;
import genetics.api.definition.IIndividualDefinition;
import genetics.api.gene.IGeneType;

import genetics.Genetics;

public class GeneTemplate implements IGeneTemplate, ICapabilitySerializable<NBTTagCompound> {
	@Nullable
	private IAllele<?> allele;
	@Nullable
	private IGeneType type;
	@Nullable
	private IIndividualDefinition definition;

	@Override
	public Optional<IAllele<?>> getAllele() {
		return Optional.ofNullable(allele);
	}

	@Override
	public Optional<IGeneType> getType() {
		return Optional.ofNullable(type);
	}

	@Override
	public Optional<IIndividualDefinition> getDefinition() {
		return Optional.ofNullable(definition);
	}

	@Override
	public void setAllele(@Nullable IAllele<?> allele, @Nullable IGeneType type) {
		this.allele = allele;
		this.type = type;
		if (type != null) {
			definition = type.getDefinition();
		} else {
			definition = null;
		}
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		if (allele != null) {
			compound.setString("Allele", allele.getRegistryName().toString());
		}
		if (type != null && definition != null) {
			compound.setByte("Type", (byte) type.getIndex());
			compound.setString("Definition", definition.getUID());
		}
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		if (compound.hasKey("Type") && compound.hasKey("Definition")) {
			GeneticsAPI.geneticSystem.getDefinition(compound.getString("Definition")).ifPresent(definition -> {
				this.definition = definition;
				type = definition.getKaryotype().getGeneTypes()[compound.getByte("Type")];
			});
		}
		if (compound.hasKey("Allele")) {
			allele = GeneticsAPI.alleleRegistry.getAllele(compound.getString("Allele")).orElse(null);
		}
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == Genetics.GENE_TEMPLATE;
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == Genetics.GENE_TEMPLATE ? Genetics.GENE_TEMPLATE.cast(this) : null;
	}
}
