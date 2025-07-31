package bot;

import java.util.HashMap;

import rules.Board;
import rules.Game;
import rules.Move;
import rules.NumCoordinate;
import rules.Piece;

public class MiniMaxBot implements Bot {   //TODO, this is probably a bit more difficult than I first assumed.

    private HashMap<String, Integer> EVALS;

    public MiniMaxBot(){
        EVALS.put("wK", 0);
        EVALS.put("wQ", 9);
        EVALS.put("wR", 5);
        EVALS.put("wB", 3);
        EVALS.put("wN", 3);
        EVALS.put("wP", 1);
        EVALS.put("bK", 0);
        EVALS.put("bQ", -9);
        EVALS.put("bR", -5);
        EVALS.put("bB", -3);
        EVALS.put("bN", -3);
        EVALS.put("bP", -1);
        EVALS.put("--", 0);
    }

    @Override
    public Move move(Game game) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }

    private Move miniMax() throws Exception{     // Jag behöver kunna söka igenom ställningar men har inte något bra sätt att göra det nu.
        throw new Exception();
    }

    private int evaluation(Game game){
        int result = 0;
        if (game.whiteToMove == true){
            for (int r = 0; r<8; r++){
                for (int c = 0; c<8; c++){
                    Piece piece = game.board.get(r, c);
                    result += (EVALS.get(piece.toString()));
                }
            }
        }
        else{
            if (game.whiteToMove == true){
            for (int r = 0; r<8; r++){
                for (int c = 0; c<8; c++){
                    Piece piece = game.board.get(r, c);
                    result -= (EVALS.get(piece.toString()));  // this row is only difference depending on if it is white or black to play.
                }
            }
        }
        }
        return result;
    }

}
