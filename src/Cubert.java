import java.io.*;
import java.util.Scanner;
import java.util.logging.FileHandler;

public class Cubert {

    public static void main(String[] args) throws IOException {
        BufferedReader scan = null;
        Cube cube;
        FileWriter fw = new FileWriter("flatCubes.txt");
        try {
            String filepath = new File("").getAbsolutePath();
            scan = new BufferedReader(new FileReader(new File(filepath.concat("/tables/data.txt"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int count = 0;
        for (int x = 1; x <=500; x++) {

            count++;
            cube = new Cube();
            String moves = scan.readLine();
            Utils.move(cube, moves);
            //if (x > 300) {
                //FileWriter fw2 = new FileWriter("flatCubesExtra.txt");
                //fw2.append(cube.toFlat());
            //} else {
                fw.append(cube.toFlat());
            //}
            fw.append("\n");
        }
        fw.close();
        System.out.println(count);

    }

}
