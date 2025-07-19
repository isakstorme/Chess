package rules;

import java.util.ArrayList;

public class King implements Piece  {
    private int player;
    private ArrayList<Move> moves;

    public King(Boolean isWhite){
        if(isWhite){
            player = 1;
        }
        else{
            player = 2;
        }
        moves = new ArrayList<>();
        moves.add(new Move(1, 1));
        moves.add(new Move(1, -1));
        moves.add(new Move(-1, 1));
        moves.add(new Move(-1, -1));
        moves.add(new Move(-1, 0));
        moves.add(new Move(1, 0));
        moves.add(new Move(0, -1));
        moves.add(new Move(0, 1));
        moves.add(new Move(2, 0));
        moves.add(new Move(-2, 0));

    }

    @Override
    public int player(){
        return player;
    }

    public void moved(){
        moves.removeIf(m -> Math.abs(m.deltaFile) == 2);
    }

    @Override
    public ArrayList<Move> moves() {
        return moves;
    }

    @Override
    public String toString() {
        if (player == 1){
            return "wK";
        }
        else return "bK";
    }

}
