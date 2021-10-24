import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes={Concrete.class})
@ExtendWith(SpringExtension.class)
public class IOC_demo {

    @Test
    public void getting_automatic_injection() {
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.registerBean(Concrete.class);
        ctx.registerBean(Client.class);
        ctx.refresh();

        Client client = ctx.getBean(Client.class);
        client.doit();
    }

}

interface Abstraction {
    void greet();
}

class Concrete implements Abstraction {
    @Override
    public void greet() {
        System.out.println("Hello!");
    }
}

class Client {
    private Abstraction abstraction;

    public Client(Abstraction abstraction) {
        this.abstraction = abstraction;
    }

    public void doit() {
        abstraction.greet();
    }
}