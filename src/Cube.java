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
        this.asList.add(this.white);
        this.asList.add(this.red);
        this.asList.add(this.blue);
        this.asList.add(this.orange);
        this.asList.add(this.yellow);
        this.asList.add(this.green);
    }

    private void setupConnections() {
        this.white = this.white.setEdges(this.blue, this.red, this.green, this.orange);
        this.blue = this.blue.setEdges(this.yellow, this.red, this.white, this.orange);
        this.red = this.red.setEdges(this.blue, this.yellow, this.green, this.white);
        this.orange = this.orange.setEdges(this.green, this.yellow, this.blue, this.white);
        this.yellow = this.yellow.setEdges(this.green, this.red, this.blue, this.orange);
        this.green = this.green.setEdges(this.blue, this.red, this.green, this.orange);
    }

    @Override
    public String toString() {
        ArrayList<ArrayList<Integer>> printer = new ArrayList<ArrayList<Integer>>();
        int count = 1;
        ArrayList<Integer> currentRow = new ArrayList<Integer>();
        for (Side s: asList) {
            if (count >= 2 && count <= 4) {
                currentRow.add(s.squares[0][0]);
                currentRow.add(s.squares[0][0]);
                currentRow.add(s.squares[0][0]);
                if (count ==4) {
                    printer.add(currentRow);
                    currentRow = new ArrayList<Integer>();
                }
            } else {
                currentRow.add(0);
                currentRow.add(0);
                currentRow.add(0);
                currentRow.add(s.squares[0][0]);
                currentRow.add(s.squares[0][0]);
                currentRow.add(s.squares[0][0]);
                printer.add(currentRow);
                currentRow = new ArrayList<Integer>();
            }
            count++;
        }
        StringBuffer sb = new StringBuffer();
        for (int h = 0; h < printer.size(); h++) {
            sb.append(printer.get(h));
            sb.append("\n");
        }
        return sb.toString();
    }


}
