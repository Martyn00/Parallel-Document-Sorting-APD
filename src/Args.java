public class Args {
    private Integer numberOfThreads;
    private String fileIn;
    private String fileOut;

// prelucrearea argumentelor primite de program
    public Args(String[] args) {
        this.numberOfThreads = Integer.parseInt(args[0]);
        this.fileIn = args[1];
        this.fileOut = args[2];
    }

    public Integer getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(Integer numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public String getFileIn() {
        return fileIn;
    }

    public void setFileIn(String fileIn) {
        this.fileIn = fileIn;
    }

    public String getFileOut() {
        return fileOut;
    }

    public void setFileOut(String fileOut) {
        this.fileOut = fileOut;
    }

}
