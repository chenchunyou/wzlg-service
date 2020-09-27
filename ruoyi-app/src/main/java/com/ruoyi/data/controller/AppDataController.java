package com.ruoyi.data.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.data.domain.Student;
import com.ruoyi.data.exception.LoginException;
import com.ruoyi.data.service.IAppDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * APP数据Controller
 *
 * @author chunyou
 * @date 2020-09-02
 */
@RestController
@RequestMapping("/app/data")
public class AppDataController {

    @Autowired
    private IAppDataService appDataService;

    /**
     * 获取学生数据
     */
    //@PreAuthorize("@ss.hasPermi('app:version:list')")
    @PostMapping("/student")
    public AjaxResult getStudentData(Student student)
    {
        Student retStudent;
        try {
            retStudent = appDataService.getStudentData(student);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.error(500, "服务器异常了哦，稍后再试一下吧");
        } catch (LoginException e) {
            e.printStackTrace();
            return AjaxResult.error(300, e.getMessage());
        }
        return AjaxResult.success("登录成功！", retStudent);
    }

}
