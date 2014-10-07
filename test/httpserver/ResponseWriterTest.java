package httpserver;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;
import java.io.*;

public class ResponseWriterTest {

    ResponseWriter rw = new ResponseWriter();

    public static HTTPResponse sampleResponse() {
        String statusLine = "HTTP/1.1 200 OK";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Allow", "GET,HEAD");
        byte[] body = "[body]".getBytes();

        return new HTTPResponse(statusLine, headers, body);
    }

    @Test
    public void testGetHeadersString() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Allow", "GET,HEAD");
        headers.put("Header2", "some info");
        String option1 = "Allow: GET,HEAD\nHeader2: some info\n";
        String option2 = "Header2: some info\nAllow: GET,HEAD\n";
        String actual = rw.getHeadersString(headers);

        assertTrue(actual.equals(option1) || actual.equals(option2));
    }

    @Test
    public void testSendResponse() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String expected = "HTTP/1.1 200 OK\nAllow: GET,HEAD\n\n[body]";
        rw.sendResponse(out, sampleResponse());

        assertTrue(expected.equals(new String(out.toByteArray())));
    }


}