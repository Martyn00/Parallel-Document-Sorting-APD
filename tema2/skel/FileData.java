//Prelucrarea datelor de final ce se vor afisa in fisierul de iesire
public class FileData {
    private String filename;
    private Float rank;
    private String maximalWord;

    public FileData(String filename, Float rank, String maximalWord) {
        this.filename = filename;
        this.rank = rank;
        this.maximalWord = maximalWord;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Float getRank() {
        return rank;
    }

    public void setRank(Float rank) {
        this.rank = rank;
    }

    public String getMaximalWord() {
        return maximalWord;
    }

    public void setMaximalWord(String maximalWord) {
        this.maximalWord = maximalWord;
    }

    @Override
    public String toString() {
        String[] strings = filename.split("/");
        String actualFileName =strings[strings.length- 1];
        return actualFileName + "," + String.format("%.2f", rank) + "," + maximalWord;
    }

}
