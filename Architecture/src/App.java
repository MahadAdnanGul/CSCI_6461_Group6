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
                        if (tokens[0].equals("Data") || tokens[0].equals("LDX")) {
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

                String instruction = fillerAdd(octal, 6);
                String address = fillerAdd(octalAddress, 6);
                output[0] = address;
                output[1] = instruction;
                currentAddress++;
                break;

            case "LDX":
                String opCode = fillerAdd(Integer.toBinaryString(Integer.parseInt(opcodeMap.get(tokens[0]), 2)), 6);
                String R = "00";// fillerAdd(Integer.toBinaryString(Integer.parseInt(tokens[1])), 2);
                String iX = fillerAdd(Integer.toBinaryString(Integer.parseInt(tokens[1])), 2);
                String i = "0";
                String add = fillerAdd(Integer.toBinaryString(Integer.parseInt(tokens[2])), 5);
                String binary = opCode + R + iX + i + add;
                System.out.println(binary);

                String octal0 = Integer.toOctalString(Integer.parseInt(binary.substring(0, 1), 2));
                String octal1 = Integer.toOctalString(Integer.parseInt(binary.substring(1, 4), 2));
                String octal2 = Integer.toOctalString(Integer.parseInt(binary.substring(4, 7), 2));
                String octal3 = Integer.toOctalString(Integer.parseInt(binary.substring(7, 10), 2));
                String octal4 = Integer.toOctalString(Integer.parseInt(binary.substring(10, 13), 2));
                String octal5 = Integer.toOctalString(Integer.parseInt(binary.substring(13, 16), 2));
                String concat = octal0 + octal1 + octal2 + octal3 + octal4 + octal5;
                output[0] = fillerAdd(Integer.toOctalString(currentAddress), 6);
                output[1] = concat;
                currentAddress++;
                break;

            default:
                break;
        }
        return output;
    }

    private static String fillerAdd(String input, int length) {
        String filler = "";
        for (int i = 0; i < length - input.length(); i++) {
            filler += "0";
        }
        return filler + input;
    }

    private static final Map<String, String> opcodeMap = createOpcodeMap();

    private static Map<String, String> createOpcodeMap() {
        Map<String, String> map = new HashMap<>();
        map.put("LDR", "000001");
        map.put("LDA", "000011");
        map.put("LDX", "000100");
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
