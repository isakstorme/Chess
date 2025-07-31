package rules;

import java.util.ArrayList;

public class Knight implements Piece  {
    private int player;
    private ArrayList<Movement> moves;

    public Knight(Boolean isWhite){
        if (isWhite){
            player = 1;
        }
        else{
            player = 2;
        }
        moves = new ArrayList<>();
        moves.add(new Movement(-2, -1));
        moves.add(new Movement(-1, -2));
        moves.add(new Movement(2, -1));
        moves.add(new Movement(-1, 2));
        moves.add(new Movement(2, 1));
        moves.add(new Movement(1, 2));
        moves.add(new Movement(-2, 1));
        moves.add(new Movement(1, -2));

    }

    @Override
    public int player() {
        return player;
    }

    @Override
    public ArrayList<Movement> moves() {
        return moves;
    }

    @Override
    public String toString() {
        if (player == 1){
            return "wN";
        }
        else return "bN";
    }

}
