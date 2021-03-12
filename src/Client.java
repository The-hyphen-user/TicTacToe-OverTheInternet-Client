//import jdk.internal.access.JavaIOFileDescriptorAccess;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static DataOutputStream dataOut;
    public static Board board;
    public static boolean isMyTurn;
    public static ArrayList<Square> squares = new ArrayList<>(10);
    public static String XorO;
    public static String opponentXorO;
    private static boolean clientGoesFirst;
    public static boolean gameIsOver = false;
    private static GameOver gameOverJFrame;
    public static boolean opponentIsReady = true;
    private static Listener listener;
    private static DataInputStream  dataIn;
    private static Socket socket;

    public static void main(String[] args) throws IOException {


        String IP;
/*
        JFrame IPWindow = new JFrame();
        IPWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        IPWindow.setTitle("Enter IP Address of Server");
        IPWindow.setVisible(true);
        JTextField IPTextField = new JTextField();
        IPWindow.add(new JPanel().add(IPTextField));
 */

        System.out.println("Enter IP:");
        Scanner sc = new Scanner(System.in);
        IP = sc.next();
        //IPWindow.add(IPTextField);

        //IPTextField.addAct

        socket = new Socket(IP, 9090);
        //socket = new Socket("localhost", 9090);


        dataIn = new DataInputStream(socket.getInputStream());

        clientGoesFirst = (boolean) dataIn.readBoolean();
        isMyTurn = clientGoesFirst;
        boolean clientIsX = (boolean) dataIn.readBoolean();
        if (clientIsX){
            XorO = "X";
            opponentXorO = "O";
        } else{
            XorO = "O";
            opponentXorO = "X";
        }

        listener = new Listener(socket);
        new Thread(listener).start();

        dataOut = new DataOutputStream(socket.getOutputStream());




        makeBoard();









    }//end of main

    public static void CloseConnection() throws IOException {
        System.out.println("closing connection");
        listener.stop();

        dataIn.close();
        dataOut.close();
        socket.close();
    }

    public static void gameOver(){
        gameIsOver = true;
        gameOverJFrame = new GameOver();
    }

    public static void reset(){
        gameOverJFrame.dispose();
        gameIsOver = false;
        try {
            dataOut.writeInt(10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Game Resetting");
        for (Square asquare: squares) {
            asquare.text = "-1";
            asquare.setText("");
            clientGoesFirst = !clientGoesFirst;
            if (clientGoesFirst) isMyTurn = true;
            if (!clientGoesFirst) isMyTurn = false;
        }
        board.repaint();
    }

    public static void makeBoard(){
        if (XorO == "X") board = new Board(true, true, clientGoesFirst);
        if (XorO == "O") board = new Board(true, false, clientGoesFirst);
        board.setLayout(new GridLayout(3,3));
        for (int i = 0; i < 9; i++) {//0-8, feels bad on a 9 grid
            Square square = new Square(i);
            squares.add(square);
            board.add(square);
        }
        board.repaint();
    }

    public static void setBoardTitle(String title) { board.setTitle(title); }

    public static void OpponentMoved(int num){

        if (num == -1){
            Client.gameOver();
            opponentIsReady = false;
        } else if (num == 10) {
            opponentIsReady = true;
        } else if (num == 11) {
            System.out.println("default message recieved from listener");
        } else {
            System.out.println("Opponent moved " + num);
            isMyTurn = true;
            board.setTitle("Client, Your Turn, " + XorO);
            squares.get(num).setText(opponentXorO);
            squares.get(num).text = opponentXorO;
        }

    }
}
