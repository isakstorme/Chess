package rules;

import java.util.ArrayList;

public class Queen implements Piece  {
    private int player;
    private ArrayList<Movement> moves;

    public Queen(Boolean isWhite){
        if (isWhite){
            player = 1;
        }
        else {
            player = 2;
        }
        moves = new ArrayList<>();
        for (int i = 1; i <= 7; i++){
            moves.add(new Movement(i, i));
            moves.add(new Movement(i, -i));
            moves.add(new Movement(-i, i));
            moves.add(new Movement(-i, -i));
            moves.add(new Movement(-i, 0));
            moves.add(new Movement(i, 0));
            moves.add(new Movement(0, -i));
            moves.add(new Movement(0, i));
        }

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
            return "wQ";
        }
        else return "bQ";
    }

}
