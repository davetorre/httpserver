package httpserver;

import java.util.HashMap;

public class NotFoundResponder implements Responder {
    public HTTPResponse respond(HTTPRequest request) {
        return new HTTPResponse("HTTP/1.1 404 Not Found",
                new HashMap<String, String>(),
                new byte[0]);
    }
}
