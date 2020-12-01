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

        int count = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if ((x == 1 && y == 0) || (x == 1 && y == 2) || (x == 0 && y == 1) || (x == 2 && y == 1)) {
                    Character edge = frontFace.squares[x][y];
                    if (edge.equals(this.colorToFace.get('L').getLetter())
                            || edge.equals(this.colorToFace.get('R').getLetter())) {
                        count++;
                    } else if (edge.equals(this.colorToFace.get('U').getLetter())
                            || edge.equals(this.colorToFace.get('D').getLetter())) {
                        Character oppEdge;
                        if (y == 1) {
                            if (x == 0) {oppEdge = leftFace.getSquares()[2][1];}
                            if (x == 2) {oppEdge = rightFace.getSquares()[0][1];}
                        }
                    }


                }

            }
        }

    }



}
