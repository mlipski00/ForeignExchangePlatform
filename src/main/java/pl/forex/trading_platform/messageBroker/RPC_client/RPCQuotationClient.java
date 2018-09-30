package pl.forex.trading_platform.messageBroker.RPC_client;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

@Component
public class RPCQuotationClient {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";

    public RPCQuotationClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public String call(String message) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);

        String ctag = channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(corrId)) {
                    response.offer(new String(body, "UTF-8"));
                }
            }
        });

        String result = response.take();
        channel.basicCancel(ctag);
        return result;
    }

    public void close() throws IOException {
        connection.close();
    }

    public static void run(int quotetionsSize, String quotetionsBody) {
        RPCQuotationClient qutationRpc = null;
        String response = null;
        try {
            qutationRpc = new RPCQuotationClient();

                System.out.println(" [x] Requesting sending (" + quotetionsSize + ") qoutations RPC message broker.");
                response = qutationRpc.call(quotetionsBody);
                System.out.println(" [.] Got response: '" + response + "'");

        } catch (IOException | TimeoutException | InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            if (qutationRpc != null) {
                try {
                    qutationRpc.close();
                } catch (IOException _ignore) {
                }
            }
        }
    }
}
