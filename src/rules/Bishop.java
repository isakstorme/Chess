package rules;

import java.util.ArrayList;

public class Bishop implements Piece  {
    int player;
    ArrayList<Move> moves;

    public Bishop(Boolean isWhite){
        if (isWhite){
            player = 1;
        }
        else{
            player = 2;
        }
        moves = new ArrayList<>();
        for (int i = 1; i <= 7; i++){
            moves.add(new Move(i, i));
            moves.add(new Move(i, -i));
            moves.add(new Move(-i, i));
            moves.add(new Move(-i, -i));
        }

    }

    @Override
    public int player() {
        return player;
    }

    @Override
    public ArrayList<Move> moves() {
        return moves;
    }

    @Override
    public String toString() {
        if (player == 1){
            return "wB";
        }
        else return "bB";
    }

}
