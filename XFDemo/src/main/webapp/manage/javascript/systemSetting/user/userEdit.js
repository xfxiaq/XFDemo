define([
       "text!../../../../pages/systemSetting/user/user-edit.html",
        'util',
        '../../../assets/js/plugins/bootstrap-switch/bootstrap-switch.min',
        '../../../assets/js/plugins/select2/select2.min',
        '../../../assets/js/plugins/jquery-multi-select/jquery.multi-select',//多选
        '../../../assets/js/plugins/jquery-multi-select/jquery.quicksearch'//
        ], 
		
function (editPage,util) {
	var templateData = {};
	
	
	function  consigneeSelect(dateId){
		var data = util.getJsonData({
			uri:"/manage/common/user/consigneeSelect.json",
			data:{id:dateId}
		});
		if(data!=null){
			var $select = $('#my-select');  
			for(var i=0, len = data.length;i<len;i++){  
				if(data[i]['selected']){
					  $select.append('<option value="'+data[i]['id']+'" selected>'+data[i]['value']+' </option>');  

				}else{
					  $select.append('<option value="'+data[i]['id']+'">'+data[i]['value']+'</option>');  

				}
			}  
			
		}
		$('#my-select').multiSelect({
				  selectableHeader: "<input type='text' class='search-input' autocomplete='off' placeholder='请输入企业名称'>",
				  selectionHeader: "<input type='text' class='search-input' autocomplete='off' placeholder='请输入企业名称'>",
				  afterInit: function(ms){
				    var that = this,
				        $selectableSearch = that.$selectableUl.prev(),
				        $selectionSearch = that.$selectionUl.prev(),
				        selectableSearchString = '#'+that.$container.attr('id')+' .ms-elem-selectable:not(.ms-selected)',
				        selectionSearchString = '#'+that.$container.attr('id')+' .ms-elem-selection.ms-selected';

				    that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
				    .on('keydown', function(e){
				      if (e.which === 40){
				        that.$selectableUl.focus();
				        return false;
				      }
				    });

				    that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
				    .on('keydown', function(e){
				      if (e.which == 40){
				        that.$selectionUl.focus();
				        return false;
				      }
				    });
				  },
				  afterSelect: function(o){
					var data = util.getJsonData({
							uri:"/manage/common/user/updateBusinessMan.json",
							data:{id:dateId,companyId:o[0]}
					});
				    this.qs1.cache();
				    this.qs2.cache();
				  },
				  afterDeselect: function(o){
					  var data = util.getJsonData({
							uri:"/manage/common/user/deBusinessMan.json",
							data:{id:dateId,companyId:o[0]}
					});
				    this.qs1.cache();
				    this.qs2.cache();
				  }
				});
	}
	function loadPage(dateId){
		var postData = {"id":dateId}
		
		templateData = util.getJsonData({
			uri:"common/user/show.json",
			data:postData
		});		
		
		templateData = util.loadPageContent({
			template:editPage,
			templateData:templateData,
			EventConfig:editEventConfig		
		})
		
		consigneeSelect(dateId);
		if(templateData.shipper){
			$("#manageCompany").removeClass("hidden");
		}
	}
	
	function  initPageElement(){
		
		// 是否启用
		$('#enable').bootstrapSwitch();
		$("#roles").select2({
			//placeholder: "请选择角色",
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
			initSelection: function(element, callback) {
				var backid='';
				var backtxt='请选择';
				if(templateData){
					backid=templateData.companyId;
					backtxt=templateData.companyName;
				}
				callback({id: backid,text:backtxt});
				},
			minimumInputLength: 0,
			  ajax: {
				  type: "post",
				    url: "/manage/common/select2/COMPANYLIST",
				    dataType: 'json',
				    delay: 1250,
				    data: function (term) {
			      return {
			    	  param: term
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
		
		$('#roles').data().select2.updateSelection(templateData.defaultRoles);
		if(templateData.defaultRoles){
			var roleIds = "";
			$.each(templateData.defaultRoles,function(n,value) {
				roleIds += value.id + ",";
			})
			if(roleIds.indexOf(',') != -1){
				roleIds = roleIds.substring(0,roleIds.length - 1);
				templateData.roles = roleIds;
			}
			
		}
		
		
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
	var editEventConfig = {
			_createEvent:function(e){
				if($("#advanced-form").parsley().validate()){
						var data = util.getJsonData({
							uri:"common/user/update.json",
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
		initPage:function (dateId) {
			// 加载list页面
			 loadPage(dateId);
			 
			 initPageElement();
	    }
	
	}
});