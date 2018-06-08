package genetics.api.events;

import net.minecraftforge.fml.common.eventhandler.Event;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;

public class AlleleRegisterEvent extends Event {
	private final IAllele allele;
	private final IAlleleKey[] keys;

	public AlleleRegisterEvent(IAllele allele, IAlleleKey... keys) {
		this.allele = allele;
		this.keys = keys;
	}

	public IAllele getAllele() {
		return allele;
	}

	public IAlleleKey[] getKeys() {
		return keys;
	}
}
