package core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shera on 29.03.2018.
 */
public enum Genre {
    ART(0), BUSINESS(1), CHILDRES(2), CONTEMPORARY(3), CRIME(4), FANTASY(5),
    FICTION(6), HISTORY(7), HORROR(8), HUMOR(9), ROMANCE(10), SCIFI(11), THRILLER(12);

    private int value;
    private static Map map = new HashMap<>();

    private Genre(int value) {
        this.value = value;
    }

    static {
        for (Genre genre : Genre.values()) {
        map.put(genre.value, genre);
        }
    }

    public static Genre valueOf(int genre) {
        return (Genre) map.get(genre);
    }

    public int getValue() {
        return value;
    }
}

