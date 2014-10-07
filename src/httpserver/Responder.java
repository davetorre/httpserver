package httpserver;

public interface Responder {

    public HTTPResponse respond(HTTPRequest request);
}
