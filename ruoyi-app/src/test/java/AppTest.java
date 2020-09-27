import com.ruoyi.common.utils.sign.Md5Utils;
import com.ruoyi.data.domain.Course;
import com.ruoyi.data.domain.Student;
import com.ruoyi.data.domain.Week;
import com.ruoyi.data.exception.LoginException;
import com.ruoyi.data.service.impl.AppDataServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import sun.security.provider.MD5;
import sun.security.provider.SHA;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

public class AppTest {

    @Test
    public void test() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int weekYear = calendar.get(Calendar.WEEK_OF_YEAR);
        System.out.println(weekYear);
    }
}
