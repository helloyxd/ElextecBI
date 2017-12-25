<!DOCTYPE html>
<html>
<head>
  	<title>任务调度中心</title>
  	<#import "/common/common.macro.ftl" as netCommon>
	<@netCommon.commonStyle />
	<!-- DataTables -->
  	<link rel="stylesheet" href="${request.contextPath}/static/adminlte/plugins/datatables/dataTables.bootstrap.css">

</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && "off" == cookieMap["xxljob_adminlte_settings"].value >sidebar-collapse</#if>">
<div class="wrapper">
	<!-- header -->
	<@netCommon.commonHeader />
	<!-- left -->
	<@netCommon.commonLeft "userinfo" />
	
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>用户管理<small>任务调度中心</small></h1>
			<!--
			<ol class="breadcrumb">
				<li><a><i class="fa fa-dashboard"></i>调度管理</a></li>
				<li class="active">调度中心</li>
			</ol>
			-->
		</section>
		
		<!-- Main content -->
	    <section class="content">
	    
	    	<div class="row">
                <div class="col-xs-4">
                    <div class="input-group">
                        <span class="input-group-addon">用户名</span>
                        <input type="text" class="form-control" id="userName">
                    </div>
                </div>
	            <div class="col-xs-2">
	            	<button class="btn btn-block btn-info" id="searchBtn">搜索</button>
	            </div>
	            <div class="col-xs-2 pull-right">
	            	<button class="btn btn-block btn-success add" type="button">+新增用户</button>
	            </div>
          	</div>
	    	
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
			            <div class="box-header hide">
			            	<h3 class="box-title">用户列表</h3>
			            </div>
			            <div class="box-body" >
			              	<table id="user_list" class="table table-bordered table-striped display" width="100%">
				                <thead>
					            	<tr>
					                	<th name="loginName">登录名</th>
                                        <th name="userName">用户名</th>
					                  	<th name="sex">性别</th>
                                        <th name="age">年龄</th>
					                  	<#--<th name="position">职位</th>-->
                                        <th name="createTime">创建时间</th>
					                  	<th>操作</th>
					                </tr>
				                </thead>
				                <tbody></tbody>
				                <tfoot></tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
	    </section>
	</div>
	
	<!-- footer -->
	<@netCommon.commonFooter />
</div>

<!-- 用户新增.模态框 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog"  aria-hidden="true">
	<div class="modal-dialog modal-body">
		<div class="modal-content">
			<div class="modal-header">
            	<h4 class="modal-title" >新增用户</h4>
         	</div>
         	<div class="modal-body">
				<form class="form-horizontal form" role="form" >
					<div class="form-group">
                        <label for="lastname" class="col-sm-4 control-label">登录帐号<font color="red">*</font></label>
                        <div class="col-sm-6"><input type="text" class="form-control" name="loginName" placeholder="请输入登录帐号" maxlength="50" ></div>
					</div>
                    <div class="form-group">
                        <label for="firstname" class="col-sm-4 control-label">用户名称<font color="black">*</font></label>
                        <div class="col-sm-6"><input type="text" class="form-control" name="userName" placeholder="请输入用户名" maxlength="50" ></div>
                    </div>
                    <div class="form-group">
                        <label for="lastname" class="col-sm-4 control-label">密码<font color="red">*</font></label>
                        <div class="col-sm-6"><input type="password" class="form-control" name="password" placeholder="请输入密码" maxlength="20" ></div>
                    </div>
                    <div class="form-group">
                        <label for="firstname" class="col-sm-4 control-label">确认密码<font color="red">*</font></label>
                        <div class="col-sm-6"><input type="password" class="form-control" name="repassword" placeholder="请再次输入密码" maxlength="20" ></div>
                    </div>
                    <div class="form-group">
                        <label for="firstname" class="col-sm-4 control-label">性别<font color="black">*</font></label>
                        <div class="col-sm-6">
                            <select class="form-control" name="sex" >
								<option value="1" >男</option>
                                <option value="0" >女</option>
                            </select>
						</div>
                    </div>
                    <div class="form-group">
                        <label for="lastname" class="col-sm-4 control-label">年龄<font color="black">*</font></label>
                        <div class="col-sm-6"><input type="text" class="form-control" name="age" placeholder="请输入年龄"></div>
                    </div>
                    <hr>
					<div class="form-group">
                        <div class="col-sm-offset-4 col-sm-6">
                            <button type="submit" class="btn btn-primary col-sm-4"  >保存</button>
                            &nbsp;&nbsp;
                            <button type="button" class="btn btn-default col-sm-4" data-dismiss="modal">取消</button>
                        </div>
					</div>
				</form>
         	</div>
		</div>
	</div>
</div>

<!-- 更新.模态框 -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"  aria-hidden="true">
	<div class="modal-dialog modal-body">
		<div class="modal-content">
			<div class="modal-header">
            	<h4 class="modal-title" >修改用户</h4>
         	</div>
         	<div class="modal-body">
				<form class="form-horizontal form" role="form" >
                    <div class="form-group">
                        <label for="lastname" class="col-sm-4 control-label">登录帐号<font color="red">*</font></label>
                        <div class="col-sm-6"><input type="text" class="form-control" name="loginName" placeholder="请输入登录帐号" maxlength="50" readonly></div>
                    </div>
                    <div class="form-group">
                        <label for="firstname" class="col-sm-4 control-label">用户名称<font color="black">*</font></label>
                        <div class="col-sm-6"><input type="text" class="form-control" name="userName" placeholder="请输入用户名" maxlength="50" ></div>
                    </div>
                    <#--<div class="form-group">-->
                        <#--<label for="lastname" class="col-sm-2 control-label">密码<font color="red">*</font></label>-->
                        <#--<div class="col-sm-4"><input type="password" class="form-control" name="password" placeholder="请输入密码" maxlength="20" ></div>-->
                        <#--<label for="firstname" class="col-sm-2 control-label">确认密码<font color="red">*</font></label>-->
                        <#--<div class="col-sm-4"><input type="password" class="form-control" name="repassword" placeholder="请再次输入密码" maxlength="20" ></div>-->
                    <#--</div>-->
                    <div class="form-group">
                        <label for="firstname" class="col-sm-4 control-label">性别<font color="black">*</font></label>
                        <div class="col-sm-6">
                            <select class="form-control" name="sex" >
                                <option value="1" >男</option>
                                <option value="0" >女</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lastname" class="col-sm-4 control-label">年龄<font color="black">*</font></label>
                        <div class="col-sm-6"><input type="text" class="form-control" name="age" placeholder="请输入年龄"></div>
                    </div>
					<hr>
					<div class="form-group">
                        <div class="col-sm-offset-4 col-sm-6">
                            <button type="submit" class="btn btn-primary col-sm-4"  >保存</button>
                            &nbsp;&nbsp;
                            <button type="button" class="btn btn-default col-sm-4" data-dismiss="modal">取消</button>
                            <input type="hidden" name="id" >
                        </div>
					</div>
				</form>
         	</div>
		</div>
	</div>
</div>

<@netCommon.commonScript />
<!-- DataTables -->
<script src="${request.contextPath}/static/adminlte/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${request.contextPath}/static/adminlte/plugins/datatables/dataTables.bootstrap.min.js"></script>
<#--<script src="${request.contextPath}/static/plugins/jquery/jquery.validate.min.js"></script>-->
<!-- moment -->
<script src="${request.contextPath}/static/adminlte/plugins/daterangepicker/moment.min.js"></script>
<script src="${request.contextPath}/static/js/userinfo.index.1.js"></script>
</body>
</html>
