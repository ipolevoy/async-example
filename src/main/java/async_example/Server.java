package async_example;

import org.javalite.async.Async;
import org.javalite.async.CommandListener;
import org.javalite.async.QueueConfig;

/**
 * @author Igor Polevoy on 2/12/16.
 */
public class Server {

    public static void main(String[] args){
        Async async = new Async("artemis_data", false, new QueueConfig("Mail", CommandListener.class, 30, true));
        async.configureNetty(Connection.HOST, Connection.PORT);
        async.start();
    }
}
