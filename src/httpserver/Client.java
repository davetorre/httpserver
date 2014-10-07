package httpserver;

import java.io.*;
import java.net.*;

public class Client implements Runnable {

    Socket clientSocket;
    Router router;


    public void readAndRespond(InputStream in, OutputStream out) throws IOException {
        HTTPRequest request = new RequestReader().read(in);
        HTTPResponse response = router.getResponder(request.routeName).respond(request);
        new ResponseWriter().sendResponse(out, response);
    }

    public void run() {
        try {
            readAndRespond(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Client(Socket clientSocket, Router router) {
        this.clientSocket = clientSocket;
        this.router = router;
    }
}
