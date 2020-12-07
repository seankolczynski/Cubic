import java.time.temporal.ChronoField;

public enum Corner {
    UFL('U', 'F', 'L'),
    UFR('U', 'F', 'R'),
    UBL('U', 'B', 'L'),
    UBR('U', 'B', 'R'),
    DFL('D', 'F', 'L'),
    DFR('D', 'F', 'R'),
    DBL('D', 'B', 'L'),
    DBR('D', 'B', 'R');

    public Character upDown;
    public Character frontBack;
    public Character leftRight;

    Corner(Character ud, Character fb, Character lr) {
        this.upDown = ud;
        this.frontBack = fb;
        this.leftRight = lr;
    }

    public static Corner numToCorner(int corner) {
        switch (corner) {
            case 1:
                return UBL;
            case 2:
                return DFL;
            case 3:
                return DBR;
            case 4:
                return DFR;
            case 5:
                return UFL;
            case 6:
                return DBL;
            case 7:
                return UFR;
            case 8:
                return UBR;
            default:
                throw new IllegalArgumentException("Not a corner!");
        }
    }

}
