package notation;

public class Main {
    public static void main(String[] args) {
        Person person = NotationProxyFactory.createProxy(new Person("a", 1));

        System.out.println(person);
    }
}

