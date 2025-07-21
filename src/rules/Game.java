package rules;

import java.util.ArrayList;

public class Game {

    public Board board;
    public Boolean whiteToMove;
    private NumCoordinate[] lastMove;  // Move here defined as element 0 is starting square and element 1 is final square
    private NumCoordinate [][] coordinates;
    private Boolean hasEnded;
    private int movesWithoutPawnOrCapture; //Strictly speaking half moves
    private Boolean wKCastle;
    private Boolean wQCastle;
    private Boolean bKCastle;
    private Boolean bQCastle;
    private Boolean hasEnPassant;
    private ArrayList<FEN> positionHistory;


    public Game(){
        hasEnded = false;
        board = new Board();
        whiteToMove = true;
        wKCastle = true;
        wQCastle = true;
        bKCastle = true;
        bQCastle = true;
        hasEnPassant = false;
        positionHistory = new ArrayList<>();
        coordinates = new NumCoordinate[8][8];
        for (int f = 0; f<8; f++){
            for (int r = 0; r<8; r++){
                coordinates[f][r] = new NumCoordinate(f, r);
            }
        }
        positionHistory.add(new FEN(board, 1, wKCastle, wQCastle, bKCastle, bQCastle, hasEnPassant, coordinates));
        lastMove = new NumCoordinate[2];
        lastMove[0] = coordinates[0][0]; //this is ugly, might fix later. It is to avoid nullpointerexception on first move
        lastMove[1] = coordinates[0][0];
        movesWithoutPawnOrCapture = 0;
        System.out.println(board);
    }
    
    public Boolean hasEnded(){  //Todo
        //if (hasEnded){
        //    for (Movement m : log){
        //        System.out.println(m);
        //    }
        //}
        return hasEnded;
    }

    private boolean isFifty() {
        if (movesWithoutPawnOrCapture >= 100){
            return true;
        }
        return false;
    }

    private boolean isRepetition() {
        int length = positionHistory.size();
        FEN current = positionHistory.get(length - 1);
        int repetitions = 0;
        for (int i = length - 1; i>=0; i--){
            if (current.equals(positionHistory.get(i))){
                repetitions += 1;
                if (repetitions == 3){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isInsufficientMaterial() {   // This one is difficult and not even Lichess does this correctly, for example when position is blocked by pawns,
        for (int f = 0; f < 8; f++){
            for (int r = 0; r < 8; r++){
                if (!((board.get(coordinates[f][r]) instanceof King || board.get(coordinates[f][r]) instanceof EmptySquare))){
                    return false;
                }
            }
        }
        return true;
    }

    public Boolean isCheck(){ 
        ArrayList<NumCoordinate> opponentThreats = new ArrayList<>();  // Stores not the square from where the pieces are, but all squares that are controlled by opponent
        whiteToMove = !whiteToMove;
        Boolean isCheck = false;
        for (int f = 0; f < 8; f++){
            for (int r= 0; r<8; r++){  // for every square of the board,
                NumCoordinate coordinate = coordinates[f][r];
                Piece piece = board.get(coordinate);
                for (Move m : piece.moves()){   // check which moves of all potential ones are legal.
                    if (isOutOfBoard(coordinate.file + m.deltaFile, coordinate.rank + m.deltaRank)){
                        continue;
                    }
                    NumCoordinate to = coordinates[coordinate.file + m.deltaFile] [coordinate.rank + m.deltaRank];
                    if (isValidMove(coordinate, to)){
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
        if (!isCheck()){
            return false;
        }

        for (int f = 0; f < 8; f++){
            for (int r= 0; r<8; r++){  // for every square of the board,
                NumCoordinate coordinate = coordinates[f][r];
                Piece piece = board.get(coordinate);
                for (Move m : piece.moves()){   // check which moves of all potential ones are legal.
                    if (isOutOfBoard(coordinate.file + m.deltaFile, coordinate.rank + m.deltaRank)){
                        continue;
                    }
                    NumCoordinate to = coordinates[coordinate.file + m.deltaFile] [coordinate.rank + m.deltaRank];
                    if (isLegalMove(coordinate, to)){
                        return false;
                    }
                }
            }
        }
        hasEnded = true;
        return true;
    }

    public Boolean isStalemate(){  //Todo
        if (isCheck()){
            return false;
        }
        for (int f = 0; f < 8; f++){
            for (int r= 0; r<8; r++){  // for every square of the board,
                NumCoordinate coordinate = coordinates[f][r];
                Piece piece = board.get(coordinate);
                for (Move m : piece.moves()){   // check which moves of all potential ones are legal.
                    if (isOutOfBoard(coordinate.file + m.deltaFile, coordinate.rank + m.deltaRank)){
                        continue;
                    }
                    NumCoordinate to = coordinates[coordinate.file + m.deltaFile][coordinate.rank + m.deltaRank];
                    if (isLegalMove(coordinate, to)){
                        return false;
                    }
                }
            }
        }
        hasEnded = true;
        return true;
    }

    public Boolean isValidMove(NumCoordinate from, NumCoordinate to){  // doesn't check if results in check which is important for checking check mate and things. Does not check if squares are outside of board
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
        if (piece instanceof King && Math.abs(from.file - to.file) == 2){
            return isValidCastle(from, to);
        }

        return true;
    }

    private Boolean isValidCastle(NumCoordinate from, NumCoordinate to) {
        if (whiteToMove){
            if (to.file - from.file == 2){  //Kingside
                NumCoordinate e1 = coordinates[4][0];
                NumCoordinate f1 = coordinates[5][0];
                NumCoordinate g1 = coordinates[6][0];
                if (opponentThreats().contains(e1) 
                || opponentThreats().contains(f1)
                || opponentThreats().contains(g1)
                || !(board.get(f1) instanceof EmptySquare)
                || !(board.get(g1) instanceof EmptySquare)){
                    return false;
                }
            }
            else{
                NumCoordinate e1 = coordinates[4][0];
                NumCoordinate d1 = coordinates[3][0];
                NumCoordinate c1 = coordinates[2][0];
                if (opponentThreats().contains(e1) 
                || opponentThreats().contains(d1)
                || opponentThreats().contains(c1)
                || !(board.get(d1) instanceof EmptySquare)
                || !(board.get(c1) instanceof EmptySquare)){
                    return false;
                }
            }
        }
        else{
            if (to.file - from.file == 2){  //Kingside
                NumCoordinate e8 = coordinates[4][7];;
                NumCoordinate f8 = coordinates[5][7];;
                NumCoordinate g8 = coordinates[6][7];;
                if (opponentThreats().contains(e8) 
                || opponentThreats().contains(f8)
                || opponentThreats().contains(g8)
                || !(board.get(f8) instanceof EmptySquare)
                || !(board.get(g8) instanceof EmptySquare)){
                    return false;
                }
            }
            else{
                NumCoordinate e8 = coordinates[4][7];;
                NumCoordinate d8 = coordinates[3][7];;
                NumCoordinate c8 = coordinates[2][7];;
                if (opponentThreats().contains(e8) 
                || opponentThreats().contains(d8)
                || opponentThreats().contains(c8)
                || !(board.get(d8) instanceof EmptySquare)
                || !(board.get(c8) instanceof EmptySquare)){
                    return false;
                }
            }
        }
        return true;
    }

    private ArrayList<NumCoordinate> opponentThreats(){
        ArrayList<NumCoordinate> opponentThreats = new ArrayList<>();  // Stores not the square from where the pieces are, but all squares that are controlled by opponent
        whiteToMove = !whiteToMove;
        for (int f = 0; f < 8; f++){
            for (int r= 0; r<8; r++){  // for every square of the board,
                NumCoordinate coordinate = coordinates[f] [r];
                Piece piece = board.get(coordinate);
                for (Move m : piece.moves()){   // check which moves of all potential ones are legal.
                    if (isOutOfBoard(coordinate.file + m.deltaFile, coordinate.rank + m.deltaRank)){
                        continue;
                    }
                    NumCoordinate to = coordinates[coordinate.file + m.deltaFile] [coordinate.rank + m.deltaRank];
                    if (piece instanceof King && Math.abs(m.deltaFile) == 2){   // If we check castling we can happen in a very strange loop 
                        //where we get stack overflow with stack trace isValidCastle-opponentThreats-isValidMove-isValidCastle 
                        continue;
                    }
                    if (isValidMove(coordinate, to)){
                        opponentThreats.add(to);
                    }
                }
            }
        }
        whiteToMove = !whiteToMove;
        return opponentThreats;
    }

    public Boolean isLegalMove(NumCoordinate from, NumCoordinate to){ // Does not check if move is out of board
        if (hasEnded){
            return false;
        }
        if (!(kingInCheck(from, to)) && isValidMove(from, to)){
            return true;
        }
        return false;
    }

    public ArrayList<NumCoordinate[]> allLegalMoves(){  // returns a number of lists where each list has two elements. Element 0 is starting square and element 1 is ending square
        ArrayList<NumCoordinate[]> result = new ArrayList<>();
        for (int f = 0; f < 8; f++ ){
            for (int r = 0; r < 8; r++){
                NumCoordinate from = coordinates[f][r];
                Piece piece = board.get(from);
                for (Move m : piece.moves()){
                    int newFile = f + m.deltaFile;
                    int newRank = r + m.deltaRank;
                    if (isOutOfBoard(newFile, newRank)){
                        continue;
                    }
                    NumCoordinate to = coordinates[newFile]  [newRank];
                    if (isLegalMove(from, to)){
                        NumCoordinate[] toAdd = {from, to};
                        result.add(toAdd);
                    }
                }
            }
        }
        return result;
    }

    public ArrayList<NumCoordinate> LegalFromSquare(NumCoordinate from){
        ArrayList<NumCoordinate> result = new ArrayList<>();
        Piece piece = board.get(from);
        for (Move m : piece.moves()){
            int newFile = from.file + m.deltaFile;
            int newRank = from.rank + m.deltaRank;
            if (isOutOfBoard(newFile, newRank)){
                continue;
            }
            NumCoordinate to = coordinates[newFile]  [newRank];
            if (isLegalMove(from, to)){
                result.add(to);
            }
        }
        return result;
    }
    

    private boolean kingInCheck(NumCoordinate from, NumCoordinate to) {
        Boolean result;
        Piece piece = board.get(from);
        Piece otherPiece = board.get(to);  // could be empty square
        board.position[from.file][from.rank] = new EmptySquare();
        board.position[to.file][to.rank] = piece;
        if (isCheck()){
            result = true;
        }
        else{
            result = false;
        }
        board.position[to.file][to.rank] = otherPiece;
        board.position[from.file][from.rank] = piece;
        return result;
    }

    private boolean isOutOfBoard(int file, int rank) {
        if (file < 0 || file > 7 || rank < 0 || rank > 7){
            return true;
        }
        return false;
    }

    private Boolean isValidPawnMove(Piece piece, Move move, NumCoordinate from, NumCoordinate to) {
        if (move.deltaFile != 0){   //if pawn move is diagonal
            if (board.get(to).player() !=0 && board.get(to).player() != piece.player()){   //checks if we capture opponent's piece
                return true;
            }
            //checks en passant
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
            NumCoordinate newCoordinate = coordinates[from.file + m.deltaFile][from.rank + m.deltaRank];
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

        movesWithoutPawnOrCapture += 1;

        if (piece instanceof Pawn){
            ((Pawn)piece).moved();
            movesWithoutPawnOrCapture = 0;   //reset 50 move rule if pawn moves
            if (from.file != to.file  && (board.get(to) instanceof EmptySquare)){  // Handles en passant
                board.position[lastMove[1].file][lastMove[1].rank] = new EmptySquare();
            }
            if (to.rank == 7){
                piece = new Queen(true);  // TODO: Should fix so not automatically promote to queen
            }
            if (to.rank == 0){
                piece = new Queen(false);
            }
        }

        if (piece instanceof King){
            ((King)piece).moved();
            if (whiteToMove){
                wKCastle = false;
                wQCastle = false;
            }
            else {
                bKCastle = false;
                bQCastle = false;
            }
            if (to.file - from.file == 2){
                board.position[5][to.rank] = board.position[7][to.rank];
                board.position[7][to.rank] = new EmptySquare();
            }
            if (to.file - from.file == -2){
                board.position[3][to.rank] = board.position[0][to.rank];
                board.position[0][to.rank] = new EmptySquare();
            }
        }

        if (piece instanceof Rook){
            if (from.file == 0 && from.rank == 0){
                wQCastle = false;
                if (board.get(coordinates[4][0]) instanceof King){
                    board.get(coordinates[4][0]).moves().removeIf(m -> m.deltaFile == -2);
                }
            }
            if (from.file == 7 && from.rank == 0){
                wKCastle = false;
                if (board.get(coordinates[4][0]) instanceof King){
                    board.get(coordinates[4][0]).moves().removeIf(m -> m.deltaFile == 2);
                }
            }
            if (from.file == 0 && from.rank == 7){
                bQCastle = false;
                if (board.get(coordinates[4][7]) instanceof King){
                    board.get(coordinates[4][7]).moves().removeIf(m -> m.deltaFile == -2);
                }
            }
            if (from.file == 7 && from.rank == 7){
                bKCastle = false;
                if (board.get(coordinates[4][7]) instanceof King){
                    board.get(coordinates[4][7]).moves().removeIf(m -> m.deltaFile == 2);
                }
            }
        }

        //These below check if a rook is taken

        if (to.file == 0 && to.rank == 0){
            wQCastle = false;
            if (board.get(coordinates[4][0]) instanceof King){
                    board.get(coordinates[4][0]).moves().removeIf(m -> m.deltaFile == -2);
            }
        }

        if (to.file == 7 && to.rank == 0){
            wKCastle = false;
            if (board.get(coordinates[4][0]) instanceof King){
                    board.get(coordinates[4][0]).moves().removeIf(m -> m.deltaFile == 2);
            }
        }
        if (to.file == 0 && to.rank == 7){
            bQCastle = false;
            if (board.get(coordinates[4][7]) instanceof King){
                board.get(coordinates[4][7]).moves().removeIf(m -> m.deltaFile == -2);
            }
        }
        if (to.file == 7 && to.rank == 7){
            bKCastle = false;
            if (board.get(coordinates[4][7]) instanceof King){
                board.get(coordinates[4][7]).moves().removeIf(m -> m.deltaFile == 2);
            }
        }

        if (!(board.get(to) instanceof EmptySquare)){
            movesWithoutPawnOrCapture = 0;
        }

        board.position[tFile][tRank] = piece;
        board.position[fFile][fRank] = new EmptySquare();
        whiteToMove = !whiteToMove;
        lastMove[1] = to;
        lastMove[0] = from;
        System.out.println(board);
        if (isMate()){
            System.out.println("win by checkmate");  
            hasEnded = true;   // Detta ska ändras. Sidoeffekter som utskrifter bör vara i BoardView
        }
        if (isStalemate()){
            System.out.println("draw by stalemate");
            hasEnded = true;
        }

        System.out.println(movesWithoutPawnOrCapture);

        if (isFifty()){
            System.out.println("draw by fifty move rule");
            hasEnded = true;
        }

        if (isInsufficientMaterial()){
            hasEnded = true;
            System.out.println("insufficient material");
        }

        if (whiteToMove){
            positionHistory.add(new FEN(board, 1, wKCastle, wQCastle, bKCastle, bQCastle, hasEnPassant, coordinates));
        }
        else{
            positionHistory.add(new FEN(board, 2, wKCastle, wQCastle, bKCastle, bQCastle, hasEnPassant, coordinates));
        }
        if (isRepetition()){
            hasEnded = true;
            System.out.println("Draw, same position has been repeated three times");
        }
    }

}
