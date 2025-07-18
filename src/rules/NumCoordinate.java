package rules;

import java.util.HashMap;

import view.Coordinate;

public class NumCoordinate {
    public int file;
    public int rank;
    private HashMap<Character, Integer> fileMap;
    private HashMap<Character, Integer> rankMap;

    public NumCoordinate(int file, int rank){
        this.file = file;
        this.rank = rank;
    }

    public NumCoordinate(String coordinate){
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
        rankMap.put('8', 7);
        rankMap.put('7', 6);
        rankMap.put('6', 5);
        rankMap.put('5', 4);
        rankMap.put('4', 3);
        rankMap.put('3', 2);
        rankMap.put('2', 1);
        rankMap.put('1', 0);
        file = fileMap.get(tmp[0]);
        rank = rankMap.get(tmp[1]);
    }

    public NumCoordinate move(Move m){
        int deltaFile = m.deltaFile;
        int deltaRank = m.deltaRank;

        return new NumCoordinate(file + deltaFile, rank + deltaRank);
    }


}
