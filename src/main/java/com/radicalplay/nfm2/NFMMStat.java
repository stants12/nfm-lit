package com.radicalplay.nfm2;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static com.radicalplay.nfm2.Utility.getstring;
import static com.radicalplay.nfm2.Utility.getvalue;

public final class NFMMStat {
    private static final int[][] swits = {
            {
                    50, 185, 282
            }, {
            100, 200, 310
    }, {
            60, 180, 275
    }, {
            76, 195, 298
    }, {
            70, 170, 275
    }, {
            70, 202, 293
    }, {
            60, 170, 289
    }, {
            70, 206, 291
    }, {
            90, 210, 295
    }, {
            90, 190, 276
    }, {
            70, 200, 295
    }, {
            50, 160, 270
    }, {
            90, 200, 305
    }, {
            50, 130, 210
    }, {
            80, 200, 300
    }, {
            70, 210, 290
    }
    };
    private static final float[][] acelf = {
            {
                    11.0F, 5.0F, 3.0F
            }, {
            14.0F, 7.0F, 5.0F
    }, {
            10.0F, 5.0F, 3.5F
    }, {
            11.0F, 6.0F, 3.5F
    }, {
            10.0F, 5.0F, 3.5F
    }, {
            12.0F, 6.0F, 3.0F
    }, {
            7.0F, 9.0F, 4.0F
    }, {
            11.0F, 5.0F, 3.0F
    }, {
            12.0F, 7.0F, 4.0F
    }, {
            12.0F, 7.0F, 3.5F
    }, {
            11.5F, 6.5F, 3.5F
    }, {
            9.0F, 5.0F, 3.0F
    }, {
            13.0F, 7.0F, 4.5F
    }, {
            7.5F, 3.5F, 3.0F
    }, {
            11.0F, 7.5F, 4.0F
    }, {
            12.0F, 6.0F, 3.5F
    }
    };

    record OtherStats(String createdby, int publish, int cclass) {
    }

    public static OtherStats loadstat(Stat stat, final byte[] is, final int maxR, final int roofat, final int wh, final int idx) {
        boolean bool = false;
        boolean bool3 = false;
        String line;
        final int[] statValues = {
                128, 128, 128, 128, 128
        };
        int i6 = 640;
        final int[] physicsValues = {
                50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50
        };
        final int[] lastPhysValues = {
                50, 50, 50
        };
        stat.engine = 0;
        float f = 0.0F;
        String createdby = "";
        int publish = 0;
        int cclass = 0;
        try {
            final BufferedReader statReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(is)));
            while ((line = statReader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("stat(")) {
                    try {
                        i6 = 0;
                        for (int i9 = 0; i9 < 5; i9++) {
                            statValues[i9] = getvalue("stat", line, i9);
                            if (statValues[i9] > 200) {
                                statValues[i9] = 200;
                            }
                            if (statValues[i9] < 16) {
                                statValues[i9] = 16;
                            }
                            i6 += statValues[i9];
                        }
                        bool = true;
                    } catch (final Exception exception) {
                        bool = false;
                    }
                }
                if (line.startsWith("physics(")) {
                    try {
                        for (int i10 = 0; i10 < 11; i10++) {
                            physicsValues[i10] = getvalue("physics", line, i10);
                            if (physicsValues[i10] > 100) {
                                physicsValues[i10] = 100;
                            }
                            if (physicsValues[i10] < 0) {
                                physicsValues[i10] = 0;
                            }
                        }
                        for (int i11 = 0; i11 < 3; i11++) {
                            lastPhysValues[i11] = getvalue("physics", line, i11 + 11);
                            if (i11 != 0 && lastPhysValues[i11] > 100) {
                                lastPhysValues[i11] = 100;
                            }
                            if (lastPhysValues[i11] < 0) {
                                lastPhysValues[i11] = 0;
                            }
                        }
                        stat.engine = getvalue("physics", line, 14);
                        if (stat.engine > 4) {
                            stat.engine = 0;
                        }
                        if (stat.engine < 0) {
                            stat.engine = 0;
                        }
                        f = getvalue("physics", line, 15);
                        if (f > 0.0F) {
                            bool3 = true;
                        }
                    } catch (final Exception exception) {
                        bool3 = false;
                    }
                }
                if (line.startsWith("handling(")) {
                    try {
                        int i12 = getvalue("handling", line, 0);
                        if (i12 > 200) {
                            i12 = 200;
                        }
                        if (i12 < 50) {
                            i12 = 50;
                        }
                        stat.dishandle = i12 / 200.0F;
                    } catch (final Exception ignored) {

                    }
                }
                if (line.startsWith("carmaker(")) {
                    createdby = getstring("carmaker", line, 0); // TODO getSvalue
                }
                if (line.startsWith("publish(")) {
                    publish = getvalue("publish", line, 0);
                }
            }
            statReader.close();
        } catch (final Exception exception) {
            System.out.println("Error Loading Car Stat: " + exception);
        }
        if (bool && bool3) {
            int i13 = 0;
            if (i6 > 680) {
                i13 = 680 - i6;
            }
            if (i6 > 640 && i6 < 680) {
                i13 = 640 - i6;
            }
            if (i6 > 600 && i6 < 640) {
                i13 = 600 - i6;
            }
            if (i6 > 560 && i6 < 600) {
                i13 = 560 - i6;
            }
            if (i6 > 520 && i6 < 560) {
                i13 = 520 - i6;
            }
            if (i6 < 520) {
                i13 = 520 - i6;
            }
            while (i13 != 0) {
                for (int i14 = 0; i14 < 5; i14++) {
                    if (i13 > 0 && statValues[i14] < 200) {
                        statValues[i14]++;
                        i13--;
                    }
                    if (i13 < 0 && statValues[i14] > 16) {
                        statValues[i14]--;
                        i13++;
                    }
                }
            }
            i6 = 0;
            for (int i15 = 0; i15 < 5; i15++) {
                i6 += statValues[i15];
            }
            if (i6 == 520) {
                cclass = 0;
            }
            if (i6 == 560) {
                cclass = 1;
            }
            if (i6 == 600) {
                cclass = 2;
            }
            if (i6 == 640) {
                cclass = 3;
            }
            if (i6 == 680) {
                cclass = 4;
            }
            int i16 = 0;
            int i17 = 0;
            float f18 = 0.5F;
            if (statValues[0] == 200) {
                i16 = 1;
                i17 = 1;
            }
            if (statValues[0] > 192 && statValues[0] < 200) {
                i16 = 12;
                i17 = 1;
                f18 = (statValues[0] - 192) / 8.0F;
            }
            if (statValues[0] == 192) {
                i16 = 12;
                i17 = 12;
            }
            if (statValues[0] > 148 && statValues[0] < 192) {
                i16 = 14;
                i17 = 12;
                f18 = (statValues[0] - 148) / 44.0F;
            }
            if (statValues[0] == 148) {
                i16 = 14;
                i17 = 14;
            }
            if (statValues[0] > 133 && statValues[0] < 148) {
                i16 = 10;
                i17 = 14;
                f18 = (statValues[0] - 133) / 15.0F;
            }
            if (statValues[0] == 133) {
                i16 = 10;
                i17 = 10;
            }
            if (statValues[0] > 112 && statValues[0] < 133) {
                i16 = 15;
                i17 = 10;
                f18 = (statValues[0] - 112) / 21.0F;
            }
            if (statValues[0] == 112) {
                i16 = 15;
                i17 = 15;
            }
            if (statValues[0] > 107 && statValues[0] < 112) {
                i16 = 11;
                i17 = 15;
                f18 = (statValues[0] - 107) / 5.0F;
            }
            if (statValues[0] == 107) {
                i16 = 11;
                i17 = 11;
            }
            if (statValues[0] > 88 && statValues[0] < 107) {
                i16 = 13;
                i17 = 11;
                f18 = (statValues[0] - 88) / 19.0F;
            }
            if (statValues[0] == 88) {
                i16 = 13;
                i17 = 13;
            }
            if (statValues[0] > 88) {
                stat.swits[0] = (int) ((swits[i17][0] - swits[i16][0]) * f18 + swits[i16][0]);
                stat.swits[1] = (int) ((swits[i17][1] - swits[i16][1]) * f18 + swits[i16][1]);
                stat.swits[2] = (int) ((swits[i17][2] - swits[i16][2]) * f18 + swits[i16][2]);
            } else {
                f18 = statValues[0] / 88.0F;
                if (f18 < 0.76) {
                    f18 = 0.76F;
                }
                stat.swits[0] = (int) (50.0F * f18);
                stat.swits[1] = (int) (130.0F * f18);
                stat.swits[2] = (int) (210.0F * f18);
            }
            i16 = 0;
            i17 = 0;
            f18 = 0.5F;
            if (statValues[1] == 200) {
                i16 = 1;
                i17 = 1;
            }
            if (statValues[1] > 150 && statValues[1] < 200) {
                i16 = 14;
                i17 = 1;
                f18 = (statValues[1] - 150) / 50.0F;
            }
            if (statValues[1] == 150) {
                i16 = 14;
                i17 = 14;
            }
            if (statValues[1] > 144 && statValues[1] < 150) {
                i16 = 9;
                i17 = 14;
                f18 = (statValues[1] - 144) / 6.0F;
            }
            if (statValues[1] == 144) {
                i16 = 9;
                i17 = 9;
            }
            if (statValues[1] > 139 && statValues[1] < 144) {
                i16 = 6;
                i17 = 9;
                f18 = (statValues[1] - 139) / 5.0F;
            }
            if (statValues[1] == 139) {
                i16 = 6;
                i17 = 6;
            }
            if (statValues[1] > 128 && statValues[1] < 139) {
                i16 = 15;
                i17 = 6;
                f18 = (statValues[1] - 128) / 11.0F;
            }
            if (statValues[1] == 128) {
                i16 = 15;
                i17 = 15;
            }
            if (statValues[1] > 122 && statValues[1] < 128) {
                i16 = 10;
                i17 = 15;
                f18 = (statValues[1] - 122) / 6.0F;
            }
            if (statValues[1] == 122) {
                i16 = 10;
                i17 = 10;
            }
            if (statValues[1] > 119 && statValues[1] < 122) {
                i16 = 3;
                i17 = 10;
                f18 = (statValues[1] - 119) / 3.0F;
            }
            if (statValues[1] == 119) {
                i16 = 3;
                i17 = 3;
            }
            if (statValues[1] > 98 && statValues[1] < 119) {
                i16 = 5;
                i17 = 3;
                f18 = (statValues[1] - 98) / 21.0F;
            }
            if (statValues[1] == 98) {
                i16 = 5;
                i17 = 5;
            }
            if (statValues[1] > 81 && statValues[1] < 98) {
                i16 = 0;
                i17 = 5;
                f18 = (statValues[1] - 81) / 17.0F;
            }
            if (statValues[1] == 81) {
                i16 = 0;
                i17 = 0;
            }
            if (statValues[1] <= 80) {
                i16 = 2;
                i17 = 2;
            }
            if (statValues[0] <= 88) {
                i16 = 13;
                i17 = 13;
            }
            stat.acelf[0] = (acelf[i17][0] - acelf[i16][0]) * f18 + acelf[i16][0];
            stat.acelf[1] = (acelf[i17][1] - acelf[i16][1]) * f18 + acelf[i16][1];
            stat.acelf[2] = (acelf[i17][2] - acelf[i16][2]) * f18 + acelf[i16][2];
            if (statValues[1] <= 70 && statValues[0] > 88) {
                stat.acelf[0] = 9.0F;
                stat.acelf[1] = 4.0F;
                stat.acelf[2] = 3.0F;
            }
            f18 = (statValues[2] - 88) / 109.0F;
            if (f18 > 1.0F) {
                f18 = 1.0F;
            }
            if (f18 < -0.55) {
                f18 = -0.55F;
            }
            stat.airs = 0.55F + 0.45F * f18 + 0.4F * (physicsValues[9] / 100.0F);
            if (stat.airs < 0.3) {
                stat.airs = 0.3F;
            }
            stat.airc = (int) (10.0F + 70.0F * f18 + 30.0F * (physicsValues[10] / 100.0F));
            if (stat.airc < 0) {
                stat.airc = 0;
            }
            int i19 = (int) (670.0F - (physicsValues[9] + physicsValues[10]) / 200.0F * 420.0F);
            if (statValues[0] <= 88) {
                i19 = (int) (1670.0F - (physicsValues[9] + physicsValues[10]) / 200.0F * 1420.0F);
            }
            if (statValues[2] > 190 && i19 < 300) {
                i19 = 300;
            }
            stat.powerloss = i19 * 10000;
            stat.moment = 0.7F + (statValues[3] - 16) / 184.0F * 1.0F;
            if (statValues[0] < 110) {
                stat.moment = 0.75F + (statValues[3] - 16) / 184.0F * 1.25F;
            }
            if (statValues[3] == 200 && statValues[4] == 200 && statValues[0] <= 88) {
                stat.moment = 3.0F;
            }
            float f20 = 0.9F + (statValues[4] - 90) * 0.01F;
            if (f20 < 0.6) {
                f20 = 0.6F;
            }
            if (statValues[4] == 200 && statValues[0] <= 88) {
                f20 = 3.0F;
            }
            stat.maxmag = (int) (f * f20);
            stat.outdam = 0.35F + (f20 - 0.6F) * 0.5F;
            if (stat.outdam < 0.35) {
                stat.outdam = 0.35F;
            }
            if (stat.outdam > 1.0F) {
                stat.outdam = 1.0F;
            }
            stat.clrad = (int) (lastPhysValues[0] * lastPhysValues[0] * 1.5);
            if (stat.clrad < 1000) {
                stat.clrad = 1000;
            }
            stat.dammult = 0.3F + lastPhysValues[1] * 0.005F;
            stat.msquash = (int) (2.0 + lastPhysValues[2] / 7.6);
            stat.flipy = roofat;
            stat.handb = (int) (7.0F + physicsValues[0] / 100.0F * 8.0F);
            stat.turn = (int) (4.0F + physicsValues[1] / 100.0F * 6.0F);
            stat.grip = 16.0F + physicsValues[2] / 100.0F * 14.0F;
            if (stat.grip < 21.0F) {
                stat.swits[0] += 40.0F * ((21.0F - stat.grip) / 5.0F);
                if (stat.swits[0] > 100) {
                    stat.swits[0] = 100;
                }
            }
            stat.bounce = 0.8F + physicsValues[3] / 100.0F * 0.6F;
            if (physicsValues[3] > 67) {
                stat.airs *= 0.76F + (1.0F - physicsValues[3] / 100.0F) * 0.24F;
                stat.airc *= 0.76F + (1.0F - physicsValues[3] / 100.0F) * 0.24F;
            }
            stat.lift = (int) ((float) physicsValues[5] * (float) physicsValues[5] / 10000.0F * 30.0F);
            stat.revlift = (int) (physicsValues[6] / 100.0F * 32.0F);
            stat.push = (int) (2.0F + physicsValues[7] / 100.0F * 2.0F * ((30 - stat.lift) / 30));
            stat.revpush = (int) (1.0F + physicsValues[8] / 100.0F * 2.0F);
            stat.comprad = maxR / 400.0F + (statValues[3] - 16) / 184.0F * 0.2F;
            if (stat.comprad < 0.4) {
                stat.comprad = 0.4F;
            }
            stat.simag = (wh - 17) * 0.0167F + 0.85F;
        }

        return new OtherStats(createdby, publish, cclass);
    }
}
