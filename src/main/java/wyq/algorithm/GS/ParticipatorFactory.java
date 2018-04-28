package wyq.algorithm.GS;

import java.util.ArrayList;
import java.util.List;

import wyq.algorithm.GS.mulitthread.Boy2;
import wyq.algorithm.GS.mulitthread.Girl2;

public class ParticipatorFactory {

    public static int MAX_NUM_BOYS = 1000;
    public static int MAX_NUM_GIRLS = 1000;

    public List<Boy> createBoyList(int num) {
        return createList(Boy.class, "Boy", Math.min(num, MAX_NUM_BOYS));
    }

    public List<Girl> createGirlList(int num) {
        return createList(Girl.class, "Girl", Math.min(num, MAX_NUM_GIRLS));
    }

    public List<Boy2> createMultiThreadBoyList(int num) {
        return createList(Boy2.class, "Boy", Math.min(num, MAX_NUM_BOYS));
    }

    public List<Girl2> createMultiThreadGirlList(int num) {
        return createList(Girl2.class, "Girl", Math.min(num, MAX_NUM_GIRLS));
    }

    protected <T extends Participator> List<T> createList(Class<T> clazz,
            String Name, int MAX) {
        List<T> list = new ArrayList<>();
        try {
            for (int i = 0; i < MAX; i++) {
                T girl = clazz.newInstance();
                girl.setName(Name + i);
                list.add(girl);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}
