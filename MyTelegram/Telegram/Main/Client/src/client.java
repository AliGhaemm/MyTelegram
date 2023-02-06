import java.io.*;
import java.net.*;
import java.nio.*;
import java.security.spec.*;

public class client {

    private final Socket socket;
    private final BufferedReader reader;
    private final  DataInputStream socketReader;
    private final DataOutputStream outputStream;

    public client(String address, int port) throws IOException {
        try{
            socket = new Socket(address, port);
            System.out.println("Connected. now you can write your request!");
            reader = new BufferedReader(new InputStreamReader(System.in));
            outputStream = new DataOutputStream(socket.getOutputStream());
            socketReader = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean a = true;
        while (a) {
            switch (socketReader.readInt()){
                case -1 -> {
                    try {
                        System.out.print("----->>>>  ");
                        String line = reader.readLine();
                        try {
                            outputStream.writeInt(Integer.parseInt(line));
                        } catch (Exception e){
                            System.out.println("in exception");

                            outputStream.writeUTF(line);
                        }
                    } catch (Exception e) {
                        System.out.println("Exception occurred!");
                        e.printStackTrace();
                    }
                }
                case 1 -> {
                    try {
                        System.out.println(socketReader.readUTF());
                        System.out.print("----->>>>  ");
                        String line = reader.readLine();
                        try {
                            outputStream.writeInt(Integer.parseInt(line));
                        } catch (Exception e){
                            System.out.println("in exception");
                            outputStream.writeUTF(line);
                        }
                    } catch (Exception e) {
                        System.out.println("Exception occurred!");
                        e.printStackTrace();
                    }
                }
                case 0 -> {
                    try {
                        System.out.println(socketReader.readUTF());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("You are out now!");
                    a = false;
                }
            }
        }
//        while (true){
//            try {
//                System.out.print("----->>>>  ");
//                line = reader.readLine();
//                try {
//                    outputStream.writeInt(Integer.parseInt(line));
//                } catch (Exception e){
//                    outputStream.writeUTF(line);
//                }
//                System.out.println(socketReader.readUTF());
//            } catch (Exception e) {
//                System.out.println("Exception occurred!");
//                e.printStackTrace();
//                break;
//            }
//        }
        try {
            reader.close();
            outputStream.close();
            socket.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        client client = new client("127.0.0.1", 11111);
    }
}
