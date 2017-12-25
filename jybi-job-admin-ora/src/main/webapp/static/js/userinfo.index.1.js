$(function() {
	// init date tables
	var userTable = $("#user_list").dataTable({
		"deferRender": true,
		"processing" : true, 
	    "serverSide": true,
		"ajax": {
			url: base_url + "/userinfo/pageList",
			type:"post",
	        data : function ( d ) {
	        	var obj = {};
	        	obj.userName = $('#userName').val();
	        	obj.start = d.start;
	        	obj.length = d.length;
                return obj;
            }
	    },
        "searching": false,
	    "ordering": false,
	    //"scrollX": true,	// X轴滚动条，取消自适应
	    "columns": [
	                { 
	                	"data": 'loginName'
                    },
					{
						"data": 'userName'
					},
	                {   "data": 'sex',
                        "render": function ( data, type, row ) {
                            if (row.sex==0) {
                                return "女";
                            }else if(row.sex==1){
                                return "男";
							}else{
                            	return "";
							}
                        }
	                },
					{
						"data": 'age'
					},
	                // { "data": 'position'},
					{ "data": 'createTimeStr'},
	                {
						"data": '操作' ,
	                	"render": function ( data, type, row ) {
	                		return function(){
								// html
								// if("admin"==row.loginName){
                                    tableData['key'+row.id] = row;
                                    var html = '<p id="'+ row.id +'" >'+
                                        '<button class="btn btn-warning btn-xs update" type="button">编辑</button> ';
                                    if("admin" == row.loginName){
                                    	html = html+
											'<button class="btn btn-primary btn-xs job_operate" _type="pwd_reset" type="button">密码重置</button>  '+
											'</p>'
									}else{
                                    	html = html+
											'<button class="btn btn-primary btn-xs job_operate" _type="pwd_reset" type="button">密码重置</button>  '+
											'<button class="btn btn-danger btn-xs job_operate" _type="user_del" type="button">删除</button>  '+
                                            '</p>';
									}
                                    return html;
								// }
							};
	                	}
	                }
	            ],
		"language" : {
			"sProcessing" : "处理中...",
			"sLengthMenu" : "每页 _MENU_ 条记录",
			"sZeroRecords" : "没有匹配结果",
			"sInfo" : "第 _PAGE_ 页 ( 总共 _PAGES_ 页，_TOTAL_ 条记录 )",
			"sInfoEmpty" : "无记录",
			"sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
			"sInfoPostFix" : "",
			"sSearch" : "搜索:",
			"sUrl" : "",
			"sEmptyTable" : "表中数据为空",
			"sLoadingRecords" : "载入中...",
			"sInfoThousands" : ",",
			"oPaginate" : {
				"sFirst" : "首页",
				"sPrevious" : "上页",
				"sNext" : "下页",
				"sLast" : "末页"
			},
			"oAria" : {
				"sSortAscending" : ": 以升序排列此列",
				"sSortDescending" : ": 以降序排列此列"
			}
		}
	});

    // table data
    var tableData = {};

	// 搜索按钮
	$('#searchBtn').on('click', function(){
        userTable.fnDraw();
	});

    // user operate
    $("#user_list").on('click', '.job_operate',function() {
        var typeName;
        var url;
        var needFresh = false;
        var type = $(this).attr("_type");
        if ("user_del" == type) {
            typeName = "删除";
            url = base_url + "/userinfo/remove";
            needFresh = true;
        } else if("pwd_reset"==type){
            typeName = "密码重置";
            url = base_url + "/userinfo/reset";
            needFresh = true;

		} else {
            return;
        }

        var id = $(this).parent('p').attr("id");

        layer.confirm('确认' + typeName + '?', {icon: 3, title:'系统提示'}, function(index){
            layer.close(index);
            $.ajax({
                type : 'POST',
                url : url,
                data : {
                    "id" : id
                },
                dataType : "json",
                success : function(data){
                    if (data.code == 200) {
                        layer.open({
                            title: '系统提示',
                            content: typeName + "成功",
                            icon: '1',
                            end: function(layero, index){
                                if (needFresh) {
                                    //window.location.reload();
                                    userTable.fnDraw();
                                }
                            }
                        });
                    } else {
                        layer.open({
                            title: '系统提示',
                            content: (data.msg || typeName + "失败"),
                            icon: '2'
                        });
                    }
                },
            });
        });
    });


	
	// 新增
	$(".add").click(function(){
		$('#addModal').modal({backdrop: false, keyboard: false}).modal('show');
	});
	var addModalValidate = $("#addModal .form").validate({
		errorElement : 'span',  
        errorClass : 'help-block',
        focusInvalid : true,  
        rules : {
			jobDesc : {
				required : true,
				maxlength: 50
			},
            jobCron : {
            	required : true
            },
			author : {
				required : true
			}
        }, 
        messages : {  
            loginName : {
            	required :"请输入用户登录名"
            },
            password : {
            	required :"请输入密码"
            },
            repassword : {
            	required : "请再次输入密码"
            }
        },
		highlight : function(element) {  
            $(element).closest('.form-group').addClass('has-error');  
        },
        success : function(label) {  
            label.closest('.form-group').removeClass('has-error');  
            label.remove();  
        },
        errorPlacement : function(error, element) {  
            element.parent('div').append(error);  
        },
        submitHandler : function(form) {
			var password = $("#password").val();
			var repassword = $("#repassword").val();
			if(password != repassword){
                layer.open({
                    title: '系统提示',
                    content: ("两次输入密码不一致"),
                    icon: '2'
                });
                return;
			}
        	$.post(base_url + "/userinfo/add",  $("#addModal .form").serialize(), function(data, status) {
    			if (data.code == "200") {
					$('#addModal').modal('hide');
					layer.open({
						title: '系统提示',
						content: '新增用户成功',
						icon: '1',
						end: function(layero, index){
							userTable.fnDraw();
							//window.location.reload();
						}
					});
    			} else {
					layer.open({
						title: '系统提示',
						content: (data.msg || "新增失败"),
						icon: '2'
					});
    			}
    		});
		}
	});


	$("#addModal").on('hide.bs.modal', function () {
		$("#addModal .form")[0].reset();
		addModalValidate.resetForm();
		$("#addModal .form .form-group").removeClass("has-error");
		$(".remote_panel").show();	// remote

		$("#addModal .form input[name='executorHandler']").removeAttr("readonly");
	});


	// 更新
	$("#user_list").on('click', '.update',function() {

        var id = $(this).parent('p').attr("id");
        console.log(id);
        var row = tableData['key'+id];
        if (!row) {
            layer.open({
                title: '系统提示',
                content: ("任务信息加载失败，请刷新页面"),
                icon: '2'
            });
            return;
        }

		// base data
		$("#updateModal .form input[name='id']").val( row.id );
        $("#updateModal .form input[name='loginName']").val( row.loginName );
        $("#updateModal .form input[name='userName']").val( row.userName );
		$('#updateModal .form select[name=sex] option[value='+ row.sex +']').prop('selected', true);
		$("#updateModal .form input[name='age']").val( row.age );


		// show
		$('#updateModal').modal({backdrop: false, keyboard: false}).modal('show');
	});
	var updateModalValidate = $("#updateModal .form").validate({
		errorElement : 'span',  
        errorClass : 'help-block',
        focusInvalid : true,

		rules : {
			jobDesc : {
				required : true,
				maxlength: 50
			},
			jobCron : {
				required : true
			},
			author : {
				required : true
			}
		},
		messages : {
            loginName : {
                required :"请输入用户登录名"
            },
            password : {
                required :"请输入密码"
            },
            repassword : {
                required : "请再次输入密码"
            }
		},
		highlight : function(element) {
            $(element).closest('.form-group').addClass('has-error');  
        },
        success : function(label) {  
            label.closest('.form-group').removeClass('has-error');  
            label.remove();  
        },
        errorPlacement : function(error, element) {  
            element.parent('div').append(error);  
        },
        submitHandler : function(form) {
			// post
    		$.post(base_url + "/userinfo/reschedule", $("#updateModal .form").serialize(), function(data, status) {
    			if (data.code == "200") {
					$('#updateModal').modal('hide');
					layer.open({
						title: '系统提示',
						content: '更新成功',
						icon: '1',
						end: function(layero, index){
							//window.location.reload();
							userTable.fnDraw();
						}
					});
    			} else {
					layer.open({
						title: '系统提示',
						content: (data.msg || "更新失败"),
						icon: '2'
					});
    			}
    		});
		}
	});
	$("#updateModal").on('hide.bs.modal', function () {
		$("#updateModal .form")[0].reset()
	});

});
