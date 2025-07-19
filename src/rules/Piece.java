package rules;

import java.util.ArrayList;

public interface Piece {
    
    int player();   // 0 is empty, 1 is white and 2 is black.
    ArrayList<Move> moves();      //Note that this is all moves that could ideally be made, but they will have to be checked later and many will be discarded.
}
