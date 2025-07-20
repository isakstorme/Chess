package view;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import rules.Game;
import rules.NumCoordinate;
import rules.*;

//This is partly (creating the board almost entirely) based on https://www.youtube.com/watch?v=vO7wHV0HB8w
// Todo, fix graphics on en passant

public class BoardView {

    public final int squareSize = 64;   // Square size or something
    public Image bB;
    public Image bK;
    public Image bN;
    public Image bP;
    public Image bQ;
    public Image bR;
    public Image wB;
    public Image wK;
    public Image wN;
    public Image wP;
    public Image wR;
    public Image wQ;
    JFrame frame;
    JPanel chessBoard;
    ArrayList<PiecePair> pieces;    // Stores all pieces and the coordinates they are standing on.
    Boolean hasClicked;
    Coordinates coordinates;
    public ArrayList<Coordinate> legalSquares;
    public Game game;

    public BoardView() throws IOException{
        game = new Game();
        bB = ImageIO.read(new File("Images/bB.png"));   // These images are converted from jpg to png. Takes from the lichess repo.
        bK = ImageIO.read(new File("Images/bK.png"));
        bN = ImageIO.read(new File("Images/bN.png"));
        bP = ImageIO.read(new File("Images/bP.png"));
        bQ = ImageIO.read(new File("Images/bQ.png"));
        bR = ImageIO.read(new File("Images/bR.png"));
        wB = ImageIO.read(new File("Images/wB.png"));
        wK = ImageIO.read(new File("Images/wK.png"));
        wN = ImageIO.read(new File("Images/wN.png"));
        wP = ImageIO.read(new File("Images/wP.png"));
        wR = ImageIO.read(new File("Images/wR.png"));
        wQ = ImageIO.read(new File("Images/wQ.png"));
        frame = new JFrame("Chess");
        frame.setBounds(10, 10, 512, 512);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        coordinates = new Coordinates();
        pieces = getStartingPosition();
        hasClicked = false;
        legalSquares = new ArrayList<>();
        chessBoard= new JPanel(){
            @Override
            public void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);   // I think this is supposed to be good practise but not quite sure.
                Boolean isWhite = true;
                for (int r = 0; r < 8; r++){
                    for (int c = 0; c < 8; c++){
                        if (legalSquares.contains(coordinates.get(r, c))){
                            g.setColor(Color.RED);
                        }

                        else if (isWhite){
                            g.setColor(Color.WHITE);
                        }
                        else{
                            g.setColor(Color.BLACK);
                        }
                        g.fillRect(r * 64, c * 64, 64, 64);
                        isWhite = !isWhite;   //Alternating square colour.
                    }
                    isWhite = !isWhite;
                }
                for (PiecePair pp: pieces){
                    String p = pp.piece;
                    Coordinate co = pp.coordinate;
                    int r = co.numFile;
                    int c = co.numRank;
                    g.drawImage(getImage(p), r * squareSize, c * squareSize, squareSize, squareSize, this);
                }
            };
        };
        frame.add(chessBoard);
        frame.setVisible(true);
    }
    public static void main(String[] args) throws Exception {
        BoardView bb = new BoardView();
        bb.mainLoop();
        //bb.chessGame();
    }

    private void mainLoop() {
        chessBoard.addMouseListener(new MouseAdapter() {
            Coordinate from = coordinates.get("b1");
            Coordinate to = coordinates.get("a1");
            
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = e.getX();
                int c = e.getY();
                Coordinate coordinate = getCoordinateOfClick(r, c);
                if (!hasClicked){
                    from = coordinate;
                    NumCoordinate numCoordinate = new NumCoordinate(from.coordinate);
                    ArrayList<NumCoordinate> legalSquaresNum = game.LegalFromSquare(numCoordinate); 
                    if (legalSquaresNum.isEmpty()){
                        return;
                    }
                    for (NumCoordinate cNum : legalSquaresNum){
                        legalSquares.add(coordinateConverter(cNum));
                    }
                    hasClicked = !hasClicked;
                    chessBoard.repaint();
                }
                else{
                    to = coordinate;
                    if (to.equals(from)){ //makes sure you can double click on a piece without strange things happening
                        return;
                    }
                    move(from, to);
                    hasClicked = !hasClicked;
                    legalSquares = new ArrayList<>();
                }
            }
        });
    }
    private Coordinate getCoordinateOfClick(int r, int c) {  //Finds coordinate of click
                int numR = r/squareSize;
                int numC = c/squareSize;
                return coordinates.get(numR, numC);
            }

    public Coordinate coordinateConverter(NumCoordinate numcoordinate){
        int file = numcoordinate.file;
        int rank = Math.abs(numcoordinate.rank - 7);

        return coordinates.get(file, rank);
    }
    public Image getImage(String piece){
        if (piece == "wP"){
            return wP;
        }
        if (piece == "wR"){
            return wR;
        }
        if (piece == "wN"){
            return wN;
        }
        if (piece == "wB"){
            return wB;
        }
        if (piece == "wQ"){
            return wQ;
        }
        if (piece == "wK"){
            return wK;
        }
        if (piece == "bP"){
            return bP;
        }
        if (piece == "bR"){
            return bR;
        }
        if (piece == "bN"){
            return bN;
        }
        if (piece == "bB"){
            return bB;
        }
        if (piece == "bQ"){
            return bQ;
        }
        if (piece == "bK"){
            return bK;
        }
        return null; //hehe
    }

    public ArrayList<PiecePair> getStartingPosition(){
        pieces = new ArrayList<>();    // Stores all pieces and the coordinates they are standing on.
        pieces.add(new PiecePair("wR", coordinates.get("a1")));
        pieces.add(new PiecePair("wN", coordinates.get("b1")));
        pieces.add(new PiecePair("wB", coordinates.get("c1")));
        pieces.add(new PiecePair("wQ", coordinates.get("d1")));
        pieces.add(new PiecePair("wK", coordinates.get("e1")));
        pieces.add(new PiecePair("wB", coordinates.get("f1")));
        pieces.add(new PiecePair("wN", coordinates.get("g1")));
        pieces.add(new PiecePair("wR", coordinates.get("h1")));
        pieces.add(new PiecePair("wP", coordinates.get("a2")));
        pieces.add(new PiecePair("wP", coordinates.get("b2")));
        pieces.add(new PiecePair("wP", coordinates.get("c2")));
        pieces.add(new PiecePair("wP", coordinates.get("d2")));
        pieces.add(new PiecePair("wP", coordinates.get("e2")));
        pieces.add(new PiecePair("wP", coordinates.get("f2")));
        pieces.add(new PiecePair("wP", coordinates.get("g2")));
        pieces.add(new PiecePair("wP", coordinates.get("h2")));
        pieces.add(new PiecePair("bR", coordinates.get("a8")));
        pieces.add(new PiecePair("bN", coordinates.get("b8")));
        pieces.add(new PiecePair("bB", coordinates.get("c8")));
        pieces.add(new PiecePair("bQ", coordinates.get("d8")));
        pieces.add(new PiecePair("bK", coordinates.get("e8")));
        pieces.add(new PiecePair("bB", coordinates.get("f8")));
        pieces.add(new PiecePair("bN", coordinates.get("g8")));
        pieces.add(new PiecePair("bR", coordinates.get("h8")));
        pieces.add(new PiecePair("bP", coordinates.get("a7")));
        pieces.add(new PiecePair("bP", coordinates.get("b7")));
        pieces.add(new PiecePair("bP", coordinates.get("c7")));
        pieces.add(new PiecePair("bP", coordinates.get("d7")));
        pieces.add(new PiecePair("bP", coordinates.get("e7")));
        pieces.add(new PiecePair("bP", coordinates.get("f7")));
        pieces.add(new PiecePair("bP", coordinates.get("g7")));
        pieces.add(new PiecePair("bP", coordinates.get("h7")));
        return pieces;
    }

    public ArrayList<PiecePair> boardToImage(Board board){
        ArrayList<PiecePair> result = new ArrayList<>();
        char [] lines = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        char [] ranks = {'1', '2', '3', '4', '5', '6', '7', '8'};
        for (char l : lines){
            for (char r : ranks){
                char [] tmp = {l, r};
                String coordinateString = String.valueOf(tmp);
                NumCoordinate coordinate = new NumCoordinate(String.valueOf(coordinateString));
                Piece piece = board.get(coordinate);
                if (!(piece instanceof EmptySquare)){
                    result.add(new PiecePair(piece.toString(), coordinates.get(coordinateString)));
                }
            }
        }
        return result;
    }

    
    public void addPiece(Coordinate coordinate, String piece, JPanel chessBoard){  // adds chosen piece to choosen square.
            pieces.add(new PiecePair(piece, coordinate));

    }
    public void removePiece(Coordinate coordinate){
        pieces.removeIf(p -> p.coordinate.equals(coordinate));
    }
    

    public void move(Coordinate from, Coordinate to){
        if (!game.isLegalMove(new NumCoordinate(from.coordinate), new NumCoordinate(to.coordinate))){ 
            return; //break the function if move is illeal
            
        }
        game.move(new NumCoordinate(from.coordinate), new NumCoordinate(to.coordinate));
        SwingUtilities.invokeLater(() -> {
            pieces = boardToImage(game.board);  // Probably not efficient to rewrite everything but I don't think the cost in performance matters much.
            chessBoard.repaint();
        });

    }

    public void chessGame(){
        try {
            Thread.sleep(2000);
            move(coordinates.get("f2"), coordinates.get("f3"));
            Thread.sleep(2000);
            move(coordinates.get("e7"), coordinates.get("e5"));
            Thread.sleep(2000);
            move(coordinates.get("g2"), coordinates.get("g4"));
            Thread.sleep(2000);
            move(coordinates.get("d8"), coordinates.get("h4"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
