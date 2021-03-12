import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Square extends JButton implements MouseListener {

    private int position;//0-8
    String text;//X or O  until i add icons

    Square(int position){
        this.position = position;
        this.addMouseListener(this);
        this.text = "-1";
    }



    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(position+" Clicked");

        if(Client.isMyTurn && this.text.equals("-1") && !Client.gameIsOver && Client.opponentIsReady){

            this.text = Client.XorO;
            this.setText(this.text);

            boolean gameOver = checkForWin();

            if (gameOver) System.out.println("game ooooooooooooooover");
            if (!gameOver) System.out.println("game NOT over");

            System.out.println("clicked empty square on my turn");
            Client.isMyTurn = false;
            Client.setBoardTitle("Client, Opponent's Turn, "+Client.XorO);

            try {
                Client.dataOut.writeInt(position);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            if (gameOver){
                try {
                    Client.gameOver();
                    Client.dataOut.writeInt(-1);
                    Client.opponentIsReady = false;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }




    }

    private boolean checkForWin(){

        if (Client.squares.get(0).text == "X" && Client.squares.get(1).text == "X" && Client.squares.get(2).text == "X") return true;
        if (Client.squares.get(3).text == "X" && Client.squares.get(4).text == "X" && Client.squares.get(5).text == "X") return true;
        if (Client.squares.get(6).text == "X" && Client.squares.get(7).text == "X" && Client.squares.get(8).text == "X") return true;

        if (Client.squares.get(0).text == "O" && Client.squares.get(1).text == "O" && Client.squares.get(2).text == "O") return true;
        if (Client.squares.get(3).text == "O" && Client.squares.get(4).text == "O" && Client.squares.get(5).text == "O") return true;
        if (Client.squares.get(6).text == "O" && Client.squares.get(7).text == "O" && Client.squares.get(8).text == "O") return true;

        if (Client.squares.get(0).text == "X" && Client.squares.get(3).text == "X" && Client.squares.get(6).text == "X") return true;
        if (Client.squares.get(1).text == "X" && Client.squares.get(4).text == "X" && Client.squares.get(7).text == "X") return true;
        if (Client.squares.get(2).text == "X" && Client.squares.get(5).text == "X" && Client.squares.get(8).text == "X") return true;

        if (Client.squares.get(0).text == "O" && Client.squares.get(3).text == "O" && Client.squares.get(6).text == "O") return true;
        if (Client.squares.get(1).text == "O" && Client.squares.get(4).text == "O" && Client.squares.get(7).text == "O") return true;
        if (Client.squares.get(2).text == "O" && Client.squares.get(5).text == "O" && Client.squares.get(8).text == "O") return true;


        if (Client.squares.get(0).text == "O" && Client.squares.get(4).text == "O" && Client.squares.get(8).text == "O") return true;
        if (Client.squares.get(2).text == "O" && Client.squares.get(4).text == "O" && Client.squares.get(6).text == "O") return true;

        if (Client.squares.get(0).text == "X" && Client.squares.get(4).text == "X" && Client.squares.get(8).text == "X") return true;
        if (Client.squares.get(2).text == "X" && Client.squares.get(4).text == "X" && Client.squares.get(6).text == "X") return true;

        for (Square asquare:Client.squares) {
            if (asquare.text == "-1") return false;
        }
        return true;
    }





    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) {}
}
