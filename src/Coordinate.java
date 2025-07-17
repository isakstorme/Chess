import java.util.HashMap;

public class Coordinate {
    public int file; // zero indexed
    public int rank; // zero indexed
    public int numFile;
    public int numRank;
    private HashMap<Character, Integer> fileMap;
    private HashMap<Character, Integer> rankMap;

    public Coordinate(String coordinate){   // Takes input like "d4" or "e5" and creates a zero indexed numerical representation. Used by Gui
        char[] tmp = coordinate.toCharArray();
        fileMap = new HashMap<>();
        fileMap.put('a', 0);
        fileMap.put('b', 1);
        fileMap.put('c', 2);
        fileMap.put('d', 3);
        fileMap.put('e', 4);
        fileMap.put('f', 5);
        fileMap.put('g', 6);
        fileMap.put('h', 7);
        rankMap = new HashMap<>();
        rankMap.put('8', 0);
        rankMap.put('7', 1);
        rankMap.put('6', 2);
        rankMap.put('5', 3);
        rankMap.put('4', 4);
        rankMap.put('3', 5);
        rankMap.put('2', 6);
        rankMap.put('1', 7);
        file = tmp[0];
        rank = tmp[1];
        numFile = fileMap.get(tmp[0]);
        numRank = rankMap.get(tmp[1]);
    }

}
