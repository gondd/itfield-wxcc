<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.keyword" placeholder="关键字" size="small"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getTableData" icon="el-icon-search" size="small">执行查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary"  icon="el-icon-plus" size="small"  v-on:click="addTeacher" >讲师新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="tableData" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column  label="头像">
				<template scope="scope">
					<el-image style="width: 40px;height: 40px"
							:src="scope.row.headImg"></el-image>
				</template>
			</el-table-column>
			<el-table-column prop="name" label="名称" >
			</el-table-column>
			<el-table-column prop="position" label="职位"  >
			</el-table-column>
			<el-table-column prop="tags" label="标签"  >
			</el-table-column>
			<el-table-column prop="intro" label="介绍" width="200" >
				<template slot-scope="scope">
					<el-popover trigger="hover" placement="top" >
						<div style="width: 400px;">{{ scope.row.intro }}</div>
						<div slot="reference" class="name-wrapper" >
							<el-tag >
								{{ scope.row.intro }}
							</el-tag>
						</div>
					</el-popover>
				</template>
			</el-table-column>
			<el-table-column label="操作" width="200">
				<template scope="scope">
					<el-button size="small"  @click="edit(scope.row)" icon="el-icon-edit" type="primary">编辑</el-button>
					<el-button type="danger" size="small" @click="del(scope.row)" icon="el-icon-remove">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0" icon="el-icon-remove" size="small">批量删除</el-button>
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="cur":current-page="page" :total="total" style="float:right;">
			</el-pagination>
		</el-col>
    <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="addForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="name">
          <el-input v-model="addForm.intro" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="技术栈" prop="name">
          <el-input v-model="addForm.technology" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="职位" prop="name">
          <el-input v-model="addForm.position" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item prop="headImg" v-model="addForm.headImg" label="头像">
          <el-upload
              class="upload-demo"
              action="http://localhost:10010/wxcc/common/fast/upload"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :on-success="handleSuccess"
              :file-list="fileList"
              ref="upload"
              list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="标签" prop="name">
          <el-input v-model="addForm.tags" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false" icon="el-icon-close">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading" icon="el-icon-check">提交</el-button>
      </div>
    </el-dialog>

	</section>
</template>

<script>

	export default {
		data() {
			return {
				filters: {
					keyword: ''
				},
        addFormRules: {
          name: [
            { required: true, message: '请输入姓名', trigger: 'blur' }
          ]
        },
        addFormVisible: false,
				tableData: [],
				total: 0,
				page: 1,
        cur:2,
        fileList: [],
				listLoading: false,
				sels:[],
        addLoading: false,
        addForm: {
          id:'',
          name: '',
          intro: -1,
          technology: 0,
          position: '',
          headImg: '',
          tags:''
        }
			}

		},
		methods: {
      handlePreview(file) {
        console.log(file);
      },
      handleRemove(file, fileList) {
        var filePath =file.response.data;
        console.log(filePath)
        this.$http.delete("/common/fast/?path="+filePath)
            .then(res=>{
              if(res.data.success){
                this.$message({
                  message: '删除成功!',
                  type: 'success'
                });
              }else{
                this.$message({
                  message: '删除失败!',
                  type: 'error'
                });
              }
            })

        //删除图片后移除data里面数据
        let resourcesTmp = '';
        if (fileList != null && fileList.length > 0) {
          for (var i = 0; i < fileList.length; i++) {
            if (i == 0) {
              resourcesTmp = resourcesTmp + fileList[i].response.resultObj;
            } else {
              resourcesTmp = resourcesTmp + "," + fileList[i].response.resultObj;
            }
          }
        }
        this.editForm.resources = resourcesTmp;
      },
      handleSuccess(response, file, fileList){
        console.log(file)
        console.log(fileList)
        this.addForm.headImg = response.data;
      },
			selsChange(sels){
				this.sels = sels;
			},
			//性别显示转换
			formatState: function (row, column) {
				return row.sex == 1 ? '禁用' : '启用';
			},
			handleCurrentChange(val) {
				this.page = val;
				this.getTableData();
			},
			//获取用户列表
			getTableData() {
				let para = {
          pageNo: this.page,
          pageSize:this.cur,
					keyword: this.filters.keyword
				};
				this.listLoading = true; //显示加载圈
				this.$http.post("/course/teacher/pagelist",para).then(result=>{
					this.total = result.data.data.total;
					this.tableData = result.data.data.rows;
					this.listLoading = false;  //关闭加载圈
				});
			},
			edit(row){
        this.addFormVisible = true;
        this.fileList=[];
        this.addForm = Object.assign({}, row);
        this.fileList.push({url: this.addForm.headImg})
        /*var resources = row.headImg;
         this.fileList.push({url: resources})*/
        /*this.fileList = [];
        var resources = row.resources;
        this.fileList.push({url: "http://123.207.27.208" + resourcesArr[i]})*/
			},
			del(row){
        this.$http.delete("/course/teacher/"+row.id).then(res=>{
          if(res.data.success){
            this.$message({ message: "删除成功", type: 'error' });
            this.getTableData()
          }
        })

			},
			batchRemove(){
				this.$message({ message: "功能未开放", type: 'error' });
			},
			addTeacher(){
        this.addFormVisible = true;
        for(let p in this.addForm){
          console.log(p);
          this.addForm[p] = '';
        }
        this.clearFiles()
			},
      addSubmit(){
        let para = Object.assign({}, this.addForm);
        this.$http.post("/course/teacher/save",para).then(res=>{
          console.log(res)
          if(res.data.success){
            this.addFormVisible = false;
            this.getTableData();
            this.$message({ message: "成功", type: 'error' });
            this.listLoading = false;
            this.clearFiles()
          }
        })
      },
      clearFiles(){
        this.$refs["upload"].clearFiles()
      }
		},
		mounted() {
			this.getTableData();
		}
	}

</script>

<style scoped>

</style>