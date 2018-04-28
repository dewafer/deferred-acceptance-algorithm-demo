package wyq.algorithm.GS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {

    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    protected List<Participator> allParticipators = new ArrayList<Participator>();
    protected List<Boy> allBoys;
    protected List<Girl> allGirls;
    protected ParticipatorFactory factory = new ParticipatorFactory();
    protected double boysAverageHappiness = 0f;
    protected double girlsAverageHappiness = 0f;
    protected double allAverageHappiness = 0f;

    /**
     * @param args
     */
    public static void main(String[] args) {
        new Game().run();
        // @formatter:off
        // multi-run
//        for (int i = 0; i < 10; i++) {
//            final long start = System.currentTimeMillis();
//            final int counti = i;
//            new Thread(()-> {
//                String gameName = "game" + (counti + 1);
//                logger.info("Running {}..." , gameName);
//                demonstrator(gameName);
//                logger.info("{}: {} ms. used...", gameName ,
//                         (System.currentTimeMillis() - start));
//            }).start();
//        }
        // @formatter:on
    }

    private static void demonstrator(String gameName) {
        // no log

        // 2000 participators
        ParticipatorFactory.MAX_NUM_BOYS = 100;
        ParticipatorFactory.MAX_NUM_GIRLS = 100;

        int MAX_TRY = 100;
        double[] boysHappinessPoints = new double[MAX_TRY];
        double[] girlsHappinessPoints = new double[MAX_TRY];
        double[] allHappinessPoints = new double[MAX_TRY];
        for (int i = 0; i < MAX_TRY; i++) {
            Game game = new Game();
            game.run();
            boysHappinessPoints[i] = game.boysAverageHappiness;
            girlsHappinessPoints[i] = game.girlsAverageHappiness;
            allHappinessPoints[i] = game.allAverageHappiness;
            System.gc();
        }

        logger.info("{}:boys average happiness point:{}", gameName, average(boysHappinessPoints));
        logger.info("{}:girls average happiness point:{}", gameName, average(girlsHappinessPoints));
        logger.info("{}:all average happiness point:{}", gameName, average(allHappinessPoints));

    }

    private static double average(double[] nums) {
        return Arrays.stream(nums).average().getAsDouble();
    }

    private void run() {
        this.allBoys = this.factory.createBoyList(10);
        this.allGirls = this.factory.createGirlList(10);
        this.allParticipators.addAll(this.allBoys);
        this.allParticipators.addAll(this.allGirls);

        for (Participator p : this.allParticipators) {
            if (p instanceof Boy) {
                p.createPreferenceList(this.allGirls);
            }
            if (p instanceof Girl) {
                p.createPreferenceList(this.allBoys);
            }
        }

        Collections.shuffle(this.allParticipators);

        logger.info("all participators:{}", this.allParticipators);
        for (Participator p : this.allParticipators) {
            if (p instanceof Boy) {
                Boy boy = (Boy) p;
                boy.lookForLove();
            }
        }

        logger.info("------------ GAME OVER : RESULT ------------");
        double happiness = 0f;
        double allHappiness = 0f;
        for (Participator p : this.allBoys) {
            logParticipator(p);
            happiness += p.happiness();
            allHappiness += p.happiness();
        }
        this.boysAverageHappiness = happiness / this.allBoys.size();
        logger.info("Boys' average happiness:{}", this.boysAverageHappiness);

        logger.info("--------------------------------------------");
        happiness = 0f;
        for (Participator p : this.allGirls) {
            logParticipator(p);
            happiness += p.happiness();
            allHappiness += p.happiness();
        }
        this.girlsAverageHappiness = happiness / this.allGirls.size();
        logger.info("Girls' average happiness:{}", this.girlsAverageHappiness);

        logger.info("--------------------------------------------");
        this.allAverageHappiness = allHappiness / this.allParticipators.size();
        logger.info("All happiness average:{}", this.allAverageHappiness);

    }

    private void logParticipator(Participator p) {
        logger.info("{}, [myLove: {}, happiness: {}, blackName: {}, myPref: {}]", p, p.myLove, p.happiness(),
                p.blacknameList,
                p.preferenceList);

    }

}
