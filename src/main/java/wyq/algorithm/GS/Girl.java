package wyq.algorithm.GS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Girl extends Participator {

    private static final Logger logger = LoggerFactory.getLogger(Girl.class);

    public boolean love(Participator boy) {
        if (this.myLove == null) {
            this.myLove = boy;
            logger.info("{}: {} is my first love, accept.", this, boy);
            return true;
        } else {
            int myLoveIndex = this.preferenceList.indexOf(this.myLove);
            int newLoveIndex = this.preferenceList.indexOf(boy);

            if (newLoveIndex >= 0 && newLoveIndex < myLoveIndex) {
                logger.info("{}: {} is better than my current love: {}, breakup with current.", this, boy, this.myLove);
                Boy ex = (Boy) this.myLove;
                this.blacknameList.add(ex);
                this.myLove = boy;
                ex.breakup();
                return true;
            } else {
                logger.info("{}: {} is not better than my current love: {}, reject.", this, boy, this.myLove);
                return false;
            }
        }
    }

}
