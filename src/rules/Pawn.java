package rules;

import java.util.ArrayList;

public class Pawn implements Piece  {
    Boolean isWhite;
    ArrayList<Move> moves;

    public Pawn(Boolean isWhite){
        this.isWhite = isWhite;
        moves = new ArrayList<>();
        if (isWhite){
            moves.add(new Move(0, 2));
            moves.add(new Move(0, 1));
            moves.add(new Move(1, 1));
            moves.add(new Move(-1, 1));
        }
        else{
            moves.add(new Move(0, -2));
            moves.add(new Move(0, -1));
            moves.add(new Move(1, -1));
            moves.add(new Move(-1, -1));
        }

    }

    @Override
    public Boolean isWhite() {
        return isWhite;
    }

    @Override
    public ArrayList<Move> moves() {
        return moves;
    }

    @Override
    public String toString() {
        if (isWhite){
            return "wP";
        }
        else return "bP";
    }

}
