import java.awt.Color;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

//This is based on https://www.youtube.com/watch?v=vO7wHV0HB8w

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

    public BoardView() throws IOException{
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
                g.drawImage(bB, 0, 0, 64, 64, this);
                g.drawImage(bK, 64, 0, 64, 64, this);
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
        try {
            Thread.sleep(2000);
        }catch(Exception e){
            System.out.println("din mamma");
        }
        display(0, 0, wK, chessBoard);
        chessBoard.repaint();
    }
    public static void main(String[] args) throws Exception {
        BoardView bb = new BoardView();
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

    
    public void display(int r, int c, Image piece, JPanel chessBoard){
        chessBoard.imageUpdate(piece, r, 7, c, squareSize, squareSize);
        System.out.println("din mamma");
    }
}
