public enum Edge {
    FD('F', 'D'),
    FU('F', 'U'),
    FL('F', 'L'),
    FR('F', 'R'),
    BD('B', 'D'),
    BU('B', 'U'),
    BL('B', 'L'),
    BR('B', 'R'),
    UL('U', 'L'),
    UR('U', 'R'),
    DL('D', 'L'),
    DR('D', 'R');

    public Character primary;
    public Character secondary;

    Edge(Character prime, Character second) {
        this.primary = prime;
        this.secondary = second;
    }

    /**
     * Basic Dictionary that returns the number for a specific edge
     *
     * @param side The side with the bad edge
     * @param x    The x position of that bad edge on the given side
     * @param y    The y position of that bad edge on the given side
     * @return Number corresponding with that edge.
     * FD = 1, FU = 2, FL = 3, FR = 4, BD = 5, BU = 6, BL = 7, BR = 8, UL = 9, UR = 10, DL = 11, DR = 12
     */
    public static Edge edgeNumber(Character side, int x, int y) {
        if (side.equals('F') && x == 1 && y == 0) {
            return FD;
        }
        if (side.equals('F') && x == 1 && y == 2) {
            return FU;
        }
        if (side.equals('F') && x == 0 && y == 1) {
            return FL;
        }
        if (side.equals('F') && x == 2 && y == 1) {
            return FR;
        }
        if (side.equals('B') && x == 1 && y == 0) {
            return BD;
        }
        if (side.equals('B') && x == 1 && y == 2) {
            return BU;
        }
        if (side.equals('B') && x == 0 && y == 1) {
            return BR;
        }
        if (side.equals('B') && x == 2 && y == 1) {
            return BL;
        }
        if (side.equals('U') && x == 0 && y == 1) {
            return UL;
        }
        if (side.equals('U') && x == 2 && y == 1) {
            return UR;
        }
        if (side.equals('D') && x == 0 && y == 1) {
            return DL;
        }
        if (side.equals('D') && x == 2 && y == 1) {
            return DR;
        } else {
            throw new IllegalArgumentException("Not given an edge");
        }
    }
}
