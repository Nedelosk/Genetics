package genetics.items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import genetics.api.GeneticsAPI;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;
import genetics.api.definition.IGeneticDefinition;
import genetics.api.gene.IGeneType;
import genetics.api.items.IGeneTemplate;

import genetics.Genetics;

public class GeneTemplate implements IGeneTemplate, ICapabilityProvider {
	private final IAlleleKey key;
	private final IGeneType type;
	private final IGeneticDefinition definition;

	public GeneTemplate(IAlleleKey key, IGeneType type, IGeneticDefinition definition) {
		this.key = key;
		this.type = type;
		this.definition = definition;
	}

	@Override
	public Optional<IAllele<?>> getAllele() {
		return GeneticsAPI.alleleRegistry.getAllele(key);
	}

	@Override
	public IGeneType getType() {
		return type;
	}

	@Override
	public IGeneticDefinition getDescription() {
		return definition;
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
