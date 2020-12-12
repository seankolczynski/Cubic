import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

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

    public static ArrayList<Move> grabFinalMoves(int row) {
        ArrayList<Move> moves = new ArrayList<Move>();
        String filepath = new File("").getAbsolutePath();
        Scanner line = null;
        try {
            BufferedReader bruh = new BufferedReader(new FileReader(filepath.concat("/tables/moves4.txt")));
            for (int i = 0; i < row; i++) {
                bruh.readLine();
            }
            line = new Scanner(bruh.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (line.hasNext()) {
            int m = line.nextInt();
            switch(m) {
                case 1:
                    moves.add(Move.lEighty);
                    break;
                case 2:
                    moves.add(Move.fEighty);
                    break;
                case 3:
                    moves.add(Move.rEighty);
                    break;
                case 4:
                    moves.add(Move.bEighty);
                    break;
                case 5:
                    moves.add(Move.uEighty);
                    break;
                case 6:
                    moves.add(Move.dEighty);
                    break;
            }
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

    public static ArrayList<Edge> stage3Edges() {
        ArrayList<Edge> udSlice = new ArrayList<Edge>();
        udSlice.add(Edge.UL);
        udSlice.add(Edge.DL);
        udSlice.add(Edge.UR);
        udSlice.add(Edge.DR);
        udSlice.add(Edge.BL);
        udSlice.add(Edge.FL);
        udSlice.add(Edge.FR);
        udSlice.add(Edge.BR);
        return udSlice;
    }
    
    public static int getStage3(Character leftRight, Character other) {
        if (leftRight.equals('L') && other.equals('U')) {
            return 1;
        }
        if (leftRight.equals('L') && other.equals('D')) {
            return 2;
        }
        if (leftRight.equals('R') && other.equals('U')) {
            return 3;
        }
        if (leftRight.equals('R') && other.equals('D')) {
            return 4;
        }
        return 0;
    }



    public static Cube rotateCommands(Cube c, String commands) {
        ArrayList<Move> moves = new ArrayList<Move>();
        Stack<Character> stack = new Stack<Character>();
        for (Character ch : commands.toCharArray()) {
            if (!ch.equals(' ')) {
                stack.push(ch);
            }
        }
        while (!stack.empty()) {
            Character com = stack.pop();
            switch (com) {
                case '1':
                    moves.add(Move.lEighty);
                    break;
                case '2':
                    moves.add(Move.fEighty);
                    break;
                case '3':
                    moves.add(Move.rEighty);
                    break;
                case '4':
                    moves.add(Move.bEighty);
                    break;
                case '5':
                    moves.add(Move.uEighty);
                    break;
                case '6':
                    moves.add(Move.dEighty);
                    break;
            }
        }

        for (Move move : moves) {
            switch (move) {
                case lEighty:
                    c.oneEighty(Color.ORANGE);
                    break;
                case fEighty:
                    c.oneEighty(Color.GREEN);
                    break;
                case rEighty:
                    c.oneEighty(Color.RED);
                    break;
                case bEighty:
                    c.oneEighty(Color.BLUE);
                    break;
                case uEighty:
                    c.oneEighty(Color.WHITE);
                    break;
                case dEighty:
                    c.oneEighty(Color.YELLOW);
                    break;
            }
        }
        return c;

    }

    public static HashMap<String, Integer> fbInit() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("LDLURDRU", 12);
        map.put("LDRDLURU", 123);
        map.put("LDRDRULU", 1234);
        map.put("LDRURDLU", 124);
        map.put("RDLDLURU", 13);
        map.put("RDLULDRU", 132);
        map.put("RDRULDLU", 1324);
        map.put("RDLDRULU", 134);
        map.put("RDLURULD", 1342);
        map.put("RULDRDLU", 14);
        map.put("RULURDLD", 142);
        map.put("RURDLULD", 1423);
        map.put("RULDLURD", 143);
        map.put("RULULDRD", 1432);
        map.put("LURDLDRU", 23);
        map.put("LURDRULU", 234);
        map.put("LURURDLD", 24);
        map.put("LURULDRD", 243);
        map.put("LULDRURD", 34);
        map.put("LDLURURD", 12340);
        map.put("RDRULULD", 13240);
        map.put("RURDLDLU", 14230);
        return map;
    }

    public static HashMap<String, Integer> udInit() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("LFLBRFRB", 12);
        map.put("LFRFLBRB", 123);
        map.put("LFRFRBLB", 1234);
        map.put("LFRBRFLB", 124);
        map.put("LFRBLBRF", 1243);
        map.put("RFLFLBRB", 13);
        map.put("RFLBRFRB", 132);
        map.put("RFRBLFLB", 1324);
        map.put("RFLFRBLB", 134);
        map.put("RFLBRBLF", 1342);
        map.put("RBLFRFLB", 14);
        map.put("RBLBRFLF", 142);
        map.put("RBRFLBLF", 1423);
        map.put("RBLFLBRF", 143);
        map.put("RBLBLFRF", 1432);
        map.put("LBRFLFRB", 23);
        map.put("LBRFRBLF", 234);
        map.put("LBBRBLRB", 24);
        map.put("LBRBLFRF", 243);
        map.put("LBLFRBRF", 34);
        map.put("LFLBRBRF", 12340);
        map.put("RFRBLBLF", 13240);
        map.put("RBRFLFLB", 14230);
        return map;
    }

    public static HashMap<String, Integer> lrInit() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("FDFUBDBU", 12);
        map.put("FDBDFUBU", 123);
        map.put("FDBDBUFU", 1234);
        map.put("FDBUBDFU", 124);
        map.put("BDFDFUBU", 13);
        map.put("BDFUFDBU", 132);
        map.put("BDBUFDFU", 1324);
        map.put("BDFDBUFU", 134);
        map.put("BDFUBUFD", 1342);
        map.put("BUFDBDFU", 14);
        map.put("BUFUBDFD", 142);
        map.put("BUBDFUFD", 1423);
        map.put("BUFDFUBD", 143);
        map.put("BUFUFDBD", 1432);
        map.put("FUBDFDBU", 23);
        map.put("FUBDBUFD", 234);
        map.put("FUBUBDFD", 24);
        map.put("FUBUFDBD", 243);
        map.put("FUFDBUBD", 34);
        map.put("FDFUBUBD", 12340);
        map.put("BDBUFUFD", 13240);
        map.put("BUBDFDFU", 14230);
        return map;
    }

    public static HashMap<TripleKey, Integer> keyChain() {
        HashMap<TripleKey, Integer> stage4moves = new HashMap<TripleKey, Integer>();
        stage4moves.put(new TripleKey(0,0,0), 1);
        stage4moves.put(new TripleKey(12,0,34), 2);
        stage4moves.put(new TripleKey(12340,0,0), 3);
        stage4moves.put(new TripleKey(12340,0,12340), 4);
        stage4moves.put(new TripleKey(12,0,1324), 5);
        stage4moves.put(new TripleKey(12340,0,123), 6);
        stage4moves.put(new TripleKey(1234,0,12), 7);
        stage4moves.put(new TripleKey(0,0,123), 8);
        stage4moves.put(new TripleKey(24,0,12), 9);
        stage4moves.put(new TripleKey(0,23,12), 10);
        stage4moves.put(new TripleKey(0,14,12), 11);
        stage4moves.put(new TripleKey(14,0,12), 12);
        stage4moves.put(new TripleKey(1243,0,12), 13);
        stage4moves.put(new TripleKey(1324,0,1423), 14);
        stage4moves.put(new TripleKey(0,23,1423), 15);
        stage4moves.put(new TripleKey(12340,0,13240), 16);
        stage4moves.put(new TripleKey(0,1423,1423), 17);
        stage4moves.put(new TripleKey(1324,1324,0), 18);
        stage4moves.put(new TripleKey(1324,1234,0), 19);
        stage4moves.put(new TripleKey(12340,12340,0), 20);
        stage4moves.put(new TripleKey(1234,12,0), 21);
        stage4moves.put(new TripleKey(0,1324,134), 22);
        stage4moves.put(new TripleKey(1432,0,1423), 23);
        stage4moves.put(new TripleKey(14230,0,142), 24);
        stage4moves.put(new TripleKey(1234,12,0), 25);
        stage4moves.put(new TripleKey(0,0,13240), 26);
        stage4moves.put(new TripleKey(1324,0,24), 27);
        stage4moves.put(new TripleKey(0,13,24), 28);
        stage4moves.put(new TripleKey(0,123,142), 29);
        stage4moves.put(new TripleKey(0,134,142), 30);
        stage4moves.put(new TripleKey(12,13,0), 31);
        stage4moves.put(new TripleKey(1324,13,0), 32);
        stage4moves.put(new TripleKey(1243,12,0), 33);
        stage4moves.put(new TripleKey(1423,0,1342), 34);
        stage4moves.put(new TripleKey(1324,1432,0), 35);
        stage4moves.put(new TripleKey(1432,1432,0), 36);
        stage4moves.put(new TripleKey(142,123,0), 37);
        stage4moves.put(new TripleKey(243,123,0), 38);
        stage4moves.put(new TripleKey(1324,1423,0), 39);
        stage4moves.put(new TripleKey(1324,1324,0), 40);
        stage4moves.put(new TripleKey(124,123,142), 41);
        stage4moves.put(new TripleKey(124,134,142), 42);
        stage4moves.put(new TripleKey(132,134,142), 43);
        stage4moves.put(new TripleKey(132,123,142), 44);
        stage4moves.put(new TripleKey(142,123,142), 45);
        stage4moves.put(new TripleKey(123,243,134), 46);
        stage4moves.put(new TripleKey(123,134,134), 47);
        stage4moves.put(new TripleKey(142,142,142), 48);
        stage4moves.put(new TripleKey(142,142,123), 49);
        stage4moves.put(new TripleKey(123,134,243), 50);
        stage4moves.put(new TripleKey(14230,142,134), 51);
        stage4moves.put(new TripleKey(14230,243,134), 52);
        stage4moves.put(new TripleKey(13240,243,0), 53);
        stage4moves.put(new TripleKey(13240,142,134), 54);
        stage4moves.put(new TripleKey(134,243,13240), 55);
        stage4moves.put(new TripleKey(123,243,13240), 56);
        stage4moves.put(new TripleKey(142,12340,142), 57);
        stage4moves.put(new TripleKey(123,14230,134), 58);
        stage4moves.put(new TripleKey(13240,13240,14230), 59);
        stage4moves.put(new TripleKey(13240,14230,14230), 60);
        stage4moves.put(new TripleKey(13240,12340,14230), 61);
        stage4moves.put(new TripleKey(13240,13240,13240), 62);
        stage4moves.put(new TripleKey(13240,13240,243), 63);
        stage4moves.put(new TripleKey(14230,13240,14230), 64);
        stage4moves.put(new TripleKey(14230,14230,14230), 65);
        stage4moves.put(new TripleKey(14230,13240,243), 66);
        stage4moves.put(new TripleKey(234,14230,14230), 67);
        stage4moves.put(new TripleKey(234,14230,13240), 68);
        stage4moves.put(new TripleKey(243,12340,14230), 69);
        stage4moves.put(new TripleKey(12340,12340,142), 70);
        stage4moves.put(new TripleKey(12340,14230,1423), 71);
        stage4moves.put(new TripleKey(234,14230,12340), 72);
        stage4moves.put(new TripleKey(12,12,124), 73);
        stage4moves.put(new TripleKey(12,34,124), 74);
        stage4moves.put(new TripleKey(12,12340,12), 75);
        stage4moves.put(new TripleKey(34,13240,34), 76);
        stage4moves.put(new TripleKey(12,13,123), 77);
        stage4moves.put(new TripleKey(12,24,123), 78);
        stage4moves.put(new TripleKey(124,34,12), 79);
        stage4moves.put(new TripleKey(124,12,12), 80);
        stage4moves.put(new TripleKey(12340,14,12), 81);
        stage4moves.put(new TripleKey(12340,23,12), 82);
        stage4moves.put(new TripleKey(12,132,12), 83);
        stage4moves.put(new TripleKey(12,143,12), 84);
        stage4moves.put(new TripleKey(12,124,12), 85);
        stage4moves.put(new TripleKey(12340,14,14), 86);
        stage4moves.put(new TripleKey(12340,12,24), 87);
        stage4moves.put(new TripleKey(13,13240,34), 88);
        stage4moves.put(new TripleKey(12340,14,24), 89);
        stage4moves.put(new TripleKey(34,14230,13), 90);
        stage4moves.put(new TripleKey(13240,34,24), 91);
        stage4moves.put(new TripleKey(12,24,12340), 92);
        stage4moves.put(new TripleKey(13240,34,34), 93);
        stage4moves.put(new TripleKey(12, 12340, 14), 94);
        stage4moves.put(new TripleKey(34,24,13240), 95);
        stage4moves.put(new TripleKey(13,24,13240), 96);
        stage4moves.put(new TripleKey(24,23,234), 97);
        stage4moves.put(new TripleKey(13,23,132), 98);
        stage4moves.put(new TripleKey(24,132,24), 99);
        stage4moves.put(new TripleKey(24,234,24), 12);
        stage4moves.put(new TripleKey(13240,23,24), 100);
        stage4moves.put(new TripleKey(13240,23,12), 101);
        stage4moves.put(new TripleKey(13240,14,12), 102);
        stage4moves.put(new TripleKey(1324,13240,1423), 103);
        stage4moves.put(new TripleKey(1324,14230,1423), 104);
        stage4moves.put(new TripleKey(1234,13240,1324), 105);
        stage4moves.put(new TripleKey(1423,14230,1432), 106);
        stage4moves.put(new TripleKey(1423,1324,243), 107);
        stage4moves.put(new TripleKey(1423,1423,243), 108);
        stage4moves.put(new TripleKey(1423,1432,243), 109);
        stage4moves.put(new TripleKey(1423,1234,243), 110);
        stage4moves.put(new TripleKey(234,1324,1324), 111);
        stage4moves.put(new TripleKey(234,1423,1324), 112);
        stage4moves.put(new TripleKey(1324,12340,1423), 113);
        stage4moves.put(new TripleKey(13240,1243,1324), 114);
        stage4moves.put(new TripleKey(13240,1342,1324), 115);
        stage4moves.put(new TripleKey(14230,1342,1324), 116);
        stage4moves.put(new TripleKey(14230,1243,1324), 117);
        stage4moves.put(new TripleKey(1423,124,1324), 118);
        stage4moves.put(new TripleKey(1423,132,1324), 119);
        stage4moves.put(new TripleKey(1423,143,1324), 120);
        stage4moves.put(new TripleKey(1423,12340,1342), 121);
        stage4moves.put(new TripleKey(13240,1243,1342), 122);
        stage4moves.put(new TripleKey(13240,1342,1234), 123);
        stage4moves.put(new TripleKey(14230,1432,1234), 124);
        stage4moves.put(new TripleKey(13240,1234,1234), 125);
        stage4moves.put(new TripleKey(1423,12340,1234), 126);
        stage4moves.put(new TripleKey(1423,1234,14230), 127);
        stage4moves.put(new TripleKey(1234,1342,143), 128);
        stage4moves.put(new TripleKey(14230,1342,1432), 129);
        stage4moves.put(new TripleKey(1423,1342,124), 130);
        stage4moves.put(new TripleKey(1432,124,1234), 131);
        stage4moves.put(new TripleKey(1432,143,1234), 132);
        stage4moves.put(new TripleKey(34,13240,1423), 133);
        stage4moves.put(new TripleKey(34,14230,1423), 134);
        stage4moves.put(new TripleKey(24,13240,1324), 135);
        stage4moves.put(new TripleKey(1423,14230,24), 136);
        stage4moves.put(new TripleKey(1423,14230,34), 137);
        stage4moves.put(new TripleKey(14230,1342,34), 138);
        stage4moves.put(new TripleKey(143,1342,34), 139);
        stage4moves.put(new TripleKey(143,1243,34), 140);
        stage4moves.put(new TripleKey(13240,1243,34), 141);
        stage4moves.put(new TripleKey(1423,124,34), 142);
        stage4moves.put(new TripleKey(1423,234,34), 143);
        stage4moves.put(new TripleKey(1423,132,34), 144);
        stage4moves.put(new TripleKey(1423,143,34), 145);
        stage4moves.put(new TripleKey(23,12340,1423), 146);
        stage4moves.put(new TripleKey(1324,12340,14), 147);
        stage4moves.put(new TripleKey(13240,1243,14), 148);
        stage4moves.put(new TripleKey(1234,23,12340), 149);
        stage4moves.put(new TripleKey(1234,24,12340), 150);
        stage4moves.put(new TripleKey(24,1234,13240), 151);
        stage4moves.put(new TripleKey(12340,14,1432), 152);
        stage4moves.put(new TripleKey(13240,1342,13), 153);
        stage4moves.put(new TripleKey(24,1324,14230), 154);
        stage4moves.put(new TripleKey(1234,12340,12), 155);
        stage4moves.put(new TripleKey(24,14230,1324), 156);
        stage4moves.put(new TripleKey(132,12,1423), 157);
        stage4moves.put(new TripleKey(143,1423,34), 158);
        stage4moves.put(new TripleKey(143,1324,34), 159);
        stage4moves.put(new TripleKey(132,34,1423), 160);
        stage4moves.put(new TripleKey(1324,13,142), 161);
        stage4moves.put(new TripleKey(12,1234,142), 162);
        stage4moves.put(new TripleKey(34,1432,134), 163);
        stage4moves.put(new TripleKey(1423,24,134), 164);
        stage4moves.put(new TripleKey(12,13240,1432), 165);
        stage4moves.put(new TripleKey(34,1432,13240), 166);
        stage4moves.put(new TripleKey(23,1423,14230), 167);
        stage4moves.put(new TripleKey(34,14230,1342), 168);
        stage4moves.put(new TripleKey(1324,13240,14), 169);
        stage4moves.put(new TripleKey(1234,13,14230), 170);
        stage4moves.put(new TripleKey(12340,1243,13), 171);
        stage4moves.put(new TripleKey(1234,124,13), 172);
        stage4moves.put(new TripleKey(1234,143,13), 173);
        stage4moves.put(new TripleKey(12,132,1432), 174);
        stage4moves.put(new TripleKey(1423,124,13), 175);
        stage4moves.put(new TripleKey(1423,143,13), 176);
        stage4moves.put(new TripleKey(12,234,1432), 177);
        stage4moves.put(new TripleKey(14230,14,1234), 178);
        return stage4moves;
    }

    public static Cube move(Cube c, String moves) {
        Scanner s = new Scanner(moves);
        int count = 0;
        while (s.hasNext()) {
            count++;
            String motion = s.next();
            switch (motion) {
                case "F":
                    c.rotate(Color.GREEN, true); break;
                case "F2":
                    c.oneEighty(Color.GREEN); break;
                case "F'":
                    c.rotate(Color.GREEN, false); break;
                case "B":
                    c.rotate(Color.BLUE, true); break;
                case "B2":
                    c.oneEighty(Color.BLUE); break;
                case "B'":
                    c.rotate(Color.BLUE, false); break;
                case "U":
                    c.rotate(Color.WHITE, true); break;
                case "U2":
                    c.oneEighty(Color.WHITE); break;
                case "U'":
                    c.rotate(Color.WHITE, false); break;
                case "D":
                    c.rotate(Color.YELLOW, true); break;
                case "D2":
                    c.oneEighty(Color.YELLOW); break;
                case "D'":
                    c.rotate(Color.YELLOW, false); break;
                case "L":
                    c.rotate(Color.ORANGE, true); break;
                case "L2":
                    c.oneEighty(Color.ORANGE); break;
                case "L'":
                    c.rotate(Color.ORANGE, false); break;
                case "R":
                    c.rotate(Color.RED, true); break;
                case "R2":
                    c.oneEighty(Color.RED); break;
                case "R'":
                    c.rotate(Color.RED, false); break;
            }
        }
        System.out.println("Num Moves: " + count);
        return c;
    }


}

