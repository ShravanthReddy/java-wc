import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

public class wc {
    static String[] definedCommands = {"-c", "-l", "-w", "-f", "-h"};
    static HashSet<String> commands = new HashSet<>();
    static HashSet<String> filePaths = new HashSet<>();

    public static void main(String[] args) {
        if (args.length > 0 && argsProcessor(args)) {
            printToConsole("Commands: " + commands);
            printToConsole("File Paths: " + filePaths);
        }
    }

    static boolean argsProcessor(String[] args) {
        boolean fileFound = false;
        for (String arg: args) {
            if (arg.startsWith("-")) {
                if (Arrays.asList(definedCommands).contains(arg)) {
                    if (fileFound) {
                        System.out.println("Wc: illegal input format\nusage: java Wc [-clwfh] [file ...]");
                        return false;
                    }
                    commands.add(arg);
                } else {
                    System.out.println("Wc: illegal option - " + arg + "\nusage: java Wc [-clwfh] [file ...]");
                    return false;
                }
            } else if (arg.lastIndexOf(".") != -1) {
                fileFound = true;
                filePaths.add(arg);
            } else {
                System.out.println("Wc: " + arg + ": open: No such file or directory");
                return false;
            }
        }
        return true;
    }

    static String standardInput(InputStreamReader isr) throws IOException {
        WcOperations wcOperations = new WcOperations(new BufferedReader(isr));
        return getOutput(wcOperations);
    }

    static String fileInput(FileReader fileReader) throws IOException {
        WcOperations wcOperations = new WcOperations(new BufferedReader(fileReader));
        return getOutput(wcOperations);
    }

    static String getOutput(WcOperations wcOperations) {
        if (commands.isEmpty()) {
            return wcOperations.getAll();
        }
        StringBuilder result = new StringBuilder();
        for (String cmd: commands) {
            result.append(switch (cmd) {
                case "-c" -> "B:" + wcOperations.getCountOfBytes() + "\n";
                case "-w" -> "W:" + wcOperations.getCountOfWords() + "\n";
                case "-l" -> "L:" + wcOperations.getCountOfLines() + "\n";
                case "-f" -> wcOperations.getWordHashMap() + "\n";
                case "-h" -> wcOperations.getHighestWordCount() + "\n";
                default -> "";
            });
        }
        return result.deleteCharAt(result.length() - 1).toString();
    }

    static void printToConsole(String s) {
        System.out.println(s);
    }
}