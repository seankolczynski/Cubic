import org.junit.Test;

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



}
