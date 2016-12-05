define([
        "text!../../../../pages/systemSetting/function/function-add.html",
        'util',
        'plugins/bootstrap-touchspin/jquery.bootstrap-touchspin',
        'plugins/select2/select2.min'
        ], 
		
function (addPage,util) {
	var templateData = {};

	function  loadPage(){
		
		templateData = {
				id:'',
				functionName:"",
				functionUrl:"",
				urlRouter:"",
				authUrl:"",
				ico:""
		};
		
		
		templateData = util.loadPageContent({
			contentId:"content",
			template:addPage,
			templateData:templateData,
			EventConfig:addEventConfig
		})
		
		// 父菜单
		$("#parentId").select2({
			placeholder: "请输入父菜单名称",
			minimumInputLength: 0,
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
					    	  selfId:""
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
	var addEventConfig = {
			_createEvent:function(e){
					if($("#advanced-form").parsley().validate()){
						var data = util.getJsonData({
							uri:"systemSetting/function/create.json",
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
		initPage:function (tab) {
			
			// 加载list页面
			 loadPage();
	    }
	
	}
});