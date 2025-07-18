package rules;

import java.util.ArrayList;

interface Piece {
    
    Boolean isWhite();
    ArrayList<Move> moves();      //Note that this is all moves that could ideally be made, but they will have to be checked later and many will be discarded.
}
