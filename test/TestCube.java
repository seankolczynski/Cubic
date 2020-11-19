import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class TestCube {

    @Test
    public void testOne() {
        Cube cub = new Cube();
        cub.rotate(Color.WHITE, true);
        System.out.println(cub.toString());
    }

}
