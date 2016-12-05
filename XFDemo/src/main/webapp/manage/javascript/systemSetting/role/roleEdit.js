define([
       "text!../../../../pages/systemSetting/role/role-edit.html",
        'util'
        ], 
		
function (editPage,util) {
	var templateData = {};
	function loadPage(dateId){
		var postData = {"id":dateId}
		
		templateData = util.getJsonData({
			uri:"systemSetting/role/show.json",
			data:postData
		});		
		
		templateData = util.loadPageContent({
			template:editPage,
			templateData:templateData,
			EventConfig:editEventConfig		
		})
	}
	function  initPageElement(){
	}
	/**
	 * 左侧菜单事件
	 */
	var editEventConfig = {
			_createEvent:function(e){
				if($("#advanced-form").parsley().validate()){
						var data = util.getJsonData({
							uri:"systemSetting/role/update.json",
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
			},
			// checkbox 全选
			_allCheckEvent:function(e){
					$("#datatable-function").jstree("check_all");
			},
			// checkbox 全部取消
			_allCancelEvent:function(e){
					$("#datatable-function").jstree("uncheck_all");
			},
			// 权限保存
			_allSaveEvent:function(e){
					var ids="";
					var nodes=$("#datatable-function").jstree("get_checked"); //使用get_checked方法 
					$.each(nodes, function(i, n) { 
						ids += n+",";
					}); 
					
					// 不判断是否为空, 为空的时候清空所选
					//if(ids != ""){
						var data = util.getJsonData({
							uri:"systemSetting/role/updateRoleFunction.json",
							data:{
								roleId:templateData.id,
								ids:ids
								}
						});
						if(data){
							util.showSuccessMessage("操作成功。");
							// 刷新tree
							$("#datatable-function").jstree("refresh",false);
						}
			},
			//////////////////////////////
			// checkbox 全选
			_allOpCheckEvent:function(e){
					$("#datatable-function-phone").jstree("check_all");
			},
			// checkbox 全部取消
			_allOpCancelEvent:function(e){
					$("#datatable-function-phone").jstree("uncheck_all");
			},
			// 权限保存
			_allOpSaveEvent:function(e){
					var ids="";
					var nodes=$("#datatable-function-phone").jstree("get_checked"); //使用get_checked方法 
					$.each(nodes, function(i, n) { 
						ids += n+",";
					}); 
					
					// 不判断是否为空, 为空的时候清空所选
					//if(ids != ""){
						var data = util.getJsonData({
							uri:"systemSetting/role/updateRoleOpFunction.json",
							data:{
								roleId:templateData.id,
								ids:ids
								}
						});
						if(data){
							util.showSuccessMessage("操作成功。");
							// 刷新tree
							$("#datatable-function-phone").jstree("refresh",false);
						}
			}
		};
	function  initFunctionList(){
		// 初始化功能权限树形列表
			$("#datatable-function").jstree({//从这里开始初始化JSTree  
				'core' : {					
					'data' : {
						"url" : "systemSetting/role/roleFunctionJsTree.json/" + templateData.id,
						"dataType" : "json", // needed only if you do not supply JSON headers
						"type": "POST"
					}
					
					
				},
			        plugins : [ "themes", "json_data", "checkbox", "ui" ]   //加载插件
			}); 
			//****

	}
	function addSearch(postData){
		if(templateData.id){
			postData.filter.filters.push({
                "field": "id",
                "operator": "eq",
                "value": templateData.id
			});
		}
	}
	// 提供给外部调用的方法
	return {
		initPage:function (dateId) {
			// 加载list页面
			 loadPage(dateId);
			 
			 initPageElement();
			 
			 initFunctionList();
	    }
	
	}
});
