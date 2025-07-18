package rules;

public class Board {
    public Piece [][] position;

    public Board(){
        position = startingPosition();
    }

    private Piece[][] startingPosition() {
        Piece [][] startingPosition = new Piece [8][8];
        startingPosition[0][0] = new Rook(true);
        startingPosition[1][0] = new Knight(true);
        startingPosition[2][0] = new Bishop(true);
        startingPosition[3][0] = new Queen(true);
        startingPosition[4][0] = new King(true);
        startingPosition[5][0] = new Bishop(true);
        startingPosition[6][0] = new Knight(true);
        startingPosition[7][0] = new Rook(true);
        startingPosition[0][1] = new Pawn(true);
        startingPosition[1][1] = new Pawn(true);
        startingPosition[2][1] = new Pawn(true);
        startingPosition[3][1] = new Pawn(true);
        startingPosition[4][1] = new Pawn(true);
        startingPosition[5][1] = new Pawn(true);
        startingPosition[6][1] = new Pawn(true);
        startingPosition[7][1] = new Pawn(true);
        startingPosition[0][7] = new Rook(false);
        startingPosition[1][7] = new Knight(false);
        startingPosition[2][7] = new Bishop(false);
        startingPosition[3][7] = new Queen(false);
        startingPosition[4][7] = new King(false);
        startingPosition[5][7] = new Bishop(false);
        startingPosition[6][7] = new Knight(false);
        startingPosition[7][7] = new Rook(false);
        startingPosition[0][6] = new Pawn(false);
        startingPosition[1][6] = new Pawn(false);
        startingPosition[2][6] = new Pawn(false);
        startingPosition[3][6] = new Pawn(false);
        startingPosition[4][6] = new Pawn(false);
        startingPosition[5][6] = new Pawn(false);
        startingPosition[6][6] = new Pawn(false);
        startingPosition[7][6] = new Pawn(false);
        for (int i = 2; i < 6; i++){
            for (int e = 0; e<8; e++){
                startingPosition[e][i] = new EmptySquare();
            }
        }
        return startingPosition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 7; i>=0; i--){
            StringBuilder sb2 = new StringBuilder();
            for (int e = 0; e<8; e++){
                sb2.append(position[e][i].toString());
            }
            sb2.append("\n");
            sb.append(sb2.toString());
        }
        return sb.toString();
    }

    


}
