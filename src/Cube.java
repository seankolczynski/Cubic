import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representation of a Rubik's Cube. Has six sides, each of which can be rotated.
 *
 * Below is the layout of the cube, as enforced by the setupConnections method
 *
 *       W W W
 *       W W W
 *       W W W
 * O O O G G G R R R B B B
 * O O O G G G R R R B B B
 * O O O G G G R R R B B B
 *       Y Y Y
 *       Y Y Y
 *       Y Y Y
 *
 *
 */
public class Cube {
    private Side red;
    private Side blue;
    private Side green;
    private Side yellow;
    private Side white;
    private Side orange;
    private ArrayList<Side> asList;

    //Constructor
    public Cube() {
        this.white = new Side('W');
        this.blue = new Side('B');
        this.red = new Side('R');
        this.orange = new Side('O');
        this.green = new Side('G');
        this.yellow = new Side('Y');
        this.setupConnections();
        this.asList = new ArrayList<Side>();
        this.asList.add(this.blue);
        this.asList.add(this.orange);
        this.asList.add(this.white);
        this.asList.add(this.red);
        this.asList.add(this.green);
        this.asList.add(this.yellow);
    }

    /**
     * The messy bits. Basically assigns all the side relations of each face
     */
    private void setupConnections() {
        this.white = this.white.setEdges(this.blue, this.red, this.green, this.orange);
        this.blue = this.blue.setEdges(this.white, this.orange, this.yellow, this.red);
        this.red = this.red.setEdges(this.white, this.blue, this.yellow, this.green);
        this.orange = this.orange.setEdges(this.white, this.green, this.yellow, this.blue);
        this.yellow = this.yellow.setEdges(this.green, this.red, this.blue, this.orange);
        this.green = this.green.setEdges(this.white, this.red, this.yellow, this.orange);
    }

    /**
     *  Rotates the cube
     *  Color: The side being spun
     *  Dir: True = clockwise, false = counterclockwise
     */
    public void rotate(Color color, boolean dir) {
        Side chosen = this.getSide(color);
        if (dir) {
            chosen.turnClockwise();
        } else {
            chosen.turnCounterClockwise();
        }
    }

    public Side getSide(Color color) {
        switch (color) {
            case RED:
                return this.red;
            case BLUE:
                return this.blue;
            case WHITE:
                return this.white;
            case YELLOW:
                return this.yellow;
            case GREEN:
                return this.green;
            case ORANGE:
                return this.orange;
            default:
                throw new IllegalArgumentException("Not a color");
        }
    }

    public Cube shuffle(int moves) {
        System.out.println("BEFORE SHUFFLE\n");
        System.out.println(this.toString());
        Random random = new Random();
        for (int turn = 1; turn <= moves; turn++) {
            boolean dir = random.nextBoolean();
            Color[] colors = Color.RED.getDeclaringClass().getEnumConstants();
            this.rotate(colors[random.nextInt(colors.length)], dir);
        }
        System.out.println("AFTER SHUFFLE\n");
        System.out.println(this.toString());
        return this;
    }



    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Side b = this.getSide(Color.BLUE);
        Side w = this.getSide(Color.WHITE);
        for (int y = 2; y >= 0; y = y - 1) {
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append(w.squares[0][y]);
            sb.append(" ");
            sb.append(w.squares[1][y]);
            sb.append(" ");
            sb.append(w.squares[2][y]);
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append("\n");
        }
        Side o = this.getSide(Color.ORANGE);
        Side g = this.getSide(Color.GREEN);
        Side r = this.getSide(Color.RED);
        for (int y = 2; y >= 0; y = y - 1) {
            sb.append(o.squares[0][y]);
            sb.append(" ");
            sb.append(o.squares[1][y]);
            sb.append(" ");
            sb.append(o.squares[2][y]);
            sb.append(" ");
            sb.append(g.squares[0][y]);
            sb.append(" ");
            sb.append(g.squares[1][y]);
            sb.append(" ");
            sb.append(g.squares[2][y]);
            sb.append(" ");
            sb.append(r.squares[0][y]);
            sb.append(" ");
            sb.append(r.squares[1][y]);
            sb.append(" ");
            sb.append(r.squares[2][y]);
            sb.append(" ");
            sb.append(b.squares[0][y]);
            sb.append(" ");
            sb.append(b.squares[1][y]);
            sb.append(" ");
            sb.append(b.squares[2][y]);

            sb.append("\n");
        }
        Side ye = this.getSide(Color.YELLOW);
        for (int y = 2; y >= 0; y = y - 1) {
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append(ye.squares[0][y]);
            sb.append(" ");
            sb.append(ye.squares[1][y]);
            sb.append(" ");
            sb.append(ye.squares[2][y]);
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append("\n");
        }

        return sb.toString();
    }


}
