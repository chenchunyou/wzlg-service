package com.ruoyi.version.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * APP版本信息对象 app_version_info
 * 
 * @author chunyou
 * @date 2020-09-01
 */
public class AppVersionInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增主键id */
    private Long versionId;

    /** 应用包名 */
    @Excel(name = "应用包名")
    private String appPackage;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long versionCode;

    /** 版本名称 */
    @Excel(name = "版本名称")
    private String versionName;

    /** 更新内容 */
    @Excel(name = "更新内容")
    private String updateContent;

    /** 强制更新标识（0-非强制，1-强制） */
    @Excel(name = "强制更新标识", readConverterExp = "0=-非强制，1-强制")
    private Long updateRequired;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private Long fileSize;

    /** 文件下载地址 */
    @Excel(name = "文件下载地址")
    private String downloadUrl;

    public void setVersionId(Long versionId) 
    {
        this.versionId = versionId;
    }

    public Long getVersionId() 
    {
        return versionId;
    }
    public void setAppPackage(String appPackage) 
    {
        this.appPackage = appPackage;
    }

    public String getAppPackage() 
    {
        return appPackage;
    }
    public void setVersionCode(Long versionCode) 
    {
        this.versionCode = versionCode;
    }

    public Long getVersionCode() 
    {
        return versionCode;
    }
    public void setVersionName(String versionName) 
    {
        this.versionName = versionName;
    }

    public String getVersionName() 
    {
        return versionName;
    }
    public void setUpdateContent(String updateContent) 
    {
        this.updateContent = updateContent;
    }

    public String getUpdateContent() 
    {
        return updateContent;
    }
    public void setUpdateRequired(Long updateRequired) 
    {
        this.updateRequired = updateRequired;
    }

    public Long getUpdateRequired() 
    {
        return updateRequired;
    }
    public void setFileSize(Long fileSize) 
    {
        this.fileSize = fileSize;
    }

    public Long getFileSize() 
    {
        return fileSize;
    }
    public void setDownloadUrl(String downloadUrl) 
    {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() 
    {
        return downloadUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("versionId", getVersionId())
            .append("appPackage", getAppPackage())
            .append("versionCode", getVersionCode())
            .append("versionName", getVersionName())
            .append("updateContent", getUpdateContent())
            .append("updateRequired", getUpdateRequired())
            .append("fileSize", getFileSize())
            .append("downloadUrl", getDownloadUrl())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
