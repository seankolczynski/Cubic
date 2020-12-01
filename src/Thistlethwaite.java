import java.util.ArrayList;
import java.util.HashMap;

public class Thistlethwaite {

    HashMap<Character, Color> colorToFace;
    Cube cube;


    public Thistlethwaite() {
    }

    public Cube solve(Cube cube) {
        this.cube = cube;
        colorToFace = this.pairSides();
        this.phaseOne();

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

        int countBad = 0;
        ArrayList<Integer> badEdges = new ArrayList();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if ((x == 1 && y == 0) || (x == 1 && y == 2) || (x == 0 && y == 1) || (x == 2 && y == 1)) {
                    Character edgeF = frontFace.squares[x][y];
                    if (edgeF.equals(this.colorToFace.get('L').getLetter())
                            || edgeF.equals(this.colorToFace.get('R').getLetter())) {
                        countBad++;
                        badEdges.add(this.edgeNumber('F', x, y));
                    } else if (edgeF.equals(this.colorToFace.get('U').getLetter())
                            || edgeF.equals(this.colorToFace.get('D').getLetter())) {
                        Character oppEdgeF = null;
                        if (y == 1) {
                            if (x == 0) {oppEdgeF = leftFace.getSquares()[2][1];}
                            if (x == 2) {oppEdgeF = rightFace.getSquares()[0][1];}
                        }
                        else if (x == 1) {
                            if (y == 0) {oppEdgeF = downFace.getSquares()[1][2];}
                            if (y == 2) {oppEdgeF = upFace.getSquares()[1][0];}
                        }
                        if (oppEdgeF.equals(this.colorToFace.get('F').getLetter()) ||
                            oppEdgeF.equals(this.colorToFace.get('B').getLetter())) {
                            countBad = countBad + 1;
                            badEdges.add(this.edgeNumber('F', x, y));
                        }
                    }

                    Character edgeB = backFace.squares[x][y];
                    if (edgeB.equals(this.colorToFace.get('L').getLetter()) ||
                            edgeB.equals(this.colorToFace.get('R').getLetter())) {
                        countBad++;
                        badEdges.add(this.edgeNumber('B', x, y));
                    } else if (edgeB.equals(this.colorToFace.get('U').getLetter())
                            || edgeB.equals(this.colorToFace.get('D').getLetter())) {
                        Character oppEdgeB = null;
                        if (y == 1) {
                            if (x == 0) {oppEdgeB = rightFace.getSquares()[2][1];}
                            if (x == 2) {oppEdgeB = leftFace.getSquares()[0][1];}
                        }
                        else if (x == 1) {
                            if (y == 0) {oppEdgeB = downFace.getSquares()[1][0];}
                            if (y == 2) {oppEdgeB = upFace.getSquares()[1][2];}
                        }
                        if (oppEdgeB.equals(this.colorToFace.get('F').getLetter()) ||
                                oppEdgeB.equals(this.colorToFace.get('B').getLetter())) {
                            countBad++;
                            badEdges.add(this.edgeNumber('B', x, y));
                        }
                    }
                    if ((x == 0 && y == 1) || (x == 2 && y == 1)) {
                        Character edgeU = upFace.squares[x][y];
                        if (edgeU.equals(this.colorToFace.get('L').getLetter()) ||
                                edgeU.equals(this.colorToFace.get('R').getLetter())) {
                            countBad++;
                            badEdges.add(this.edgeNumber('U', x, y));
                        } else if (edgeU.equals(this.colorToFace.get('U').getLetter())
                                || edgeU.equals(this.colorToFace.get('D').getLetter()))  {
                            Character oppEdgeU = null;
                            if (x == 0) {oppEdgeU = leftFace.getSquares()[1][2];}
                            if (x == 2) {oppEdgeU = rightFace.getSquares()[1][2];}
                            if (oppEdgeU.equals(this.colorToFace.get('F').getLetter()) ||
                                    oppEdgeU.equals(this.colorToFace.get('B').getLetter())) {
                                countBad++;
                                badEdges.add(this.edgeNumber('U', x, y));
                            }
                        }

                        Character edgeD = downFace.squares[x][y];
                        if (edgeD.equals(this.colorToFace.get('L').getLetter()) ||
                                edgeD.equals(this.colorToFace.get('R').getLetter())) {
                            countBad++;
                            badEdges.add(this.edgeNumber('D', x, y));
                        } else if (edgeD.equals(this.colorToFace.get('U').getLetter())
                                || edgeD.equals(this.colorToFace.get('D').getLetter()))  {
                            Character oppEdgeD = null;
                            if (x == 0) {oppEdgeD = leftFace.getSquares()[1][0];}
                            if (x == 2) {oppEdgeD = rightFace.getSquares()[1][0];}
                            if (oppEdgeD.equals(this.colorToFace.get('F').getLetter()) ||
                                    oppEdgeD.equals(this.colorToFace.get('B').getLetter())) {
                                countBad++;
                                badEdges.add(this.edgeNumber('D', x, y));
                            }
                        }
                    }
                }
            }
        }

        //Now that we have our edges, we have to act
        switch(badEdges.size()) {
            case 0:
                //By some miracle(.05% chance) no edges are bad! Do nothing.
                return;
            case 2:
                


        }
        System.out.println(countBad);
    }

    /**
     * Basic Dictionary that returns the number for a specific edge
     * @param side The side with the bad edge
     * @param x The x position of that bad edge on the given side
     * @param y The y position of that bad edge on the given side
     * @return Number corresponding with that edge.
     * FD = 1, FU = 2, FL = 3, FR = 4, BD = 5, BU = 6, BL = 7, BR = 8, UL = 9, UR = 10, DL = 11, DR = 12
     */
    private int edgeNumber(Character side, int x, int y) {
        if (side.equals('F') && x == 1 && y == 0) {
            //FD
            return 1;
        }
        if (side.equals('F') && x == 1 && y == 2) {
            //FU
            return 2;
        }
        if (side.equals('F') && x == 0 && y == 1) {
            //FL
            return 3;
        }
        if (side.equals('F') && x == 2 && y == 1) {
            //FR
            return 4;
        }
        if (side.equals('B') && x == 1 && y == 0) {
            //BD
            return 5;
        }
        if (side.equals('B') && x == 1 && y == 2) {
            //BU
            return 6;
        }
        if (side.equals('B') && x == 0 && y == 1) {
            //BL
            return 7;
        }
        if (side.equals('B') && x == 2 && y == 1) {
            //BR
            return 8;
        }
        if (side.equals('U') && x == 0 && y == 1) {
            //FD
            return 9;
        }
        if (side.equals('U') && x == 2 && y == 1) {
            //FU
            return 10;
        }
        if (side.equals('D') && x == 0 && y == 1) {
            //FL
            return 11;
        }
        if (side.equals('D') && x == 2 && y == 1) {
            //FR
            return 12;
        }
        else {
            throw new IllegalArgumentException("Not given an edge");
        }

    }



}
