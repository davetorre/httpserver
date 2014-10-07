package httpserver;

import java.util.Map;

public class HTTPResponse {
    public String statusLine;
    public Map<String, String> headers;
    public byte[] body;

    public HTTPResponse(String statusLine, Map<String, String> headers, byte[] body) {

        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }
}
