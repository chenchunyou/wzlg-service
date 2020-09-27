package com.ruoyi.version.mapper;

import java.util.List;
import com.ruoyi.version.domain.AppVersionInfo;
import org.springframework.stereotype.Component;

/**
 * APP版本信息Mapper接口
 * 
 * @author chunyou
 * @date 2020-09-01
 */
@Component
public interface AppVersionInfoMapper 
{
    /**
     * 查询APP版本信息
     * 
     * @param versionId APP版本信息ID
     * @return APP版本信息
     */
    public AppVersionInfo selectAppVersionInfoById(Long versionId);

    /**
     * 查询APP版本信息列表
     * 
     * @param appVersionInfo APP版本信息
     * @return APP版本信息集合
     */
    public List<AppVersionInfo> selectAppVersionInfoList(AppVersionInfo appVersionInfo);

    /**
     * 新增APP版本信息
     * 
     * @param appVersionInfo APP版本信息
     * @return 结果
     */
    public int insertAppVersionInfo(AppVersionInfo appVersionInfo);

    /**
     * 修改APP版本信息
     * 
     * @param appVersionInfo APP版本信息
     * @return 结果
     */
    public int updateAppVersionInfo(AppVersionInfo appVersionInfo);

    /**
     * 删除APP版本信息
     * 
     * @param versionId APP版本信息ID
     * @return 结果
     */
    public int deleteAppVersionInfoById(Long versionId);

    /**
     * 批量删除APP版本信息
     * 
     * @param versionIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAppVersionInfoByIds(Long[] versionIds);

    /**
     *
     * 获取APP最新版本
     *
     * @return APP版本信息
     */
    public AppVersionInfo selectLatestAppVersionInfo();
}
