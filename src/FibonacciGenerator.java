import java.util.ArrayList;
import java.util.List;

// generator de numere fibonacci in functie de pozitie
public class FibonacciGenerator {
    private static List<Integer> fibNumbers;
    public synchronized Integer  getNumber(Integer val){
            generateNumbers(val -1);
        return fibNumbers.get(val-1);
    }

    private void generateNumbers(Integer val) {
        Integer lastCreated = fibNumbers.size() -1;
        while(lastCreated < val){
            fibNumbers.add(fibNumbers.get(lastCreated) + fibNumbers.get(lastCreated - 1) );
            lastCreated++;
        }
    }

    public FibonacciGenerator() {
        fibNumbers = new ArrayList<>();
        fibNumbers.add(1);
        fibNumbers.add(2);
        fibNumbers.add(3);
        fibNumbers.add(5);
    }
}
