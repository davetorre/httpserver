package httpserver;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

public class RequestReaderTest {

    public static RequestReader reader = new RequestReader();

    public static Map<String, String> sampleHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Header1", "things");
        headers.put("Content-Length", "25");
        headers.put("Header3", "more things");

        return headers;
    }

    public BufferedReader getBufferedReader(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        return new BufferedReader(new InputStreamReader(in));
    }

    @Test
    public void testGetBodySize() throws Exception {
        assertEquals(25, reader.getBodySize(sampleHeaders()));
    }

    @Test
    public void testParseRouteName() throws Exception {
        String requestLine = "GET /thing?params^&* HTTP/1.1\n";
        assertTrue("GET /thing".equals(reader.parseRouteName(requestLine)));
    }

    @Test
    public void testParseParams() throws Exception {
        String requestLine = "GET /thing?params^&* HTTP/1.1\n";
        assertTrue("params^&*".equals(reader.parseParams(requestLine)));
    }

    @Test
    public void testReadHeaders() throws Exception {
        BufferedReader br = getBufferedReader(
                "Header1:things\nContent-Length: 25\nHeader3: more things\n\n[body]");

        assertTrue(sampleHeaders().equals(reader.readHeaders(br)));
        assertEquals(((int)'['), (br.read())); // the next char should be '['
    }

    @Test
    public void testReadBody() throws Exception {
        BufferedReader br = getBufferedReader("[body]");
        byte[] expected = "[body]".getBytes();
        byte[] actual = reader.readBody(br, expected.length);

        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void testReadBodyLengthTooSmall() throws Exception {
        BufferedReader br = getBufferedReader("[body]");
        byte[] expected = "[bod".getBytes();
        byte[] actual = reader.readBody(br, 4);

        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void testReadBodyLengthTooBig() throws Exception {
        BufferedReader br = getBufferedReader("[body]");
        byte[] expected = "[body]".getBytes();
        byte[] actual = reader.readBody(br, 8);

        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    public void testReadMessage() throws Exception {
        String s = "GET /?some-params%%% HTTP/1.1\nContent-Length:6\n\n[body]";
        InputStream in = new ByteArrayInputStream(s.getBytes());

        String routeName = "GET /";
        String params = "some-params%%%";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Length", "6");
        String body = "[body]";

        HTTPRequest request = reader.read(in);

        assertTrue(request.routeName.equals(routeName));
        assertTrue(request.parameters.equals(params));
        assertTrue(request.headers.equals(headers));
        assertTrue(body.equals(new String(request.body)));
    }
}
