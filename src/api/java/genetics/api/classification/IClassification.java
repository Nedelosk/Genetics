package genetics.api.classification;

import javax.annotation.Nullable;

import genetics.api.alleles.IAlleleSpecies;

/**
 * Biological classifications from domain down to genus.
 * <p>
 * Used by the forestry analyzers to display hierarchies.
 */
public interface IClassification {

	enum EnumClassLevel {

		DOMAIN(0x777fff, true), KINGDOM(0x77c3ff), PHYLUM(0x77ffb6, true), DIVISION(0x77ffb6, true), CLASS(0x7bff77), ORDER(0xbeff77), FAMILY(0xfffd77),
		SUBFAMILY(0xfffd77), TRIBE(0xfffd77), GENUS(0xffba77);

		private final int colour;
		private final boolean isDroppable;

		EnumClassLevel(int colour) {
			this(colour, false);
		}

		EnumClassLevel(int colour, boolean isDroppable) {
			this.colour = colour;
			this.isDroppable = isDroppable;
		}

		/**
		 * @return Colour to use for displaying this classification.
		 */
		public int getColour() {
			return colour;
		}

		/**
		 * @return Indicates whether display of this classification level can be ommitted in case of space constraints.
		 */
		public boolean isDroppable() {
			return isDroppable;
		}
	}

	/**
	 * @return Level inside the full hierarchy this particular classification is located at.
	 */
	EnumClassLevel getLevel();

	/**
	 * @return Unique String identifier.
	 */
	String getUID();

	/**
	 * @return Localized branch name for user display.
	 */
	String getName();

	/**
	 * A branch approximates a "genus" in real life. Real life examples: "Micrapis", "Megapis"
	 *
	 * @return flavour text
	 */
	String getScientific();

	/**
	 * @return Localized description of this branch.
	 */
	String getDescription();

	/**
	 * @return Member groups of this one.
	 */
	IClassification[] getMemberGroups();

	/**
	 * Adds subgroups to this group.
	 */
	void addMemberGroup(IClassification group);

	/**
	 * @return Member species of this group.
	 */
	IAlleleSpecies[] getMemberSpecies();

	/**
	 * Used by the allele registry to populate internal collection of branch members on the fly.
	 */
	void addMemberSpecies(IAlleleSpecies species);

	/**
	 * @return Parent classification, null if this is root.
	 */
	@Nullable
	IClassification getParent();

	/**
	 * Only used internally by the AlleleRegistry if this classification has been added to another one.
	 */
	void setParent(IClassification parent);
}
