package wyq.algorithm.GS.mulitthread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wyq.algorithm.GS.Participator;

public class Boy2 extends Participator implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Boy2.class);

    @Override
    public void run() {
        try {
            while (!Thread.interrupted() && this.iterator.hasNext()) {
                Girl2 girl = (Girl2) this.iterator.next();
                logger.info("{}: pursuit girl: {}", this, girl);
                setMyLove(girl);
                LoveLetter mail = new LoveLetter(this);
                girl.getMailBox().offer(mail);
                while (!mail.isRejected()) {
                    logger.info("{}: I'm waiting for girl: {}", this, girl);
                    synchronized (this) {
                        wait();
                    }
                }
                if (mail.isRejected()) {
                    logger.info("{}: rejected by girl: {}, pursuit next.", this, girl);
                    this.getBlacknameList().add(girl);
                }
            }
            if (!this.iterator.hasNext()) {
                logger.info("{}: no more girls.", this);
            }
        } catch (InterruptedException e) {
            logger.info("{}: interrupted.", this);
        }
    }

}
