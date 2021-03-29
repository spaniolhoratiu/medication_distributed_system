package ro.tuc.ds2020;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.tools.jsonrpc.JsonRpcClient;
import ro.tuc.ds2020.gui.GUI;

import java.net.URI;

@SpringBootApplication
public class SpaniolHoratiuAssignment3PillDispenserApplication {

    public static final String QUEUE = "json-rpc-queue";
    public static Connection connection;
    public static Channel channel;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpaniolHoratiuAssignment3PillDispenserApplication.class, args);
        disableHeadlessProperty();
        startGui();

        connectToQueue();
    }

    private static void disableHeadlessProperty()
    {
        System.setProperty("java.awt.headless", "false");
    }

    private static void startGui() {
        GUI gui = new GUI();
        gui.start();
    }

    @SneakyThrows
    private static void connectToQueue(){
        URI uri = new URI(	"amqps://xlsdmhwt:9dypdfDEmDarR6rIaIpsO66t2VzZ61Eh@chinook.rmq.cloudamqp.com/xlsdmhwt");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE, false, false, false, null);
    }



    @SneakyThrows
    private static void testRPC()
    {
        // Create JSON-RPC client
        JsonRpcClient client = new JsonRpcClient(channel, "", QUEUE);

        // Call one of the remote methods provided by the JSON-RPC server
        String method = "add";
        Integer[] arguments = {3, 4};
        Integer result = (Integer) client.call(method, arguments);

        System.out.println("Getting result: " + result);

        // Note:
        // Call client.getServiceDescription() to get a ServiceDescription that
        // describes all the remote methods that the JSON-RPC server provides.

        client.close();
        channel.close();
        connection.close();
    }
}
