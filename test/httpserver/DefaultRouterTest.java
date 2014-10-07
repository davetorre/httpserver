package httpserver;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class DefaultRouterTest {
    @Test
    public void testAddRouteGetResponder() throws Exception {
        DefaultRouter router = new DefaultRouter();
        TestResponder responder = new TestResponder();
        router.addRoute("GET /testing", () -> responder);
        assertTrue(responder.equals(router.getResponder("GET /testing")));
    }

    @Test
    public void testGetResponderUnknownRouteName() throws Exception {
        DefaultRouter router = new DefaultRouter();
        Class classOfResponder = router.getResponder("GET /unknown").getClass();
        assertTrue(NotFoundResponder.class.equals(classOfResponder));
    }
}
