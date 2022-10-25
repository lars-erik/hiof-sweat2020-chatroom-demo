import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void decoration() {
        GenericApplicationContext ctx = new GenericApplicationContext();
        ctx.registerBean(AbstractionFactory.class, () -> (input) -> {
            Abstraction abstraction;
            if (input == "Lars-Erik") {
                abstraction = ctx.getBean(FancyConcrete.class);
            } else {
                abstraction = ctx.getBean(Concrete.class);
            }
            return new Decorated(abstraction);
        });

        ctx.registerBean(Concrete.class);
        ctx.registerBean(FancyConcrete.class);
        ctx.registerBean(Client.class);
        ctx.refresh();

        Client client = ctx.getBean(Client.class);
        assertEquals("Hello, Mats! What a wonderful day it is.", client.doit("Mats"));
        assertEquals("Well hello there, Lars-Erik! What a wonderful day it is.", client.doit("Lars-Erik"));
    }
}

interface Abstraction {
    String greet(String input);
}

interface AbstractionFactory {
    Abstraction get(String input);
}

class Decorated implements Abstraction {
    public Abstraction inner;

    public Decorated(Abstraction inner) {
        this.inner = inner;
    }

    @Override
    public String greet(String input) {
        return String.format("%s What a wonderful day it is.", inner.greet(input));
    }
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