import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WcOperations {
    BufferedReader br;
    private final HashMap<String, Integer> wordsHashMap = new HashMap<>();
    private int countOfWords;
    private int countOfLines;
    private int countOfBytes;

    public WcOperations(BufferedReader br) throws IOException {
        this.br = br;
        this.countWordsLinesBytes();
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

    public String getWordHashMap() {
        StringBuilder output = new StringBuilder();
        for (String key: wordsHashMap.keySet()) {
            output.append("Word: ").append(key).append("; Count: ").append(wordsHashMap.get(key)).append("\n");
        }
        return output.toString();
    }

    public String getHighestWordCount() {
        String key = Collections.max(wordsHashMap.entrySet(), Map.Entry.comparingByValue()).getKey();
        return "Word: " + key + "; Count: " + wordsHashMap.get(key);
    }

    public void countWordsLinesBytes() throws IOException {
        int wordCounter = 0;
        int byteCounter = 0;
        int lineCounter = 0;
        String input;
        while (((input = br.readLine()) != null)) {
            lineCounter++;
            String[] lineWords = input.split("\\s+");
            for (String word: lineWords) {
                word = word.replaceAll("[^A-Za-z0-9]","");
                wordsHashMap.put(word, wordsHashMap.getOrDefault(word, 0) + 1);
            }
            byteCounter += input.getBytes().length;
            wordCounter += lineWords.length;
        }
        setCountOfWords(wordCounter);
        setCountOfLines(lineCounter);
        setCountOfBytes(byteCounter);
    }
}
