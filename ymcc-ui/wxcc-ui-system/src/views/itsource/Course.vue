<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :model="filters" :inline="true">
				<el-form-item>
					<el-input v-model="filters.keywords" size="small"  placeholder="关键字" ></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="warning" v-on:click="getCourses" size="small" icon="el-icon-search">查询课程</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="addHandler" size="small" icon="el-icon-notebook-1">新增课程</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="success" @click="onLineCourse" size="small" icon="el-icon-s-promotion">课程发布</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="danger" @click="offLineCourse" size="small" icon="el-icon-download">课程下架</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="killCourseModelView" size="small" icon="el-icon-sell">加入秒杀</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表v-loading="listLoading"-->
		<el-table  :data="courses" v-loading="listLoading"  @selection-change="selsChange"
				  highlight-current-row  style="width: 100%;">
			<!--多选框-->
			<el-table-column type="selection" width="55e">
			</el-table-column>
			<!--其他都设置值,只有一个不设置值就自动适应了-->
			<el-table-column prop="name" label="课程名称" >
			</el-table-column>
			<el-table-column prop="chapterCount" label="课程数" >
			</el-table-column>
			<!--<el-table-column prop="courseType.name" label="类型">-->
			<!--</el-table-column>-->
			<el-table-column prop="gradeName" label="等级" >
			</el-table-column>
			<el-table-column prop="status" label="状态" :formatter="statusFormatter">
			</el-table-column>
			<el-table-column prop="forUser" label="适用人群" width="220">
			</el-table-column>
			<!--<el-table-column prop="tenantName" label="所属机构">-->
			<!--</el-table-column>-->
			<el-table-column prop="teacherNames" label="讲师" width="140">
			</el-table-column>
			<el-table-column label="操作" width="200">
				<template scope="scope">
					<el-button size="small"  @click="edit(scope.index,scope.row)" icon="el-icon-edit" type="primary">编辑</el-button>
					<el-button type="danger" size="small" @click="del(scope.row)" icon="el-icon-remove">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger"  @click="batchRemove" :disabled="this.sels.length===0" icon="el-icon-remove" size="small">批量删除</el-button>
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange"  :page-size="10" :total="total" style="float:right;">
			</el-pagination>
		</el-col>

		<!--新增界面-->
		<el-dialog title="新增" :visible.sync="addFormVisible"  :close-on-click-modal="false" width="860px">
			<el-form :inline="true" :model="addForm" label-width="80px"  ref="addForm">
				<el-form-item label="课程名称" prop="name" >
					<el-input v-model="addForm.name" placeholder="课程名称" auto-complete="off" style="width: 300px" />
				</el-form-item>
				<el-form-item label="适用人群" prop="forUser">
					<el-input v-model="addForm.forUser" placeholder="适用人群" auto-complete="off" style="width: 300px"/>
				</el-form-item>
				<el-form-item label="课程类型" prop="courseTypeId" >
					<el-cascader style="width: 300px"
							:props="courseTypeProps"
							v-model="addForm.courseTypeId"
							placeholder="课程类型"
							:options="courseTypes"
							expand-trigger="hover"
							:show-all-levels="false"
							filterable
							change-on-select
					></el-cascader>
				</el-form-item>
				<el-form-item label="添加讲师" prop="teachers">
					<el-select v-model="addForm.teacharIds" multiple placeholder="可选多个讲师" style="width: 300px">
						<el-option
								v-for="item in teachers"
								:key="item.id"
								:label="item.name"
								:value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="课程周期" >
					<el-date-picker  style="width: 200px"
									 v-model="addForm.startTime"
									 type="date"
									 value-format="yyyy-MM-dd"
									 size="small"
									 placeholder="课程开始日期">
					</el-date-picker> -
					<el-date-picker   style="width: 200px"
									  v-model="addForm.endTime"
									  type="date"
									  value-format="yyyy-MM-dd"
									  size="small"
									  placeholder="课程结束日期">
					</el-date-picker>
				</el-form-item >

				<el-form-item label="购买可看" >
					<el-input placeholder="可看天数"  type="number" v-model="addForm.validDays" auto-complete="off" style="width: 165px"/>&nbsp;天
				</el-form-item>

				<el-form-item label="课程等级" prop="courseTypeId"  style="width: 700px">
					<el-radio-group v-model="addForm.gradeId">
						<el-radio v-for="grade in grades" :label="grade.id">{{grade.name}}</el-radio>
					</el-radio-group>
				</el-form-item>

				<el-form-item prop="logo" style="width: 400px" >
					<!--<el-input type="text" v-model="employee.logo" auto-complete="off" placeholder="请输入logo！"></el-input>-->
					<el-upload
							class="upload-demo"
							action="http://wxcc-20220323.oss-cn-chengdu.aliyuncs.com"
							:data="uploadPicData"
              list-type="picture"
              :file-list="picfileList"
              :on-remove="handleRemove"
							:before-upload="beforePicUpload"
							:on-success="handlePicSuccess"
							:limit="1">
						<el-button size="small" type="primary"  icon="el-icon-picture-outline">上传封面</el-button>
						&nbsp;&nbsp;<span slot="tip" class="el-upload__tip">支持500kb，格式jpg</span>
					</el-upload>
				</el-form-item>

				<el-form-item prop="logo"  >
					<!--<el-input type="text" v-model="employee.logo" auto-complete="off" placeholder="请输入logo！"></el-input>-->
					<el-upload
							class="upload-demo"
							action="http://wxcc-20220323.oss-cn-chengdu.aliyuncs.com"
							:data="uploadZipData"
              :file-list="zicfileList"
							:before-upload="beforeZipUpload"
              :on-remove="handleRemove"
							:on-success="handleZipSuccess"
							:limit="1">
						<el-button size="small" type="primary" icon="el-icon-upload">上传课件</el-button>
						&nbsp;&nbsp;<span slot="tip" class="el-upload__tip">支持压缩格式</span>
					</el-upload>
				</el-form-item>
				<el-divider></el-divider>java1030

				<el-form-item label="收费规则" prop="gradeId" size="width:100%">
					<el-radio-group v-model="addForm.chargeId">
						<el-radio @change="changeCharge" v-for="charge in charges" :label="charge.id">{{charge.name}}</el-radio>
					</el-radio-group>
				</el-form-item>

				<el-form-item label="课程价格" prop="price">
					<el-input :disabled="priceDisabled" type="number" v-model="addForm.price" auto-complete="off" style="width: 185px"/>
				</el-form-item>
				<el-form-item label="课程原价">
					<el-input :disabled="priceDisabled" type="number" v-model="addForm.priceOld" auto-complete="off" style="width: 185px"/>
				</el-form-item>

				<el-form-item label="咨询QQ" prop="qq">
					<el-input v-model="addForm.qq" auto-complete="off" style="width: 150px"></el-input>
				</el-form-item>


				<el-form-item label="课程简介" prop="description">
					<el-input style="width: 450px"
							:rows="2"
							placeholder="请输入内容"
							v-model="addForm.description">
					</el-input>
				</el-form-item>

				<el-form-item label="课程详情" prop="intro">
					<div class="edit_container">
						<quill-editor
								v-model="addForm.intro"
								ref="myQuillEditor"
								class="editer"
								:options="editorOption"
								@ready="onEditorReady($event)">
						</quill-editor>
					</div>
				</el-form-item>


			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false" icon="el-icon-remove">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" icon="el-icon-check" >提交</el-button>
			</div>
		</el-dialog>



		<el-dialog title="添加秒杀课程" :visible.sync="killCourseFormVisible"  :close-on-click-modal="false">
			<el-form :model="killCourseForm" label-width="80px"  ref="addForm">
				<el-form-item label="课程名字" prop="price">
					<el-input :disabled="true"  v-model="killCourseForm.courseName" auto-complete="off"></el-input>
				</el-form-item>

				<el-form-item label="秒杀活动" prop="activityId">
					<el-select v-model="killCourseForm.activityId" placeholder="请选择秒杀活动">
						<el-option v-for="item in killActivitys"
								:key="item.id"
								:label="item.name"
								:value="item.id">
							<span style="float: left">{{ item.name }}</span>
							<span style="float: right; color: #8492a6; font-size: 13px">{{ item.timeStr }}</span>
						</el-option>
					</el-select>
					&nbsp;&nbsp;秒杀课程加入秒杀活动，秒杀时间以活动时间为准
				</el-form-item>

				<el-form-item label="秒杀价格" prop="price">
					<el-input  v-model="killCourseForm.killPrice" auto-complete="off"></el-input>
				</el-form-item>

				<el-form-item label="秒杀数量" prop="name">
					<el-input v-model="killCourseForm.killCount" auto-complete="off"></el-input>
				</el-form-item>
				<!--
				<el-form-item label="时间范围" >
					<el-date-picker
							v-model="killCourseForm.startTime"
							type="datetime"
							value-format="yyyy-MM-dd HH:mm:ss"
							size="small"
							placeholder="秒杀开始日期">
					</el-date-picker>

					<el-date-picker
							v-model="killCourseForm.endTime"
							type="datetime"
							value-format="yyyy-MM-dd HH:mm:ss"
							size="small"
							placeholder="秒杀结束日期">
					</el-date-picker>
				</el-form-item>
				-->
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="killCourseFormVisible = false" icon="el-icon-remove">取消</el-button>
				<el-button type="primary" @click.native="addKillCourseSubmit"  icon="el-icon-check">提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
    import { quillEditor } from "vue-quill-editor"; //调用编辑器
    import "quill/dist/quill.core.css"
	import "quill/dist/quill.snow.css"
	import "quill/dist/quill.bubble.css"

	export default {
        computed: {
			editor() {
                return this.$refs.myQuillEditor.quill
            }
		},
        components: {//使用编辑器
            quillEditor
        },
		data() {
			return {
				uploadPicData:{},
				uploadZipData:{},
        picfileList:[],
        zicfileList:[],
				uploadResource:{
			      	courseId:1,
					paths:[]
				},
                row:"",
                courseTypeProps:{
                    value:"id",
                    label:"name"
				},
                priceDisabled:true,
                editorOption: {},//富文本编辑框配置
			    grades:[
					{id:1,name:"青铜"},{id:2,name:"白银"},{id:3,name:"黄金"},{id:4,name:"白金"},{id:5,name:"钻石"}
				],
                charges:[
					{"id":1 , "name":"免费"},
					{"id":2 , "name":"收费"}
				],
				teachers:[],
				courseTypes:[],
        addFormVisible:false,
				//images:[xxx.jgp,xxxx,jpg,xxxx.jpg],
				killActivitys:[],
				addForm:{
          id:'',
          startTime:'',
          endTime:'',
          validDays:'',
          name:'',
          forUser:'',
          gradeId:'',
					teacharIds:'',
          courseTypeId:'',
          description:'',
          intro:'',
          chargeId:'',
          price:'',
          priceOld:'',
          qq:'',
          pic:'',
					zipResources:''
				},
        listLoading:false,
				//查询对象
				filters:{
					keywords:''
				},
				page:1,//当前页,要传递到后台的
				total:0, //分页总数
			    courses:[], //当前页数据
				sels:[],
				//秒杀相关================
				killCourseFormVisible:false,
				killCourseForm:{
					courseId:"",
					killCount:"",
					startTime:"",
					endTime:"",
					killPrice:"",
					courseName:"",
					coursePic:"",
					teacherNames:"",
					activityId:"",
				},
			}
		},
		methods: {
      handleRemove(file){
       var b=this.uploadPicData.key.split("$")
        var a=b[0]
        var c=a.split("/")
        var filename=c[1]+file.name
        console.log(filename)
        this.$http.get("/common/oss/delete/"+filename).then(res=>{

        })
      },
        	//秒杀相关
			getKillActivitys(){
				this.$http.get("/kill/killActivity/list").then(result=>{
					let {data,success,message} = result.data;
					if(success){
						this.killActivitys = data;
					}else{
						this.$message({ message: message,type: 'error'});
					}
				}).catch(error => {
					this.$message({ message: error.message,type: 'error'});
				});
			},
			killCourseModelView(){
				//获取选中的行
				if(!this.sels || this.sels.length  == 0){
					this.$message({ message: '老铁，请选择数据',type: 'error'});
					return;
				}
        if(!this.sels || this.sels.length  > 1){
          this.$message({ message: '老铁，只能选择一条数据',type: 'error'});
          return;
        }
				this.killCourseForm.killCount = "";
				this.killCourseForm.killPrice = "";
				this.killCourseForm.startTime = "";
				this.killCourseForm.endTime = "";

				this.killCourseForm.courseId = this.sels[0].id;
				this.killCourseForm.courseName = this.sels[0].name;
				this.killCourseForm.coursePic = this.sels[0].pic;
				this.killCourseForm.teacherNames = this.sels[0].teacherNames;
				this.killCourseFormVisible = true;
			},
			addKillCourseSubmit(){
        console.log(this.killCourseForm)
				this.$http.post("/kill/killCourse/save",this.killCourseForm).then(res=>{
					var ajaxResult = res.data;
					if(ajaxResult.success){
						this.$message({
							message: '加入秒杀成功，请在秒杀中心查看!',
							type: 'success'
						});
						this.killCourseFormVisible = false;
						//this.getCourses();
					}else{
						this.$message({ message: ajaxResult.message,type: 'error'});
					}
				}).catch(error=>{
					this.$message({ message: '保存失败!',type: 'error'});
				})
			},
			async getSign(data){
				//让这个请求变成同步请求
				await this.$http.get("/common/oss/sign").then(response=>{
          console.log(response)
					//设置相关的参数
					var resultObj = response.data.data;
					data.policy = resultObj.policy;
					data.signature = resultObj.signature;
					data.ossaccessKeyId = resultObj.accessid;
					data.dir = resultObj.dir;
					data.host = resultObj.host;
					//上传的文件名，使用UUID处理一下
					data.key = resultObj.dir + '/'+this.getUUID()+'_${filename}';
				});
			},
        	//文件上传===============================================================
			getUUID() {
				var s = [];
				var hexDigits = "0123456789abcdef";
				for (var i = 0; i < 36; i++) {
					s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
				}
				s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
				s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
				s[8] = s[13] = s[18] = s[23] = "-";
				var uuid = s.join("");
				return uuid;
			},
			async beforePicUpload(){
				await this.getSign(this.uploadPicData);
			},
			async beforeZipUpload(){
				await this.getSign(this.uploadZipData);
			},
			handlePicSuccess(res, file) {
				//上传的完整的文件地址 ： https://java1030.oss-cn-chengdu.aliyuncs.com/ymcc393olajewfjaolfja.jpg
				var urlPath = this.uploadPicData.host + '/' + this.uploadPicData.key.replace("${filename}",file.name) ;
				this.addForm.pic = urlPath;
				this.$message({message: '上传成功', type: 'success' });
			},
			handleZipSuccess(res, file) {
        console.log(file)
				//上传的完整的文件地址
				var urlPath = this.uploadZipData.host + '/' + this.uploadZipData.key.replace("${filename}",file.name) ;
				this.addForm.zipResources = urlPath;
				this.$message({message: '上传成功', type: 'success' });
			},
            addSubmit(){
                this.addForm.courseTypeId = this.addForm.courseTypeId[this.addForm.courseTypeId.length - 1];
				var gradeName;
				for(var i = 0 ; i < this.grades.length ; i++){
                    var grade = this.grades[i];
                    if(grade.id === this.addForm.gradeId){
                        gradeName = grade.name;
                        break;
					}
				}

                var param = {
                    course:{
                        id:this.addForm.id,
                        courseTypeId:this.addForm.courseTypeId,
                        name:this.addForm.name,
                        forUser:this.addForm.forUser,
                        gradeId:this.addForm.gradeId,
                        gradeName:gradeName,
                        pic:this.addForm.pic,
                        startTime:this.addForm.startTime,
                        endTime:this.addForm.endTime
					},
                	courseDetail:{
                        description:this.addForm.description,
                        intro:this.addForm.intro
					},
                	courseMarket:{
                        charge:this.addForm.chargeId,
                        qq:this.addForm.qq,
                        price:this.addForm.price,
                        priceOld:this.addForm.priceOld,
                        validDays:this.addForm.validDays
					},
					courseResource:{
                    	resources:this.addForm.zipResources,
						type:0	//课件
					},
					teacharIds:this.addForm.teacharIds
				};
                console.log(param)
                this.$http.post("/course/course/save",param).then(res=>{
                    var ajaxResult = res.data;
                    if(ajaxResult.success){
                        this.$message({
                            message: '保存成功!',
                            type: 'success'
                        });
                        this.addFormVisible = false;
                        this.getCourses();
                    }else{
                        this.$message({
                            message: '提交失败['+res.data.message+"]",
                            type: 'error'
                        });
                    }
				});
			},
			getGrades(){
              this.$http.get("/system/systemdictionaryitem/listBySn/dj").then(result=>{
                  this.grades = result.data.data;
              });
			},
      getCourseTypes(){
        this.$http.get("/course/courseType/treeData").then(result=>{
            this.courseTypes = result.data.data;
        });
			},
        changeCharge(chargeId){
            if(chargeId === 1){
                this.priceDisabled = true;
                this.addForm.price = "";
                this.addForm.priceOld = "";
            }else{
                this.priceDisabled = false;
            }
			},
            onEditorReady(editor) {
                //当富文本编辑框初始化好执行
            },
      addHandler(){
				this.addFormVisible = true;
        this.addForm={
          id:'',
          startTime:'',
          endTime:'',
          validDays:'',
          name:'',
          forUser:'',
          gradeId:'',
          teacharIds:'',
          courseTypeId:'',
          description:'',
          intro:'',
          chargeId:'',
          price:'',
          priceOld:'',
          qq:'',
          pic:'',
          zipResources:''
        };
        this.picfileList=[];
        this.zicfileList=[];
			},
            handleCurrentChange(curentPage){
                this.page = curentPage;
                this.getCourses();
			},
            getCourses(){
                //发送Ajax请求后台获取数据  axios
				//添加分页条件及高级查询条件
				let para = {
          "pageNo":this.page,
					"keyword":this.filters.keywords
				};
				this.listLoading = true; //显示加载圈
				//分页查询
                this.$http.post("/course/course/pagelist",para).then(result=>{
                        this.total = result.data.data.total;
                        this.courses = result.data.data.rows;
                        this.listLoading = false;  //关闭加载圈
				}).catch(error => {
					this.$message({ message: error.message,type: 'error'});
				});
			},
            onLineCourse(){
                //获取选中的行
				if(!this.sels || this.sels.length  == 0){
                    this.$message({ message: '老铁，你不选中数据，臣妾上不了啊....',type: 'error'});
				    return;
				}
        let courseIds=this.sels.map(row=>row.id)
				this.$http.post("/course/course/onLineCourse",courseIds).then(res=>{
				    var ajaxResult = res.data;
				    if(ajaxResult.success){
                        this.$message({ message: '老铁，上线成功.',type: 'success'});
                        this.getCourses();
					}else{
                        this.$message({ message: ajaxResult.message,type: 'error'});
					}
				}).catch(error => {
					this.$message({ message: error.message,type: 'error'});
				});
			},
            offLineCourse(){
                //获取选中的行
                if(!this.sels || this.sels==0){
                    this.$message({ message: '老铁，你不选中数据，臣妾下不了啊....',type: 'error'});
                    return;
                }
              let courseids=this.sels.map(row=>row.id)
              console.log(courseids)
                this.$http.post("/course/course/offLineCourse",courseids).then(res=>{
                    var ajaxResult = res.data;
                    if(ajaxResult.success){
                        this.$message({ message: '老铁，下线成功.',type: 'success'});
                        this.getCourses();
                    }else{
                        this.$message({ message: ajaxResult.message,type: 'error'});
                    }
                })
			},
            rowClick(row){
				this.row = row;
			},
            statusFormatter: function (row, column) {
                return row.status == 1 ? '已上线' : '未上线';
            },

			//讲师
			getTeachers(){
				this.$http.get("/course/teacher/list")
						.then(result=>{
							this.teachers = result.data.data;
							console.log(this.teachers)
						}).catch(error => {
							this.$message({ message: error.message,type: 'error'});
						});
			},
			edit(index,row){
        this.addFormVisible=true
        this.$http.get("/course/course/gettimetable/"+row.id).then(res=>{
          this.addForm =Object.assign({},row)
          this.addForm.validDays=res.data.data.market.validDays
          this.addForm.chargeId=res.data.data.market.charge
          this.addForm.price=res.data.data.market.price
          this.addForm.priceOld=res.data.data.market.priceOld
          this.addForm.qq=res.data.data.market.qq
          this.addForm.description=res.data.data.detail.description
          this.addForm.intro=res.data.data.detail.intro
          this.picfileList=[]
          this.picfileList.push({url: this.addForm.pic})
          this.zicfileList=[]
          this.zicfileList.push({url: this.addForm.zipResources})
          this.teachers=res.data.data.teacharIds
          this.addForm.teacharIds=res.data.data.teacharIds
        })
			},
			del(){
				this.$message({ message: "功能未开放", type: 'error' });
			},
			batchRemove(){
				this.$message({ message: "功能未开放", type: 'error' });
			},
			selsChange(sels){
				this.sels = sels;
			},
		},

		mounted() {
		    this.getCourses();
		    //this.getGrades();
		    this.getCourseTypes();
		    this.getTeachers();
		    this.getKillActivitys();
		}
	}

</script>

<style scoped>

</style>