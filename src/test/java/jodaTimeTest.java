import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2014/8/6.
 */
public class jodaTimeTest {
   private static final DateTime dt = new DateTime("2013-09-12");
    public static void main(String[] args){
        DateTime dt = new DateTime();
        List<String> allDate = new ArrayList<>();
        for(int i=0;i<6;i++){
            DateTime dt1 = dt.minusDays(i);
            String dt2 = dt1.toString("yyyy-MM-dd");
            allDate.add(dt2);
        }
        List<String> inDate = new ArrayList<>();
        inDate.add("2014-08-01");
        inDate.add("2014-08-05");
        allDate.removeAll(inDate);
        for(String s:allDate){
            System.out.println(s);
        }

        String endTime = "2014-08-12";
        DateTime dt3 = new DateTime(endTime);
        System.out.println(dt3.isBeforeNow());
    }
}
