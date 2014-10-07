package httpserver;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    private Router router;
    private boolean isServing = false;
    ExecutorService pool;

    public void serve(int port) throws IOException {
        isServing = true;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket;
        pool = Executors.newFixedThreadPool(25);

        while (isServing) {
            clientSocket = serverSocket.accept();
            pool.execute(new Client(clientSocket, router));
        }
    }

    public void stop() {
        pool.shutdown();
        isServing = false;
    }

    public Server(Router router) {
        this.router = router;
    }
}
