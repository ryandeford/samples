public class ConsoleApp {

    public static void main(String[] args) {
        System.out.println("Starting sample application created in Java");

        ServiceInterface service = new DefaultServiceImpl();
        ServiceResult serviceResult = service.executeRequest();

        System.out.printf("Result: %s%n", serviceResult);
    }
}
