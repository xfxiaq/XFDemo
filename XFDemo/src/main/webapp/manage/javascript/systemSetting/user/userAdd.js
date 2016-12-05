define([
        "text!../../../../pages/systemSetting/user/user-add.html",
        'util',
        '../../../assets/js/plugins/bootstrap-switch/bootstrap-switch.min',
        '../../../assets/js/plugins/select2/select2.min',
        ], 
		
function (addPage,util) {
	var templateData = {};

	function  loadPage(){
		
		templateData = {
				id:'',
				userId:"",
				password:"",
				userName:"",
				tenantId:"",
				companyId:"",
				enable:true
		};
		
		
		templateData = util.loadPageContent({
			contentId:"content",
			template:addPage,
			templateData:templateData,
			EventConfig:addEventConfig
		})
		
		// 是否启用
		$('#enable').bootstrapSwitch();
		
		$("#roles").select2({
			placeholder: "请选择角色",
			minimumInputLength: 0,
			multiple:true,
			  ajax: {
				  type: "post",
				  params: {
					    contentType: 'application/json; charset=utf-8'
					  },
				    url: "systemSetting/role/getRoleSelectList.json",
				    dataType: 'json',
				    delay: 250,
				    data: function (term) {
				      return JSON.stringify({
				    	  param: term
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
		$("#companyId").select2({
			placeholder: "请选择公司",
			minimumInputLength: 0,
			  ajax: {
				  type: "POST",
				    url: "/manage/common/select2/COMPANYLIST",
				    dataType: 'json',
				    delay: 1250,
				    data: function (term) {
			      return {
			    	  param: term// search term
			      };
			    },
			    results: function (data, page) {
			        return {
			          results: data
			        };
			      },
			      cache: true
			  }
	});
		var transportModelData = [ 
		                          { id: '10', text: '陆运业务' },
		                          { id: '20', text: '海运业务' },
		                          { id: '30', text: '铁运业务' }];
		$("#tenantId").select2({
			data: transportModelData,
			placeholder: "业务类型",
			multiple:true
		});
	}
	
	/**
	 * 左侧菜单事件
	 */
	var addEventConfig = {
			_createEvent:function(e){
					if($("#advanced-form").parsley().validate()){
						var data = util.getJsonData({
							uri:"common/user/create.json",
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
		initPage:function (tab) {
			
			// 加载list页面
			 loadPage();
	    }
	
	}
});