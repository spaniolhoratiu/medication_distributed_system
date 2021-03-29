package ro.tuc.ds2020.jsonrpc;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.tools.jsonrpc.JsonRpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.net.URI;

@Configuration
public class MyJsonRpcConfig {
    private static final String QUEUE = "json-rpc-queue";

    private static JsonRpcServer server;


    public static void startServer() throws Exception {
        URI uri = new URI(	"amqps://xlsdmhwt:9dypdfDEmDarR6rIaIpsO66t2VzZ61Eh@chinook.rmq.cloudamqp.com/xlsdmhwt");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        Channel channel = factory.newConnection().createChannel();
        channel.queueDeclare(QUEUE, false, false, false, null);
        // Create JSON-RPC server providing remote methods defined in Service
        server = new JsonRpcServer(channel,
                QUEUE,
                Service.class,
                new ServiceImpl());

        // Start listening for RPC requests from client
            server.mainloop();
    }

    public static void stopServer() {
        server.terminateMainloop();
    }
}
