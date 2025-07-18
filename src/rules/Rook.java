package rules;

import java.util.ArrayList;

public class Rook implements Piece  {
    Boolean isWhite;
    ArrayList<Move> moves;

    public Rook(Boolean isWhite){
        this.isWhite = isWhite;
        moves = new ArrayList<>();
        for (int i = 1; i <= 7; i++){
            moves.add(new Move(-i, 0));
            moves.add(new Move(i, 0));
            moves.add(new Move(0, -i));
            moves.add(new Move(0, i));
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
            return "wR";
        }
        else return "bR";
    }

}
