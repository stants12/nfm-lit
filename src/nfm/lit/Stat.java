package nfm.lit;
/**
 * contrary to popular belief, this class was not jacked from src, but developed alongside it, for different, yet resemblant, cases.
 *
 * @author eli
 */
public class Stat {

    /**
     * the stats
     */

    public float acelf[] = new float[3];

    public int airc = 0;

    public float airs = 0F;

    public float bounce = 0F;

    public int clrad = 0;

    public float comprad = 0F;

    public float dammult = 0F;

    public int flipy = 0;

    public float grip = 0F;

    public int handb = 0;

    public int lift = 0;

    public int maxmag = 0;

    public float moment = 0;

    public int msquash = 0;

    public int powerloss = 0;

    public int push = 0;

    public int revlift = 0;

    public int revpush = 0;

    public float simag = 0F;

    public int swits[] = new int[3];

    public int turn = 0;

    public float dishandle = 0F;

    public float outdam = 0F;

    public int engine = 0;

    /**
     * set up a new stat
     *
     * @param car the car
     */
    public Stat(final int car, ContO aconto) {
        acelf = (car >= 0 && car < StatList.acelf.length && StatList.acelf[car] != null) ? StatList.acelf[car].clone() : aconto.acelf;
        swits = (car >= 0 && car < StatList.swits.length && StatList.swits[car] != null) ? StatList.swits[car].clone() : aconto.swits;
        airc = (car >= 0 && car < StatList.airc.length && StatList.airc[car] != 0) ? StatList.airc[car] : aconto.airc;
        airs = (car >= 0 && car < StatList.airs.length && StatList.airs[car] != 0F) ? StatList.airs[car] : aconto.airs;
        bounce = (car >= 0 && car < StatList.bounce.length && StatList.bounce[car] != 0F) ? StatList.bounce[car] : aconto.bounce;
        clrad = (car >= 0 && car < StatList.clrad.length && StatList.clrad[car] != 0) ? StatList.clrad[car] : aconto.clrad;
        comprad = (car >= 0 && car < StatList.comprad.length && StatList.comprad[car] != 0F) ? StatList.comprad[car] : aconto.comprad;
        dammult = (car >= 0 && car < StatList.dammult.length && StatList.dammult[car] != 0F) ? StatList.dammult[car] : aconto.dammult;
        flipy = (car >= 0 && car < StatList.flipy.length && StatList.flipy[car] != 0) ? StatList.flipy[car] : aconto.flipy;
        grip = (car >= 0 && car < StatList.grip.length && StatList.grip[car] != 0F) ? StatList.grip[car] : aconto.grip;
        handb = (car >= 0 && car < StatList.handb.length && StatList.handb[car] != 0) ? StatList.handb[car] : aconto.handb;
        lift = (car >= 0 && car < StatList.lift.length && StatList.lift[car] != 0) ? StatList.lift[car] : aconto.lift;
        maxmag = (car >= 0 && car < StatList.maxmag.length && StatList.maxmag[car] != 0) ? StatList.maxmag[car] : aconto.maxmag;
        moment = (car >= 0 && car < StatList.moment.length && StatList.moment[car] != 0F) ? StatList.moment[car] : aconto.moment;
        msquash = (car >= 0 && car < StatList.msquash.length && StatList.msquash[car] != 0) ? StatList.msquash[car] : aconto.msquash;
        powerloss = (car >= 0 && car < StatList.powerloss.length && StatList.powerloss[car] != 0) ? StatList.powerloss[car] : aconto.powerloss;
        push = (car >= 0 && car < StatList.push.length && StatList.push[car] != 0) ? StatList.push[car] : aconto.push;
        revlift = (car >= 0 && car < StatList.revlift.length && StatList.revlift[car] != 0) ? StatList.revlift[car] : aconto.revlift;
        revpush = (car >= 0 && car < StatList.revpush.length && StatList.revpush[car] != 0) ? StatList.revpush[car] : aconto.revpush;
        turn = (car >= 0 && car < StatList.turn.length && StatList.turn[car] != 0) ? StatList.turn[car] : aconto.turn;
        simag = (car >= 0 && car < StatList.simag.length && StatList.simag[car] != 0F) ? StatList.simag[car] : aconto.simag;
        outdam = (car >= 0 && car < StatList.outdam.length && StatList.outdam[car] != 0F) ? StatList.outdam[car] : aconto.outdam;
        dishandle = (car >= 0 && car < StatList.dishandle.length && StatList.dishandle[car] != 0F) ? StatList.dishandle[car] : aconto.dishandle;
        engine = (car >= 0 && car < StatList.engine.length && StatList.engine[car] != 0) ? StatList.engine[car] : aconto.engine;
    }

    public Stat(){
    }

}
