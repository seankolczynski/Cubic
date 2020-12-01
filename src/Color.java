import java.util.ArrayList;
import java.util.List;

public enum Color {
    RED ('R'),
    BLUE('B'),
    WHITE('W'),
    GREEN('G'),
    YELLOW('Y'),
    ORANGE('O');

    public Character letter;

    Color(Character c) {
        this.letter = c;
    }

    public Character getLetter() {
        return this.letter;
    }


}


