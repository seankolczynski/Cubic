import java.util.HashMap;

public class Utils {

    /**
     * For edges, that are on the U/D side, we want the non U/D side
     * @param e
     * @return
     */
    static Character notUpOrDown(Edge e) {
        if (e.primary == 'U' || e.primary == 'D') {
            return e.secondary;
        } else {
            return e.primary;
        }
    }

    static Character otherSide(Character c, Edge e) {
        if (e.primary.equals(c)) {
            return e.secondary;
        } else {
            return e.primary;
        }
    }

    static Character commonFace(Edge e, Edge f) {
        if (e.primary.equals(f.primary)) {
            return e.primary;
        } else {
            return f.secondary;
        }
    }

    static boolean onSide(Character c, Edge one, Edge two) {
        return c.equals(one.primary) || c.equals(one.secondary) || c.equals(two.primary) || c.equals(two.secondary);
    }

    static boolean oneOnSide(Character c, Edge e) {
        return c.equals(e.primary) || c.equals(e.secondary);
    }

    static boolean shareFace(Edge one, Edge two) {
        return one.primary.equals(two.primary) || one.primary.equals(two.secondary) || one.secondary.equals(two.primary) || one.secondary.equals(two.secondary);
    }

    public static HashMap<Character,Character> horizontalMap(Character c) {
        HashMap<Character, Character> result = new HashMap<Character, Character>();

        switch(c) {
            case 'F':
                result.put('F', 'F');
                result.put('R', 'R');
                result.put('B', 'B');
                result.put('L', 'L');
                break;
            case 'L':
                result.put('F', 'L');
                result.put('R', 'F');
                result.put('B', 'R');
                result.put('L', 'B');
                break;
            case 'B':
                result.put('F', 'B');
                result.put('R', 'L');
                result.put('B', 'F');
                result.put('L', 'R');
                break;
            case 'R':
                result.put('F', 'R');
                result.put('R', 'B');
                result.put('B', 'L');
                result.put('L', 'F');
                break;
            default:
                result.put('F', 'F');
                result.put('R', 'R');
                result.put('B', 'B');
                result.put('L', 'L');
        }
        result.put('U', 'U');
        result.put('D', 'D');
        return result;
    }


}
