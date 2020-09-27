package com.ruoyi.data.service;

import com.ruoyi.data.domain.Student;
import com.ruoyi.data.exception.LoginException;

import java.io.IOException;

/**
 * APP数据Service接口
 *
 * @author chunyou
 * @date 2020-09-02
 */
public interface IAppDataService {

    /**
     * 获取学生数据
     * @param student
     * @return
     */
    Student getStudentData(Student student) throws IOException, LoginException;
}
