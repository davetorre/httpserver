package httpserver;

public class TestResponder implements Responder {

    public HTTPResponse respond(HTTPRequest request) {
        return new HTTPResponse("HTTP/1.1 200 OK", request.headers, request.body);
    }
}
