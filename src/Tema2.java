import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Tema2 {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
//        Prelucrarea datelor de intrare
        Args arguments = new Args(args);
        InputData inputData = new InputData(arguments.getFileIn());
        Service packService = new Service();
        List<Pack> startingPacks = packService.createStartingPacks(inputData);
//        Se creeaza executorService  cu numarul de thread-uri egal cu cel dat la intrare
        ExecutorService executorService = Executors.newFixedThreadPool(arguments.getNumberOfThreads());
//        Crearea task-urilor ce rezolva mapping-ul
        List<MapSolver> mapSolvers = new ArrayList<>();
        for (int i = 0; i < startingPacks.size(); i++) {
            mapSolvers.add(new MapSolver(startingPacks, i));
        }
//        invocare task-urilor
        executorService.invokeAll(mapSolvers);
        executorService.awaitTermination(1, TimeUnit.NANOSECONDS);
        executorService.shutdown();

//        Crearea task-urile ce fac reduce-ul(se creeaza numarul de file-uri task-uri)
        List<ReduceSolver> solvers = packService.createReduceSolvers(inputData, startingPacks);
        executorService = Executors.newFixedThreadPool(arguments.getNumberOfThreads());
//        invocare task-uri
        List<Future<FileData>> futureFileDatas = executorService.invokeAll(solvers);
        executorService.shutdown();
        List<FileData> fileDatas = new ArrayList<>();
        for (var val : futureFileDatas) {
            fileDatas.add(val.get());
        }
//        sortarea dupa rank
        fileDatas.sort((FileData val1, FileData val2) -> {
            if(val2.getRank() >= val1.getRank()){
                return 1;
            }else{
                return -1;
            }
            });
//        scrierea in fisierul de iesire
        FileWriter myWriter = new FileWriter(arguments.getFileOut());
        fileDatas.forEach(line -> {
            try {
                myWriter.write(String.valueOf(line) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        myWriter.close();
    }

}
