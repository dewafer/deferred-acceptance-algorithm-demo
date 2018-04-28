package wyq.algorithm.GS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class Participator {

    protected List<Participator> preferenceList = new ArrayList<Participator>();

    public List<Participator> getPreferenceList() {
        return this.preferenceList;
    }

    protected Iterator<Participator> iterator;

    public List<Participator> createPreferenceList(
            Collection<? extends Participator> allObjects) {
        this.preferenceList.addAll(allObjects);
        Collections.shuffle(this.preferenceList);
        this.iterator = this.preferenceList.iterator();
        return this.preferenceList;
    }

    protected String name;
    protected Participator myLove = null;

    public synchronized Participator getMyLove() {
        return this.myLove;
    }

    public synchronized void setMyLove(Participator myLove) {
        this.myLove = myLove;
    }

    protected List<Participator> blacknameList = new ArrayList<Participator>();

    public synchronized List<Participator> getBlacknameList() {
        return this.blacknameList;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double happiness() {
        return (this.preferenceList.contains(this.myLove)) ? ((double) (this.preferenceList
                .size() - this.preferenceList.indexOf(this.myLove)) / (double) this.preferenceList
                .size()) * 100f
                : 0f;
    }
}
