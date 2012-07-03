public enum ABEnum {
    // An enum class for Smotif designations
    AlphaAlpha (0),
    AlphaBeta (1),
    BetaAlpha (2),
    BetaBeta (3);

    private final int designator;

    ABEnum (int designator) {
	this.designator = designator;
    }

    public int designator() {return designator;}
}