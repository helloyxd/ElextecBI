$(function(){

	// logout
	$("#logoutBtn").click(function(){
		layer.confirm('确认注销登录?', {icon: 3, title:'系统提示'}, function(index){
			layer.close(index);

			$.post(base_url + "/logout", function(data, status) {
				if (data.code == "200") {
					layer.open({
						title: '系统提示',
						content: '注销成功',
						icon: '1',
						end: function(layero, index){
							window.location.href = base_url + "/";
						}
					});
				} else {
					layer.open({
						title: '系统提示',
						content: (data.msg || "操作失败"),
						icon: '2'
					});
				}
			});
		});

	});


    // $("#updatePwdBtn").click(function(){
    //     $('#updatePwdModal').modal({backdrop: false, keyboard: false}).modal('show');
    // });

	// slideToTop
	var slideToTop = $("<div />");
	slideToTop.html('<i class="fa fa-chevron-up"></i>');
	slideToTop.css({
		position: 'fixed',
		bottom: '20px',
		right: '25px',
		width: '40px',
		height: '40px',
		color: '#eee',
		'font-size': '',
		'line-height': '40px',
		'text-align': 'center',
		'background-color': '#222d32',
		cursor: 'pointer',
		'border-radius': '5px',
		'z-index': '99999',
		opacity: '.7',
		'display': 'none'
	});
	slideToTop.on('mouseenter', function () {
		$(this).css('opacity', '1');
	});
	slideToTop.on('mouseout', function () {
		$(this).css('opacity', '.7');
	});
	$('.wrapper').append(slideToTop);
	$(window).scroll(function () {
		if ($(window).scrollTop() >= 150) {
			if (!$(slideToTop).is(':visible')) {
				$(slideToTop).fadeIn(500);
			}
		} else {
			$(slideToTop).fadeOut(500);
		}
	});
	$(slideToTop).click(function () {
		$("body").animate({
			scrollTop: 0
		}, 100);
	});

    // 修改密码
    $("#updatePwdBtn").click(function(){
        $('#updatePwdModal').modal({backdrop: false, keyboard: false}).modal('show');
    });
    var updatePwdModalValidate = $("#updatePwdModal .form").validate({
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
        	var oldpassword = $("#oldPassword").val();
            var password = $("#newPassword").val();
            var repassword = $("#rePassword").val();
            if(oldpassword == null || $.trim(oldpassword) == ''){
                layer.open({
                    title: '系统提示',
                    content: ("旧密码不能为空"),
                    icon: '2'
                });
                return;
			}
            if(password == null || $.trim(password) == ''){
                layer.open({
                    title: '系统提示',
                    content: ("新密码不能为空"),
                    icon: '2'
                });
                return;
            }
            if(repassword == null || $.trim(repassword) == ''){
                layer.open({
                    title: '系统提示',
                    content: ("请再次输入新密码"),
                    icon: '2'
                });
                return;
            }
            if(password != repassword){
                layer.open({
                    title: '系统提示',
                    content: ("两次输入密码不一致"),
                    icon: '2'
                });
                return;
            }
            $.post(base_url + "/userinfo/changePwd",  $("#updatePwdModal .form").serialize(), function(data, status) {
                if (data.code == "200") {
                    $('#updatePwdModal').modal('hide');
                    layer.open({
                        title: '系统提示',
                        content: '修改密码成功',
                        icon: '1',
                        end: function(layero, index){
                            userTable.fnDraw();
                            //window.location.reload();
                        }
                    });
                } else {
                    layer.open({
                        title: '系统提示',
                        content: (data.msg || "修改失败"),
                        icon: '2'
                    });
                }
            });
        }
    });


	// 左侧菜单状态，js + 后端 + cookie方式（新）
	$('.sidebar-toggle').click(function(){
		var xxljob_adminlte_settings = $.cookie('xxljob_adminlte_settings');	// 左侧菜单展开状态[xxljob_adminlte_settings]：on=展开，off=折叠
		if ('off' == xxljob_adminlte_settings) {
            xxljob_adminlte_settings = 'on';
		} else {
            xxljob_adminlte_settings = 'off';
		}
		$.cookie('xxljob_adminlte_settings', xxljob_adminlte_settings, { expires: 7 });	//$.cookie('the_cookie', '', { expires: -1 });
	});
	// 左侧菜单状态，js + cookie方式（遗弃）
	/*
	 var xxljob_adminlte_settings = $.cookie('xxljob_adminlte_settings');
	 if (xxljob_adminlte_settings == 'off') {
	 $('body').addClass('sidebar-collapse');
	 }
	 */
	
});
