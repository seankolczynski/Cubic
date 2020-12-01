
public class Side {
    private Side front;
    private Side right;
    private Side back;
    private Side left;
    Integer[][] squares;
    int center;

    Side(int i) {
        this.center = i;
        Integer[][] arr = new Integer[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                arr[x][y] = i;
            }
        }
        this.squares = arr;
    }

    public Side setEdges(Side f, Side r, Side b, Side l) {
        if (f == null || r == null || b == null || l == null) {
            throw new IllegalArgumentException("Provided null sides.");
        }
        if (front == null && right == null && back == null && left == null) {
            this.front = f;
            this.right = r;
            this.back = b;
            this.left = l;
            return this;
        }
        else {
            System.out.println(this.front.squares[0][0]);
            System.out.println(this.right.squares[0][0]);
            System.out.println(this.back.squares[0][0]);
            System.out.println(this.left.squares[0][0]);
            throw new IllegalStateException("Can only set sides once");
        }
    }

    public void turnClockwise() {
        int topLeft = this.squares[0][2];
        int topMid = this.squares[1][2];
        int topRight = this.squares[2][2];
        int midLeft = this.squares[0][1];
        int midRight = this.squares[2][1];
        int bottomLeft = this.squares[0][0];
        int bottomMid = this.squares[1][0];
        int bottomRight = this.squares[2][0];

        this.squares[0][2] = bottomLeft;
        this.squares[1][2] = midLeft;
        this.squares[2][2] = topLeft;
        this.squares[0][1] = bottomMid;
        this.squares[2][1] = topMid;
        this.squares[0][0] = bottomRight;
        this.squares[1][0] = midRight;
        this.squares[2][0] = topRight;

        this.front.sideClockwise(this.center);

    }

    private void sideClockwise(int origin) {
        this.sideClockwiseHelper(origin, 0, 0, 0, 0);
    }

    private void sideClockwiseHelper(int origin, int val1, int val2, int val3, int count) {
        int origVal1;
        int origVal2;
        int origVal3;
        Side next = this;
        int x1 = 0;
        int x2 = 1;
        int x3 = 2;
        int y1 = 0;
        int y2 = 1;
        int y3 = 2;
        if (origin == front.center) {
            y1 = 2;
            y2 = 2;
            y3 = 2;
            next = left;
        }
        if (origin == right.center) {
            x1 = 2;
            x2 = 2;
            y1 = 2;
            y2 = 1;
            y3 = 0;
            next = front;
        }
        if (origin == back.center) {
            y1 = 0;
            y2 = 0;
            y3 = 0;
            x1 = 2;
            x2 = 1;
            x3 = 0;
            next = right;
        }
        if (origin == left.center) {
            x1 = 0;
            x2 = 0;
            x3 = 0;
            next = back;
        }
        origVal1 = this.squares[x1][y1];
        origVal2 = this.squares[x2][y2];
        origVal3 = this.squares[x3][y3];
        if (count > 0) {
            this.squares[x1][y1] = val1;
            this.squares[x2][y2] = val2;
            this.squares[x3][y3] = val3;
        }
        count++;
        if (count > 4) {
            return;
        } else {
            next.sideClockwiseHelper(origin, origVal1, origVal2, origVal3, count);
        }
    }

    public void turnCounterClockwise() {
        int topLeft = this.squares[0][2];
        int topMid = this.squares[1][2];
        int topRight = this.squares[2][2];
        int midLeft = this.squares[0][1];
        int midRight = this.squares[2][1];
        int bottomLeft = this.squares[0][0];
        int bottomMid = this.squares[1][0];
        int bottomRight = this.squares[2][0];

        this.squares[0][2] = topRight;
        this.squares[1][2] = midRight;
        this.squares[2][2] = bottomRight;
        this.squares[0][1] = topMid;
        this.squares[2][1] = bottomMid;
        this.squares[0][0] = topLeft;
        this.squares[1][0] = midLeft;
        this.squares[2][0] = bottomLeft;

        this.front.sideCounterClockwise(this.center);

    }

    private void sideCounterClockwise(int origin) {
        this.sideCounterClockwiseHelper(origin, 0, 0, 0, 0);
    }

    private void sideCounterClockwiseHelper(int origin, int val1, int val2, int val3, int count) {
        int origVal1;
        int origVal2;
        int origVal3;
        Side next = this;
        int x1 = 0;
        int x2 = 1;
        int x3 = 2;
        int y1 = 0;
        int y2 = 1;
        int y3 = 2;
        if (origin == front.center) {
            x1 = 2;
            x2 = 1;
            x3 = 0;
            y1 = 2;
            y2 = 2;
            y3 = 2;
            next = this.right;
        }
        if (origin == right.center) {
            x1 = 2;
            x2 = 2;
            next = this.back;
        }
        if (origin == back.center) {
            y1 = 0;
            y2 = 0;
            y3 = 0;
            next = this.left;
        }
        if (origin == left.center) {
            x1 = 0;
            x2 = 0;
            x3 = 0;
            y1 = 2;
            y2 = 1;
            y3 = 0;
            next = this.front;
        }
        origVal1 = this.squares[x1][y1];
        origVal2 = this.squares[x2][y2];
        origVal3 = this.squares[x3][y3];
        if (count > 0) {
            this.squares[x1][y1] = val1;
            this.squares[x2][y2] = val2;
            this.squares[x3][y3] = val3;
        }
        count++;
        if (count > 4) {
            return;
        } else {
            next.sideCounterClockwiseHelper(origin, origVal1, origVal2, origVal3, count);
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(squares[i][j]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
