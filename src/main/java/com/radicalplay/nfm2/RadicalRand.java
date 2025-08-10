package com.radicalplay.nfm2;

import java.util.concurrent.ThreadLocalRandom;

public final class RadicalRand {
    public static boolean bool() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static double random() {
        return ThreadLocalRandom.current().nextDouble();
    }
}
