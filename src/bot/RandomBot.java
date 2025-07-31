package bot;

import java.util.ArrayList;
import java.util.Random;

import rules.Game;
import rules.Move;
import rules.NumCoordinate;

public class RandomBot implements Bot {
    Boolean isWhite;
    Random random;

    public RandomBot(){
        random = new Random();
    }

    public Move move(Game game){
        ArrayList<Move> moves = game.allLegalMoves();
        int len = moves.size();
        int randomInt = random.nextInt(len);

        return moves.get(randomInt);
    }
}
