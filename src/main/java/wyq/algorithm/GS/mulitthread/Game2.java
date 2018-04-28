package wyq.algorithm.GS.mulitthread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wyq.algorithm.GS.Participator;
import wyq.algorithm.GS.ParticipatorFactory;

public class Game2 {

    private static final Logger logger = LoggerFactory.getLogger(Game2.class);

    protected List<Participator> allParticipators = new ArrayList<Participator>();
    protected List<Boy2> allBoys;
    protected List<Girl2> allGirls;
    protected ParticipatorFactory factory = new ParticipatorFactory();
    protected double boysAverageHappiness = 0f;
    protected double girlsAverageHappiness = 0f;
    protected double allAverageHappiness = 0f;
    private Random random = new Random();

    // mulit-thread executor
    protected ExecutorService exec = Executors.newCachedThreadPool();

    /**
     * @param args
     */
    public static void main(String[] args) {
        new Game2().run();
    }

    private void run() {
        this.allBoys = this.factory.createMultiThreadBoyList(10);
        this.allGirls = this.factory.createMultiThreadGirlList(10);
        this.allParticipators.addAll(this.allBoys);
        this.allParticipators.addAll(this.allGirls);

        for (Participator p : this.allParticipators) {
            if (p instanceof Boy2) {
                p.createPreferenceList(this.allGirls);
            }
            if (p instanceof Girl2) {
                p.createPreferenceList(this.allBoys);
            }
        }

        for (Girl2 g : this.allGirls) {
            // girls go first
            this.exec.execute(g);
        }
        for (Boy2 b : this.allBoys) {
            // boys go second
            this.exec.execute(b);
        }
        // wait for 3 seconds...
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // end the task.
            this.exec.shutdownNow();

            logger.info("all participators: {}", this.allParticipators);

            logger.info("------------ GAME OVER : RESULT ------------");
            double happiness = 0f;
            double allHappiness = 0f;
            for (Participator p : this.allBoys) {
                logParticipator(p);
                happiness += p.happiness();
                allHappiness += p.happiness();
            }
            this.boysAverageHappiness = happiness / this.allBoys.size();
            logger.info("Boys' average happiness: {}", this.boysAverageHappiness);

            logger.info("--------------------------------------------");
            happiness = 0f;
            for (Participator p : this.allGirls) {
                logParticipator(p);
                happiness += p.happiness();
                allHappiness += p.happiness();
            }
            this.girlsAverageHappiness = happiness / this.allGirls.size();
            logger.info("Girls' average happiness: {}", this.girlsAverageHappiness);

            logger.info("--------------------------------------------");
            this.allAverageHappiness = allHappiness / this.allParticipators.size();
            logger.info("All happiness average: {}", this.allAverageHappiness);

        }
    }

    private void logParticipator(Participator p) {
        logger.info("{}, [myLove: {}, happiness: {}, blackName: {}, myPref: {}]", p, p.getMyLove(), p.happiness(),
                p.getBlacknameList(),
                p.getPreferenceList());
    }

}
