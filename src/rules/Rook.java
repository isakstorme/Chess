package rules;

import java.util.ArrayList;

public class Rook implements Piece  {
    private int player;
    private ArrayList<Move> moves;

    public Rook(Boolean isWhite){
        if(isWhite){
            player = 1;
        }
        else{
            player = 2;
        }
        moves = new ArrayList<>();
        for (int i = 1; i <= 7; i++){
            moves.add(new Move(-i, 0));
            moves.add(new Move(i, 0));
            moves.add(new Move(0, -i));
            moves.add(new Move(0, i));
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
            return "wR";
        }
        else return "bR";
    }

}
