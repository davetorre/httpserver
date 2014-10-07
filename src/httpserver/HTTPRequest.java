package httpserver;

import java.util.Map;

public class HTTPRequest {
    public String routeName;
    public String parameters;
    public Map<String, String> headers;
    public byte[] body;

    public HTTPRequest(String routeName, Map<String, String> headers, byte[] body) {
        this.routeName = routeName;
        this.parameters = "";
        this.headers = headers;
        this.body = body;
    }

    public HTTPRequest(String routeName, String parameters, Map<String, String> headers, byte[] body) {
        this.routeName = routeName;
        this.parameters = parameters;
        this.headers = headers;
        this.body = body;
    }
}
