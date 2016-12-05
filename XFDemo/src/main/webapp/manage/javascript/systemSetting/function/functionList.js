define([
        "text!../../../../pages/systemSetting/function/function-list.html",
        'util',
        'plugins/select2/select2.min'
        ], 
		
function (listHandler,util) {
	
	/**
	 *  初始化内容区域，设置默认显示菜单。
	 */
	function initContent(){
		util.loadPageContent({
			contentId:"content",
			template:listHandler,
			EventConfig:listEventConfig
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
		
	}
	
	/**
	 * 页面事件
	 */
	var listEventConfig = {
			_seachEvent:function(e){
				renderTable();
			},
			_createEvent:function(e){
				    require(["../../javascript/systemSetting/function/functionAdd"],function(add){
				    	add.initPage();
			    });
			},
			_resetEvent:function(e){				
			     $("#functionName").val("");
			     $("#functionUrl").val("");
			     $("#parentId").select2('val', '');
			     renderTable();
		    }

		};

	function renderTable(){

		$("#datatable-column").dataTable(

				{
				      "bSort": false,                                //开关，是否启用各列具有按列排序的功能
				      "bServerSide": true,
				      "bPaginate": true,                             //开关，是否显示分页器
				      "sEcho": 3,
				      "bDestroy": true,                              //先解除原有的数据源，然后重新绑定
				      "bAutoWidth" : true,
				      "bProcessing" : true,
				      "bFilter" : false,
				      "scrollX" : true,
				      "oLanguage": {                                 //语言国际化
				        "sProcessing": "正在加载中......",
				        "sLengthMenu": "每页显示 _MENU_ 条记录",
				        "sZeroRecords": "对不起，查询不到相关数据！",
				        "sEmptyTable": "表中无数据存在！",
				        "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
				        "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
				        "sSearch": "搜索",
				        "sInfoEmpty": "显示0条记录",
				        "oPaginate": {
				          "sFirst": "首页",
				          "sPrevious": "上一页",
				          "sNext": "下一页",
				          "sLast": "末页"
				        }
				      },
				     "sAjaxSource": "systemSetting/function/auth_menu_list.json",     //指定要从哪个URL获取数据, 注意, 此处必须有 不然下方获取参数不正常
				      "fnServerData": function ( sSource, aoData, fnCallback, oSettings ) { //用于替换默认发到服务端的请求操作
				        var start = 0,limit = 10,sSearch = "";
				        $.each(aoData,function(index,param){
				            if ("iDisplayStart" == param['name']) {
				                start = param['value'];
				              }
				            if ("iDisplayLength" == param['name']) {
				                limit = param['value'];
				              }
				              if ("search" == param['name']) {
				                sSearch = param['value'];
				              }

				        });
				        
				        var postData = {

				        	    "page": start/limit + 1,
				        	    "pageSize": limit,
				        	    "sort": [
				        	             {"field":"sort","dir":"asc"},
				        	    ],
				        	    "filter": {
				        	        "logic": "and",
				        	        "filters": []
				        	    }
				        	};
				        
				        addSearch(postData);

				        oSettings.jqXHR = $.ajax( {
					        url: "systemSetting/function/auth_menu_list.json",
					        type: "POST",
					        dataType: 'json',
					        contentType:'application/json;charset=UTF-8',
					        data: JSON.stringify(postData),

				          "success": function(json) {
				            var resultData = json.content;
				            var totalItems = json.totalElements;

				            fnCallback( {
				              iTotalRecords: totalItems          //json.data.totalItems
				              , iTotalDisplayRecords: totalItems //json.data.totalItems
				              , aaData: resultData
				            });
				          }
				        });
				      },

				      "aoColumns": [
				        {"sTitle":"操作", "mData": "id", "sDefaultContent": "", "sClass": "left",'width':'80px',
					          "mRender": function (data) {
						            return  "<a  href='javascript:void(0)' class='gridRow'  dateId="+ data +" \"> 编辑 </a>" 
						            				+"<a  href='javascript:void(0)' class='gridRowDel'  dateId="+ data +" \"> 删除 </a>"
						            				;
						          }	
				        },
				        {"sTitle":"功能菜单名称", "mData":"functionName", "sDefaultContent": "","sClass": "left",'width':'120px'},
				        {"sTitle":"功能菜单路径", "mData": "functionUrl", "sDefaultContent": "", "sClass": "left",'width':'200px'},
				        {"sTitle":"路由地址", "mData": "urlRouter", "sDefaultContent": "", "sClass": "left",'width':'120px'},
				        {"sTitle":"权限地址", "mData": "authUrl", "sDefaultContent": "", "sClass": "left",'width':'120px'},
				        {"sTitle":"图标", "mData": "ico", "sDefaultContent": "", "sClass": "left",'width':'120px'},
				        {"sTitle":"父菜单", "mData": "parentName", "sDefaultContent": "", "sClass": "left",'width':'120px'},
				        {"sTitle":"是否预置", "mData": "isPreset", "sDefaultContent": "", "sClass": "left",'width':'80px',
				        	"mRender": function (data) {
					            if (data == true){
					            	return "是"
					            }else{
					            	return "否"
					              }
					          }	
				         },
				        {"sTitle":"排序", "mData": "sort", "sDefaultContent": "", "sClass": "left",'width':'60px'}
				      ],
				      "initComplete": function(settings, json) {

						$( "#datatable-column").off('click', '.gridRow',rowEdit);
						$( "#datatable-column").on('click', '.gridRow',rowEdit);
						
						$( "#datatable-column").off('click', '.gridRowDel',gridRowDel);
						$( "#datatable-column").on('click', '.gridRowDel',gridRowDel);

				    }

				    });
		
	}
	
	function rowEdit(e){
		var dateId = $(e.currentTarget).attr("dateId");
		
	    require(["../../javascript/systemSetting/function/functionEdit"],function(edit){
	    	edit.initPage(dateId);
	    });
	}
	
	function gridRowDel(e){
		var mes=confirm("您确定要删除吗？");
		if(mes==true){
		var dateId = $(e.currentTarget).attr("dateId");
		var delData = util.getJsonData({
			uri:"systemSetting/function/delFunction.json/"+dateId,
			data:{}
		});
		if(delData){
			util.showSuccessMessage("操作成功。");
			renderTable();
		}
		}
	}
	
	function addSearch(postData){
		if($("#functionName").val().trim().length > 0){
			postData.filter.filters.push({
                "field": "functionName",
                "operator": "eq",
                "value": $("#functionName").val().trim()
			});

		}
		if($("#functionUrl").val().trim().length > 0){			
			postData.filter.filters.push({
                "field": "functionUrl",
                "operator": "eq",
                "value": $("#functionUrl").val().trim()
			});
		}
		if($("#parentId").val().trim().length > 0){			
			postData.filter.filters.push({
                "field": "parentId",
                "operator": "eq",
                "value": $("#parentId").val().trim()
			});
		}
	}
	
	// 提供给外部调用的方法
	return {
		initPage:function (tab) {
			// 初始化内容区域
			initContent();			
			renderTable();
	    }
	
	}
});