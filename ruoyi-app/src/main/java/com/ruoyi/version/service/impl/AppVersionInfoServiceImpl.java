package com.ruoyi.version.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.version.mapper.AppVersionInfoMapper;
import com.ruoyi.version.domain.AppVersionInfo;
import com.ruoyi.version.service.IAppVersionInfoService;

/**
 * APP版本信息Service业务层处理
 * 
 * @author chunyou
 * @date 2020-09-01
 */
@Service
public class AppVersionInfoServiceImpl implements IAppVersionInfoService 
{
    @Autowired
    private AppVersionInfoMapper appVersionInfoMapper;

    /**
     * 查询APP版本信息
     * 
     * @param versionId APP版本信息ID
     * @return APP版本信息
     */
    @Override
    public AppVersionInfo selectAppVersionInfoById(Long versionId)
    {
        return appVersionInfoMapper.selectAppVersionInfoById(versionId);
    }

    /**
     * 查询APP版本信息列表
     * 
     * @param appVersionInfo APP版本信息
     * @return APP版本信息
     */
    @Override
    public List<AppVersionInfo> selectAppVersionInfoList(AppVersionInfo appVersionInfo)
    {
        return appVersionInfoMapper.selectAppVersionInfoList(appVersionInfo);
    }

    /**
     * 新增APP版本信息
     * 
     * @param appVersionInfo APP版本信息
     * @return 结果
     */
    @Override
    public int insertAppVersionInfo(AppVersionInfo appVersionInfo)
    {
        appVersionInfo.setCreateTime(DateUtils.getNowDate());
        return appVersionInfoMapper.insertAppVersionInfo(appVersionInfo);
    }

    /**
     * 修改APP版本信息
     * 
     * @param appVersionInfo APP版本信息
     * @return 结果
     */
    @Override
    public int updateAppVersionInfo(AppVersionInfo appVersionInfo)
    {
        appVersionInfo.setUpdateTime(DateUtils.getNowDate());
        return appVersionInfoMapper.updateAppVersionInfo(appVersionInfo);
    }

    /**
     * 批量删除APP版本信息
     * 
     * @param versionIds 需要删除的APP版本信息ID
     * @return 结果
     */
    @Override
    public int deleteAppVersionInfoByIds(Long[] versionIds)
    {
        return appVersionInfoMapper.deleteAppVersionInfoByIds(versionIds);
    }

    /**
     * 删除APP版本信息信息
     * 
     * @param versionId APP版本信息ID
     * @return 结果
     */
    @Override
    public int deleteAppVersionInfoById(Long versionId)
    {
        return appVersionInfoMapper.deleteAppVersionInfoById(versionId);
    }

    /**
     *
     * 获取APP最新版本
     *
     * @return APP版本信息
     */
    @Override
    public AppVersionInfo selectLatestAppVersionInfo() {
        return appVersionInfoMapper.selectLatestAppVersionInfo();
    }


}
