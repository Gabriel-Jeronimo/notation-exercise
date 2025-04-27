package notation;

public class Main {
    public static void main(String[] args) {
        IPerson person = GenericProxy.createProxy(new Person("a", 1), IPerson.class);

        System.out.println(person);
    }
}

