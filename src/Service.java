import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
//    crearea pachetelor initiale
    public List<Pack> createStartingPacks(InputData inputData) throws IOException {
        List<Pack> packs = new ArrayList<>();
        int step = inputData.getFragmentDimension();
        for (String fileName : inputData.getFileNames()) {

            long dim = Files.size(Path.of(fileName));
            for (int i = 0; i < dim - (dim % step); i += step) {
                Pack pack = new Pack();
                pack.setStep(step);
                pack.setFileName(fileName);
                pack.setStart(i);
                packs.add(pack);
            }
            Pack pack = new Pack();
            pack.setStep((int) (dim % step));
            pack.setFileName(fileName);
            pack.setStart((int) (dim - (dim % step)));
            packs.add(pack);
        }
        return packs;
    }

//    crearea task-urilor reduceSolvers
    public List<ReduceSolver> createReduceSolvers(InputData inputData, List<Pack> packs) {
        FibonacciGenerator fibonacciGenerator = new FibonacciGenerator();
        return inputData.getFileNames().stream()
                .map(file -> new ReduceSolver(file, packs, fibonacciGenerator))
                .collect(Collectors.toList());
    }

}
