package view;
public class PiecePair {   // represent a pair, where first element is supposed to represent the piece and the second 
    String piece; // for example wB is white bishop
    Coordinate coordinate;

    public PiecePair(String piece, Coordinate coordinate){
        this.piece = piece;
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return piece + " : " + coordinate.toString();
    }


}
