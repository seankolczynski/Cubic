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
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if ((x == 1 && y == 0) || (x == 1 && y == 2) || (x == 0 && y == 1) || (x == 2 && y == 1)) {
                    Character edgeF = frontFace.squares[x][y];
                    if (edgeF.equals(this.colorToFace.get('L').getLetter())
                            || edgeF.equals(this.colorToFace.get('R').getLetter())) {
                        countBad++;
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
                        }
                    }

                    Character edgeB = backFace.squares[x][y];
                    if (edgeB.equals(this.colorToFace.get('L').getLetter()) ||
                            edgeB.equals(this.colorToFace.get('R').getLetter())) {
                        countBad++;
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
                            countBad = countBad + 1;
                        }
                    }
                    if ((x == 0 && y == 1) || (x == 2 && y == 1)) {
                        Character edgeU = upFace.squares[x][y];
                        if (edgeU.equals(this.colorToFace.get('L').getLetter()) ||
                                edgeU.equals(this.colorToFace.get('R').getLetter())) {
                            countBad++;
                        } else if (edgeU.equals(this.colorToFace.get('U').getLetter())
                                || edgeU.equals(this.colorToFace.get('D').getLetter()))  {
                            Character oppEdgeU = null;
                            if (x == 0) {oppEdgeU = leftFace.getSquares()[1][2];}
                            if (x == 2) {oppEdgeU = rightFace.getSquares()[1][2];}
                            if (oppEdgeU.equals(this.colorToFace.get('F').getLetter()) ||
                                    oppEdgeU.equals(this.colorToFace.get('B').getLetter())) {
                                countBad = countBad + 1;
                            }
                        }

                        Character edgeD = downFace.squares[x][y];
                        if (edgeD.equals(this.colorToFace.get('L').getLetter()) ||
                                edgeD.equals(this.colorToFace.get('R').getLetter())) {
                            countBad++;
                        } else if (edgeD.equals(this.colorToFace.get('U').getLetter())
                                || edgeD.equals(this.colorToFace.get('D').getLetter()))  {
                            Character oppEdgeD = null;
                            if (x == 0) {oppEdgeD = leftFace.getSquares()[1][0];}
                            if (x == 2) {oppEdgeD = rightFace.getSquares()[1][0];}
                            if (oppEdgeD.equals(this.colorToFace.get('F').getLetter()) ||
                                    oppEdgeD.equals(this.colorToFace.get('B').getLetter())) {
                                countBad = countBad + 1;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(countBad);
    }



}
