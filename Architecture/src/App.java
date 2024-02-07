import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;

public class App {
    static int currentAddress = 0;

    public static void main(String[] args) {
        assemble("src/Sample.txt", "src/output.txt");
    }

    private static void assemble(String inputFileName, String outputFileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFileName)))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (!line.isEmpty()) {
                    String[] tokens = line.split("[,\\s]+");
                    if (tokens[0].equals("LOC")) {
                        currentAddress = Integer.parseInt(tokens[1]);
                    } else {
                        /*
                         * String opcode = tokens[0];
                         * int r = Character.getNumericValue(tokens[1].charAt(0));
                         * int ix = Character.getNumericValue(tokens[1].charAt(1));
                         * int i = Character.getNumericValue(tokens[1].charAt(2));
                         * // int addressField = Integer.parseInt(tokens[2]);
                         * String binaryInstruction = opcodeMap.get(opcode) +
                         * String.format("%02d", r) +
                         * String.format("%02d", ix) +
                         * String.format("%01d", i) +
                         * String.format("%04d", currentAddress);
                         * String octalInstruction = binaryToOctal(binaryInstruction);
                         * writer.write(String.format("%06o\t%s\t%s%n", currentAddress,
                         * octalInstruction, line));
                         * currentAddress++;
                         */
                        if (tokens[0].equals("Data")) {
                            String[] instruction = ParseInstruction(tokens);
                            for (int i = 0; i < 2; i++) {
                                System.out.println(instruction[i]);
                                writer.write(instruction[i]);
                                writer.write("  ");
                            }
                            writer.newLine();
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] ParseInstruction(String[] tokens) {
        String[] output = new String[2];
        switch (tokens[0]) {
            case "Data":
                String octal;
                if (tokens[1].equals("End")) {
                    octal = Integer.toOctalString(1024);
                } else {
                    octal = Integer.toOctalString(Integer.parseInt(tokens[1]));
                }

                String octalAddress = Integer.toOctalString(currentAddress);
                int strLength = octal.length();
                String filler = "";
                String addressFiller = "";
                for (int i = 0; i < 6 - strLength; i++) {
                    filler += "0";
                }
                for (int i = 0; i < 6 - octalAddress.length(); i++) {
                    addressFiller += "0";
                }
                String instruction = filler + octal;
                String address = addressFiller + octalAddress;
                output[0] = address;
                output[1] = instruction;
                currentAddress++;
                break;

            case "LDX":

                break;
            default:
                break;
        }
        return output;
    }

    private static final Map<String, String> opcodeMap = createOpcodeMap();

    private static Map<String, String> createOpcodeMap() {
        Map<String, String> map = new HashMap<>();
        map.put("LDR", "000001");
        map.put("LDA", "000011");
        map.put("LDX", "101001");
        map.put("STR", "000010");
        map.put("STX", "101010");
        map.put("JZ", "001010");
        map.put("JNE", "001011");
        map.put("JCC", "001100");
        map.put("JMA", "001101");
        map.put("JSR", "001110");
        map.put("RFS", "001111");
        map.put("SOB", "110000");
        map.put("JGE", "110001");
        map.put("AMR", "000100");
        map.put("SMR", "000101");
        map.put("AIR", "000110");
        map.put("SIR", "000111");
        map.put("MLT", "010100");
        map.put("DVD", "010101");
        map.put("TRR", "010110");
        map.put("AND", "010111");
        map.put("ORR", "011000");
        map.put("NOT", "011001");
        map.put("SRC", "011111");
        map.put("RRC", "100000");
        map.put("TRAP", "100100");
        map.put("IN", "111101");
        map.put("OUT", "111110");
        map.put("FADD", "100001");
        map.put("FSUB", "100010");
        map.put("CNVRT", "100101");
        map.put("LDFR", "110010");
        map.put("STFR", "110011");
        map.put("VADD", "100110");
        map.put("VSUB", "100111");

        return map;
    }
}
