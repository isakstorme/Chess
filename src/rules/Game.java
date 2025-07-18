package rules;

public class Game {

    public Board board;

    public Game(){
        board = new Board();
        System.out.println(board);
    }

    public Boolean isWhiteMove;
    
    public Boolean hasEnded(){  //Todo
        return false;
    }

    public Boolean isMate(){  //Todo
        return false;
    }

    public Boolean isStalemate(){  //Todo
        return false;
    }

    public Boolean isLegalMove(NumCoordinate from, NumCoordinate to){
        System.out.println(to.rank);
        System.out.println(from.rank);
        int deltaFile = to.file - from.file;
        int deltaRank = to.rank - from.rank;
        Move move = new Move(deltaFile, deltaRank);
        Piece piece = board.position[from.file][from.rank];
        System.out.println(piece.moves());
        System.out.println(move);
        if (piece.moves().contains(move)){
            return true;
        }
        return false;
    }

    public void move(NumCoordinate from, NumCoordinate to) {   //Might create a function that runs isLegalMove as part of move, when boolean returns if succesful or not
        int fFile = from.file;
        int fRank = from.rank;
        int tFile = to.file;
        int tRank = to.rank;

        board.position[tFile][tRank] = board.position[fFile][fRank];
        board.position[fFile][fRank] = new EmptySquare();
    }

}
