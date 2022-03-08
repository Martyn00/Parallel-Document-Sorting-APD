import java.util.List;
import java.util.Map;

// Pachete fiecarui task in parte
public class Pack {
    private String fileName;
    private Integer start;
    private Integer step;
    private Map<Integer, Long> result;
    private List<String> maximalWords;

    public List<String> getMaximalWords() {
        return maximalWords;
    }

    public void setMaximalWords(List<String> maximalWords) {
        this.maximalWords = maximalWords;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Map<Integer, Long> getResult() {
        return result;
    }

    public synchronized void setResult(Map<Integer, Long> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Pack{" +
                "fileName='" + fileName + '\'' +
                ", start=" + start +
                ", step=" + step +
                ", result=" + result +
                ", maximalWords=" + maximalWords +
                '}';
    }
}
