import java.awt.*;
import java.util.ArrayList;

public class Cube {
    private Side red;
    private Side blue;
    private Side green;
    private Side yellow;
    private Side white;
    private Side orange;
    private ArrayList<Side> asList;

    public Cube() {
        this.white = new Side(1);
        this.blue = new Side(2);
        this.red = new Side(3);
        this.orange = new Side(4);
        this.green = new Side(5);
        this.yellow = new Side(6);
        this.setupConnections();
        this.asList = new ArrayList<Side>();
        this.asList.add(this.blue);
        this.asList.add(this.orange);
        this.asList.add(this.white);
        this.asList.add(this.red);
        this.asList.add(this.green);
        this.asList.add(this.yellow);
    }

    private void setupConnections() {
        this.white = this.white.setEdges(this.blue, this.red, this.green, this.orange);
        this.blue = this.blue.setEdges(this.yellow, this.red, this.white, this.orange);
        this.red = this.red.setEdges(this.blue, this.yellow, this.green, this.white);
        this.orange = this.orange.setEdges(this.blue, this.white, this.green, this.yellow);
        this.yellow = this.yellow.setEdges(this.green, this.red, this.blue, this.orange);
        this.green = this.green.setEdges(this.white, this.red, this.yellow, this.orange);
    }

    public void rotate(Color color, boolean dir) {
        Side chosen = this.getSide(color);
        if (dir) {
            chosen.turnClockwise();
        } else {
            chosen.turnCounterClockwise();
        }
    }

    private Side getSide(Color color) {
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

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Side b = this.getSide(Color.BLUE);
        for (int y = 2; y >= 0; y = y - 1) {
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append(b.squares[0][y]);
            sb.append(b.squares[1][y]);
            sb.append(b.squares[2][y]);
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append("\n");
        }
        Side o = this.getSide(Color.ORANGE);
        Side w = this.getSide(Color.WHITE);
        Side r = this.getSide(Color.RED);
        for (int y = 2; y >= 0; y = y - 1) {
            sb.append(o.squares[0][y]);
            sb.append(o.squares[1][y]);
            sb.append(o.squares[2][y]);
            sb.append(w.squares[0][y]);
            sb.append(w.squares[1][y]);
            sb.append(w.squares[2][y]);
            sb.append(r.squares[0][y]);
            sb.append(r.squares[1][y]);
            sb.append(r.squares[2][y]);
            sb.append("\n");
        }
        Side g = this.getSide(Color.GREEN);
        for (int y = 2; y >= 0; y = y - 1) {
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append(g.squares[0][y]);
            sb.append(g.squares[1][y]);
            sb.append(g.squares[2][y]);
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append("\n");
        }
        Side ye = this.getSide(Color.YELLOW);
        for (int y = 2; y >= 0; y = y - 1) {
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append(ye.squares[0][y]);
            sb.append(ye.squares[1][y]);
            sb.append(ye.squares[2][y]);
            sb.append(" ");
            sb.append(" ");
            sb.append(" ");
            sb.append("\n");
        }

        return sb.toString();
    }


}
