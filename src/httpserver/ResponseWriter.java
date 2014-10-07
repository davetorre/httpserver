package httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ResponseWriter {
    public String getHeadersString(Map<String, String> headers) {
        String headersString = "";
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            headersString += entry.getKey() + ": " + entry.getValue() + "\n";
        }

        return headersString;
    }

    public void sendResponse(OutputStream out, HTTPResponse response) throws IOException {
        out.write(response.statusLine.getBytes());
        out.write("\n".getBytes());
        out.write(getHeadersString(response.headers).getBytes());
        out.write("\n".getBytes());
        out.write(response.body);
    }
}
