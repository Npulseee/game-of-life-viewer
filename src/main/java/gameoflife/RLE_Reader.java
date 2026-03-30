package gameoflife;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RLE_Reader {

    /**
     * Reads the content of a given .rle file and returns it as a Configuration
     */
    public static Configuration importConfigurationFromFile(String filename) {
        // read the file and sanitize the string
        String data = readFromInputStream(filename);
        data = data.replace(" ", "");
        data = data.toLowerCase();

        String header = "";
        StringBuilder pattern_data = new StringBuilder();

        // extract header and pattern information
        for(String line : data.split("\n")){
            // ignore lines that contain comments or other metadata
            if (line.startsWith("#")) continue;

            if (line.contains("rule")){
                header = line;
            } else {
                pattern_data.append(line);
            }
        }

        Pattern header_pattern = Pattern.compile("x=(\\d+),y=(\\d+),rule=b(\\d+)/s(\\d+).*");
        Matcher matcher = header_pattern.matcher(header);

        // return empty configuration if the header does not match the expected pattern
        if (!matcher.matches()) {
            return new Configuration(0, 0, new int[0], new int[0], new boolean[0][0]);
        }

        // parse the information contained in the header
        int width = Integer.parseInt(matcher.group(1));
        int height = Integer.parseInt(matcher.group(2));
        int[] birth = extractDigits(matcher.group(3));
        int[] survival = extractDigits(matcher.group(4));

        boolean[][] pattern = new boolean[width][height];
        int x = 0;
        int y = 0;
        StringBuilder digits = new StringBuilder();

        // parse the pattern encoded in the pattern_data string
        for (char c : pattern_data.toString().toCharArray()){
            if (Character.isDigit(c)){
                digits.append(c);
            } else {
                int run_count = (digits.isEmpty()) ? 1 : Integer.parseInt(digits.toString());
                digits = new StringBuilder();
                switch (c) {
                    case '$':
                        y += run_count;
                        x = 0;
                        break;
                    case 'o':
                        for (int i = 0; i < run_count; i++) {
                            pattern[x][y] = true;
                            x++;
                        }
                        break;
                    default:
                        x += run_count;
                }
            }
        }

        return new Configuration(width, height, survival, birth, pattern);
    }


    /**
     * Extracts the individual digits from a string and returns them as an int array
     */
    private static int[] extractDigits(String number_str) {
        int[] digits = new int[number_str.length()];
        int number = Integer.parseInt(number_str);

        for (int i = 0; i < digits.length; i++) {
            digits[i] = number % 10;
            number /= 10;
        }
        return digits;
    }


    /**
     * Returns the content of a file as a string
     */
    private static String readFromInputStream(String filename) {
        StringBuilder resultStringBuilder = new StringBuilder();
        InputStream inputStream = RLE_Reader.class.getResourceAsStream(filename);
        try {
            assert inputStream != null;
            try (BufferedReader br
                         = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    resultStringBuilder.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultStringBuilder.toString();
    }
}
