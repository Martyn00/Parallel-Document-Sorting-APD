import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Prelucrarea datelor de input din fisierul de intrare
public class InputData {
    private Integer fragmentDimension;
    private List<String> fileNames;

    public InputData(String filename) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            this.fragmentDimension = Integer.parseInt(bufferedReader.readLine());
            this.fileNames = new ArrayList<>();
            String line;
            bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null){
                fileNames.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getFragmentDimension() {
        return fragmentDimension;
    }

    public void setFragmentDimension(Integer fragmentDimension) {
        this.fragmentDimension = fragmentDimension;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    @Override
    public String toString() {
        return "InputData{" +
                "fragmentDimension=" + fragmentDimension +
                ", fileNames=" + fileNames +
                '}';
    }
}
