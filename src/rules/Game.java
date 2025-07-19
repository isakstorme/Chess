package rules;

import java.util.ArrayList;

public class Game {

    //Todo, fix castling and promotion
    public Board board;
    public Boolean whiteToMove;
    private NumCoordinate[] lastMove;  // Move here defined as element 0 is starting square and element 1 is final square
    private Boolean wKCastleRight;
    private Boolean wQCastleRight;
    private Boolean bKCastleRight;
    private Boolean bQCastleRight;


    public Game(){
        board = new Board();
        whiteToMove = true;
        lastMove = new NumCoordinate[2];
        lastMove[0] = new NumCoordinate(0, 0); //this is ugly, might fix later. It is to avoid nullpointerexception on first move
        lastMove[1] = new NumCoordinate(0, 0);
        wKCastleRight = true;
        wQCastleRight = true;
        bKCastleRight = true;
        bQCastleRight = true;
        System.out.println(board);
    }

    public Boolean isWhiteMove;
    
    public Boolean hasEnded(){  //Todo
        return (isMate() || isStalemate() || isInsufficientMaterial() || isRepetition() || isFifty());
    }

    private boolean isFifty() {
        return false;
    }

    private boolean isRepetition() {
        return false;
    }

    private boolean isInsufficientMaterial() {
        return false;
    }

    public Boolean isCheck(){ 
        ArrayList<NumCoordinate> opponentThreats = new ArrayList<>();  // Stores not the square from where the pieces are, but all squares that are controlled by opponent
        whiteToMove = !whiteToMove;
        Boolean isCheck = false;
        for (int f = 0; f < 8; f++){
            for (int r= 0; r<8; r++){  // for every square of the board,
                NumCoordinate coordinate = new NumCoordinate(f, r);
                Piece piece = board.get(coordinate);
                for (Move m : piece.moves()){   // check which moves of all potential ones are legal.
                    NumCoordinate to = new NumCoordinate(coordinate.file + m.deltaFile, coordinate.rank + m.deltaRank);
                    if (isLegalMove(coordinate, to)){
                        opponentThreats.add(to);
                        if (board.get(to) instanceof King){
                            isCheck = true;
                        }
                    }
                }
            }
        }
        whiteToMove = !whiteToMove;
        return isCheck;
    }

    public Boolean isMate(){  //Todo
        return false;
    }

    public Boolean isStalemate(){  //Todo
        return false;
    }

    public Boolean isLegalMove(NumCoordinate from, NumCoordinate to){
        if (isOutOfBoard(from) || isOutOfBoard(to)){
            return false;
        }
        int deltaFile = to.file - from.file;
        int deltaRank = to.rank - from.rank;
        Move move = new Move(deltaFile, deltaRank);
        Piece piece = board.get(from);
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

    private boolean isOutOfBoard(NumCoordinate coordinate) {
        if (coordinate.file < 0 || coordinate.file > 7 || coordinate.rank < 0 || coordinate.rank > 7){
            return true;
        }
        return false;
    }

    private Boolean isValidPawnMove(Piece piece, Move move, NumCoordinate from, NumCoordinate to) {
        if (move.deltaFile != 0){   //if pawn move is diagonal
            if (board.get(to).player() !=0 && board.get(to).player() != piece.player()){   //checks if we capture opponent's piece
                return true;
            }
            //checks en passent
            else if ((to.rank == lastMove[1].rank + 1 || to.rank == lastMove[1].rank - 1) 
            && to.file == lastMove[1].file 
            && (Math.abs(lastMove[1].rank - lastMove[0].rank) == 2)
            && board.get(lastMove[1]) instanceof Pawn){
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
        if (piece.player() == board.get(to).player()){
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
        while (Math.abs(m.deltaFile) < Math.abs(move.deltaFile) || Math.abs(m.deltaRank) < Math.abs(move.deltaRank)){ // We examine all squares leading up to the square we want to go to
            NumCoordinate newCoordinate = from.move(m);
            if (!(board.get(newCoordinate) instanceof EmptySquare)){
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


    public void move(NumCoordinate from, NumCoordinate to) {   // This assumemes the move is legal, no check is performed here
        int fFile = from.file;
        int fRank = from.rank;
        int tFile = to.file;
        int tRank = to.rank;
        Piece piece = board.position[fFile][fRank];
        board.position[tFile][tRank] = piece;
        board.position[fFile][fRank] = new EmptySquare();
        if (piece instanceof Pawn){
            ((Pawn)piece).moved();
            if (from.file != to.file  && !(board.get(to) instanceof EmptySquare)){  // Handles en passant
                board.position[lastMove[1].file][lastMove[1].rank] = new EmptySquare();
            }
            if (to.rank == 7){
                board.position[tFile][tRank] = new Queen(true);  // TODO: Should fix so not automatically promote to queen
            }
            if (to.rank == 0){
                board.position[tFile][tRank] = new Queen(false);
            }
        }

        if (piece instanceof King){
            ((King)piece).moved();
            if (to.file - from.file == 2){
                board.position[5][to.rank] = board.position[7][to.rank];
                board.position[7][to.rank] = new EmptySquare();
            }
            if (to.file - from.file == -2){
                board.position[3][to.rank] = board.position[0][to.rank];
                board.position[0][to.rank] = new EmptySquare();
            }
        }
        whiteToMove = !whiteToMove;
        lastMove[1] = to;
        lastMove[0] = from;
        System.out.println(board);
        if (isCheck()){
            System.out.println("Schack");
        }
    }

}
