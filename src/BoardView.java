import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BoardView {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Chess");
        frame.setBounds(10, 10, 512, 512);
        JPanel chessBoard = new JPanel(){
            public void paint(java.awt.Graphics g) {
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
            };
        };
        frame.add(chessBoard);
        frame.setVisible(true);
    }
}
