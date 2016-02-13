package async_example;

/**
 * @author Igor Polevoy on 2/12/16.
 */
public class ExampleCommand extends org.javalite.async.Command {

    private String content;

    public ExampleCommand(String content) {
        this.content = content;
    }

    @Override
    public void execute() {
        System.out.println("Executing: "+ this);
    }
}
