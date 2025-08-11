package nfm.lit;

/**
 * Refactored: Extracted stage constants to StageConfig, improved field visibility,
 * added getters/setters, added comments for clarity.
 * TODO: Implement IStage interface for further decoupling.
 */
class CheckPoints {

    // Checkpoint coordinates
    public final int[] x = new int[StageConfig.MAX_CHECKPOINTS];
    public final int[] z = new int[StageConfig.MAX_CHECKPOINTS];
    public final int[] y = new int[StageConfig.MAX_CHECKPOINTS];
    public final int[] typ = new int[StageConfig.MAX_CHECKPOINTS];
    
    // Counters
    public int pcs = 0;
    public int nsp = 0;
    public int n = 0;
    
    // Special points
    public final int[] fx = new int[StageConfig.MAX_SPECIAL_POINTS];
    public final int[] fz = new int[StageConfig.MAX_SPECIAL_POINTS];
    public final int[] fy = new int[StageConfig.MAX_SPECIAL_POINTS];
    public final boolean[] roted = new boolean[StageConfig.MAX_SPECIAL_POINTS];
    public final boolean[] special = new boolean[StageConfig.MAX_SPECIAL_POINTS];
    
    // Track information
    static boolean customTrack = false;
    static String trackname = "";
    static String trackformat = "";
    public int fn = 0;
    public int stage = 1;
    public int nlaps = 0;
    
    // Stage name - defaults to "hogan rewish" if none found
    public String name = StageConfig.DEFAULT_STAGE_NAME;
    
    // Car positions (initialized to default position)
    public final int[] pos = new int[StageConfig.MAX_CARS];
    {
        for (int i = 0; i < pos.length; i++) {
            pos[i] = StageConfig.DEFAULT_POSITION;
        }
    }
    
    // Car states
    public final int[] clear = new int[StageConfig.MAX_CARS];
    public final int[] dested = new int[StageConfig.MAX_CARS];
    public int wasted = 0;
    public boolean haltall = false;
    public int pcleared = 0;
    public final int[] opx = new int[StageConfig.MAX_CARS];
    public final int[] opz = new int[StageConfig.MAX_CARS];
    public final int[] onscreen = new int[StageConfig.MAX_CARS];
    public final int[] omxz = new int[StageConfig.MAX_CARS];
    public int catchfin = 0;
    private int postwo = 0;

    // Getters and setters for fields accessed by other classes
    public int[] getX() { return x; }
    public int[] getZ() { return z; }
    public int[] getY() { return y; }
    public int[] getTyp() { return typ; }
    
    public int getN() { return n; }
    public void setN(int n) { this.n = n; }
    
    public int getNsp() { return nsp; }
    public void setNsp(int nsp) { this.nsp = nsp; }
    
    public int getFn() { return fn; }
    public void setFn(int fn) { this.fn = fn; }
    
    public boolean isHaltall() { return haltall; }
    public void setHaltall(boolean haltall) { this.haltall = haltall; }
    
    public int getStage() { return stage; }
    public void setStage(int stage) { this.stage = stage; }
    
    public int getNlaps() { return nlaps; }
    public void setNlaps(int nlaps) { this.nlaps = nlaps; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int[] getPos() { return pos; }
    public int[] getClear() { return clear; }
    public int[] getDested() { return dested; }

    public void checkstat(Madness amadness[], ContO aconto[], Record record, int ncars) {
        if (!haltall) {
            pcleared = amadness[0].pcleared;
            int i = 0;
            do {
                pos[i] = 0;
                onscreen[i] = aconto[i].dist;
                opx[i] = aconto[i].x;
                opz[i] = aconto[i].z;
                omxz[i] = amadness[i].mxz;
                if (dested[i] == 0) {
                    clear[i] = amadness[i].clear;
                } else {
                    clear[i] = -1;
                }
            } while (++i < ncars);
            i = 0;
            do {
                for (int l = i + 1; l < ncars; l++) {
                    if (clear[i] != clear[l]) {
                        if (clear[i] < clear[l]) {
                            pos[i]++;
                        } else {
                            pos[l]++;
                        }
                    } else {
                        int j1;
                        for (j1 = amadness[i].pcleared + 1; typ[j1] <= 0; ) {
                            if (++j1 == n) {
                                j1 = 0;
                            }
                        }

                        if (Utility.py(aconto[i].x / 100, x[j1] / 100, aconto[i].z / 100, z[j1] / 100) > Utility.py(aconto[l].x / 100,
                                x[j1] / 100, aconto[l].z / 100, z[j1] / 100)) {
                            pos[i]++;
                        } else {
                            pos[l]++;
                        }
                    }
                }

            } while (++i < ncars);
            if (stage > 2) {
                int j = 0;
                do {
                    if (clear[j] == nlaps * nsp && pos[j] == 0) {
                        if (j == 0) {
                            int i1 = 0;
                            do {
                                if (pos[i1] == 1) {
                                    postwo = i1;
                                }
                            } while (++i1 < ncars);
                            if (Utility.py(opx[0] / 100, opx[postwo] / 100, opz[0] / 100, opz[postwo] / 100) < 14000
                                    && clear[0] - clear[postwo] == 1) {
                                catchfin = 30;
                            }
                        } else if (pos[0] == 1 && Utility.py(opx[0] / 100, opx[j] / 100, opz[0] / 100, opz[j] / 100) < 14000
                                && clear[j] - clear[0] == 1) {
                            catchfin = 30;
                            postwo = j;
                        }
                    }
                } while (++j < ncars);
            }
        }
        wasted = 0;
        int k = 1;
        do {
            if (amadness[k].dest) {
                wasted++;
            }
        } while (++k < ncars);
        if (catchfin != 0) {
            catchfin--;
            if (catchfin == 0) {
                record.cotchinow(postwo);
                record.closefinish = pos[0] + 1;
            }
        }
    }
}
