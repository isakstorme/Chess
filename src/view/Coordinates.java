package view;

import java.util.HashMap;

public class Coordinates {
    private Coordinate[][] coordinates;
    private HashMap<Character, Integer> fileMap;
    private HashMap<Character, Integer> rankMap;
    private HashMap<Integer, Character> fileMapRev;
    private HashMap<Integer, Character> rankMapRev;
    
    public Coordinates(){
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
        fileMapRev = new HashMap<>();
        fileMapRev.put(0, 'a');
        fileMapRev.put(1, 'b');
        fileMapRev.put(2, 'c');
        fileMapRev.put(3, 'd');
        fileMapRev.put(4, 'e');
        fileMapRev.put(5, 'f');
        fileMapRev.put(6, 'g');
        fileMapRev.put(7, 'h');
        rankMapRev = new HashMap<>();
        rankMapRev.put(0, '8');
        rankMapRev.put(1, '7');
        rankMapRev.put(2, '6');
        rankMapRev.put(3, '5');
        rankMapRev.put(4, '4');
        rankMapRev.put(5, '3');
        rankMapRev.put(6, '2');
        rankMapRev.put(7, '1');
        coordinates = new Coordinate[8][8];
        for (int f = 0; f<8; f++ ){
            for (int r = 0; r < 8; r++){
                char[] coordinateArray = {fileMapRev.get(f), rankMapRev.get(r)};
                String coordinate = String.valueOf(coordinateArray);
                coordinates[f][r] = new Coordinate(coordinate);
            }
        }
    }

    public Coordinate get(String coordinate){
        char[] tmp = coordinate.toCharArray();
        int numFile = fileMap.get(tmp[0]);
        int numRank = rankMap.get(tmp[1]);
        return coordinates[numFile][numRank];
    }

    public Coordinate get(int file, int rank){
        return coordinates[file][rank];
    }
}
