package httpserver;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.io.*;
import java.net.*;

public class ClientTest {
    DefaultRouter router;
    Client client;

    @Before
    public void setUp() throws Exception {
        router = new DefaultRouter();
        router.addRoute("GET /", () -> new TestResponder());
        client = new Client(new Socket(), router);
    }

    @Test
    public void testReadAndRespondToRoute() throws Exception {
        String body = "Body";
        int contentLength = body.getBytes().length;
        String s = "GET / HTTP/1.1\nContent-Length: " + contentLength + "\n\n" + body;
        InputStream in = new ByteArrayInputStream(s.getBytes());
        OutputStream out = new ByteArrayOutputStream();
        String expected = "HTTP/1.1 200 OK\nContent-Length: " + contentLength + "\n\n" + body;
        client.readAndRespond(in, out);

        assertTrue(expected.equals(out.toString()));
    }

    @Test
    public void testReadAndRespondToNonRoute() throws Exception {
        InputStream in = new ByteArrayInputStream("GET /nothing HTTP/1.1\n\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        String expected = "HTTP/1.1 404 Not Found\n\n";
        client.readAndRespond(in, out);

        assertTrue(expected.equals(out.toString()));
    }

}
