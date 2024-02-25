import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

public class Wc {
    static String[] definedCommands = {"-c", "-l", "-w", "-f", "-m", "-h"};
    static HashSet<String> commands = new HashSet<>();
    static HashSet<String> filePaths = new HashSet<>();

    public static void main(String[] args) {
        StringBuilder finalOutput = new StringBuilder();
        boolean isFileProvided = false;

        if (args.length > 0 && argsProcessor(args)) {
            isFileProvided = !filePaths.isEmpty();
        }

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(System.in);
            if (inputStreamReader.ready()) {
                finalOutput.append(standardInput(inputStreamReader)).append("\n");
            }
            if (isFileProvided) {
                for (String filePath: filePaths) {
                    FileReader fileReader = new FileReader(filePath);
                    finalOutput.append(fileInput(fileReader)).append(" ").append(filePath).append("\n");
                }
            }
            if (!finalOutput.isEmpty()) {
                printToConsole(finalOutput.deleteCharAt(finalOutput.length() - 1).toString());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Wc: File not found");

        } catch (IOException e) {
            System.out.println("Wc: Unable to read input");
        }

        if (args.length == 1 && commands.contains("-h")) {
            String helpOutput = "java Wc [flags] [filename] \n\nFlags:\n  -h,\thelp\n  -c,\tOutputs the numbers of bytes in the file\n  -l,\tOutputs the numbers of lines in the file\n  -w,\tOutputs the numbers of words in the file\n  -f\tOutputs the freq of each word in the file\n  -m,\tOutputs the most repeated word and it's count";
            printToConsole(helpOutput);
        }

    }

    static boolean argsProcessor(String[] args) {
        boolean fileFound = false;
        for (String arg: args) {
            if (arg.startsWith("-")) {
                if (Arrays.asList(definedCommands).contains(arg)) {
                    if (fileFound) {
                        System.out.println("Wc: illegal input format\nusage: java Wc [-clwfm] [file ...]");
                        return false;
                    }
                    commands.add(arg);
                } else {
                    System.out.println("Wc: illegal option - " + arg + "\nusage: java Wc [-clwfm] [file ...]");
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
                case "-m" -> wcOperations.getHighestWordCount() + "\n";
                default -> "";
            });
        }
        return result.deleteCharAt(result.length() - 1).toString();
    }

    static void printToConsole(String s) {
        System.out.println(s);
    }
}