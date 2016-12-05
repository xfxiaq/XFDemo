define([
        "text!../../../../pages/systemSetting/role/role-add.html",
        'util',
        'plugins/bootstrap-touchspin/jquery.bootstrap-touchspin',
        'plugins/select2/select2.min'
        ], 
		
function (addPage,util) {
	var templateData = {};

	function  loadPage(){
		
		templateData = {
				id:'',
				roleName:"",
				roleCode:""
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
							uri:"systemSetting/role/create.json",
							data:templateData
						});
						if(data){
							util.showSuccessMessage("操作成功。");
							require(['../../javascript/systemSetting/role/roleList'], function (index) {									
								index.initPage();
							});
						}
				}
			},
			_returnEvent:function(e){
				require(['../../javascript/systemSetting/role/roleList'], function (index) {									
					index.initPage();
				});
			}
		};
	
	// 提供给外部调用的方法
	return {
		initPage:function (tab) {
			// 加载list页面
			 loadPage();
	    }
	
	}
});