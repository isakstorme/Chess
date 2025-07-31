package bot;

import rules.Game;
import rules.Move;

public interface Bot {
    public Move move(Game game);
}
