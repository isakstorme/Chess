package rules;

import java.util.ArrayList;

public class Pawn implements Piece  {
    private int player;
    private ArrayList<Move> moves;

    public Pawn(Boolean isWhite){
        if (isWhite){
            player = 1;
        }
        else{
            player = 2;
        }
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
    public int player(){
        return player;
    }

    @Override
    public ArrayList<Move> moves() {
        return moves;
    }

    public void moved(){
        if (player == 1){
            moves.removeIf(m -> m.deltaRank == 2 || m.deltaRank == -2);
        }
    }

    @Override
    public String toString() {
        if (player == 1){
            return "wP";
        }
        else return "bP";
    }

}
