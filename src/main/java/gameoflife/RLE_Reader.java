package gameoflife;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RLE_Reader {

    public static Configuration readFile(String name) {
        String data = readFromInputStream(name);

        data = data.replace(" ", "");
        String header = "";
        StringBuilder sanitized_data = new StringBuilder();

        for(String line : data.split("\n")){
            if (!line.startsWith("#")){
                if (line.contains("rule")){
                    header = line;
                } else {
                    sanitized_data.append(line);
                }
            }
        }

        Pattern p = Pattern.compile("x=(\\d+),y=(\\d+),rule=B(\\d+)/S(\\d+).*");
        Matcher matcher = p.matcher(header);

        Configuration result = null;


        if (matcher.matches()) {
            int width = Integer.parseInt(matcher.group(1));
            int height = Integer.parseInt(matcher.group(2));
            int[] birth = extractDigits(matcher.group(3));
            int[] survival = extractDigits(matcher.group(4));

            boolean[][] pattern = new boolean[width][height];

            int x = 0;
            int y = 0;

            StringBuilder digits = new StringBuilder();
            for (char c : sanitized_data.toString().toCharArray()){
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
            result = new Configuration(width, height, survival, birth, pattern);
        }

        return result;
    }


    private static int[] extractDigits(String number_str) {

        int[] digits = new int[number_str.length()];
        int number = Integer.parseInt(number_str);
        for (int i = 0; i < digits.length; i++) {
            digits[i] = number % 10;
            number /= 10;
        }
        return digits;
    }


    private static String readFromInputStream(String name) {
        StringBuilder resultStringBuilder = new StringBuilder();
        InputStream inputStream = RLE_Reader.class.getResourceAsStream(name);
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
