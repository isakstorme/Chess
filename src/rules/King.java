package rules;

import java.util.ArrayList;

public class King implements Piece  {
    private int player;
    public ArrayList<Movement> moves;

    public King(Boolean isWhite){
        if(isWhite){
            player = 1;
        }
        else{
            player = 2;
        }
        moves = new ArrayList<>();
        moves.add(new Movement(1, 1));
        moves.add(new Movement(1, -1));
        moves.add(new Movement(-1, 1));
        moves.add(new Movement(-1, -1));
        moves.add(new Movement(-1, 0));
        moves.add(new Movement(1, 0));
        moves.add(new Movement(0, -1));
        moves.add(new Movement(0, 1));
        moves.add(new Movement(2, 0));
        moves.add(new Movement(-2, 0));

    }

    @Override
    public int player(){
        return player;
    }

    public void moved(){
        moves.removeIf(m -> Math.abs(m.deltaFile) == 2);
    }

    @Override
    public ArrayList<Movement> moves() {
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
