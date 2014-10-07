package httpserver;

import java.util.HashMap;

public class DefaultRouter implements Router {
    public HashMap<String, ResponderCreator> routes = new HashMap<>();

    public void addRoute(String name, ResponderCreator rc) {
        routes.put(name, rc);
    }

    public synchronized Responder getResponder(String routeName) {
        Responder responder = new NotFoundResponder();

        if (routes.containsKey(routeName)) {
            responder = routes.get(routeName).create();
        }

        return responder;
    }
}
