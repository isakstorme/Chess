package rules;

import java.util.ArrayList;

public class King implements Piece  {
    Boolean isWhite;
    ArrayList<Move> moves;

    public King(Boolean isWhite){
        this.isWhite = isWhite;
        moves = new ArrayList<>();
        moves.add(new Move(1, 1));
        moves.add(new Move(1, -1));
        moves.add(new Move(-1, 1));
        moves.add(new Move(-1, -1));
        moves.add(new Move(-1, 0));
        moves.add(new Move(1, 0));
        moves.add(new Move(0, -1));
        moves.add(new Move(0, 1));

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
            return "wK";
        }
        else return "bK";
    }

}
