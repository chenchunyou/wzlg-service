<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="应用包名" prop="appPackage">
        <el-input
          v-model="queryParams.appPackage"
          placeholder="请输入应用包名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本号" prop="versionCode">
        <el-input
          v-model="queryParams.versionCode"
          placeholder="请输入版本号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本名称" prop="versionName">
        <el-input
          v-model="queryParams.versionName"
          placeholder="请输入版本名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="强制更新" prop="updateRequired">
        <el-select v-model="queryParams.updateRequired" placeholder="请选择强制更新标识" clearable size="small">
          <el-option
            v-for="dict in updateRequiredOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['app:version:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['app:version:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['app:version:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['app:version:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="versionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="自增主键id" align="center" prop="versionId" />
      <el-table-column label="应用包名" align="center" prop="appPackage" />
      <el-table-column label="版本号" align="center" prop="versionCode" />
      <el-table-column label="版本名称" align="center" prop="versionName" />
      <el-table-column label="更新内容" align="center" prop="updateContent" />
      <el-table-column label="强制更新" align="center" prop="updateRequired" :formatter="updateRequiredFormat" />
      <el-table-column label="文件大小" align="center" prop="fileSize" />
      <el-table-column label="下载地址" align="center" prop="downloadUrl" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['app:version:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['app:version:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 修改APP版本信息对话框 -->
    <el-dialog :title="title" :visible.sync="openUpdate" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="应用包名" prop="appPackage">
          <el-input v-model="form.appPackage" placeholder="请输入应用包名" />
        </el-form-item>
        <el-form-item label="版本号" prop="versionCode">
          <el-input v-model="form.versionCode" placeholder="请输入版本号" />
        </el-form-item>
        <el-form-item label="版本名称" prop="versionName">
          <el-input v-model="form.versionName" placeholder="请输入版本名称" />
        </el-form-item>
        <el-form-item label="更新内容" prop="updateContent">
          <el-input v-model="form.updateContent" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="强制更新">
          <el-radio-group v-model="form.updateRequired">
            <el-radio
              v-for="dict in updateRequiredOptions"
              :key="dict.dictValue"
              :label="parseInt(dict.dictValue)"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="文件大小" prop="fileSize">
          <el-input v-model="form.fileSize" placeholder="请输入文件大小" />
        </el-form-item>
        <el-form-item label="下载地址" prop="downloadUrl">
          <el-input v-model="form.downloadUrl" placeholder="请输入文件下载地址" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancelUpdate">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加APP版本对话框 -->
    <el-dialog :title="title" :visible.sync="openAdd" width="500px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".apk"
        :action="upload.url"
        :headers="upload.headers"
        :file-list="upload.fileList"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;" size="small" type="success" :loading="upload.isUploading" @click="submitUpload">上传到服务器</el-button>
        <div slot="tip" class="el-upload__tip">请上传对应的apk文件</div>
      </el-upload>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="应用包名" prop="appPackage">
          <el-input v-model="form.appPackage" placeholder="请输入应用包名" />
        </el-form-item>
        <el-form-item label="版本号" prop="versionCode">
          <el-input v-model="form.versionCode" placeholder="请输入版本号" />
        </el-form-item>
        <el-form-item label="版本名称" prop="versionName">
          <el-input v-model="form.versionName" placeholder="请输入版本名称" />
        </el-form-item>
        <el-form-item label="更新内容" prop="updateContent">
          <el-input v-model="form.updateContent" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="强制更新">
          <el-radio-group v-model="form.updateRequired">
            <el-radio
              v-for="dict in updateRequiredOptions"
              :key="dict.dictValue"
              :label="parseInt(dict.dictValue)"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="文件大小" prop="fileSize">
          <el-input v-model="form.fileSize" placeholder="文件大小" :disabled="true" />
        </el-form-item>
        <el-form-item label="下载地址" prop="downloadUrl">
          <el-input v-model="form.downloadUrl" placeholder="文件下载地址" :disabled="true" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancelAdd">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listVersion, getVersion, delVersion, addVersion, updateVersion, exportVersion } from "@/api/app/version";
import { getToken } from "@/utils/auth";
export default {
  name: "Version",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // APP版本信息表格数据
      versionList: [],
      // 弹出层标题
      title: "",
      // 是否显示（添加）弹出层
      openAdd: false,
      // 是否显示（修改）弹出层
      openUpdate: false,
      // 强制更新标识字典
      updateRequiredOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        appPackage: null,
        versionCode: null,
        versionName: null,
        updateContent: null,
        updateRequired: null,
        fileSize: null,
        downloadUrl: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        appPackage: [
          { required: true, message: "应用包名不能为空", trigger: "blur" }
        ],
        versionCode: [
          { required: true, message: "版本号不能为空", trigger: "blur" }
        ],
        versionName: [
          { required: true, message: "版本名称不能为空", trigger: "blur" }
        ],
        updateContent: [
          { required: true, message: "更新内容不能为空", trigger: "blur" }
        ],
        updateRequired: [
          { required: true, message: "强制更新标识（0-非强制，1-强制）不能为空", trigger: "blur" }
        ],
        fileSize: [
          { required: true, message: "文件大小不能为空", trigger: "blur" }
        ],
        downloadUrl: [
          { required: true, message: "下载地址不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "修改时间不能为空", trigger: "blur" }
        ]
      },
      // 上传参数
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/common/upload",
        // 上传的文件列表
        fileList: []
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("version_update_required").then(response => {
      this.updateRequiredOptions = response.data;
    });
  },
  methods: {
    /** 查询APP版本信息列表 */
    getList() {
      this.loading = true;
      listVersion(this.queryParams).then(response => {
        this.versionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 强制更新标识字典翻译
    updateRequiredFormat(row, column) {
      return this.selectDictLabel(this.updateRequiredOptions, row.updateRequired);
    },
    // 取消（添加）按钮
    cancelAdd() {
      this.openAdd = false;
      this.reset();
    },
    // 取消（修改）按钮
    cancelUpdate() {
      this.openUpdate = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        versionId: null,
        appPackage: null,
        versionCode: null,
        versionName: null,
        updateContent: null,
        updateRequired: 0,
        fileSize: null,
        downloadUrl: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.versionId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.openAdd = true;
      this.title = "添加APP版本信息";
      this.upload.fileList = [];
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const versionId = row.versionId || this.ids
      getVersion(versionId).then(response => {
        this.form = response.data;
        this.openUpdate = true;
        this.title = "修改APP版本信息";
      });
      this.upload.fileList = [{ name: this.form.fileName, url: this.form.filePath }];
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.versionId != null) {
            updateVersion(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.openUpdate = false;
                this.getList();
              }
            });
          } else {
            addVersion(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.openAdd = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const versionIds = row.versionId || this.ids;
      this.$confirm('是否确认删除APP版本信息编号为"' + versionIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delVersion(versionIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有APP版本信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportVersion(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    },
    // 文件提交处理
    submitUpload() {
      this.$refs.upload.submit();
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.isUploading = false;
      // this.form.filePath = response.url;
      this.msgSuccess(response.msg);
      this.form.downloadUrl = response.url;
      this.form.fileSize = response.fileSize;
    }
  }
};
</script>
