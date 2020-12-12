import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Thistlethwaite {

    HashMap<Character, Color> colorToFace;
    Cube cube;
    Random r;
    HashMap<Character, Character> translate;
    HashMap<Integer, Integer> cornerRows;
    HashMap<String, Integer> fbSlice;
    HashMap<String, Integer> udSlice;
    HashMap<String, Integer> lrSlice;
    HashMap<TripleKey, Integer> finalKey = Utils.keyChain();


    public Thistlethwaite() {
        this.r = new Random();
        try {
            this.cornerRows = Utils.initialize();
            this.fbSlice = Utils.fbInit();
            this.udSlice = Utils.udInit();
            this.lrSlice = Utils.lrInit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Cube solve(Cube cube) {
        this.cube = cube;
        colorToFace = this.pairSides();
        System.out.println("Cube to be solved:\n");
        System.out.println(cube.toString());
        this.phaseOne();
        this.phaseTwo();
        this.phaseThree();
        this.phaseFour();
        System.out.println("SOLVED!");
        System.out.println(cube.toString());
        return cube;
    }


    public HashMap<Character, Color> pairSides() {
        HashMap<Character, Color> encoder = new HashMap<>();
        encoder.put('F', Color.GREEN);
        encoder.put('U', Color.WHITE);
        encoder.put('R', Color.RED);
        encoder.put('L', Color.ORANGE);
        encoder.put('D', Color.YELLOW);
        encoder.put('B', Color.BLUE);
        return encoder;
    }

    /**
     * Orients cube so that no U or D quarter turns are required, moving us into group 1
     */
    private void phaseOne() {
        Side frontFace = this.cube.getSide(this.colorToFace.get('F'));
        Side backFace = this.cube.getSide(this.colorToFace.get('B'));
        Side rightFace = this.cube.getSide(this.colorToFace.get('R'));
        Side leftFace = this.cube.getSide(this.colorToFace.get('L'));
        Side upFace = this.cube.getSide(this.colorToFace.get('U'));
        Side downFace = this.cube.getSide(this.colorToFace.get('D'));

        int countBad = 1;
        ArrayList<Edge> badEdges;
        while (countBad > 0) {
            this.translate = Utils.horizontalMap('F');
            countBad = 0;
            badEdges = new ArrayList();
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    if ((x == 1 && y == 0) || (x == 1 && y == 2) || (x == 0 && y == 1) || (x == 2 && y == 1)) {
                        Character edgeF = frontFace.squares[x][y];
                        if (edgeF.equals(this.colorToFace.get('L').getLetter())
                                || edgeF.equals(this.colorToFace.get('R').getLetter())) {
                            countBad++;
                            badEdges.add(Edge.edgeNumber('F', x, y));
                        } else if (edgeF.equals(this.colorToFace.get('U').getLetter())
                                || edgeF.equals(this.colorToFace.get('D').getLetter())) {
                            Character oppEdgeF = null;
                            if (y == 1) {
                                if (x == 0) {
                                    oppEdgeF = leftFace.getSquares()[2][1];
                                }
                                if (x == 2) {
                                    oppEdgeF = rightFace.getSquares()[0][1];
                                }
                            } else if (x == 1) {
                                if (y == 0) {
                                    oppEdgeF = downFace.getSquares()[1][2];
                                }
                                if (y == 2) {
                                    oppEdgeF = upFace.getSquares()[1][0];
                                }
                            }
                            if (oppEdgeF.equals(this.colorToFace.get('F').getLetter()) ||
                                    oppEdgeF.equals(this.colorToFace.get('B').getLetter())) {
                                countBad = countBad + 1;
                                badEdges.add(Edge.edgeNumber('F', x, y));
                            }
                        }

                        Character edgeB = backFace.squares[x][y];
                        if (edgeB.equals(this.colorToFace.get('L').getLetter()) ||
                                edgeB.equals(this.colorToFace.get('R').getLetter())) {
                            countBad++;
                            badEdges.add(Edge.edgeNumber('B', x, y));
                        } else if (edgeB.equals(this.colorToFace.get('U').getLetter())
                                || edgeB.equals(this.colorToFace.get('D').getLetter())) {
                            Character oppEdgeB = null;
                            if (y == 1) {
                                if (x == 0) {
                                    oppEdgeB = rightFace.getSquares()[2][1];
                                }
                                if (x == 2) {
                                    oppEdgeB = leftFace.getSquares()[0][1];
                                }
                            } else if (x == 1) {
                                if (y == 0) {
                                    oppEdgeB = downFace.getSquares()[1][0];
                                }
                                if (y == 2) {
                                    oppEdgeB = upFace.getSquares()[1][2];
                                }
                            }
                            if (oppEdgeB.equals(this.colorToFace.get('F').getLetter()) ||
                                    oppEdgeB.equals(this.colorToFace.get('B').getLetter())) {
                                countBad++;
                                badEdges.add(Edge.edgeNumber('B', x, y));
                            }
                        }
                        if ((x == 0 && y == 1) || (x == 2 && y == 1)) {
                            Character edgeU = upFace.squares[x][y];
                            if (edgeU.equals(this.colorToFace.get('L').getLetter()) ||
                                    edgeU.equals(this.colorToFace.get('R').getLetter())) {
                                countBad++;
                                badEdges.add(Edge.edgeNumber('U', x, y));
                            } else if (edgeU.equals(this.colorToFace.get('U').getLetter())
                                    || edgeU.equals(this.colorToFace.get('D').getLetter())) {
                                Character oppEdgeU = null;
                                if (x == 0) {
                                    oppEdgeU = leftFace.getSquares()[1][2];
                                }
                                if (x == 2) {
                                    oppEdgeU = rightFace.getSquares()[1][2];
                                }
                                if (oppEdgeU.equals(this.colorToFace.get('F').getLetter()) ||
                                        oppEdgeU.equals(this.colorToFace.get('B').getLetter())) {
                                    countBad++;
                                    badEdges.add(Edge.edgeNumber('U', x, y));
                                }
                            }

                            Character edgeD = downFace.squares[x][y];
                            if (edgeD.equals(this.colorToFace.get('L').getLetter()) ||
                                    edgeD.equals(this.colorToFace.get('R').getLetter())) {
                                countBad++;
                                badEdges.add(Edge.edgeNumber('D', x, y));
                            } else if (edgeD.equals(this.colorToFace.get('U').getLetter())
                                    || edgeD.equals(this.colorToFace.get('D').getLetter())) {
                                Character oppEdgeD = null;
                                if (x == 0) {
                                    oppEdgeD = leftFace.getSquares()[1][0];
                                }
                                if (x == 2) {
                                    oppEdgeD = rightFace.getSquares()[1][0];
                                }
                                if (oppEdgeD.equals(this.colorToFace.get('F').getLetter()) ||
                                        oppEdgeD.equals(this.colorToFace.get('B').getLetter())) {
                                    countBad++;
                                    badEdges.add(Edge.edgeNumber('D', x, y));
                                }
                            }
                        }
                    }
                }
            }

            //Now that we have our edges, we have to act
            int uCount = 0;
            int dCount = 0;
            int midCount = 0;
            ArrayList<Edge> upEdges = new ArrayList<>();
            ArrayList<Edge> downEdges = new ArrayList<>();
            ArrayList<Edge> midEdges = new ArrayList<>();
            for (Edge e : badEdges) {
                if (Utils.oneOnSide('U', e)) {
                    uCount++;
                    upEdges.add(e);
                } else if (Utils.oneOnSide('D', e)) {
                    dCount++;
                    downEdges.add(e);
                } else {
                    midCount++;
                    midEdges.add(e);
                }
            }
            ArrayList<Move> moves = new ArrayList<Move>();
            translate = Utils.horizontalMap('F');
            switch (badEdges.size()) {
                case 0:
                    //By some miracle(.05% chance) no edges are bad! Do nothing.
                    countBad = 0;
                    break;
                case 2:
                    if (uCount == 2) {
                        Edge upOne = upEdges.get(0);
                        Edge upTwo = upEdges.get(1);
                        Character faceValue;
                        if (upOne.primary.equals('U')) {
                            faceValue = upOne.secondary;
                        } else {
                            faceValue = upOne.primary;
                        }
                        Character otherValue;
                        if (upTwo.primary.equals('U')) {
                            otherValue = upTwo.secondary;
                        } else {
                            otherValue = upTwo.primary;
                        }
                        translate = Utils.horizontalMap(faceValue);
                        Character arrangement = translate.get(otherValue);

                        if ((faceValue == 'F' && otherValue == 'R') || (faceValue == 'R' && otherValue == 'B') || (faceValue == 'B' && otherValue == 'L') || (faceValue == 'L' && otherValue == 'F')) {
                            moves.add(Move.getMove(otherValue, Direction.Counterclockwise));
                            moves.add(Move.uCounter);
                            moves.add(Move.getMove(otherValue, Direction.Clockwise));
                            moves.add(Move.uCounter);
                        } else if ((faceValue == 'F' && otherValue == 'B') || (faceValue == 'L' && otherValue == 'R') || (faceValue == 'B' && otherValue == 'F') || (faceValue == 'R' && otherValue == 'L')) {
                            moves.add(Move.getMove(otherValue, Direction.Clockwise));
                            moves.add(Move.uClock);
                            moves.add(Move.getMove(translate.get('L'), Direction.Clockwise));
                            moves.add(Move.uCounter);
                        } else if ((faceValue == 'F' && otherValue == 'L') || (faceValue == 'L' && otherValue == 'B') || (faceValue == 'B' && otherValue == 'R') || (faceValue == 'R' && otherValue == 'F')) {
                            moves.add(Move.getMove(otherValue, Direction.Counterclockwise));
                            moves.add(Move.uClock);
                            moves.add(Move.getMove(otherValue, Direction.Clockwise));
                            moves.add(Move.uCounter);
                        }
                    } else if (dCount == 2) {
                        Edge downOne = downEdges.get(0);
                        Edge downTwo = downEdges.get(1);
                        Character faceValue;
                        if (downOne.primary.equals('D')) {
                            faceValue = downOne.secondary;
                        } else {
                            faceValue = downOne.primary;
                        }
                        Character otherValue;
                        if (downTwo.primary.equals('D')) {
                            otherValue = downTwo.secondary;
                        } else {
                            otherValue = downTwo.primary;
                        }
                        translate = Utils.horizontalMap(faceValue);
                        Character arrangement = translate.get(otherValue);
                        if ((faceValue == 'F' && otherValue == 'R') || (faceValue == 'R' && otherValue == 'B') || (faceValue == 'B' && otherValue == 'L') || (faceValue == 'L' && otherValue == 'F')) {
                            moves.add(Move.getMove(otherValue, Direction.Clockwise));
                            moves.add(Move.dClock);
                            moves.add(Move.getMove(otherValue, Direction.Counterclockwise));
                            moves.add(Move.dCounter);
                        } else if ((faceValue == 'F' && otherValue == 'B') || (faceValue == 'L' && otherValue == 'R') || (faceValue == 'B' && otherValue == 'F') || (faceValue == 'R' && otherValue == 'L')) {
                            moves.add(Move.getMove(otherValue, Direction.Clockwise));
                            moves.add(Move.dClock);
                            moves.add(Move.getMove(translate.get('R'), Direction.Clockwise));
                            moves.add(Move.dCounter);
                        } else if ((faceValue == 'F' && otherValue == 'L') || (faceValue == 'L' && otherValue == 'B') || (faceValue == 'B' && otherValue == 'R') || (faceValue == 'R' && otherValue == 'F')) {
                            moves.add(Move.getMove(otherValue, Direction.Counterclockwise));
                            moves.add(Move.uCounter);
                            moves.add(Move.getMove(otherValue, Direction.Clockwise));
                            moves.add(Move.uClock);
                        }
                    } else if (uCount == 1) {
                        if (dCount == 1) {
                            Edge up = upEdges.get(0);
                            Edge down = downEdges.get(0);
                            Character matters;
                            if (down.primary.equals('D')) {
                                matters = down.secondary;
                            } else {
                                matters = down.primary;
                            }
                            if (Utils.shareFace(up, down)) {
                                moves.add(Move.uEighty);
                                moves.add(Move.getMove(matters, Direction.OneEighty));
                            } else {
                                moves.add(Move.getMove(matters, Direction.OneEighty));
                            }
                        } else if (midCount == 1) {
                            Edge m = midEdges.get(0);
                            Edge u = upEdges.get(0);
                            switch (m) {
                                case FL:
                                    if (u.equals(Edge.FU)) {
                                        moves.add(Move.uClock);
                                        moves.add(Move.lCounter);
                                        moves.add(Move.uCounter);
                                    } else if (u.equals(Edge.BU)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.lCounter);
                                        moves.add(Move.uClock);
                                    } else if (u.equals(Edge.UL)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.fClock);
                                        moves.add(Move.uClock);
                                    } else {
                                        moves.add(Move.uClock);
                                        moves.add(Move.fClock);
                                        moves.add(Move.uCounter);
                                    }
                                    break;
                                case FR:
                                    if (u.equals(Edge.FU)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.rClock);
                                        moves.add(Move.uClock);
                                    } else if (u.equals(Edge.BU)) {
                                        moves.add(Move.uClock);
                                        moves.add(Move.rClock);
                                        moves.add(Move.uCounter);
                                    } else if (u.equals(Edge.UL)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.fCounter);
                                        moves.add(Move.uClock);
                                    } else {
                                        moves.add(Move.uClock);
                                        moves.add(Move.fCounter);
                                        moves.add(Move.uCounter);
                                    }
                                    break;
                                case BL:
                                    if (u.equals(Edge.FU)) {
                                        moves.add(Move.uClock);
                                        moves.add(Move.lClock);
                                        moves.add(Move.uCounter);
                                    } else if (u.equals(Edge.BU)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.lClock);
                                        moves.add(Move.uClock);
                                    } else if (u.equals(Edge.UL)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.bCounter);
                                        moves.add(Move.uClock);
                                    } else {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.bCounter);
                                        moves.add(Move.uClock);
                                    }
                                    break;
                                case BR:
                                    if (u.equals(Edge.FU)) {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.rCounter);
                                        moves.add(Move.uClock);
                                    } else if (u.equals(Edge.BU)) {
                                        moves.add(Move.uClock);
                                        moves.add(Move.rCounter);
                                        moves.add(Move.uCounter);
                                    } else if (u.equals(Edge.UL)) {
                                        moves.add(Move.uClock);
                                        moves.add(Move.bClock);
                                        moves.add(Move.uCounter);
                                    } else {
                                        moves.add(Move.uCounter);
                                        moves.add(Move.bClock);
                                        moves.add(Move.uClock);
                                    }
                                    break;
                            }
                        }
                    } else if (dCount == 1) {
                        if (midCount == 1) {
                            Edge m = midEdges.get(0);
                            Edge d = downEdges.get(0);
                            switch (m) {
                                case FL:
                                    if (d.equals(Edge.FD)) {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.lClock);
                                        moves.add(Move.dClock);
                                    } else if (d.equals(Edge.BD)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.lClock);
                                        moves.add(Move.dCounter);
                                    } else if (d.equals(Edge.DL)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.fCounter);
                                        moves.add(Move.dCounter);
                                    } else {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.fCounter);
                                        moves.add(Move.dClock);
                                    }
                                    break;
                                case FR:
                                    if (d.equals(Edge.FD)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.rCounter);
                                        moves.add(Move.dCounter);
                                    } else if (d.equals(Edge.BD)) {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.rCounter);
                                        moves.add(Move.dClock);
                                    } else if (d.equals(Edge.DL)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.fClock);
                                        moves.add(Move.dCounter);
                                    } else {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.fClock);
                                        moves.add(Move.dClock);
                                    }
                                    break;
                                case BL:
                                    if (d.equals(Edge.FD)) {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.lCounter);
                                        moves.add(Move.dClock);
                                    } else if (d.equals(Edge.BD)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.lCounter);
                                        moves.add(Move.dCounter);
                                    } else if (d.equals(Edge.DL)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.bClock);
                                        moves.add(Move.dCounter);
                                    } else {
                                        moves.add(Move.dClock);
                                        moves.add(Move.bClock);
                                        moves.add(Move.dCounter);
                                    }
                                    break;
                                case BR:
                                    if (d.equals(Edge.FD)) {
                                        moves.add(Move.dClock);
                                        moves.add(Move.rClock);
                                        moves.add(Move.dCounter);
                                    } else if (d.equals(Edge.BD)) {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.rClock);
                                        moves.add(Move.dClock);
                                    } else if (d.equals(Edge.DL)) {
                                        moves.add(Move.dCounter);
                                        moves.add(Move.bCounter);
                                        moves.add(Move.dClock);
                                    } else {
                                        moves.add(Move.dClock);
                                        moves.add(Move.bCounter);
                                        moves.add(Move.dCounter);
                                    }
                                    break;
                            }
                        }
                    } else if (midCount == 2) {
                        Edge mid = midEdges.get(0);
                        moves.add(Move.getMove(mid.primary, Direction.Random));
                    }
                    break;
                case 4:
                    moves = this.fourBad(moves, upEdges, midEdges, downEdges);
                    break;
                case 6:
                    if (uCount >= 3 || dCount >= 3) {
                        if (uCount >= 3) {
                            moves.add(Move.uClock);
                        }
                        if (dCount >= 3) {
                            moves.add(Move.dClock);
                        }
                    } else {
                        ArrayList<Edge> upEdgesLocal = new ArrayList<>();
                        ArrayList<Edge> downEdgesLocal = new ArrayList<>();
                        ArrayList<Edge> midEdgesLocal = new ArrayList<>();
                        int additive = r.nextInt(6);
                        for (int i = 0; i < 4; i++) {
                            Edge e = badEdges.get((i + additive) % 6);
                            if (Utils.oneOnSide('U', e)) {
                                upEdgesLocal.add(e);
                            } else if (Utils.oneOnSide('D', e)) {
                                downEdgesLocal.add(e);
                            } else {
                                midEdgesLocal.add(e);
                            }
                        }
                        moves = this.fourBad(moves, upEdgesLocal, midEdgesLocal, downEdgesLocal);
                    }
                    break;
                case 8:
                    ArrayList<Edge> upEdgesLocalEight = new ArrayList<>();
                    ArrayList<Edge> downEdgesLocalEight = new ArrayList<>();
                    ArrayList<Edge> midEdgesLocalEight = new ArrayList<>();
                    int additive = r.nextInt(6);
                    for (int i = 0; i < 4; i++) {
                        Edge e = badEdges.get((i + additive) % 8);
                        if (Utils.oneOnSide('U', e)) {
                            upEdgesLocalEight.add(e);
                        } else if (Utils.oneOnSide('D', e)) {
                            downEdgesLocalEight.add(e);
                        } else {
                            midEdgesLocalEight.add(e);
                        }
                    }
                    moves = this.fourBad(moves, upEdgesLocalEight, midEdgesLocalEight, downEdgesLocalEight);
                    break;
                case 10:
                    if (midCount == 2) {
                        moves.add(Move.uRandom);
                        moves.add(Move.dRandom);
                    } else {
                        ArrayList<Edge> goodEdges = new ArrayList<>();
                        for (Edge e : Edge.values()) {
                            if (!badEdges.contains(e)) {
                                goodEdges.add(e);
                            }
                        }
                        for (Edge ge : goodEdges) {
                            switch (ge) {
                                case FU:
                                    moves.add(Move.fRandom);
                                    break;
                                case FD:
                                    moves.add(Move.fRandom);
                                    break;
                                case BU:
                                    moves.add(Move.bRandom);
                                    break;
                                case BD:
                                    moves.add(Move.bRandom);
                                    break;
                                case UL:
                                    moves.add(Move.lRandom);
                                    break;
                                case UR:
                                    moves.add(Move.rRandom);
                                    break;
                                case DL:
                                    moves.add(Move.lRandom);
                                    break;
                                case DR:
                                    moves.add(Move.rRandom);
                                    break;
                            }
                        }
                        moves.add(Move.uRandom);
                        moves.add(Move.dRandom);
                    }
                    break;
                case 12:
                    moves.add(Move.dClock);
                    moves.add(Move.bClock);
                    moves.add(Move.fClock);
                    moves.add(Move.uClock);
                    moves.add(Move.rCounter);
                    moves.add(Move.lCounter);
                    moves.add(Move.dClock);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + badEdges.size());
            }
            System.out.println(countBad);
            this.translateAndExecute(translate, moves);
            System.out.println(cube.toString());
        }
    }

    private ArrayList<Move> fourBad(ArrayList<Move> moves, ArrayList<Edge> upEdges, ArrayList<Edge> midEdges, ArrayList<Edge> downEdges) {
        int uCount = upEdges.size();
        int midCount = midEdges.size();
        int dCount = downEdges.size();
        if (uCount == 4) {
            moves.add(Move.uClock);
        } else if (dCount == 4) {
            moves.add(Move.dClock);
        } else if (midCount == 4) {
            moves.add(Move.lClock);
            moves.add(Move.rClock);
            moves.add(Move.uClock);
            moves.add(Move.dClock);
        } else if (uCount == 2 && dCount == 2) {
            // Check if there are any down edges that dont share with up edges
            Edge upOne = upEdges.get(0);
            Edge upTwo = upEdges.get(1);
            Edge downOne = downEdges.get(0);
            Edge downTwo = downEdges.get(1);
            Character importantFaceOne;
            Character importantFaceTwo;
            if (downOne.primary.equals('U') || downOne.primary.equals('D')) {
                importantFaceOne = downOne.secondary;
            } else {
                importantFaceOne = downOne.primary;
            }
            if (downTwo.primary.equals('U') || downTwo.primary.equals('D')) {
                importantFaceTwo = downTwo.secondary;
            } else {
                importantFaceTwo = downTwo.primary;
            }
            if ((!Utils.shareFace(upOne, downOne) && !Utils.shareFace(upTwo, downOne)) ||
                    (!Utils.shareFace(upOne, downTwo) && !Utils.shareFace(upTwo, downTwo))) {
                if (!Utils.shareFace(upOne, downOne) && !Utils.shareFace(upTwo, downOne)) {
                    moves.add(Move.getMove(importantFaceOne, Direction.OneEighty));
                }
                if (!Utils.shareFace(upOne, downTwo) && !Utils.shareFace(upTwo, downTwo)) {
                    moves.add(Move.getMove(importantFaceTwo, Direction.OneEighty));
                }
            } else {
                moves.add(Move.dClock);
            }
        } else if (midCount == 2 && uCount == 2) {
            Edge upOne = upEdges.get(0);
            Edge upTwo = upEdges.get(1);
            boolean samePrim = midEdges.get(0).primary == midEdges.get(1).primary;
            boolean sameSecon = midEdges.get(0).secondary == midEdges.get(1).secondary;
            Character sharedFace;
            if (samePrim || sameSecon) {
                if (samePrim) {
                    sharedFace = midEdges.get(0).primary;
                } else {
                    sharedFace = midEdges.get(0).secondary;
                }
                translate = Utils.horizontalMap(sharedFace);
                if (Utils.onSide(sharedFace, upOne, upTwo)) {
                    if (Utils.onSide(translate.get('L'), upOne, upTwo)) {
                        moves.add(Move.rClock);
                        moves.add(Move.lCounter);
                        // moves.add(Move.dCounter);
                        // moves.add(Move.bEighty);
                        moves.add(Move.bCounter);
                        moves.add(Move.uClock);
                    } else if (Utils.onSide(translate.get('R'), upOne, upTwo)) {
                        moves.add(Move.lCounter);
                        moves.add(Move.rClock);
                        // moves.add(Move.dClock);
                        // moves.add(Move.bEighty);
                        moves.add(Move.bClock);
                        moves.add(Move.uClock);
                    } else if (Utils.onSide(translate.get('B'), upOne, upTwo)) {
                        moves.add(Move.rClock);
                        moves.add(Move.lCounter);
                        moves.add(Move.uClock);
                    }
                } else if (Utils.onSide(translate.get('L'), upOne, upTwo)) {
                    if (Utils.onSide(translate.get('R'), upOne, upTwo)) {
                        moves.add(Move.getMove(sharedFace, Direction.Random));
                    } else if (Utils.onSide(translate.get('B'), upOne, upTwo)) {
                        moves.add(Move.rClock);
                        moves.add(Move.fClock);
                        moves.add(Move.uClock);
                    }
                } else if (Utils.onSide(translate.get('B'), upOne, upTwo)) {
                    if (Utils.onSide(translate.get('R'), upOne, upTwo)) {
                        moves.add(Move.rCounter);
                        moves.add(Move.fCounter);
                        moves.add(Move.uClock);
                    }
                }
            } else {
                Character base = null;
                if (Utils.onSide(midEdges.get(0).primary, upOne, upTwo)) {
                    base = midEdges.get(0).primary;
                } else if (Utils.onSide(midEdges.get(0).secondary, upOne, upTwo)) {
                    base = midEdges.get(0).secondary;
                } else if (Utils.onSide(midEdges.get(1).primary, upOne, upTwo)) {
                    base = midEdges.get(1).primary;
                } else if (Utils.onSide(midEdges.get(1).secondary, upOne, upTwo)) {
                    base = midEdges.get(1).secondary;
                }
                translate = Utils.horizontalMap(base);
                if (Utils.onSide(translate.get('B'), upOne, upTwo)) {
                    moves.add(Move.rClock);
                    moves.add(Move.lClock);
                } else if (Utils.onSide(translate.get('L'), upOne, upTwo)) {
                    moves.add(Move.rClock);
                    moves.add(Move.bCounter);
                } else if (Utils.onSide(translate.get('R'), upOne, upTwo)) {
                    moves.add(Move.lClock);
                    moves.add(Move.uClock);
                    moves.add(Move.rClock);
                }
            }
        } else if (midCount == 2 && dCount == 2) {
            Edge downOne = downEdges.get(0);
            Edge downTwo = downEdges.get(1);
            boolean samePrim = midEdges.get(0).primary == midEdges.get(1).primary;
            boolean sameSecon = midEdges.get(0).secondary == midEdges.get(1).secondary;
            Character sharedFace;
            if (samePrim || sameSecon) {
                if (samePrim) {
                    sharedFace = midEdges.get(0).primary;
                } else {
                    sharedFace = midEdges.get(0).secondary;
                }
                translate = Utils.horizontalMap(sharedFace);
                if (Utils.onSide(sharedFace, downOne, downTwo)) {
                    if (Utils.onSide(translate.get('L'), downOne, downTwo)) {
                        moves.add(Move.rCounter);
                        moves.add(Move.lClock);
                        // moves.add(Move.dClock);
                        // moves.add(Move.bEighty);
                        moves.add(Move.bClock);
                        moves.add(Move.dCounter);
                    } else if (Utils.onSide(translate.get('R'), downOne, downTwo)) {
                        moves.add(Move.lClock);
                        moves.add(Move.rClock);
                        // moves.add(Move.dCounter);
                        // moves.add(Move.bEighty);
                        moves.add(Move.bCounter);
                        moves.add(Move.dCounter);
                    } else if (Utils.onSide(translate.get('B'), downOne, downTwo)) {
                        moves.add(Move.rCounter);
                        moves.add(Move.lClock);
                        moves.add(Move.dCounter);
                    }
                } else if (Utils.onSide(translate.get('L'), downOne, downTwo)) {
                    if (Utils.onSide(translate.get('R'), downOne, downTwo)) {
                        moves.add(Move.getMove(sharedFace, Direction.Random));
                    } else if (Utils.onSide(translate.get('B'), downOne, downTwo)) {
                        moves.add(Move.rCounter);
                        moves.add(Move.fCounter);
                        moves.add(Move.dCounter);
                    }
                } else if (Utils.onSide(translate.get('B'), downOne, downTwo)) {
                    if (Utils.onSide(translate.get('R'), downOne, downTwo)) {
                        moves.add(Move.lClock);
                        moves.add(Move.uClock);
                        moves.add(Move.dCounter);
                    }
                }
            } else {
                Character base = null;
                if (Utils.onSide(midEdges.get(0).primary, downOne, downTwo)) {
                    base = midEdges.get(0).primary;
                } else if (Utils.onSide(midEdges.get(0).secondary, downOne, downTwo)) {
                    base = midEdges.get(0).secondary;
                } else if (Utils.onSide(midEdges.get(1).primary, downOne, downTwo)) {
                    base = midEdges.get(1).primary;
                } else if (Utils.onSide(midEdges.get(1).secondary, downOne, downTwo)) {
                    base = midEdges.get(1).secondary;
                }
                translate = Utils.horizontalMap(base);
                if (Utils.onSide(translate.get('B'), downOne, downTwo)) {
                    moves.add(Move.rCounter);
                    moves.add(Move.lCounter);
                } else if (Utils.onSide(translate.get('L'), downOne, downTwo)) {
                    moves.add(Move.rCounter);
                    moves.add(Move.bClock);
                } else if (Utils.onSide(translate.get('R'), downOne, downTwo)) {
                    moves.add(Move.lCounter);
                    moves.add(Move.uCounter);
                    moves.add(Move.rCounter);
                }
            }
        } else if (midCount == 2 && uCount == 1 && dCount == 1) {
            Edge upOne = upEdges.get(0);
            Edge downOne = downEdges.get(0);
            boolean samePrim = midEdges.get(0).primary == midEdges.get(1).primary;
            boolean sameSecon = midEdges.get(0).secondary == midEdges.get(1).secondary;
            Character sharedFace;
            if (samePrim || sameSecon) {
                if (samePrim) {
                    sharedFace = midEdges.get(0).primary;
                } else {
                    sharedFace = midEdges.get(0).secondary;
                }
                translate = Utils.horizontalMap(sharedFace);
                if (sharedFace.equals(upOne.primary) || sharedFace.equals(upOne.secondary)) {
                    if (sharedFace.equals(downOne.primary) || sharedFace.equals(downOne.secondary)) {
                        moves.add(Move.rClock);
                        moves.add(Move.lCounter);
                        //moves.add(Move.dEighty);
                        //moves.add(Move.bEighty);
                        moves.add(Move.uClock);
                    } else if (translate.get('R').equals(downOne.primary) || translate.get('R').equals(downOne.secondary)) {
                        //moves.add(Move.dClock);
                        moves.add(Move.rClock);
                        moves.add(Move.lCounter);
                        //moves.add(Move.bEighty);
                        moves.add(Move.uClock);
                    } else if (translate.get('L').equals(downOne.primary) || translate.get('L').equals(downOne.secondary)) {
                        //moves.add(Move.dCounter);
                        moves.add(Move.rClock);
                        moves.add(Move.lCounter);
                        //moves.add(Move.bEighty);
                        moves.add(Move.uClock);
                    } else if (translate.get('B').equals(downOne.primary) || translate.get('B').equals(downOne.secondary)) {
                        moves.add(Move.rClock);
                        moves.add(Move.lCounter);
                        //moves.add(Move.bEighty);
                        moves.add(Move.uClock);
                    }
                } else if (sharedFace.equals(downOne.primary) || sharedFace.equals(downOne.secondary)) {
                    if (translate.get('R').equals(upOne.primary) || translate.get('R').equals(upOne.secondary)) {
                        //moves.add(Move.uCounter);
                        moves.add(Move.rCounter);
                        moves.add(Move.lClock);
                        //moves.add(Move.bEighty);
                        moves.add(Move.dClock);
                    } else if (translate.get('L').equals(upOne.primary) || translate.get('L').equals(upOne.secondary)) {
                        //moves.add(Move.uClock);
                        moves.add(Move.rCounter);
                        moves.add(Move.lClock);
                        //moves.add(Move.bEighty);
                        moves.add(Move.dClock);
                    } else if (translate.get('B').equals(upOne.primary) || translate.get('B').equals(upOne.secondary)) {
                        moves.add(Move.rCounter);
                        moves.add(Move.lClock);
                        //moves.add(Move.bEighty);
                        moves.add(Move.dClock);
                    }
                } else {
                    moves.add(Move.getMove(sharedFace, Direction.Clockwise));
                }
            } else {
                moves.add(Move.fEighty);
            }
        } else if (midCount == 1 && dCount == 1 && uCount == 2) {
            Edge mid = midEdges.get(0);
            Edge upOne = upEdges.get(0);
            Edge upTwo = upEdges.get(1);
            if (Utils.onSide(mid.primary, upOne, upTwo)) {
                if (Utils.onSide(mid.secondary, upOne, upTwo)) {
                    moves.add(Move.uClock);
                } else {
                    moves.add(Move.getMove(mid.secondary, Direction.Clockwise));
                }
            } else if (Utils.onSide(mid.secondary, upOne, upTwo)) {
                moves.add(Move.getMove(mid.primary, Direction.Counterclockwise));
            } else {
                switch (mid) {
                    case FR:
                        moves.add(Move.fCounter);
                        break;
                    case FL:
                        moves.add(Move.fClock);
                        break;
                    case BR:
                        moves.add(Move.bClock);
                        break;
                    case BL:
                        moves.add(Move.bCounter);
                        break;
                }
            }
        } else if (midCount == 1 && uCount == 3) {
            Edge mid = midEdges.get(0);
            Edge upOne = upEdges.get(0);
            Edge upTwo = upEdges.get(1);
            Edge upThree = upEdges.get(2);
            if (Utils.onSide(mid.primary, upOne, upTwo) || Utils.oneOnSide(mid.primary, upThree)) {
                moves.add(Move.uClock);
            } else {
                moves.add(Move.getMove(mid.primary, Direction.Clockwise));
                moves.add(Move.uClock);
            }
        } else if (midCount == 1 && dCount == 2 && uCount == 1) {
            Edge mid = midEdges.get(0);
            Edge downOne = downEdges.get(0);
            Edge downTwo = downEdges.get(1);
            if (Utils.onSide(mid.primary, downOne, downTwo)) {
                if (Utils.onSide(mid.secondary, downOne, downTwo)) {
                    moves.add(Move.dClock);
                } else {
                    moves.add(Move.getMove(mid.secondary, Direction.Counterclockwise));
                }
            } else if (Utils.onSide(mid.secondary, downOne, downTwo)) {
                moves.add(Move.getMove(mid.primary, Direction.Clockwise));
            } else {
                switch (mid) {
                    case FR:
                        moves.add(Move.fClock);
                        break;
                    case FL:
                        moves.add(Move.fCounter);
                        break;
                    case BR:
                        moves.add(Move.bCounter);
                        break;
                    case BL:
                        moves.add(Move.bClock);
                        break;
                }
            }
        } else if (midCount == 1 && dCount == 3) {
            Edge mid = midEdges.get(0);
            Edge downOne = downEdges.get(0);
            Edge downTwo = downEdges.get(1);
            Edge downThree = downEdges.get(2);
            if (Utils.onSide(mid.primary, downOne, downTwo) || Utils.oneOnSide(mid.primary, downThree)) {
                moves.add(Move.dClock);
            } else {
                moves.add(Move.getMove(mid.primary, Direction.Counterclockwise));
                moves.add(Move.dClock);
            }
        } else if (midCount == 3 && uCount == 1) {
            Edge up = upEdges.get(0);
            Edge midOne = midEdges.get(0);
            Edge midTwo = midEdges.get(1);
            Edge midThree = midEdges.get(2);
            Character blocked;
            if (up.primary == 'U') {
                blocked = up.secondary;
            } else {
                blocked = up.primary;
            }
            if (midOne.primary.equals(blocked)) {
                moves.add(Move.getMove(midOne.secondary, Direction.Clockwise));
            } else if (midOne.secondary.equals(blocked)) {
                moves.add(Move.getMove(midOne.primary, Direction.Counterclockwise));
            }
            if (midTwo.primary.equals(blocked)) {
                moves.add(Move.getMove(midTwo.secondary, Direction.Clockwise));
            } else if (midTwo.secondary.equals(blocked)) {
                moves.add(Move.getMove(midTwo.primary, Direction.Counterclockwise));
            }
            if (midThree.primary.equals(blocked)) {
                moves.add(Move.getMove(midThree.secondary, Direction.Clockwise));
            } else if (midThree.secondary.equals(blocked)) {
                moves.add(Move.getMove(midThree.primary, Direction.Counterclockwise));
            }
        } else if (midCount == 3 && dCount == 1) {
            Edge down = downEdges.get(0);
            Edge midOne = midEdges.get(0);
            Edge midTwo = midEdges.get(1);
            Edge midThree = midEdges.get(2);
            Character blocked;
            if (down.primary == 'D') {
                blocked = down.secondary;
            } else {
                blocked = down.primary;
            }
            if (midOne.primary.equals(blocked)) {
                moves.add(Move.getMove(midOne.secondary, Direction.Clockwise));
            } else if (midOne.secondary.equals(blocked)) {
                moves.add(Move.getMove(midOne.primary, Direction.Counterclockwise));
            }
            if (midTwo.primary.equals(blocked)) {
                moves.add(Move.getMove(midTwo.secondary, Direction.Clockwise));
            } else if (midTwo.secondary.equals(blocked)) {
                moves.add(Move.getMove(midTwo.primary, Direction.Counterclockwise));
            }
            if (midOne.primary.equals(blocked)) {
                moves.add(Move.getMove(midOne.secondary, Direction.Clockwise));
            } else if (midThree.secondary.equals(blocked)) {
                moves.add(Move.getMove(midThree.primary, Direction.Counterclockwise));
            }
        } else if (dCount == 1 && uCount == 3) {
            Edge upOne = upEdges.get(0);
            Edge upTwo = upEdges.get(1);
            Edge upThree = upEdges.get(2);
            Edge downOne = downEdges.get(0);
            if (Utils.onSide(downOne.primary, upOne, upTwo) || Utils.onSide(downOne.secondary, upOne, upTwo) || Utils.oneOnSide(downOne.primary, upThree) || Utils.oneOnSide(downOne.secondary, upThree)) {
                moves.add(Move.uClock);
            } else {
                Character downturn;
                if (downOne.primary.equals('D')) {
                    downturn = downOne.secondary;
                } else {
                    downturn = downOne.primary;
                }
                moves.add(Move.getMove(downturn, Direction.OneEighty));
                moves.add(Move.uClock);
            }
        } else if (dCount == 3 && uCount == 1) {
            Edge downOne = downEdges.get(0);
            Edge downTwo = downEdges.get(1);
            Edge downThree = downEdges.get(2);
            Edge upOne = upEdges.get(0);
            if (Utils.onSide(upOne.primary, downOne, downTwo) || Utils.onSide(upOne.secondary, downOne, downTwo) || Utils.oneOnSide(upOne.primary, downThree) || Utils.oneOnSide(upOne.secondary, downThree)) {
                moves.add(Move.dClock);
            } else {
                Character upturn;
                if (upOne.primary.equals('U')) {
                    upturn = upOne.secondary;
                } else {
                    upturn = upOne.primary;
                }
                moves.add(Move.getMove(upturn, Direction.OneEighty));
                moves.add(Move.dClock);
            }
        }
        return moves;
    }


    private void translateAndExecute(HashMap<Character, Character> map, List<Move> moves) {
        for (Move move : moves) {
            Character actual = map.get(move.face);
            System.out.println("Original = " + move.face + " Actual = " + actual);
            Color turning = colorToFace.get(actual);
            switch (move.dir) {
                case Random:
                    this.cube.rotate(turning, r.nextBoolean());
                    break;
                case Clockwise:
                    this.cube.rotate(turning, true);
                    break;
                case Counterclockwise:
                    this.cube.rotate(turning, false);
                    break;
                case OneEighty:
                    this.cube.oneEighty(turning);
                    break;
            }
        }

    }

    private void phaseTwo() {
        Side frontFace = this.cube.getSide(this.colorToFace.get('F'));
        Side backFace = this.cube.getSide(this.colorToFace.get('B'));
        Side rightFace = this.cube.getSide(this.colorToFace.get('R'));
        Side leftFace = this.cube.getSide(this.colorToFace.get('L'));
        Side upFace = this.cube.getSide(this.colorToFace.get('U'));
        Side downFace = this.cube.getSide(this.colorToFace.get('D'));
        ArrayList<Edge> fronts = new ArrayList<>();

        for (Edge e : Edge.values()) {
            switch (e) {
                case FU:
                    if (Utils.FBUDEdges(frontFace, 1, 2, upFace, 1, 0)) {
                        fronts.add(e);
                    }
                    break;
                case FD:
                    if (Utils.FBUDEdges(frontFace, 1, 0, downFace, 1, 2)) {
                        fronts.add(e);
                    }
                    break;
                case FR:
                    if (Utils.FBUDEdges(frontFace, 2, 1, rightFace, 0, 1)) {
                        fronts.add(e);
                    }
                    break;
                case FL:
                    if (Utils.FBUDEdges(frontFace, 0, 1, leftFace, 2, 1)) {
                        fronts.add(e);
                    }
                    break;
                case BU:
                    if (Utils.FBUDEdges(backFace, 1, 2, upFace, 1, 2)) {
                        fronts.add(e);
                    }
                    break;
                case BD:
                    if (Utils.FBUDEdges(backFace, 1, 0, downFace, 1, 0)) {
                        fronts.add(e);
                    }
                    break;
                case BR:
                    if (Utils.FBUDEdges(backFace, 0, 1, rightFace, 2, 1)) {
                        fronts.add(e);
                    }
                    break;
                case BL:
                    if (Utils.FBUDEdges(backFace, 2, 1, leftFace, 0, 1)) {
                        fronts.add(e);
                    }
                    break;
                case UR:
                    if (Utils.FBUDEdges(upFace, 2, 1, rightFace, 1, 2)) {
                        fronts.add(e);
                    }
                    break;
                case UL:
                    if (Utils.FBUDEdges(upFace, 0, 1, leftFace, 1, 2)) {
                        fronts.add(e);
                    }
                    break;
                case DR:
                    if (Utils.FBUDEdges(downFace, 2, 1, rightFace, 1, 0)) {
                        fronts.add(e);
                    }
                    break;
                case DL:
                    if (Utils.FBUDEdges(downFace, 0, 1, leftFace, 1, 0)) {
                        fronts.add(e);
                    }
                    break;
            }
        }
        int uCount = 0;
        int dCount = 0;
        int midCount = 0;
        ArrayList<Edge> upEdges = new ArrayList<>();
        ArrayList<Edge> downEdges = new ArrayList<>();
        ArrayList<Edge> midEdges = new ArrayList<>();
        for (Edge e : fronts) {
            if (Utils.oneOnSide('U', e)) {
                uCount++;
                upEdges.add(e);
            } else if (Utils.oneOnSide('D', e)) {
                dCount++;
                downEdges.add(e);
            } else {
                midCount++;
                midEdges.add(e);
            }
        }
        boolean notDone = true;
        while (notDone) {
            ArrayList<Move> moves = new ArrayList<Move>();
            if (uCount == 4) {
                moves.add(Move.fEighty);
                moves.add(Move.dEighty);
                moves.add(Move.rClock);
                moves.add(Move.lCounter);
                moves.add(Move.bClock);
                notDone = false;
            } else if (dCount == 4) {
                moves.add(Move.fEighty);
                moves.add(Move.dEighty);
                moves.add(Move.lClock);
                moves.add(Move.rCounter);
                moves.add(Move.fClock);
                notDone = !notDone;
            } else if (midCount == 4) {
                notDone = false;
            } else if (uCount == 3) {
                ArrayList<Character> middles = new ArrayList();
                middles.add('F');
                middles.add('R');
                middles.add('B');
                middles.add('L');
                Edge upOne = upEdges.get(0);
                Edge upTwo = upEdges.get(1);
                Edge upThree = upEdges.get(2);
                middles.remove(Utils.notUpOrDown(upOne));
                middles.remove(Utils.notUpOrDown(upTwo));
                middles.remove(Utils.notUpOrDown(upThree));
                if (dCount == 1) {
                    Edge down = downEdges.get(0);
                    Character main = Utils.notUpOrDown(down);
                    translate = Utils.horizontalMap(main);
                    Character other = translate.get(middles.get(0));
                    switch (other) {
                        case 'R':
                            moves.add(Move.bCounter);
                            moves.add(Move.lCounter);
                            moves.add(Move.fRandom);
                            break;
                        case 'L':
                            moves.add(Move.bClock);
                            moves.add(Move.rClock);
                            moves.add(Move.fRandom);
                            break;
                        case 'B':
                            moves.add(Move.rClock);
                            moves.add(Move.lCounter);
                            moves.add(Move.fClock);
                            break;
                        case 'F':
                            moves.add(Move.uEighty);
                            moves.add(Move.rClock);
                            moves.add(Move.lCounter);
                            moves.add(Move.fRandom);
                    }
                    notDone = !notDone;
                } else if (midCount == 1) {
                    Edge mid = midEdges.get(0);
                    Character main = mid.primary;
                    Character other = middles.get(0);
                    switch (mid) {
                        case FR:
                            if (other.equals('F')) {
                                moves.add(Move.fCounter);
                                notDone = false;
                            } else if (other.equals('R')) {
                                moves.add(Move.rClock);
                                notDone = false;
                            } else if (other.equals('B')) {
                                moves.add(Move.uClock);
                            } else if (other.equals('L')) {
                                moves.add(Move.uCounter);
                            }
                            break;
                        case FL:
                            if (other.equals('F')) {
                                moves.add(Move.fClock);
                                notDone = false;
                            } else if (other.equals('R')) {
                                moves.add(Move.uClock);
                            } else if (other.equals('B')) {
                                moves.add(Move.uCounter);
                            } else if (other.equals('L')) {
                                moves.add(Move.lCounter);
                                notDone = false;
                            }
                            break;
                        case BL:
                            if (other.equals('F')) {
                                moves.add(Move.uClock);
                            } else if (other.equals('R')) {
                                moves.add(Move.uCounter);
                            } else if (other.equals('B')) {
                                moves.add(Move.bCounter);
                                notDone = false;
                            } else if (other.equals('L')) {
                                moves.add(Move.lClock);
                                notDone = false;
                            }
                            break;
                        case BR:
                            if (other.equals('F')) {
                                moves.add(Move.uCounter);
                            } else if (other.equals('R')) {
                                moves.add(Move.rCounter);
                                notDone = false;
                            } else if (other.equals('B')) {
                                moves.add(Move.bClock);
                                notDone = false;
                            } else if (other.equals('L')) {
                                moves.add(Move.uClock);
                            }
                            break;
                    }
                }
            } else if (dCount == 3) {
                ArrayList<Character> middles = new ArrayList();
                middles.add('F');
                middles.add('R');
                middles.add('B');
                middles.add('L');
                Edge downOne = downEdges.get(0);
                Edge downTwo = downEdges.get(1);
                Edge downThree = downEdges.get(2);
                middles.remove(Utils.notUpOrDown(downOne));
                middles.remove(Utils.notUpOrDown(downTwo));
                middles.remove(Utils.notUpOrDown(downThree));
                if (uCount == 1) {
                    Edge up = downEdges.get(0);
                    Character main = Utils.notUpOrDown(up);
                    translate = Utils.horizontalMap(main);
                    Character other = translate.get(middles.get(0));
                    switch (other) {
                        case 'R':
                            moves.add(Move.bClock);
                            moves.add(Move.lClock);
                            moves.add(Move.fRandom);
                            break;
                        case 'L':
                            moves.add(Move.bCounter);
                            moves.add(Move.rCounter);
                            moves.add(Move.fRandom);
                            break;
                        case 'B':
                            moves.add(Move.rCounter);
                            moves.add(Move.lClock);
                            moves.add(Move.fClock);
                            break;
                        case 'F':
                            moves.add(Move.uEighty);
                            moves.add(Move.lCounter);
                            moves.add(Move.rClock);
                            moves.add(Move.bRandom);
                    }
                    notDone = !notDone;
                } else if (midCount == 1) {
                    Edge mid = midEdges.get(0);
                    Character main = mid.primary;
                    Character other = middles.get(0);
                    switch (mid) {
                        case FR:
                            if (other.equals('F')) {
                                moves.add(Move.fClock);
                                notDone = false;
                            } else if (other.equals('R')) {
                                moves.add(Move.rCounter);
                                notDone = false;
                            } else if (other.equals('B')) {
                                moves.add(Move.dEighty);
                            } else if (other.equals('L')) {
                                moves.add(Move.dEighty);
                            }
                            break;
                        case FL:
                            if (other.equals('F')) {
                                moves.add(Move.fCounter);
                                notDone = false;
                            } else if (other.equals('R')) {
                                moves.add(Move.dCounter);
                            } else if (other.equals('B')) {
                                moves.add(Move.dClock);
                            } else if (other.equals('L')) {
                                moves.add(Move.lClock);
                                notDone = false;
                            }
                            break;
                        case BL:
                            if (other.equals('F')) {
                                moves.add(Move.dCounter);
                            } else if (other.equals('R')) {
                                moves.add(Move.dClock);
                            } else if (other.equals('B')) {
                                moves.add(Move.bClock);
                                notDone = false;
                            } else if (other.equals('L')) {
                                moves.add(Move.lCounter);
                                notDone = false;
                            }
                            break;
                        case BR:
                            if (other.equals('F')) {
                                moves.add(Move.dClock);
                            } else if (other.equals('R')) {
                                moves.add(Move.rClock);
                                notDone = false;
                            } else if (other.equals('B')) {
                                moves.add(Move.bCounter);
                                notDone = false;
                            } else if (other.equals('L')) {
                                moves.add(Move.dCounter);
                            }
                            break;
                    }
                }
            } else if (uCount == 2 && dCount == 2) {
                Edge upOne = upEdges.get(0);
                Edge upTwo = upEdges.get(1);
                Edge downOne = downEdges.get(0);
                Edge downTwo = downEdges.get(1);
                Character upOneFace = Utils.notUpOrDown(upOne);
                Character upTwoFace = Utils.notUpOrDown(upTwo);
                if (Utils.onSide(upOneFace, downOne, downTwo)) {
                    if (Utils.onSide(upTwoFace, downOne, downTwo)) {
                        moves.add(Move.dRandom);
                    } else {
                        moves.add(Move.getMove(upTwoFace, Direction.OneEighty));
                    }
                } else {
                    moves.add(Move.getMove(upOneFace, Direction.OneEighty));
                }
            } else if (uCount == 2 && dCount == 1 && midCount == 1) {
                Edge upOne = upEdges.get(0);
                Edge upTwo = upEdges.get(1);
                Edge downOne = downEdges.get(0);
                Character downOneFace = Utils.notUpOrDown(downOne);
                if (Utils.onSide(downOneFace, upOne, upTwo)) {
                    moves.add(Move.dClock);
                } else {
                    moves.add(Move.getMove(downOneFace, Direction.OneEighty));
                }
            } else if (dCount == 2 && uCount == 1 && midCount == 1) {
                Edge downOne = downEdges.get(0);
                Edge downTwo = downEdges.get(1);
                Edge upOne = upEdges.get(0);
                Character upOneFace = Utils.notUpOrDown(upOne);
                if (Utils.onSide(upOneFace, downOne, downTwo)) {
                    moves.add(Move.uClock);
                } else {
                    moves.add(Move.getMove(upOneFace, Direction.OneEighty));
                }
            } else if (midCount == 3) {
                Edge midOne = midEdges.get(0);
                Edge midTwo = midEdges.get(1);
                Edge midThree = midEdges.get(2);
                if (uCount == 1) {
                    Edge up = upEdges.get(0);
                    Character valuedSide = Utils.notUpOrDown(up);
                    translate = Utils.horizontalMap(valuedSide);
                    if (Utils.onSide(valuedSide, midOne, midTwo) || Utils.onSide(valuedSide, midTwo, midThree) || Utils.onSide(valuedSide, midOne, midThree)) {
                        moves.add(Move.uEighty);
                    } else {
                        if (Utils.oneOnSide(valuedSide, midOne)) {
                            moves = this.threeMidHelperUp(moves, valuedSide, midOne, up);
                        } else if (Utils.oneOnSide(valuedSide, midTwo)) {
                            moves = this.threeMidHelperUp(moves, valuedSide, midTwo, up);
                        } else if (Utils.oneOnSide(valuedSide, midThree)) {
                            moves = this.threeMidHelperUp(moves, valuedSide, midThree, up);
                        }
                    }
                    notDone = !notDone;
                } else if (dCount == 1) {
                    Edge down = downEdges.get(0);
                    Character valuedSide = Utils.notUpOrDown(down);
                    translate = Utils.horizontalMap(valuedSide);
                    if (Utils.onSide(valuedSide, midOne, midTwo) || Utils.onSide(valuedSide, midTwo, midThree) || Utils.onSide(valuedSide, midOne, midThree)) {
                        moves.add(Move.uEighty);
                    } else {
                        if (Utils.oneOnSide(valuedSide, midOne)) {
                            moves = this.threeMidHelperDown(moves, valuedSide, midOne, down);
                        } else if (Utils.oneOnSide(valuedSide, midTwo)) {
                            moves = this.threeMidHelperDown(moves, valuedSide, midTwo, down);
                        } else if (Utils.oneOnSide(valuedSide, midThree)) {
                            moves = this.threeMidHelperDown(moves, valuedSide, midThree, down);
                        }
                        notDone = !notDone;
                    }
                }
            } else if (midCount == 2) {
                Edge midOne = midEdges.get(0);
                Edge midTwo = midEdges.get(1);
                if (uCount == 1 && dCount == 1) {
                    Edge up = upEdges.get(0);
                    Edge down = downEdges.get(0);
                    if (Utils.shareFace(midOne, midTwo)) {
                        Character sharedMid = Utils.commonFace(midOne, midTwo);
                        translate = Utils.horizontalMap(sharedMid);
                        if (Utils.oneOnSide(sharedMid, up)) {
                            moves.add(Move.uEighty);
                        }
                        if (Utils.oneOnSide(sharedMid, down)) {
                            moves.add(Move.dEighty);
                        }
                        if (Utils.oneOnSide(Utils.otherSide(sharedMid, midOne), up) || Utils.oneOnSide(Utils.otherSide(sharedMid, midTwo), up)) {
                            if (Utils.oneOnSide(translate.get('L'), up)) {
                                if (Utils.oneOnSide(translate.get('L'), down)) {
                                    moves.add(Move.lCounter);
                                    moves.add(Move.bCounter);
                                    moves.add(Move.rClock);
                                    moves.add(Move.fCounter);
                                }
                                if (Utils.oneOnSide(translate.get('R'), down)) {
                                    moves.add(Move.lCounter);
                                    moves.add(Move.bCounter);
                                    moves.add(Move.rClock);
                                    moves.add(Move.fCounter);
                                }
                                if (Utils.oneOnSide(translate.get('B'), down)) {
                                    moves.add(Move.bEighty);
                                    moves.add(Move.rClock);
                                    moves.add(Move.fClock);
                                }
                                if (Utils.oneOnSide(translate.get('F'), down)) {
                                    moves.add(Move.dEighty);
                                }
                            } else if (Utils.oneOnSide(translate.get('R'), up)) {
                                if (Utils.oneOnSide(translate.get('R'), down)) {
                                    moves.add(Move.rClock);
                                    moves.add(Move.bClock);
                                    moves.add(Move.lCounter);
                                    moves.add(Move.fCounter);
                                }
                                if (Utils.oneOnSide(translate.get('L'), down)) {
                                    moves.add(Move.rClock);
                                    moves.add(Move.bClock);
                                    moves.add(Move.lCounter);
                                    moves.add(Move.fClock);
                                }
                                if (Utils.oneOnSide(translate.get('B'), down)) {
                                    moves.add(Move.bEighty);
                                    moves.add(Move.lCounter);
                                    moves.add(Move.fCounter);
                                }
                                if (Utils.oneOnSide(translate.get('F'), down)) {
                                    moves.add(Move.dEighty);
                                }
                            }
                        }
                        moves.add(Move.bEighty);
                    } else {
                        moves.add(Move.fEighty);
                    }

                }
                if (uCount == 2) {
                    Edge upOne = upEdges.get(0);
                    Edge upTwo = upEdges.get(1);
                    if (Utils.shareFace(midOne, midTwo)) {
                        Character sharedMid = Utils.commonFace(midOne, midTwo);
                        translate = Utils.horizontalMap(sharedMid);
                        if (Utils.oneOnSide(translate.get('F'), upOne) || Utils.oneOnSide(translate.get('F'), upTwo)) {
                            if (Utils.oneOnSide(translate.get('L'), upTwo) || Utils.oneOnSide(translate.get('L'), upOne)) {
                                moves.add(Move.fClock);
                                moves.add(Move.lCounter);
                                moves.add(Move.rEighty);
                                moves.add(Move.fRandom);
                                notDone = false;
                            } else if (Utils.oneOnSide(translate.get('R'), upTwo) || Utils.oneOnSide(translate.get('R'), upOne)) {
                                moves.add(Move.fCounter);
                                moves.add(Move.lEighty);
                                moves.add(Move.rClock);
                                moves.add(Move.fRandom);
                                notDone = false;
                            } else if (Utils.oneOnSide(translate.get('B'), upTwo) || Utils.oneOnSide(translate.get('B'), upOne)) {
                                moves.add(Move.bEighty);
                                moves.add(Move.uEighty);
                                moves.add(Move.bRandom);
                                notDone = false;
                            }
                        } else if (Utils.oneOnSide(translate.get('L'), upOne) || Utils.oneOnSide(translate.get('L'), upTwo)) {
                            if (Utils.oneOnSide(translate.get('R'), upTwo) || Utils.oneOnSide(translate.get('R'), upOne)) {
                                moves.add(Move.fRandom);
                                moves.add(Move.lCounter);
                                moves.add(Move.rClock);
                                moves.add(Move.fRandom);
                                notDone = false;
                            } else if (Utils.oneOnSide(translate.get('B'), upTwo) || Utils.oneOnSide(translate.get('B'), upOne)) {
                                moves.add(Move.fRandom);
                                moves.add(Move.bCounter);
                                moves.add(Move.lCounter);
                                moves.add(Move.fRandom);
                                notDone = false;
                            }
                        } else if (Utils.oneOnSide(translate.get('R'), upOne) || Utils.oneOnSide(translate.get('R'), upTwo)) {
                            if (Utils.oneOnSide(translate.get('B'), upTwo) || Utils.oneOnSide(translate.get('B'), upOne)) {
                                moves.add(Move.fRandom);
                                moves.add(Move.bClock);
                                moves.add(Move.rClock);
                                moves.add(Move.fRandom);
                                notDone = false;
                            }
                        }
                    }
                } else if (dCount == 2) {
                    Edge downOne = downEdges.get(0);
                    Edge downTwo = downEdges.get(1);
                    if (Utils.shareFace(midOne, midTwo)) {
                        Character sharedMid = Utils.commonFace(midOne, midTwo);
                        translate = Utils.horizontalMap(sharedMid);
                        if (Utils.oneOnSide(translate.get('F'), downOne) || Utils.oneOnSide(translate.get('F'), downTwo)) {
                            if (Utils.oneOnSide(translate.get('L'), downTwo) || Utils.oneOnSide(translate.get('L'), downOne)) {
                                moves.add(Move.fCounter);
                                moves.add(Move.lClock);
                                moves.add(Move.rEighty);
                                moves.add(Move.fRandom);
                                notDone = false;
                            } else if (Utils.oneOnSide(translate.get('R'), downTwo) || Utils.oneOnSide(translate.get('R'), downOne)) {
                                moves.add(Move.fClock);
                                moves.add(Move.rCounter);
                                moves.add(Move.lEighty);
                                moves.add(Move.fRandom);
                                notDone = false;
                            } else if (Utils.oneOnSide(translate.get('B'), downTwo) || Utils.oneOnSide(translate.get('B'), downOne)) {
                                moves.add(Move.bEighty);
                                moves.add(Move.dEighty);
                                moves.add(Move.bRandom);
                                notDone = false;
                            }
                        } else if (Utils.oneOnSide(translate.get('L'), downOne) || Utils.oneOnSide(translate.get('L'), downTwo)) {
                            if (Utils.oneOnSide(translate.get('R'), downTwo) || Utils.oneOnSide(translate.get('R'), downOne)) {
                                moves.add(Move.fRandom);
                                moves.add(Move.lClock);
                                moves.add(Move.rCounter);
                                moves.add(Move.fRandom);
                                notDone = false;
                            } else if (Utils.oneOnSide(translate.get('B'), downTwo) || Utils.oneOnSide(translate.get('B'), downOne)) {
                                moves.add(Move.fRandom);
                                moves.add(Move.bClock);
                                moves.add(Move.lClock);
                                moves.add(Move.fRandom);
                                notDone = false;
                            }
                        } else if (Utils.oneOnSide(translate.get('R'), downOne) || Utils.oneOnSide(translate.get('R'), downTwo)) {
                            if (Utils.oneOnSide(translate.get('B'), downTwo) || Utils.oneOnSide(translate.get('B'), downOne)) {
                                moves.add(Move.fRandom);
                                moves.add(Move.bCounter);
                                moves.add(Move.rCounter);
                                moves.add(Move.fRandom);
                                notDone = false;
                            }
                        }
                    }
                }
            }
            this.translateAndExecute(translate, moves);
        }


        frontFace = this.cube.getSide(this.colorToFace.get('F'));
        backFace = this.cube.getSide(this.colorToFace.get('B'));
        upFace = this.cube.getSide(this.colorToFace.get('U'));
        downFace = this.cube.getSide(this.colorToFace.get('D'));
        translate = Utils.horizontalMap('F');
        Character ud;
        Character fb;
        // Now it is time reference the tables
        StringBuffer cornerStatus = new StringBuffer();
        String adding;
        for (int corn = 0; corn < 8; corn++) {
            adding = "0";
            switch (Corner.numToCorner(corn + 1)) {
                case UBL:
                    ud = upFace.squares[0][2];
                    fb = backFace.squares[2][2];
                    if (ud.equals('L') || ud.equals('R')) {
                        adding = "2";
                    } else if (fb.equals('L') || fb.equals('R')) {
                        adding = "1";
                    }
                    break;
                case UFL:
                    ud = upFace.squares[0][0];
                    fb = frontFace.squares[0][2];
                    if (ud.equals('L') || ud.equals('R')) {
                        adding = "1";
                    } else if (fb.equals('L') || fb.equals('R')) {
                        adding = "2";
                    }
                    break;
                case UFR:
                    ud = upFace.squares[2][0];
                    fb = frontFace.squares[2][2];
                    if (ud.equals('L') || ud.equals('R')) {
                        adding = "1";
                    } else if (fb.equals('L') || fb.equals('R')) {
                        adding = "2";
                    }
                    break;
                case UBR:
                    ud = upFace.squares[2][2];
                    fb = backFace.squares[0][2];
                    if (ud.equals('L') || ud.equals('R')) {
                        adding = "2";
                    } else if (fb.equals('L') || fb.equals('R')) {
                        adding = "1";
                    }
                    break;
                case DBL:
                    ud = downFace.squares[0][0];
                    fb = backFace.squares[2][0];
                    if (ud.equals('L') || ud.equals('R')) {
                        adding = "1";
                    } else if (fb.equals('L') || fb.equals('R')) {
                        adding = "2";
                    }
                    break;
                case DBR:
                    ud = downFace.squares[2][0];
                    fb = backFace.squares[0][0];
                    if (ud.equals('L') || ud.equals('R')) {
                        adding = "1";
                    } else if (fb.equals('L') || fb.equals('R')) {
                        adding = "2";
                    }
                    break;
                case DFL:
                    ud = downFace.squares[0][2];
                    fb = frontFace.squares[0][0];
                    if (ud.equals('L') || ud.equals('R')) {
                        adding = "2";
                    } else if (fb.equals('L') || fb.equals('R')) {
                        adding = "1";
                    }
                    break;
                case DFR:
                    ud = downFace.squares[2][2];
                    fb = frontFace.squares[2][0];
                    if (ud.equals('L') || ud.equals('R')) {
                        adding = "2";
                    } else if (fb.equals('L') || fb.equals('R')) {
                        adding = "1";
                    }
                    break;
            }
            cornerStatus.append(adding);
        }
        int cornerInt = Integer.parseInt(cornerStatus.toString());
        int row = this.cornerRows.get(cornerInt);
        ArrayList<Move> moves = new ArrayList<Move>();
        moves = Utils.grabCornerMoves(row, moves);
        this.translateAndExecute(translate, moves);




    }

    private ArrayList<Move> threeMidHelperUp(ArrayList<Move> moves, Character valuedSide, Edge mid, Edge up) {
        if (Utils.oneOnSide(valuedSide, mid)) {
            if ((up.equals(Edge.FU) && mid.equals(Edge.FR)) || (up.equals(Edge.UR) && mid.equals(Edge.BR)) || (up.equals(Edge.BU) && mid.equals(Edge.BL)) || (up.equals(Edge.UL) && mid.equals(Edge.FL))) {
                moves.add(Move.getMove(translate.get('R'), Direction.Clockwise));
                moves.add(Move.getMove(translate.get('F'), Direction.Counterclockwise));
                moves.add(Move.getMove(translate.get('R'), Direction.Counterclockwise));
            } else if ((up.equals(Edge.FU) && mid.equals(Edge.FL)) || (up.equals(Edge.UL) && mid.equals(Edge.BL)) || (up.equals(Edge.BU) && mid.equals(Edge.BR)) || (up.equals(Edge.UR) && mid.equals(Edge.FR))) {
                moves.add(Move.getMove(translate.get('L'), Direction.Counterclockwise));
                moves.add(Move.getMove(translate.get('F'), Direction.Clockwise));
                moves.add(Move.getMove(translate.get('L'), Direction.Clockwise));
            }
        }
        return moves;
    }

    private ArrayList<Move> threeMidHelperDown(ArrayList<Move> moves, Character valuedSide, Edge mid, Edge down) {
        if (Utils.oneOnSide(valuedSide, mid)) {
            if ((down.equals(Edge.FD) && mid.equals(Edge.FL)) || (down.equals(Edge.DR) && mid.equals(Edge.FR)) || (down.equals(Edge.BD) && mid.equals(Edge.BR)) || (down.equals(Edge.DL) && mid.equals(Edge.BL))) {
                moves.add(Move.getMove(translate.get('L'), Direction.Counterclockwise));
                moves.add(Move.getMove(translate.get('F'), Direction.Counterclockwise));
                moves.add(Move.getMove(translate.get('L'), Direction.Clockwise));
            } else if ((down.equals(Edge.FD) && mid.equals(Edge.FR)) || (down.equals(Edge.DL) && mid.equals(Edge.FL)) || (down.equals(Edge.BD) && mid.equals(Edge.BL)) || (down.equals(Edge.DR) && mid.equals(Edge.BR))) {
                moves.add(Move.getMove(translate.get('R'), Direction.Clockwise));
                moves.add(Move.getMove(translate.get('F'), Direction.Clockwise));
                moves.add(Move.getMove(translate.get('R'), Direction.Counterclockwise));
            }
        }
        return moves;
    }

    private void phaseThree() {
        this.phaseThreeA();
        this.phaseThreeB();
    }

    //Correct corners that are out of orbit
    private void phaseThreeA() {
        Side frontFace = this.cube.getSide(this.colorToFace.get('F'));
        Side backFace = this.cube.getSide(this.colorToFace.get('B'));
        Side upFace = this.cube.getSide(this.colorToFace.get('U'));
        Side downFace = this.cube.getSide(this.colorToFace.get('D'));
        Side leftFace = this.cube.getSide(this.colorToFace.get('L'));
        Side rightFace = this.cube.getSide(this.colorToFace.get('R'));
        translate = Utils.horizontalMap('F');
        Character ud = null;
        Character fb = null;
        Character lr = null;
        boolean adding = false;
        ArrayList<Integer> notOrbital = new ArrayList<Integer>();
        for (int c = 1; c <= 8; c++) {
            switch (Corner.numToCorner(c)) {
                case UFL:
                    ud = upFace.squares[0][0];
                    fb = frontFace.squares[0][2];
                    lr = leftFace.squares[2][2];
                    break;
                case UFR:
                    ud = upFace.squares[2][0];
                    fb = frontFace.squares[2][2];
                    lr = rightFace.squares[0][2];
                    break;
                case UBL:
                    ud = upFace.squares[0][2];
                    fb = backFace.squares[2][2];
                    lr = leftFace.squares[0][2];
                    break;
                case UBR:
                    ud = upFace.squares[2][2];
                    fb = backFace.squares[0][2];
                    lr = rightFace.squares[2][2];
                    break;

                case DFL:
                    ud = downFace.squares[0][2];
                    fb = frontFace.squares[0][0];
                    lr = leftFace.squares[2][0];
                    break;

                case DFR:
                    ud = downFace.squares[2][2];
                    fb = frontFace.squares[2][0];
                    lr = rightFace.squares[0][0];
                    break;

                case DBL:
                    ud = downFace.squares[0][0];
                    fb = backFace.squares[2][0];
                    lr = leftFace.squares[0][0];
                    break;

                case DBR:
                    ud = downFace.squares[2][0];
                    fb = backFace.squares[0][0];
                    lr = rightFace.squares[0][2];
                    break;

            }
            adding = Utils.inOrbit(c, ud, fb, lr);
            if (adding) {
                notOrbital.add(c);
            }
        }
        ArrayList<Move> moves = new ArrayList<Move>();
        switch(notOrbital.size()) {
            case 0:
                break;
            case 2:
                int notOne = notOrbital.get(1);
                if (notOne == 7) {
                    moves.add(Move.fEighty);
                } else if (notOne == 8) {
                    moves.add(Move.lEighty);
                    moves.add(Move.uEighty);
                }
                break;
            case 4:
                int second = notOrbital.get(1);
                int third = notOrbital.get(2);
                int fourth = notOrbital.get(3);
                if (second == 2) {
                    if (fourth == 6) {
                        moves.add(Move.lClock);
                    } else if (fourth == 7) {
                        moves.add(Move.lClock);
                        moves.add(Move.fEighty);
                    } else if (fourth == 8) {
                        moves.add(Move.lCounter);
                        moves.add(Move.uEighty);
                    }
                } else if (second == 3) {
                    if (third == 6) {
                        moves.add(Move.uEighty);
                    } else if (fourth == 7) {
                        moves.add(Move.rEighty);
                        moves.add(Move.fEighty);
                    } else if (fourth == 8) {
                        moves.add(Move.lClock);
                        moves.add(Move.uEighty);
                    }
                }
                break;
            case 6:
                int secondSix = notOrbital.get(1);
                int thirdSix = notOrbital.get(2);
                int fourthSix = notOrbital.get(3);
                int fifthSix = notOrbital.get(4);
                int sixthSix = notOrbital.get(5);
                if (fifthSix == 6) {
                    moves.add(Move.lClock);
                } else if (fourthSix == 5) {
                    moves.add(Move.fEighty);
                    moves.add(Move.rClock);
                } else if (fourthSix == 6) {
                    moves.add(Move.lClock);
                    moves.add(Move.rClock);
                    moves.add(Move.uEighty);
                }
                break;
            case 8:
                moves.add(Move.lClock);
                moves.add(Move.rClock);
                break;

        }
        translateAndExecute(translate, moves);

    }


    public void phaseThreeB() {
        Side frontFace = this.cube.getSide(this.colorToFace.get('F'));
        Side backFace = this.cube.getSide(this.colorToFace.get('B'));
        Side upFace = this.cube.getSide(this.colorToFace.get('U'));
        Side downFace = this.cube.getSide(this.colorToFace.get('D'));
        Side leftFace = this.cube.getSide(this.colorToFace.get('L'));
        Side rightFace = this.cube.getSide(this.colorToFace.get('R'));
        translate = Utils.horizontalMap('F');
        Character ud = null;
        Character fb = null;
        Character lr = null;
        boolean adding = false;
        ArrayList<Integer> notOrbital = new ArrayList<Integer>();
        for (int c = 1; c <= 8; c++) {
            switch (Corner.numToCorner(c)) {
                case UFL:
                    ud = upFace.squares[0][0];
                    fb = frontFace.squares[0][2];
                    lr = leftFace.squares[2][2];
                    break;

                case UFR:
                    ud = upFace.squares[2][0];
                    fb = frontFace.squares[2][2];
                    lr = rightFace.squares[0][2];
                    break;

                case UBL:
                    ud = upFace.squares[0][2];
                    fb = backFace.squares[2][2];
                    lr = leftFace.squares[0][2];
                    break;

                case UBR:
                    ud = upFace.squares[2][2];
                    fb = backFace.squares[0][2];
                    lr = rightFace.squares[2][2];
                    break;

                case DFL:
                    ud = downFace.squares[0][2];
                    fb = frontFace.squares[0][0];
                    lr = leftFace.squares[2][0];
                    break;

                case DFR:
                    ud = downFace.squares[2][2];
                    fb = frontFace.squares[2][0];
                    lr = rightFace.squares[0][0];
                    break;

                case DBL:
                    ud = downFace.squares[0][0];
                    fb = backFace.squares[2][0];
                    lr = leftFace.squares[0][0];
                    break;

                case DBR:
                    ud = downFace.squares[2][0];
                    fb = backFace.squares[0][0];
                    lr = rightFace.squares[0][2];
                    break;

            }
            adding = Utils.inOrbit(c, ud, fb, lr);
            if (adding) {
                notOrbital.add(c);
            }
        }

        Character upDown;
        Character frontBack;
        Character leftRight;
        int sideEdge;
        int position = 0;
        int[] positions = new int[4];

        for (Edge e : Utils.stage3Edges()) {
            position++;
            switch (e) {
                case UL:
                    upDown = upFace.getSquares()[0][1];
                    leftRight = leftFace.getSquares()[1][2];
                    sideEdge = Utils.getStage3(leftRight, upDown);
                    break;
                case DL:
                    upDown = downFace.getSquares()[0][1];
                    leftRight = leftFace.getSquares()[1][0];
                    sideEdge = Utils.getStage3(leftRight, upDown);
                    break;
                case UR:
                    upDown = upFace.getSquares()[2][1];
                    leftRight = rightFace.getSquares()[1][2];
                    sideEdge = Utils.getStage3(leftRight, upDown);
                    break;
                case DR:
                    upDown = downFace.getSquares()[2][1];
                    leftRight = rightFace.getSquares()[1][0];
                    sideEdge = Utils.getStage3(leftRight, upDown);
                    break;
                case BL:
                    frontBack = backFace.getSquares()[2][1];
                    leftRight = rightFace.getSquares()[0][1];
                    sideEdge = Utils.getStage3(leftRight, frontBack);
                    break;
                case FL:
                    frontBack = frontFace.getSquares()[0][1];
                    leftRight = leftFace.getSquares()[2][1];
                    sideEdge = Utils.getStage3(leftRight, frontBack);
                    break;
                case FR:
                    frontBack = frontFace.getSquares()[2][1];
                    leftRight = rightFace.getSquares()[0][1];
                    sideEdge = Utils.getStage3(leftRight, frontBack);
                    break;
                case BR:
                    leftRight = leftFace.getSquares()[2][1];
                    frontBack = backFace.getSquares()[0][1];
                    sideEdge = Utils.getStage3(leftRight, frontBack);
                    break;
                default:
                    sideEdge = 0;
            }
            if (sideEdge != 0) {
                positions[sideEdge] = position;
            }
        }


        switch (notOrbital.size()) {
            case 0:

        }


    }

    public void phaseFour() {
        ArrayList<Move> moves = new ArrayList<Move>();
        Side frontFace = this.cube.getSide(this.colorToFace.get('F'));
        Side backFace = this.cube.getSide(this.colorToFace.get('B'));
        Side rightFace = this.cube.getSide(this.colorToFace.get('R'));
        Side leftFace = this.cube.getSide(this.colorToFace.get('L'));
        Side upFace = this.cube.getSide(this.colorToFace.get('U'));
        Side downFace = this.cube.getSide(this.colorToFace.get('D'));



        StringBuffer FB = new StringBuffer();
        StringBuffer UD = new StringBuffer();
        StringBuffer LR = new StringBuffer();

        FB.append(leftFace.getSquares()[1][2]);
        FB.append(upFace.getSquares()[0][1]);

        FB.append(leftFace.getSquares()[1][0]);
        FB.append(downFace.getSquares()[0][1]);

        FB.append(rightFace.getSquares()[2][1]);
        FB.append(downFace.getSquares()[2][1]);

        FB.append(rightFace.getSquares()[1][2]);
        FB.append(upFace.getSquares()[2][1]);

        UD.append(leftFace.getSquares()[0][1]);
        UD.append(backFace.getSquares()[2][1]);

        UD.append(leftFace.getSquares()[2][1]);
        UD.append(frontFace.getSquares()[0][1]);

        UD.append(rightFace.getSquares()[0][1]);
        UD.append(frontFace.getSquares()[2][1]);

        UD.append(rightFace.getSquares()[2][1]);
        UD.append(backFace.getSquares()[0][1]);

        LR.append(frontFace.getSquares()[1][2]);
        LR.append(upFace.getSquares()[1][0]);

        LR.append(frontFace.getSquares()[1][0]);
        LR.append(downFace.getSquares()[1][2]);

        LR.append(backFace.getSquares()[1][0]);
        LR.append(downFace.getSquares()[1][0]);

        LR.append(backFace.getSquares()[1][2]);
        LR.append(upFace.getSquares()[1][2]);

        int FBint = fbSlice.get(FB.toString());
        int UDint = udSlice.get(UD.toString());
        int LRint = lrSlice.get(LR.toString());

        int fin = finalKey.get(new TripleKey(FBint, UDint, LRint));

        moves = Utils.grabFinalMoves(fin);
        translate = Utils.horizontalMap('F');

        this.translateAndExecute(translate, moves);
    }

}
