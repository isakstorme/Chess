package rules;

import java.util.ArrayList;

public class Bishop implements Piece  {
    Boolean isWhite;
    ArrayList<Move> moves;

    public Bishop(Boolean isWhite){
        this.isWhite = isWhite;
        moves = new ArrayList<>();
        for (int i = 1; i <= 7; i++){
            moves.add(new Move(i, i));
            moves.add(new Move(i, -i));
            moves.add(new Move(-i, i));
            moves.add(new Move(-i, -i));
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
            return "wB";
        }
        else return "bB";
    }

}
