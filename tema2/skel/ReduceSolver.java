import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class ReduceSolver implements Callable<FileData> {
    private String filename;
    private List<Pack> packs;
    FibonacciGenerator fibonacciGenerator;

    public ReduceSolver(String filename, List<Pack> packs, FibonacciGenerator fibonacciGenerator) {
        this.filename = filename;
        this.packs = packs;
        this.fibonacciGenerator = fibonacciGenerator;
    }
// string ce returneaza lungimea maxima a string-urilor primite si numarul de cuvinte cu lungimea maxima
    private String getMaximalWords(List<String> words) {
        Integer numberOfMaximalWords = 0;
        long maxLen = 0L;
        for (String str : words) {
            if (maxLen < str.length()) {
                numberOfMaximalWords = 1;
                maxLen = str.length();
            } else if (maxLen == str.length()) {
                numberOfMaximalWords++;
            }
        }
        return maxLen+ "," + numberOfMaximalWords;
    }

    @Override
    public FileData call() throws Exception {
//        preluarea pachetelor ce filename-ul corespunzator
        List<Pack> filePacks = packs.stream()
                .filter(pack -> pack.getFileName().equals(filename))
                .collect(Collectors.toList());
//      preluarea map-urilor din file-ul respectiv
        Map<Integer, Long> map = filePacks.stream()
                .map(Pack::getResult)
                .flatMap(m -> m.entrySet().stream())
                .collect(groupingBy(Map.Entry::getKey, summingLong(Map.Entry::getValue)));
//        crearea unei liste cu cuvinte maximale
        List<String> listOfMaximalWords = filePacks.stream()
                .map(Pack::getMaximalWords)
                .flatMap(Collection::stream)
                .filter(val -> !val.equals(""))
                .collect(Collectors.toList());
//        calcularea rangului fisierului
        Float rank = calculateRank(map);
//        returnarea cumului de date prelucrate
        return new FileData(filename, rank, getMaximalWords(listOfMaximalWords));
    }

// calcularea rangului unui fisier in functie de map
    private Float calculateRank(Map<Integer, Long> map) {
        Long total = map.values().stream().mapToLong(Long::longValue).sum();
        Float sum = 0.0f;
        for(var key : map.keySet()){
            Long val = map.get(key);
            sum += fibonacciGenerator.getNumber(key) * val;
        }
        sum = sum / total;
        return sum;
    }
}
