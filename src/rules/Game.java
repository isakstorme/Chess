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
        if (invalidJumpOverPiece(from, to, move)){
            return false;
        }
        if (!(piece.moves().contains(move))){
            return false;
        }
        if (piece instanceof Pawn){
            return isValidPawnMove(piece, move, from, to);
        }

        return true;
    }

    private Boolean isValidPawnMove(Piece piece, Move move, NumCoordinate from, NumCoordinate to) {
        if (move.deltaFile != 0){   //if pawn move is diagonal
            if (board.get(to).player() !=0 && board.get(to).player() != piece.player()){   //checks if we capture opponent's piece
                return true;
            }
            else{
                return false;
            }
        }
        else{  //if pawn move is straight
            if (!(board.get(to).player() == 0)){
                return false;
            }
        }
        return true;
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

    private Boolean invalidJumpOverPiece(NumCoordinate from, NumCoordinate to, Move move){
        Piece piece = board.position[from.file][from.rank];
        if (piece instanceof Knight){
            return false;
        }
        if (Math.abs(move.deltaFile) <= 1 && Math.abs(move.deltaRank) <= 1){
            return false;
        }
        Move dir = directionOfMove(move);
        Move m = directionOfMove(move);   //this one is incremented according to dir
        while (Math.abs(m.deltaFile) < Math.abs(move.deltaFile) || Math.abs(m.deltaRank) < Math.abs(move.deltaRank)){
            NumCoordinate newCoordinate = from.move(m);
            if (!(board.position[newCoordinate.file][newCoordinate.rank] instanceof EmptySquare)){
                return true;
            }
            m = new Move(m.deltaFile + dir.deltaFile, m.deltaRank + dir.deltaRank);
        }

        return false;
    }

    private Move directionOfMove(Move move){
        if (move.deltaFile == 0){   //Vertical move, two directions, front or back
            if (move.deltaRank > 0){
                return new Move(0, 1);
            }
            else {
                return new Move(0, -1);
            }
        }
        if (move.deltaRank == 0){   // Horizontal move, two directions, left and right
            if (move.deltaFile > 0){
                return new Move(1, 0);
            }
            else{
                return new Move(-1, 0);
            }
        }

        if (move.deltaFile < 0 && move.deltaRank < 0){  // Down diagonal left
            return new Move(-1, -1);
        }
        
        if (move.deltaFile < 0 && move.deltaRank > 0){  // Left upwards
            return new Move(-1, 1);
        }
        if (move.deltaFile > 0 && move.deltaRank > 0){  // Right upwards
            return new Move(1, 1);
        }if (move.deltaFile > 0 && move.deltaRank < 0){  //Right down
            return new Move(1, -1);
        }
        return new Move(0, 0);



    }


    public void move(NumCoordinate from, NumCoordinate to) {   //Might create a function that runs isLegalMove as part of move, when boolean returns if succesful or not
        int fFile = from.file;
        int fRank = from.rank;
        int tFile = to.file;
        int tRank = to.rank;
        Piece piece = board.position[fFile][fRank];
        if (piece instanceof Pawn){
            ((Pawn)piece).hasMoved();
        }
        board.position[tFile][tRank] = piece;
        board.position[fFile][fRank] = new EmptySquare();
        whiteToMove = !whiteToMove;
    }

}
