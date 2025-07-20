package randomBot;

import java.util.ArrayList;
import java.util.Random;

import rules.Game;
import rules.NumCoordinate;

public class RandomBot {
    Boolean isWhite;
    Random random;

    public RandomBot(){
        random = new Random();
    }

    public NumCoordinate[] move(Game game){
        ArrayList<NumCoordinate[]> moves = game.allLegalMoves();
        int len = moves.size();
        int randomInt = random.nextInt(len);

        return moves.get(randomInt);
    }
}
