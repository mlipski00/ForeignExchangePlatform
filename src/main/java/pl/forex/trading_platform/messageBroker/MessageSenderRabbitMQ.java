package pl.forex.trading_platform.messageBroker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.forex.trading_platform.service.LoadQuotations;

@Component
public class MessageSenderRabbitMQ {

    @Autowired
    private LoadQuotations loadQuotations;

    private final static String QUEUE_NAME = "hello";

    public void SendMessageToBroker() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = loadQuotations.loadLastQuotations().toString();
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));

        channel.close();
        connection.close();
    }
}
