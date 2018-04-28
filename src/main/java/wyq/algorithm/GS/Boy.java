package wyq.algorithm.GS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Boy extends Participator {

    private static final Logger logger = LoggerFactory.getLogger(Boy.class);

    public void lookForLove() {
        while (this.iterator.hasNext()) {
            Girl girl = (Girl) this.iterator.next();
            logger.info("{}: request love from {}", this, girl);
            this.myLove = girl;
            if (girl.love(this)) {
                logger.info("{}: {} accepted me!", this, girl);
                return;
            } else {
                logger.info("{}: {} rejected me.", this, " rejected me.");
                this.blacknameList.add(girl);
            }
        }
        this.myLove = null;
        logger.info("{}: no more girls.", this);
    }

    public void breakup() {
        logger.info("{}: broke up with {}, looking for new love.", this, this.myLove);
        this.blacknameList.add(this.myLove);
        this.myLove = null;
        lookForLove();
    }
}
