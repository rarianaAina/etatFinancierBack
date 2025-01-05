package com.finance.finance;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] input = {4, 1, 3, 2, 6, 5};

        int index = 0;

        for (int i = 0; i < input.length; i++) {
            if (input[i] % 2 != 0) {

                int temp = input[i];
                for (int j = i; j > index; j--) {
                    input[j] = input[j - 1];
                }
                input[index] = temp;
                index++;
            }
        }

        System.out.println(Arrays.toString(input));
    }
}
