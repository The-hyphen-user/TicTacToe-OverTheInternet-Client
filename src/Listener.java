import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Listener implements Runnable{

    private DataInputStream dataIn;
    private boolean exit;

    public Listener (Socket socket)throws IOException {
        dataIn = new DataInputStream(socket.getInputStream());
        exit = false;
    }

    @Override
    public void run() {

        try {
            while (!exit) {
                int x = 11;
                try {
                    x = (int) dataIn.readInt();
                } catch (IOException e) {
                    e.printStackTrace();
                    exit = true;
                    Client.board.setTitle("Server Disconnected");
                }
                Client.OpponentMoved(x);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("close after try/finally");
        }


    }

    public void stop() {
        System.out.println("client listener stop() called");
        exit = true;
    }
}
