package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class WcOperations {
    BufferedReader br;
    private final HashMap<String, Integer> wordsHashMap = new HashMap<>();
    private int countOfWords;
    private int countOfLines;
    private int countOfBytes;

    public WcOperations(BufferedReader br) throws IOException {
        this.br = br;
    }

    public int getCountOfWords() {
        return countOfWords;
    }

    public void setCountOfWords(int countOfWords) {
        this.countOfWords = countOfWords;
    }

    public int getCountOfLines() { return countOfLines; }

    public void setCountOfLines(int countOfLines) {
        this.countOfLines = countOfLines;
    }

    public int getCountOfBytes() {
        return countOfBytes;
    }

    public void setCountOfBytes(int countOfBytes) {
        this.countOfBytes = countOfBytes;
    }

    public String getAll() {
        return "L:" + countOfLines + " W:" + countOfWords + " B:" + countOfBytes;
    }
}
