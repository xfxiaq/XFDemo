define([
       "text!../../../../pages/systemSetting/function/function-edit.html",
        'util',
        'plugins/select2/select2.min',
        'plugins/bootstrap-touchspin/jquery.bootstrap-touchspin',
        ], 
		
function (editPage,util) {
	var templateData = {};
	function loadPage(dateId){
		var postData = {"id":dateId}
		
		templateData = util.getJsonData({
			uri:"systemSetting/function/show.json",
			data:postData
		});		
		
		templateData = util.loadPageContent({
			template:editPage,
			templateData:templateData,
			EventConfig:editEventConfig		
		})
		
	}
	
	function  initPageElement(){
		
		// 父菜单
		$("#parentId").select2({
			initSelection: function(element, callback) {
				callback({id: templateData.parentId,text:templateData.parentName});
				},
			  ajax: {
				  type: "post",
				  params: {
					    contentType: 'application/json; charset=utf-8'
					  },
				    url: "systemSetting/function/getParentFunctionList.json",
				    dataType: 'json',
				    delay: 250,
				    data:function (term) {
					      return JSON.stringify( {
					    	  param: term,// search term
					    	  selfId:templateData.id
					      });
					    },
			    results: function (data, page) {
			        return {
			          results: data
			        };
			      },
			      cache: true
			  }
			  
		});
		
		// 排序
		$("#sort").TouchSpin({
		    min: 1,
		    max: 99999,
		    step: 1,
		    decimals: 0,
		    boostat: 5,
		    maxboostedstep: 10
		});
	}
	
	/**
	 * 左侧菜单事件
	 */
	var editEventConfig = {
			_createEvent:function(e){
				if($("#advanced-form").parsley().validate()){
						var data = util.getJsonData({
							uri:"systemSetting/function/update.json",
							data:templateData
						});
						if(data){
							util.showSuccessMessage("操作成功。");
							require(['../../javascript/systemSetting/function/functionList'   ], function (index) {									
								index.initPage();
							});
						}
				}
			},
				_returnEvent:function(e){
					require(['../../javascript/systemSetting/function/functionList'   ], function (index) {									
						index.initPage();
					});
				}
		};
	
	// 提供给外部调用的方法
	return {
		initPage:function (dateId) {
			// 加载list页面
			 loadPage(dateId);
			 
			 initPageElement();
	    }
	
	}
});