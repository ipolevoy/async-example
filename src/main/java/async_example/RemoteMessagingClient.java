package async_example;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.TransportConstants;
import org.javalite.async.Command;

import javax.jms.*;
import javax.jms.Connection;
import javax.xml.soap.Text;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.javalite.common.Util.blank;

/**
 * @author Igor Polevoy on 2/12/16.
 */
public class RemoteMessagingClient implements AutoCloseable {

    private final ConnectionFactory connectionFactory = createConnectionFactory();
    private boolean binaryMode = true;
    private Connection connection;

    public RemoteMessagingClient(boolean binaryMode) throws JMSException {
        this.binaryMode = binaryMode;
        connection = createConnection();
    }

    public void send(ExampleCommand command, String queueName, String filter) throws JMSException, IOException {
        try (Session session = connection.createSession()) {

            Message message;
            if(binaryMode){
                message  = session.createBytesMessage();
                ((BytesMessage)message).writeBytes(command.toBytes());
            }else{
                message = session.createTextMessage(command.toXml());
            }

            if (filter != null && !blank(filter)) {
                String[] parts = filter.split("\\s*=\\s*");
                message.setStringProperty(parts[0], parts[1]);
            }
            try (MessageProducer p = session.createProducer(ActiveMQJMSClient.createQueue(queueName))) {
                p.send(message, DeliveryMode.PERSISTENT, 4, 0);
            }
        }
    }

    public Connection createConnection() throws JMSException {
        return connectionFactory.createConnection();
    }

    private static ConnectionFactory createConnectionFactory() {
        Map<String, Object> params = new HashMap<>();
        params.put(TransportConstants.HOST_PROP_NAME, async_example.Connection.HOST);
        params.put(TransportConstants.PORT_PROP_NAME, async_example.Connection.PORT);
        return ActiveMQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF,
                new TransportConfiguration(NettyConnectorFactory.class.getName(), params));
    }


    @Override
    public void close() throws Exception {
        connection.close();
    }
}
