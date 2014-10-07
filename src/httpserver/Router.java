package httpserver;

public interface Router {
    public Responder getResponder(String uri);
}
