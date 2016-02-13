package async_example;

import javax.jms.JMSException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Igor Polevoy on 2/12/16.
 */
public class Client {

    private static final Integer THREAD_COUNT = 10;
    private static final Integer MESSAGE_COUNT = 10000;
    private static final boolean BINARY_MODE = false;


    public static void main(String[] args) throws IOException, JMSException {

        String content = new String (Files.readAllBytes(Paths.get("src/main/resources/content.txt")));

        for(int x = 0; x < THREAD_COUNT; x++){
            Thread t = new Thread(){
                @Override
                public void run() {
                    try(RemoteMessagingClient client = new RemoteMessagingClient(BINARY_MODE)) {
                        System.out.println("Starting thread: " + getId());
                        for (int i = 0; i < MESSAGE_COUNT; i++){
                            try {
                                client.send(new ExampleCommand(content), "Mail", null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
    }
}
