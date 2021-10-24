import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes={Concrete.class})
@ExtendWith(SpringExtension.class)
public class IOC_demo {

    @Test
    public void getting_automatic_injection() {
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.registerBean(AbstractionFactory.class, () -> (input) -> ctx.getBean(Concrete.class));
        ctx.registerBean(Concrete.class);
        ctx.registerBean(Client.class);
        ctx.refresh();

        Client client = ctx.getBean(Client.class);
        assertEquals("Hello, Lars-Erik!", client.doit("Lars-Erik"));
    }

    @Test
    public void getting_conditional_injection() {
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.registerBean(AbstractionFactory.class, () -> (input) -> {
            if (input == "Lars-Erik") {
                return ctx.getBean(FancyConcrete.class);
            } else {
                return ctx.getBean(Concrete.class);
            }
        });
        ctx.registerBean(Concrete.class);
        ctx.registerBean(FancyConcrete.class);
        ctx.registerBean(Client.class);
        ctx.refresh();

        Client client = ctx.getBean(Client.class);
        assertEquals("Hello, Mats!", client.doit("Mats"));
        assertEquals("Well hello there, Lars-Erik!", client.doit("Lars-Erik"));
    }
}

interface Abstraction {
    String greet(String input);
}

interface AbstractionFactory {
    Abstraction get(String input);
}

class Concrete implements Abstraction {
    @Override
    public String greet(String input) {
        return String.format("Hello, %s!", input);
    }
}

class FancyConcrete implements Abstraction {
    @Override
    public String greet(String input) {
        return String.format("Well hello there, %s!", input);
    }
}

class Client {
    private Abstraction abstraction;
    private AbstractionFactory abstractionFactory;

    public Client(AbstractionFactory abstractionFactory) {
        this.abstractionFactory = abstractionFactory;
    }

    public String doit(String input) {
        Abstraction abstraction = abstractionFactory.get(input);
        String greeting = abstraction.greet(input);
        return greeting;
    }
}