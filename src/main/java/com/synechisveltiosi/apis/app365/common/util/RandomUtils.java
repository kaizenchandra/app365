package com.synechisveltiosi.apis.app365.common.util;

import java.util.Random;

public class RandomUtils {

    public static int generate(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
