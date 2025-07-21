package rules;

public class FEN {    // FEN is a way of representing a chess position. This class does not fully implement FEN but close enough
    private String FEN;
    int playerToMove;
    Boolean wKCastle;
    Boolean wQCastle;
    Boolean bKCastle;
    Boolean bQCastle;
    Boolean hasEnPassant;


    public FEN(Board board, int playerToMove, Boolean wKCastle, Boolean wQCastle, Boolean bKCastle, Boolean bQCastle, Boolean hasEnPassant, NumCoordinate[][] coordinates){
        this.playerToMove = playerToMove;
        this.wKCastle = wKCastle;
        this.wQCastle = wQCastle;
        this.bKCastle = bKCastle;
        this.bQCastle = bQCastle;
        this.hasEnPassant = hasEnPassant;
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r <8; r++){
            for (int f = 0; f <8; f++){
                sb.append(board.get(coordinates[f][r]));
            }
        }
        FEN = sb.toString();
    }




    @Override
    public boolean equals(Object obj) {
            if (obj == this){
                return true;
            }
            else if (obj instanceof FEN){
                FEN objF = (FEN) obj;
                return objF.FEN.equals(this.FEN) && objF.wKCastle == this.wKCastle 
                && objF.wQCastle == this.wQCastle 
                && objF.bKCastle == this.bKCastle 
                && objF.bQCastle == this.bQCastle
                && objF.hasEnPassant == this.hasEnPassant;
            }

            return false;
    }
}
