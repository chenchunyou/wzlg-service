package com.ruoyi.data.service.impl;

import com.ruoyi.data.domain.Course;
import com.ruoyi.data.domain.CourseTable;
import com.ruoyi.data.domain.Student;
import com.ruoyi.data.exception.LoginException;
import com.ruoyi.data.service.IAppDataService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.io.IOException;

/**
 * APP数据Service业务层处理
 *
 * @author chunyou
 * @date 2020-09-02
 */
@Service
public class AppDataServiceImpl implements IAppDataService {

    private final String WEBFINGER = "f7cfaa7f4274cae1835386e81e3071fa";

    @Override
    public Student getStudentData(Student student) throws IOException, LoginException {
        return login(student, getCode());
    }

    // 获取验证码
    private String getCode() throws IOException {
        String url = "http://sso.jwc.whut.edu.cn/Certification/getCode.do";
        String WEBFINGER_KEY = "webfinger";
        Connection connect = Jsoup.connect(url);
        connect.data(WEBFINGER_KEY, WEBFINGER);
        Document document = connect.post();
        return document.body().html();
    }

    // 登录
    private Student login(Student student, String code) throws IOException, LoginException {
        String url = "http://sso.jwc.whut.edu.cn/Certification/login.do";
        final String CODE = "code";
        final String USERNAME1 = "userName1";
        final String PASSWORD1 = "password1";
        final String WEBFINGER_KEY = "webfinger";
        final String PASSWORD_KEY = "password";
        final String TYPE = "type";
        final String XS = "xs";
        final String PASSWORD = "qwer123456";
        Connection connect = Jsoup.connect(url);
        connect.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36")
                .header("Referer", "http://sso.jwc.whut.edu.cn/Certification/toLogin.do")
                .header("Origin", "http://sso.jwc.whut.edu.cn");
        connect.data(CODE, code)
                .data(USERNAME1, student.getStudentNumber())
                .data(PASSWORD1, student.getPassword())
                .data(WEBFINGER_KEY, WEBFINGER)
                .data(PASSWORD_KEY, PASSWORD)
                .data(TYPE, XS);
        Document document = connect.post();

        Elements select = document.body().select("script[language=\"javascript\"]");
        if (select.size() > 0) {
            String html = select.get(select.size() - 1).html();
            throw new LoginException(html.substring(19, html.length() - 3));
        } else {
            System.out.println("登陆成功！");
            // 解析并封装数据
            return parseData(student, document);
        }
    }

    private Student parseData(Student student, Document document) {
        Elements select = document.body().select("div[class=\"main-per-name\"]").select("p").select("b");
        student.setName(select.text());
        select = document.body().select("ul[class=\"clearfix\"]").select("span");
        student.setPhone(select.get(1).text());
        select = document.body().select("td[style=\"text-align: center\"]");
        int ktIndex = 0;
        CourseTable courseTable = new CourseTable();
        for (Element element : select) {
            Elements divs = element.select("div");
            if (divs.size() == 0) {
                ktIndex++;
                continue;
            }
            for (Element div : divs) {
                Elements a = div.select("a");
                // 解析a标签内容
                String[] strings = a.text().split(" ");
                int beginWeek = Integer.parseInt(strings[2].substring(2, 4));
                int endWeek = Integer.parseInt(strings[2].substring(5, 7));
                boolean isLongCourse = strings[2].contains("3-5节") || strings[2].contains("6-8节") || strings[2].contains("11-13节");
                for (int i = beginWeek - 1;i < endWeek; i++) {
                    Course course = courseTable.getWeeks().get(i).getCourses().get(ktIndex);
                    course.setName(strings[0]);
                    course.setPlace(strings[1]);
                    course.setLongCourse(isLongCourse);
                }
            }
            ktIndex++;
        }
        student.setCourseTable(courseTable);
        return student;
    }
}
