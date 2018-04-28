package wyq.algorithm.GS.mulitthread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wyq.algorithm.GS.Participator;

public class Girl2 extends Participator implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Girl2.class);

    private BlockingQueue<LoveLetter> mailBox = new LinkedBlockingQueue<LoveLetter>();

    public BlockingQueue<LoveLetter> getMailBox() {
        return this.mailBox;
    }

    @Override
    public void run() {
        try {
            LoveLetter maxPreferred = null;
            while (!Thread.interrupted()) {
                LoveLetter mail = this.mailBox.take();
                if (maxPreferred == null) {
                    maxPreferred = mail;
                    Boy2 boy = (Boy2) maxPreferred.getWriter();
                    logger.info("{}: boy: {} is my first love.", this, boy);
                    setMyLove(boy);
                } else {
                    if (isBetter(mail, maxPreferred)) {
                        Boy2 current = (Boy2) maxPreferred.getWriter();
                        Boy2 boy = (Boy2) mail.getWriter();
                        logger.info("{}: boy: {} is better than my current: {}, breakup with current and accept him.",
                                this, boy, current);
                        maxPreferred.reject();
                        maxPreferred = mail;
                        setMyLove(boy);
                        this.getBlacknameList().add(current);
                    } else {
                        Boy2 boy = (Boy2) mail.getWriter();
                        logger.info("{}, boy: {} is not better than my current: {}, reject.", this, boy, getMyLove());
                        mail.reject();
                        this.getBlacknameList().add(boy);
                    }
                }
            }
        } catch (InterruptedException e) {
            logger.warn(this + ": interrupted...");
        }
    }

    private boolean isBetter(LoveLetter a, LoveLetter b) {
        int ascore = this.getPreferenceList().indexOf(a);
        int bscore = this.getPreferenceList().indexOf(b);

        return ascore >= 0 && ascore < bscore;

    }

}
