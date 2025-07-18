package rules;

import java.util.ArrayList;

public class EmptySquare implements Piece {

    @Override
    public Boolean isWhite() {
       return false;
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
