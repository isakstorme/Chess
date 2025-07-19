package view;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import rules.Game;
import rules.NumCoordinate;
import rules.*;

//This is partly (creating the board almost entirely) based on https://www.youtube.com/watch?v=vO7wHV0HB8w

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
        pieces = getStartingPosition();
        hasClicked = false;
        chessBoard= new JPanel(){
            @Override
            public void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);   // I think this is supposed to be good practise but not quite sure.
                Boolean isWhite = true;
                for (int r = 0; r < 8; r++){
                    for (int c = 0; c < 8; c++){
                        if (isWhite){
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
    }

    private void mainLoop() {
        chessBoard.addMouseListener(new MouseAdapter() {
            Coordinate from = new Coordinate("b1");
            Coordinate to = new Coordinate("a1");
            
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = e.getX();
                int c = e.getY();
                Coordinate coordinate = getCoordinate(r, c);
                if (!hasClicked){
                    from = coordinate;
                    hasClicked = !hasClicked;
                }
                else{
                    to = coordinate;
                    move(from, to);
                    hasClicked = !hasClicked;
                }
            }

            private Coordinate getCoordinate(int r, int c) {
                int numR = r/squareSize;
                int numC = c/squareSize;
                char file = 'a';  // Just because Java says file and rank might not be initialised. But feels non-ideal
                char rank = '2';

                if  (numR == 0){
                    file = 'a';
                }
                else if (numR == 1){
                    file = 'b';
                }
                else if (numR == 2){
                    file = 'c';
                }
                else if (numR == 3){
                    file = 'd';
                }
                else if (numR == 4){
                    file = 'e';
                }
                else if (numR == 5){
                    file = 'f';
                }
                else if (numR == 6){
                    file = 'g';
                }
                else if (numR == 7){
                    file = 'h';
                }

                if  (numC == 0){
                    rank = '8';
                }
                else if (numC == 1){
                    rank = '7';
                }
                else if (numC == 2){
                    rank = '6';
                }
                else if (numC == 3){
                    rank = '5';
                }
                else if (numC == 4){
                    rank = '4';
                }
                else if (numC == 5){
                    rank = '3';
                }
                else if (numC == 6){
                    rank = '2';
                }
                else if (numC == 7){
                    rank = '1';
                }
                char [] chars = {file, rank};
                String coordinateString = new String(chars);
                return new Coordinate(coordinateString);
            }
        });
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
        pieces.add(new PiecePair("wR", new Coordinate("a1")));
        pieces.add(new PiecePair("wN", new Coordinate("b1")));
        pieces.add(new PiecePair("wB", new Coordinate("c1")));
        pieces.add(new PiecePair("wQ", new Coordinate("d1")));
        pieces.add(new PiecePair("wK", new Coordinate("e1")));
        pieces.add(new PiecePair("wB", new Coordinate("f1")));
        pieces.add(new PiecePair("wN", new Coordinate("g1")));
        pieces.add(new PiecePair("wR", new Coordinate("h1")));
        pieces.add(new PiecePair("wP", new Coordinate("a2")));
        pieces.add(new PiecePair("wP", new Coordinate("b2")));
        pieces.add(new PiecePair("wP", new Coordinate("c2")));
        pieces.add(new PiecePair("wP", new Coordinate("d2")));
        pieces.add(new PiecePair("wP", new Coordinate("e2")));
        pieces.add(new PiecePair("wP", new Coordinate("f2")));
        pieces.add(new PiecePair("wP", new Coordinate("g2")));
        pieces.add(new PiecePair("wP", new Coordinate("h2")));
        pieces.add(new PiecePair("bR", new Coordinate("a8")));
        pieces.add(new PiecePair("bN", new Coordinate("b8")));
        pieces.add(new PiecePair("bB", new Coordinate("c8")));
        pieces.add(new PiecePair("bQ", new Coordinate("d8")));
        pieces.add(new PiecePair("bK", new Coordinate("e8")));
        pieces.add(new PiecePair("bB", new Coordinate("f8")));
        pieces.add(new PiecePair("bN", new Coordinate("g8")));
        pieces.add(new PiecePair("bR", new Coordinate("h8")));
        pieces.add(new PiecePair("bP", new Coordinate("a7")));
        pieces.add(new PiecePair("bP", new Coordinate("b7")));
        pieces.add(new PiecePair("bP", new Coordinate("c7")));
        pieces.add(new PiecePair("bP", new Coordinate("d7")));
        pieces.add(new PiecePair("bP", new Coordinate("e7")));
        pieces.add(new PiecePair("bP", new Coordinate("f7")));
        pieces.add(new PiecePair("bP", new Coordinate("g7")));
        pieces.add(new PiecePair("bP", new Coordinate("h7")));
        return pieces;
    }

    
    public void addPiece(Coordinate coordinate, String piece, JPanel chessBoard){  // adds chosen piece to choosen square.
        SwingUtilities.invokeLater(() -> {
            pieces.add(new PiecePair(piece, coordinate));
            chessBoard.repaint();});
    }

    public void removePiece(Coordinate coordinate){
        SwingUtilities.invokeLater(() -> {    // I was getting some concurrency problems otherwise because somewhere, I don't know quite when, another thread was created and ArrayList is not threadSafe.
            pieces.removeIf(p -> p.coordinate.equals(coordinate));
            chessBoard.repaint();});
    }

    public void move(Coordinate from, Coordinate to){
        if (!game.isLegalMove(new NumCoordinate(from.coordinate), new NumCoordinate(to.coordinate))){ 
            return; //break the function if move is illeal
            
        }
        game.move(new NumCoordinate(from.coordinate), new NumCoordinate(to.coordinate));
        SwingUtilities.invokeLater(() -> {
            removePiece(from);
            removePiece(to);
            for (PiecePair pp : pieces){
                if (pp.coordinate.equals(from)){
                    addPiece(to, pp.piece, chessBoard);
                }
        }});

    }

    public void chessGame(){
        try {
            Thread.sleep(2000);
            move(new Coordinate("f2"), new Coordinate("f3"));
            addPiece(new Coordinate("f3"), "wP", chessBoard);
            Thread.sleep(2000);
            move(new Coordinate("e7"), new Coordinate("e5"));
            Thread.sleep(2000);
            move(new Coordinate("h2"), new Coordinate("h4"));
            Thread.sleep(2000);
            move(new Coordinate("d8"), new Coordinate("h4"));
            chessBoard.repaint();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
