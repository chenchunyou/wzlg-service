package com.ruoyi.version.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.version.domain.AppVersionInfo;
import com.ruoyi.version.service.IAppVersionInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * APP版本信息Controller
 * 
 * @author chunyou
 * @date 2020-09-01
 */
@RestController
@RequestMapping("/app/version")
public class AppVersionInfoController extends BaseController
{
    @Autowired
    private IAppVersionInfoService appVersionInfoService;

    /**
     * 查询APP版本信息列表
     */
    @PreAuthorize("@ss.hasPermi('app:version:list')")
    @GetMapping("/list")
    public TableDataInfo list(AppVersionInfo appVersionInfo)
    {
        startPage();
        List<AppVersionInfo> list = appVersionInfoService.selectAppVersionInfoList(appVersionInfo);
        return getDataTable(list);
    }

    /**
     * 导出APP版本信息列表
     */
    @PreAuthorize("@ss.hasPermi('app:version:export')")
    @Log(title = "APP版本信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AppVersionInfo appVersionInfo)
    {
        List<AppVersionInfo> list = appVersionInfoService.selectAppVersionInfoList(appVersionInfo);
        ExcelUtil<AppVersionInfo> util = new ExcelUtil<AppVersionInfo>(AppVersionInfo.class);
        return util.exportExcel(list, "version");
    }

    /**
     * 获取APP版本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('app:version:query')")
    @GetMapping(value = "/{versionId}")
    public AjaxResult getInfo(@PathVariable("versionId") Long versionId)
    {
        return AjaxResult.success(appVersionInfoService.selectAppVersionInfoById(versionId));
    }

    /**
     * 新增APP版本信息
     */
    @PreAuthorize("@ss.hasPermi('app:version:add')")
    @Log(title = "APP版本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AppVersionInfo appVersionInfo)
    {
        return toAjax(appVersionInfoService.insertAppVersionInfo(appVersionInfo));
    }

    /**
     * 修改APP版本信息
     */
    @PreAuthorize("@ss.hasPermi('app:version:edit')")
    @Log(title = "APP版本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AppVersionInfo appVersionInfo)
    {
        return toAjax(appVersionInfoService.updateAppVersionInfo(appVersionInfo));
    }

    /**
     * 删除APP版本信息
     */
    @PreAuthorize("@ss.hasPermi('app:version:remove')")
    @Log(title = "APP版本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{versionIds}")
    public AjaxResult remove(@PathVariable Long[] versionIds)
    {
        return toAjax(appVersionInfoService.deleteAppVersionInfoByIds(versionIds));
    }

    /**
     * 获取APP最新版本
     */
    //@PreAuthorize("@ss.hasPermi('app:version:query')")
    @GetMapping("/latest")
    public AjaxResult getLatestAppVersionInfo()
    {
        return AjaxResult.success(appVersionInfoService.selectLatestAppVersionInfo());
    }
}
