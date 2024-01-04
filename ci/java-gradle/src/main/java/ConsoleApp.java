import io.ryandeford.samples.data.SampleResultData;
import io.ryandeford.samples.implementations.SampleServiceDefaultImpl;

public class ConsoleApp {
    public static void main(String[] args) {
        SampleResultData result = new SampleServiceDefaultImpl().executeSampleAction();
        System.out.println(result);
    }
}
