package client;

import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.http.HttpTimeoutException;
import java.util.Random;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.delay;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;
import static com.xebialabs.restito.semantics.Condition.startsWithUri;
import static org.junit.Assert.assertEquals;

public class VKSearchClientWithStubServerTest {
    private static final int PORT = 20000 + new Random().nextInt(45535);

    private final VKSearchClient client = new VKSearchClient();
    private StubServer stubServer = null;

    @Before
    public void setUp() {
        stubServer = new StubServer(PORT).run();
    }

    @After
    public void tearDown() {
        stubServer.stop();
    }

    @Test
    public void fetchesData() {
        String response = "pong";
        whenHttp(stubServer)
                .match(
                        method(Method.GET),
                        startsWithUri("/ping"))
                .then(stringContent(response));

        try {
            String data = client.fetchData("http://localhost:" + PORT + "/ping");
            assertEquals(response, data);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e.getMessage());
        }
    }

    @Test(expected = HttpTimeoutException.class)
    public void fetchesDataWithNotFoundError() throws IOException, InterruptedException {
        whenHttp(stubServer)
                .match(method(Method.GET), startsWithUri("/ping"))
                .then(delay(11000));

        client.fetchData("http://localhost:" + PORT + "/ping");
    }
}
