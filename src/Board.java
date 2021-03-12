import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class Board extends JFrame implements WindowListener {

    Board(boolean isClient, boolean isX, boolean isMyTurn){
        setDefaultCloseOperation(Board.EXIT_ON_CLOSE);
        setSize(500,500);
        String whoAmI;
        String XorO;
        String whosTurn;
        if(isClient) {
            whoAmI = "Client, ";
        } else {
            whoAmI = "Server, ";
        }
        if (isX) {
            XorO = "X";
        } else {
            XorO = "O";
        }
        if (isMyTurn){
            whosTurn = "Your Turn, ";
        } else {
            whosTurn = "Opponent's Turn, ";
        }
        setTitle(whoAmI + whosTurn + XorO);
        setVisible(true);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("client board closing");
        try {
            Client.CloseConnection();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }



    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
