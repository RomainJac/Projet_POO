package fr.pantheonsorbonne.miage.game.classes.Cartes;

public enum CardRank {
    DEUX("DEUX", 2),
    TROIS("TROIS", 3),
    QUATRE("QUATRE", 4),
    CINQ("CINQ", 5),
    SIX("SIX", 6),
    SEPT("SEPT", 7),
    HUIT("HUIT", 8),
    NEUF("NEUF", 9),
    DIX("DIX", 10),
    VALET("VALET", 11),
    DAME("DAME", 12),
    ROI("ROI", 13),
    AS("AS", 14);

    private final String stringRepresentation;
    private final int rank;

    CardRank(String stringRepresentation, int value) {
        this.stringRepresentation = stringRepresentation;
        this.rank = value;
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }

    public int getRank() {
        return rank;
    }

    public CardRank InverserOrdre() {
        return getValueFromRank(14 - this.getRank() + 2);
    }

    private CardRank getValueFromRank(int rank) {
        for (CardRank value : CardRank.values()) {
            if (value.getRank() == rank) {
                return value;
            }
        }
        return null;
    }

}
