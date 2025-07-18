package rules;

import java.util.ArrayList;

public class EmptySquare implements Piece {

    @Override
    public int player() {
       return 0;
    }

    @Override
    public ArrayList<Move> moves() {
        return new ArrayList();
    }

    @Override
    public String toString() {
        return "--";
    }

}
