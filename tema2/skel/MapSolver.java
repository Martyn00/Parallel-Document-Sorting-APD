import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.Callable;


import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.groupingBy;

public class MapSolver implements Callable<Pack> {
    private final List<Pack> packs;
    private final Integer id;

    public MapSolver(List<Pack> packs, Integer id) {
        super();
        this.packs = packs;
        this.id = id;
    }

    @Override
    public Pack call() {
        Pack pack = packs.get(id);
        try {
            getWordCount(pack.getFileName(), pack.getStart(), pack.getStep(), pack);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pack;
    }

// adauga litere de la final in cazul in care ultimul cuvant sa extinde si pe urmatorul paragraf
    private String addLastLetters(String res, String filename, Integer offset, Integer dim) throws IOException {
        try (RandomAccessFile seeker = new RandomAccessFile(filename, "r")) {
            int pos = offset + dim;
            seeker.seek(pos);
            byte[] oneByte = new byte[1];
            seeker.read(oneByte);
            String letter = new String(oneByte);
            while (Character.isLetter(letter.charAt(0))) {
                res = res.concat(letter);
                pos++;
                seeker.seek(pos);
                oneByte = new byte[1];
                seeker.read(oneByte);
                letter = new String(oneByte);
            }
        }
        return res;
    }

//      creaza map-ul
    public void getMap(String res, String filename, Integer offset, Integer dim, Pack pack) throws IOException {
        if (res.length() > 0 && Character.isLetter(res.charAt(res.length() - 1))) {
            res = addLastLetters(res, filename, offset, dim);
        }
        String[] words = res.split("\\W");
        pack.setMaximalWords(getMaximalWords(words));
        pack.setResult(Arrays.stream(words)
                .filter(word -> !word.equals(""))
                .collect(groupingBy(String::length, counting())));
    }
//      returneaza o lista de cuvinte maximale
    private List<String> getMaximalWords(String[] words) {
        List<String> maximalWords = new ArrayList<>();
        long maxLen = 0L;
        for (String str : words) {
            if (maxLen < str.length()) {
                maximalWords.clear();
                maximalWords.add(str);
                maxLen = str.length();
            } else if (maxLen == str.length()) {
                maximalWords.add(str);
            }
        }
        return maximalWords;
    }

//    returneaza map-ul
    private void getWordCount(String filename, Integer offset, Integer dim, Pack pack) throws IOException {
        String res = getInput(filename, offset, dim);
        getMap(res, filename, offset, dim, pack);
    }

//    ia intervalul din fisier sub forma de string
    private String getInput(String filename, Integer offset, Integer dim) throws IOException {
        byte[] bytes = new byte[dim];
        String res = null;
        try (RandomAccessFile seeker = new RandomAccessFile(filename, "r")) {
            seeker.seek(offset);
            seeker.read(bytes);
            res = checkForStartWord(offset, bytes, seeker);
        }
        return res;
    }

//    Verifica daca primul cuvant este continuarea din intervalul precedent
    private String checkForStartWord(Integer offset, byte[] bytes, RandomAccessFile seeker) throws IOException {
        String res = new String(bytes);
        if (bytes[0] != ' ' && !offset.equals(0)) {// trebuie schimbat si aici...
            seeker.seek(offset - 1);
            byte[] oneByte = new byte[1];
            seeker.read(oneByte);
            String letter = new String(oneByte);
            if (Character.isLetter(letter.charAt(0))) {
                int i;
                for (i = 0; i < res.length(); i++) {
                    if (!Character.isLetter(res.charAt(i))) {
                        break;
                    }
                }
                StringBuilder stringBuilder = new StringBuilder(res);
                res = stringBuilder.substring(i, res.length());
            }
        }
        return res;
    }

}
