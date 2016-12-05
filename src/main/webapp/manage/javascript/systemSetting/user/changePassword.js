define([
        "text!/pages/systemSetting/user/change-password.html",
        'util'
        ], 
		
function (addPage,util) {
	var templateData = {};

	function  loadPage(id,userId,userName){
		
		templateData = {
				id:id,
				userId:userId,
				userName:userName,
				password:"",
				new_password:"",
				repeat_password:"",
		};
		
		
		templateData = util.loadPageContent({
			contentId:"content",
			template:addPage,
			templateData:templateData,
			EventConfig:addEventConfig
		})
		
			
	}
	
	/**
	 * 左侧菜单事件
	 */
	var addEventConfig = {
			_createEvent:function(e){
					if($("#advanced-form").parsley().validate()){
						var data = util.getJsonData({
							uri:"common/user/changeUserPassword.json",
							data:templateData
						});
						if(data){
							util.showSuccessMessage("操作成功。");
							require([  '../../javascript/systemSetting/user/userList'   ], function (index) {									
								index.initPage();
							});
						}
				}
			},
			_returnEvent:function(e){
				require([  '../../javascript/systemSetting/user/userList'   ], function (index) {									
					index.initPage();
				});
			}
		};
	
	// 提供给外部调用的方法
	return {
		initPage:function (id,userId,userName) {
			
			// 加载list页面
			 loadPage(id,userId,userName);
	    }
	
	}
});