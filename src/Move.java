public enum Move {
    uClock('U', Direction.Clockwise),
    uCounter('U', Direction.Counterclockwise),
    uEighty('U', Direction.OneEighty),
    uRandom('U', Direction.Random),
    dClock('D', Direction.Clockwise),
    dCounter('D', Direction.Counterclockwise),
    dEighty('D', Direction.OneEighty),
    dRandom('D', Direction.Random),
    fClock('F', Direction.Clockwise),
    fCounter('F', Direction.Counterclockwise),
    fEighty('F', Direction.OneEighty),
    fRandom('F', Direction.Random),
    bClock('B', Direction.Clockwise),
    bCounter('B', Direction.Counterclockwise),
    bEighty('B', Direction.OneEighty),
    bRandom('B', Direction.Random),
    rClock('R', Direction.Clockwise),
    rCounter('R', Direction.Counterclockwise),
    rEighty('R', Direction.OneEighty),
    rRandom('R', Direction.Random),
    lClock('L', Direction.Clockwise),
    lCounter('L', Direction.Counterclockwise),
    lEighty('L', Direction.OneEighty),
    lRandom('L', Direction.Random);

    public Character face;
    public Direction dir;

    Move(Character c, Direction d) {
        this.face = c;
        this.dir = d;
    }

    public static Move getMove(Character c, Direction d) {
        for (Move m : Move.values()) {
            if (m.face == c && m.dir == d) {
                return m;
            }
        }
        throw new IllegalArgumentException("Not a Move");
    }

}
