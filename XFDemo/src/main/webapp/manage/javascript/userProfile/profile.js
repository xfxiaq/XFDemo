define([
        "text!../../pages/userProfile/profile.html",
        'util',
        '../../assets/js/ajaxfileupload'
        ], 
		
function (handler,util) {
	
	/**
	 *  初始化内容区域，设置默认显示菜单。
	 */
	function initContent(){
		
		templateData = util.getJsonData({
			uri:"common/user/getLoginUserInfo.json",
			data:{}
		});		
		
		util.loadPageContent({
			contentId:"content",
			template:handler,
			templateData:templateData,
			EventConfig:EventConfig
		})
		if(templateData && templateData.avatarId){
			$("#avatar").attr("src", "common/file/download/" + templateData.avatarId);
		}
		
	}
	
	/**
	 * 页面事件
	 */
	var EventConfig = {
			// 修改密码按钮,点击后弹出窗口.
			_changePasswordEvent:function(e){
				$("#changePassword").modal()
			},	
			_saveNewPasswordEvent:function(e){
				if($("#advanced-form").parsley().validate()){
					templateData.password = $("#password").val()  ;
					templateData.new_password =   $("#new_password").val()  ;
					templateData.repeat_password =   $("#repeat_password").val()  ;
					var data = util.getJsonData({
						uri:"common/user/changePassword.json",
						data:templateData
					});
					if(data){
						
						if(data._backcode == "401"){
							$("#error_message").html("原始密码不正确");
						}else if(data._backcode == "402"){
							$("#error_message").html("新密码两次输入不一致");
						}else{
							$('.modal-backdrop').hide();
							$("#password").val("");
							$("#new_password").val("");
							$("#repeat_password").val("");
							$("#error_message").html("");
							util.showSuccessMessage("操作成功。");							
							 require(['../../javascript/userProfile/profile'], function (index) {
									index.initPage();
							});
						}
					}
				}
			},
			_uploadFile:function(e){

		            $.ajaxFileUpload
		            (
		                {
		                    url: 'common/file/uploadFile/avatar/'+templateData.id, //用于文件上传的服务器端请求地址
		                    secureuri: false, //一般设置为false
		                    fileElementId: 'file', //文件上传空间的id属性  <input type="file" id="file" name="file" />
		                    dataType: 'json', //返回值类型 一般设置为json
		                    success: function (data, status)  //服务器成功响应处理函数
		                    {
		                    	
		                        $("#avatar").attr("src", "common/file/download/" + data.id);
		                        $("#user-avatar").attr("src", "common/file/download/" + data.id);
		                        
//								$("#down").html("<a href='common/file/download/"+data.id+"' >"+data.fileName+"</a>");
		                        // 保存用户头像
		                    },
		                    error: function (data, status, e)//服务器响应失败处理函数
		                    {

		                    }
		                }
		            )
			}
		};

	
	// 提供给外部调用的方法
	return {
		initPage:function (tab) {
			// 初始化内容区域
			initContent();
	    }
	
	}
});