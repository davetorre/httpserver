package httpserver;

import java.io.*;
import java.util.*;

public class RequestReader {

    public boolean isNotBlank(String s) {
        return !s.trim().isEmpty();
    }

    public int getBodySize(Map<String, String> headers) {
        int size = 0;

        if (headers.containsKey("Content-Length")) {
            size = Integer.parseInt(headers.get("Content-Length"));
        }

        return size;
    }

    public String readRequestLine(BufferedReader br) throws IOException {
        return br.readLine();
    }

    public String parseRouteName(String requestLine) throws IOException {
        int nameEnd;
        if (requestLine.contains("?")) {
            nameEnd = requestLine.indexOf("?");
        } else {
            nameEnd = requestLine.indexOf("HTTP/1.1");
        }
        return requestLine.substring(0, nameEnd).trim();
    }

    public String parseParams(String requestLine) throws IOException {
        String params = "";
        int start;
        int end;
        if (requestLine.contains("?")) {
            start = requestLine.indexOf("?") + 1;
            end = requestLine.indexOf("HTTP/1.1");
            params = requestLine.substring(start, end).trim();
        }
        return params;
    }

    public Map<String, String> readHeaders(BufferedReader br) throws IOException {
        String nextLine;
        Map<String, String> headers = new HashMap<String, String>();

        while (isNotBlank((nextLine = br.readLine()))) {
            String[] header = nextLine.split(":");
            headers.put(header[0].trim(), header[1].trim());
        }

        return headers;
    }

    public byte[] readBody(BufferedReader br, int size) throws IOException {
        byte[] body = {};
        int nextChar;

        if (size > 0) {
            body = new byte[size];
            for (int i = 0; i < size; i++) {
                if ((nextChar = br.read()) == -1) {
                    body = Arrays.copyOfRange(body, 0, i);
                    break;
                }
                body[i] = (byte)nextChar;
            }
        }

        return body;
    }

    public HTTPRequest read(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String requestLine = readRequestLine(br);
        String routeName = parseRouteName(requestLine);
        String params = parseParams(requestLine);

        Map<String, String> headers = readHeaders(br);

        int bodySize = getBodySize(headers);
        byte[] body = readBody(br, bodySize);

        return new HTTPRequest(routeName, params, headers, body);
    }
}
