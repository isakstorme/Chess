package rules;

import java.util.HashMap;

public class NumCoordinate {
    public int file;
    public int rank;
    private HashMap<Character, Integer> fileMap;
    private HashMap<Character, Integer> rankMap;
    private HashMap<Integer, Character> fileMapReversed;
    private HashMap<Integer, Character> rankMapReversed;
    public String coordinate;

    public NumCoordinate(int file, int rank){   // does not check if file is inside board which it probably/maybe should.
        this.file = file;
        this.rank = rank;
        initfileMap();
    }


    public NumCoordinate(String coordinate){
        this.coordinate = coordinate;
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
       
    }   public NumCoordinate move(Movement m){
        int deltaFile = m.deltaFile;
        int deltaRank = m.deltaRank;

        return new NumCoordinate(file + deltaFile, rank + deltaRank);
    }

      private void initfileMap() {
        fileMapReversed = new HashMap<>(); // I don't know how efficient/inefficient it is to create a hashmap for every object. Maybe it could be static or in another class since it is used often.
        fileMapReversed.put(0, 'a');
        fileMapReversed.put(1, 'b');
        fileMapReversed.put(2, 'c');
        fileMapReversed.put(3, 'd');
        fileMapReversed.put(4, 'e');
        fileMapReversed.put(5, 'f');
        fileMapReversed.put(6, 'g');
        fileMapReversed.put(7, 'h');
        rankMapReversed = new HashMap<>();
        rankMapReversed.put(7, '8');
        rankMapReversed.put(6, '7');
        rankMapReversed.put(5, '6');
        rankMapReversed.put(4, '5');
        rankMapReversed.put(3, '4');
        rankMapReversed.put(2, '3');
        rankMapReversed.put(1, '2');
        rankMapReversed.put(0, '1');
    }
    public String coordinate(){
        if (file >= 0 && file < 8 && rank >= 0 && rank < 8 ){
            char[] coordinateArray = {fileMapReversed.get(file), rankMapReversed.get(rank)};
            coordinate = String.valueOf(coordinateArray);
            return coordinate;
        }
        return "not within board";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if ((obj instanceof NumCoordinate)){
            NumCoordinate numObj = (NumCoordinate) obj;
            return numObj.file == file && numObj.rank == rank;
        }
        return false;
    }
    @Override
    public String toString() {
        if (coordinate == null){
            return coordinate();
        }
        return coordinate;
    }


}
