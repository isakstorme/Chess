package rules;

public class Game {

    public Board board;
    public Boolean whiteToMove;

    public Game(){
        board = new Board();
        whiteToMove = true;
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
        int deltaFile = to.file - from.file;
        int deltaRank = to.rank - from.rank;
        Move move = new Move(deltaFile, deltaRank);
        Piece piece = board.position[from.file][from.rank];
        if (!rightPlayerMove(piece)){
            return false;
        }
        if (takesOwnPiece(piece, to)){
            return false;
        }
        if (piece.moves().contains(move)){
            return true;
        }
        return false;
    }

    private boolean takesOwnPiece(Piece piece, NumCoordinate to) {
        if (piece.player() == board.position[to.file][to.rank].player()){
            return true;
        }
        return false;
        
    }

    private boolean rightPlayerMove(Piece piece){
        if (whiteToMove && !(piece.player() == 1)){
            return false;
        }
        if (!whiteToMove && (piece.player() == 1)){
            return false;
        }
        return true;
    }

    public void move(NumCoordinate from, NumCoordinate to) {   //Might create a function that runs isLegalMove as part of move, when boolean returns if succesful or not
        int fFile = from.file;
        int fRank = from.rank;
        int tFile = to.file;
        int tRank = to.rank;
        board.position[tFile][tRank] = board.position[fFile][fRank];
        board.position[fFile][fRank] = new EmptySquare();
        whiteToMove = !whiteToMove;
    }

}
