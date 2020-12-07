import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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


    public static boolean FBUDEdges(Side faceOne, int x1, int y1, Side faceTwo, int x2, int y2) {
        return ((faceOne.squares[x1][y1].equals('F') || faceOne.squares[x1][y1].equals('D') || faceOne.squares[x1][y1].equals('B') || faceOne.squares[x1][y1].equals('U'))
        && faceTwo.squares[x2][y2].equals('F') || faceTwo.squares[x2][y2].equals('D') || faceTwo.squares[x2][y2].equals('B') || faceTwo.squares[x2][y2].equals('U'));
    }

    public static HashMap<Integer, Integer> initialize() throws FileNotFoundException {
        HashMap<Integer, Integer> cornerRows = new HashMap<Integer, Integer>();
        String filepath = new File("").getAbsolutePath();
        FileReader file = new FileReader(filepath.concat("/tables/justCorners.txt"));
        Scanner scan = new Scanner(file);
        int row = 0;
        while (scan.hasNext()) {
            row++;
            int cornerCombo = scan.nextInt();
            cornerRows.put(cornerCombo, row);
        }
        return cornerRows;
    }

    public static ArrayList<Move> grabCornerMoves(int row, ArrayList<Move> moves) {
        String filepath = new File("").getAbsolutePath();
        Scanner line = null;
        try {
            BufferedReader bruh = new BufferedReader(new FileReader(filepath.concat("/tables/phase2Moves.txt")));
            for (int i = 0; i < row; i++) {
                bruh.readLine();
            }
            line = new Scanner(bruh.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int j = 0; j < 8; j++) {
            int moveCode = line.nextInt();
            moves.add(Utils.numberToMove(moveCode));
        }
        return moves;
    }

    private static Move numberToMove(int code) {
        switch (code) {
            case 11:
                return Move.lCounter;
            case 12:
                return Move.lEighty;
            case 13:
                return Move.lClock;
            case 21:
                return Move.fCounter;
            case 22:
                return Move.fEighty;
            case 23:
                return Move.fClock;
            case 31:
                return Move.rCounter;
            case 32:
                return Move.rEighty;
            case 33:
                return Move.rClock;
            case 41:
                return Move.bCounter;
            case 42:
                return Move.bEighty;
            case 43:
                return Move.bClock;
            case 51:
                return Move.uEighty;
            case 52:
                return Move.dEighty;
        }
        throw new IllegalArgumentException();
    }

    public static boolean inOrbit(int num, Character ud, Character fb, Character lr) {
        Corner corn = Corner.getCorner(ud, fb, lr);
        if (num <= 4) {
            return (corn.equals(Corner.UBL) || corn.equals(Corner.DFL) || corn.equals(Corner.DBR) || corn.equals(Corner.DFR));
        } else {
            return (corn.equals(Corner.UFL) || corn.equals(Corner.DBL) || corn.equals(Corner.UFR) || corn.equals(Corner.UBR));
        }
    }
}
