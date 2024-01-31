import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        FileReader.ReadFiles("src/Sample.txt");

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
