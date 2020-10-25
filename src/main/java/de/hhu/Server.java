package de.hhu;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.logging.Logger;

public class Server {

    private int port;
    private Logger logger;
    private boolean serverIsRunning;

    final String serverName = "\n\n[SERVER]: ";

    public Server(int port, Logger logger) {
        this.port = port;
        this.logger = logger;
        serverIsRunning = false;
        listen();
    }

    private void listen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (serverIsRunning) {
                    try {
                        logger.info(serverName + "Trying to start server");
                        ServerSocket serverSocket = new ServerSocket(port);
                        logger.info(serverName + "Waiting for client connection");
                        Socket clientSocket = serverSocket.accept();
                        logClientConnection(clientSocket.getRemoteSocketAddress());

                        // TODO only first test implementation
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
//                        Scanner scanner = new Scanner(bufferedReader);
//                        if (scanner.hasNextLine()) {
//                            System.out.println(serverName + "Receiving data from " + clientSocket.getRemoteSocketAddress());
//                            System.out.println(serverName + scanner.nextLine());
//                        }
//                        scanner.close();
                        JSONTokener tokenizer = new JSONTokener(bufferedReader);
                        JSONObject json = new JSONObject(tokenizer);

                        clientSocket.close();
                        serverSocket.close();

                    } catch (IOException e) {
                        logger.warning(serverName + "Failed to start server");
                        logger.warning(Arrays.toString(e.getStackTrace()));
                    }
                }
            }
        }
        ).start();
    }

    public void start(){
        serverIsRunning = true;
    }

    public void stop(){
        serverIsRunning = false;
    }

    private void logClientConnection(SocketAddress clientAddress) {
        String status = String.format("Get connection from %s", clientAddress);
        logger.info(serverName+status);
    }
}
