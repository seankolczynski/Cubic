import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;

public class TestCube {

    @Test
    public void testClockwise() {
        Cube cub = new Cube();
        cub.rotate(Color.WHITE, true);
        System.out.println(cub.toString());
    }

    @Test
    public void testCounterClockwise() {
        Cube cub = new Cube();
        cub.rotate(Color.WHITE, false);
        System.out.println(cub.toString());
        cub.rotate(Color.RED, false);
        System.out.println(cub.toString());
    }

    @Test
    public void testEnum() {
        assertEquals((Character)'W', Color.WHITE.getLetter());
    }

    @Test
    public void orientation() {
        Cube cub = new Cube();
        cub.rotate(Color.WHITE, false);
        System.out.println(cub.toString());
    }

    @Test
    public void turnGreen() {
        Cube cub = new Cube();
        cub.rotate(Color.GREEN, true);
        System.out.println(cub.toString());
    }

    @Test
    public void turnRed() {
        Cube cub = new Cube();
        cub.rotate(Color.RED, true);
        System.out.println(cub.toString());
    }

    @Test
    public void turnBlue() {
        Cube cub = new Cube();
        cub.rotate(Color.BLUE, true);
        System.out.println(cub.toString());
    }

    @Test
    public void turnYellow() {
        Cube cub = new Cube();
        cub.rotate(Color.YELLOW, true);
        System.out.println(cub.toString());
    }
    @Test
    public void turnOrange() {
        Cube cub = new Cube();
        cub.rotate(Color.ORANGE, true);
        System.out.println(cub.toString());
    }
    @Test
    public void turnWhite() {
        Cube cub = new Cube();
        cub.rotate(Color.WHITE, true);
        System.out.println(cub.toString());
    }

    @Test
    public void testShuffle() {
        Cube cub = new Cube();
        cub.shuffle(5);
    }

    @Test
    public void testThist() {
        Thistlethwaite t = new Thistlethwaite();
        Cube c = new Cube();
        c.shuffle(14);
        t.solve(c);
        System.out.println(c.toString());
    }

    @Test
    public void testExampleOne() {
        Thistlethwaite t = new Thistlethwaite();
        Cube c = new Cube();
        c.rotate(Color.WHITE, true);
        c.oneEighty(Color.GREEN);
        c.oneEighty(Color.BLUE);
        c.oneEighty(Color.YELLOW);
        c.rotate(Color.GREEN, false);
        c.rotate(Color.WHITE, true); // U
        c.rotate(Color.GREEN, false);
        c.rotate(Color.RED, false);
        c.rotate(Color.ORANGE, false);
        c.rotate(Color.BLUE, true);
        c.rotate(Color.RED, false);
        System.out.println(c.toString());
        t.solve(c);
        System.out.println(c.toString());
    }

    @Test
    public void testExampleTwo() {
        Thistlethwaite t = new Thistlethwaite();
        Cube c = new Cube();
        c.rotate(Color.GREEN, false);
        c.rotate(Color.BLUE, false);
        c.rotate(Color.WHITE, true);
        c.oneEighty(Color.ORANGE);
        c.rotate(Color.YELLOW, true);
        c.rotate(Color.BLUE, true);
        c.oneEighty(Color.RED);
        c.oneEighty(Color.ORANGE);
        c.rotate(Color.WHITE, false);
        System.out.println(c.toString());
        t.solve(c);
        System.out.println(c.toString());
    }

    @Test
    public void testExampleThree() {
        Thistlethwaite t = new Thistlethwaite();
        Cube c = new Cube();
        c.rotate(Color.WHITE, false);
        c.rotate(Color.YELLOW, false);
        c.rotate(Color.GREEN, true);
        c.oneEighty(Color.ORANGE);
        c.rotate(Color.BLUE, true);
        c.rotate(Color.YELLOW, true);
        c.oneEighty(Color.RED);
        c.oneEighty(Color.ORANGE);
        c.rotate(Color.GREEN, false);
        System.out.println(c.toString());
        t.solve(c);
        System.out.println(c.toString());
    }

    @Test
    public void testThreeMidOneUp() {
        Thistlethwaite t = new Thistlethwaite();
        Cube c = new Cube();
        c.rotate(Color.WHITE, true);
        c.oneEighty(Color.BLUE);
        c.oneEighty(Color.YELLOW);
        c.oneEighty(Color.RED);
        c.oneEighty(Color.BLUE);
        c.oneEighty(Color.WHITE);
        c.oneEighty(Color.GREEN);
        c.oneEighty(Color.RED);
        c.rotate(Color.WHITE, false);
        c.rotate(Color.BLUE, true);
        c.rotate(Color.ORANGE, false);
        c.rotate(Color.YELLOW, true);
        c.oneEighty(Color.BLUE);
        c.rotate(Color.ORANGE, false);
        c.oneEighty(Color.WHITE);
        c.rotate(Color.RED, false);
        c.oneEighty(Color.YELLOW);
        c.rotate(Color.BLUE, true);
        c.oneEighty(Color.RED);
        c.oneEighty(Color.GREEN);
        System.out.println(c.toString());
        t.solve(c);
    }

    @Test
    public void testInfiniteTwo() {
        Thistlethwaite t = new Thistlethwaite();
        Cube c = new Cube();
        c.oneEighty(Color.BLUE);
        c.oneEighty(Color.RED);
        c.rotate(Color.YELLOW, false);
        c.oneEighty(Color.ORANGE);
        c.rotate(Color.WHITE, true);
        c.oneEighty(Color.RED);
        c.oneEighty(Color.BLUE);
        c.rotate(Color.YELLOW, true);
        c.oneEighty(Color.WHITE);
        c.rotate(Color.BLUE, false);
        c.oneEighty(Color.YELLOW);
        c.rotate(Color.BLUE, false);
        c.oneEighty(Color.GREEN);
        c.rotate(Color.YELLOW, false);
        c.rotate(Color.ORANGE, true);
        c.oneEighty(Color.BLUE);
        c.rotate(Color.WHITE, false);
        c.rotate(Color.ORANGE, false);
        c.rotate(Color.RED, true);
        c.rotate(Color.GREEN, true);
        System.out.println(c.toString());
        t.solve(c);
    }

    @Test
    public void testInfiniteFours() {
        Thistlethwaite t = new Thistlethwaite();
        Cube c = new Cube();
        c.rotate(Color.BLUE, true);
        c.rotate(Color.ORANGE, false);
        c.rotate(Color.RED, true);
        c.rotate(Color.WHITE, true);
        c.rotate(Color.BLUE, true);
        c.oneEighty(Color.GREEN);
        c.rotate(Color.ORANGE, true);
        c.rotate(Color.BLUE, true);
        c.rotate(Color.RED, true);
        c.oneEighty(Color.GREEN);
        System.out.println(c.toString());
        t.solve(c);
    }

    @Test
    public void testInfiniteTwo2() {
        Thistlethwaite t = new Thistlethwaite();
        Cube c = new Cube();
        c.rotate(Color.WHITE, false);
        c.oneEighty(Color.ORANGE);
        c.oneEighty(Color.GREEN);
        c.oneEighty(Color.ORANGE);
        c.rotate(Color.YELLOW, true);
        c.oneEighty(Color.GREEN);
        c.rotate(Color.WHITE, false);
        c.oneEighty(Color.ORANGE);
        c.oneEighty(Color.GREEN);
        c.oneEighty(Color.WHITE);
        c.rotate(Color.RED, true);
        c.rotate(Color.GREEN, true);
        c.oneEighty(Color.ORANGE);
        c.oneEighty(Color.GREEN);
        c.oneEighty(Color.ORANGE);
        c.rotate(Color.RED, false);
        c.oneEighty(Color.BLUE);
        c.oneEighty(Color.YELLOW);
        c.oneEighty(Color.GREEN);
        c.oneEighty(Color.WHITE);
        System.out.println(c.toString());
        t.solve(c);
    }

    @Test
    public void testInfiniteTwo3() {
        Thistlethwaite t = new Thistlethwaite();
        Cube c = new Cube();
        c.rotate(Color.WHITE, true);
        c.oneEighty(Color.ORANGE);
        c.oneEighty(Color.RED);
        c.rotate(Color.YELLOW, false);
        c.oneEighty(Color.BLUE);
        c.oneEighty(Color.YELLOW);
        c.oneEighty(Color.ORANGE);
        c.oneEighty(Color.YELLOW);
        c.rotate(Color.BLUE, false);
        c.oneEighty(Color.WHITE);
        c.rotate(Color.GREEN, true);
        c.rotate(Color.ORANGE, true);
        c.rotate(Color.WHITE, false);
        c.rotate(Color.ORANGE, true);
        c.rotate(Color.WHITE, false);
        c.oneEighty(Color.BLUE);
        c.oneEighty(Color.YELLOW);
        c.rotate(Color.WHITE, false);
        c.oneEighty(Color.BLUE);
        c.rotate(Color.GREEN, true);
        System.out.println(c.toString());
        t.solve(c);
    }

    @Test
    public void testAnotherInfiniteFour() {
        Thistlethwaite t = new Thistlethwaite();
        Cube c = new Cube();
        c.oneEighty(Color.RED);
        c.oneEighty(Color.YELLOW);
        c.oneEighty(Color.BLUE);
        c.oneEighty(Color.RED);
        c.oneEighty(Color.GREEN);
        c.oneEighty(Color.WHITE);
        c.oneEighty(Color.BLUE);
        c.rotate(Color.YELLOW, true);
        c.oneEighty(Color.BLUE);
        c.rotate(Color.RED, false);
        c.rotate(Color.GREEN, true);
        c.oneEighty(Color.YELLOW);
        c.oneEighty(Color.BLUE);
        c.rotate(Color.YELLOW, false);
        c.oneEighty(Color.GREEN);
        c.rotate(Color.YELLOW, true);
        c.oneEighty(Color.GREEN);
        c.oneEighty(Color.YELLOW);
        c.rotate(Color.RED, true);
        System.out.println(c.toString());
        t.solve(c);
    }

    @Test
    public void testInfiniteTwo4() {
        Thistlethwaite t = new Thistlethwaite();
        Cube c = new Cube();
        c.oneEighty(Color.BLUE);
        c.oneEighty(Color.WHITE);
        c.oneEighty(Color.ORANGE);
        c.rotate(Color.WHITE, true);
        c.oneEighty(Color.BLUE);
        c.rotate(Color.YELLOW, true);
        c.oneEighty(Color.WHITE);
        c.oneEighty(Color.BLUE);
        c.rotate(Color.ORANGE, false);
        c.rotate(Color.WHITE, false);
        c.oneEighty(Color.RED);
        c.rotate(Color.WHITE, false);
        c.oneEighty(Color.BLUE);
        c.oneEighty(Color.GREEN);
        c.oneEighty(Color.ORANGE);
        c.rotate(Color.WHITE, false);
        c.rotate(Color.BLUE, false);
        c.rotate(Color.YELLOW, true);
        c.rotate(Color.ORANGE, false);
        c.rotate(Color.BLUE, true);
        System.out.println(c.toString());
        t.solve(c);
    }

    @Test
    public void testPhaseTwo() {
        Thistlethwaite t = new Thistlethwaite();
        Cube c = new Cube();
        c.oneEighty(Color.BLUE);
        c.rotate(Color.YELLOW, true);
        c.oneEighty(Color.RED);
        c.oneEighty(Color.BLUE);
        c.oneEighty(Color.ORANGE);
        c.oneEighty(Color.GREEN);
        c.oneEighty(Color.YELLOW);
        c.rotate(Color.WHITE, true);
        c.oneEighty(Color.GREEN);
        c.oneEighty(Color.YELLOW);
        c.rotate(Color.RED, false);
        c.rotate(Color.GREEN, false);
        c.oneEighty(Color.YELLOW);
        c.oneEighty(Color.GREEN);
        c.oneEighty(Color.WHITE);
        c.oneEighty(Color.ORANGE);
        c.rotate(Color.GREEN, false);
        c.rotate(Color.RED, false);
        c.rotate(Color.BLUE, true);
        c.rotate(Color.GREEN, false);
        System.out.println(c.toString());
        t.solve(c);
    }

    @Test
    public void testReadFile() throws FileNotFoundException {
        String filepath = new File("").getAbsolutePath();
        FileReader file = new FileReader(filepath.concat("/tables/stage2.txt"));
    }

    @Test
    public void testLeadingZeroes() {
        System.out.println(0001);
        String ints = new String("");
        ints = ints + 0;
        ints = ints + 0;
        ints = ints + 0;
        ints = ints + 1;
        int i = Integer.parseInt(ints);
        System.out.println(i);

        String[] arr = new String[2];
        arr[0] = "1";
        arr[1] = "2";
        String ing = arr.toString();

    }

    @Test
    public void testStage4() {
        Cube c = new Cube();
        String commands = "1 2 1 2 1 3 5 1 6 1 5 2";
        Utils.rotateCommands(c, commands);
        System.out.println(c.toString());

    }


}
