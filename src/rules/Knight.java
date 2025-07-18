package rules;

import java.util.ArrayList;

public class Knight implements Piece  {
    Boolean isWhite;
    ArrayList<Move> moves;

    public Knight(Boolean isWhite){
        this.isWhite = isWhite;
        moves = new ArrayList<>();
        moves.add(new Move(-2, -1));
        moves.add(new Move(-1, -2));
        moves.add(new Move(2, -1));
        moves.add(new Move(-1, 2));
        moves.add(new Move(2, 1));
        moves.add(new Move(1, 2));
        moves.add(new Move(-2, 1));
        moves.add(new Move(1, -2));

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
            return "wN";
        }
        else return "bN";
    }

}
