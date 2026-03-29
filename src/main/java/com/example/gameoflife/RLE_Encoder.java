package com.example.gameoflife;

import java.io.*;
import java.util.Arrays;

public class RLE_Encoder {

    public static int[][] importFile(String name) {
        String data = readFromInputStream(name);
        String[] zeilen = data.split("B3/S23")[1].replace("\n", "").replace("!", "").split("\\$");
        String[] next = new String[zeilen.length];

        for (int s = 0; s < zeilen.length; s++) {
            StringBuilder st = new StringBuilder();
            char[] arr = zeilen[s].toCharArray();
            for (int i = 0; i < arr.length; i++) {
                st.append(arr[i]);
                if ((arr[i] == 'b' || arr[i] == 'o') && ((i < arr.length - 2 && (arr[i + 1] == 'b' || arr[i + 1] == 'o')) || (i == arr.length - 2))) {
                    st.append("1");
                }
            }
            next[s] = st.toString();
        }

        int x = Integer.parseInt(data.split("x = ")[1].split(",")[0]);
        int y = Integer.parseInt(data.split("y = ")[1].split(",")[0]);
        int[][] result = new int[x][y];

        for (int s = 0; s < next.length; s++) {
            StringBuilder num = new StringBuilder();
            char[] arr = next[s].toCharArray();
            int i = 0;
            int tem = 0;
            System.out.println("STRING: " + next[s]);
            while (i < arr.length) {
                if (arr[i] != 'o' && arr[i] != 'b') {
                    num.append(arr[i++]);
                } else {
                    System.out.println(tem);
                    num = new StringBuilder(num.toString().equals("") ? "1" : num);
                    System.out.println("num: " + num);
                    int n = Integer.parseInt(num.toString());
                    for (int j = 0; j < n; j++) {
                        result[tem + j][s] = arr[i] == 'o' ? 1 : 0;
                    }
                    tem += n;
                    num = new StringBuilder();
                    i++;
                }
            }
        }

        return result;
    }


    private static String readFromInputStream(String name) {
        StringBuilder resultStringBuilder = new StringBuilder();
        InputStream inputStream = RLE_Encoder.class.getResourceAsStream(name);
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
