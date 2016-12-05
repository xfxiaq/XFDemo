define([
        "text!../../../../pages/systemSetting/role/role-list.html",
        'util' 
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
	}
	
	/**
	 * 页面事件
	 */
	var listEventConfig = {
			_seachEvent:function(e){
				renderTable();
			},
			_createEvent:function(e){
				    require(["../../javascript/systemSetting/role/roleAdd"],function(add){
				    	add.initPage();
			    });
			}
		};

	function renderTable(){
      ///////////////////////////////////////////////////////////////////////////////
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
				     "sAjaxSource": "systemSetting/role/auth_menu_list.json",     //指定要从哪个URL获取数据, 注意, 此处必须有 不然下方获取参数不正常
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
				        	             {"field":"creationTime","dir":"asc"},
				        	    ],
				        	    "filter": {
				        	        "logic": "and",
				        	        "filters": []
				        	    }
				        	};
				        
				        addSearch(postData);

				        oSettings.jqXHR = $.ajax( {
					        url: "systemSetting/role/auth_menu_list.json",
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
						            	+ "<a  href='javascript:void(0)' class='delRow'  dateId="+ data +" \"> 删除 </a>"
						            ;
						          }	
				        },
				        {"sTitle":"角色编码", "mData":"roleCode", "sDefaultContent": "","sClass": "left",'width':'120px'},
				        {"sTitle":"角色名称", "mData": "roleName", "sDefaultContent": "", "sClass": "left",'width':'120px'},
				      ],
				      "initComplete": function(settings, json) {

						$( "#datatable-column").off('click', '.gridRow',rowEdit);
						$( "#datatable-column").on('click', '.gridRow',rowEdit);
						
						$( "#datatable-column").off('click', '.delRow',delRow);
						$( "#datatable-column").on('click', '.delRow',delRow);

				    }

				    });
	}
	
	function rowEdit(e){
		var dateId = $(e.currentTarget).attr("dateId");
		
	    require(["../../javascript/systemSetting/role/roleEdit"],function(edit){
	    	edit.initPage(dateId);
	    });
	}
	
	function delRow(e){
		var mes=confirm("您确定要删除吗？");
		if(mes==true){
		var dateId = $(e.currentTarget).attr("dateId");
		var delData = util.getJsonData({
			uri:"systemSetting/role/delRole.json/"+dateId,
			data:{}
		});
		if(delData){
			util.showSuccessMessage("操作成功。");
			renderTable();
		}
		}
	}
	
	function addSearch(postData){
		if($.trim($("#roleCode").val()).length > 0){
			postData.filter.filters.push({
                "field": "roleCode",
                "operator": "eq",
                "value": $("#roleCode").val().trim()
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