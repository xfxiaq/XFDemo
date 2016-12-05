define([
        "text!../../../../pages/systemSetting/user/user-list.html",
        'util',
        'router'
        ], 
		
function (listHandler,util,router) {
	
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
				    require(["../../javascript/systemSetting/user/userAdd"],function(add){
				    	add.initPage();
			    });
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
				     "sAjaxSource": "common/user/auth_menu_list.json",     //指定要从哪个URL获取数据, 注意, 此处必须有 不然下方获取参数不正常
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
					        url: "common/user/auth_menu_list.json",
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
					          "mRender": function (data,display,row) {
						            return  "<a  href='javascript:void(0)' class='gridRow'  dateId="+ data +" \"> 编辑 </a>" 
						            +"<a  href='javascript:void(0)' class='changePasswordRow'  dateId="+ data +" \" dateUserId="+ row.userId +" \" dateUserName="+ row.userName +" \"> 修改密码 </a>"
						            ;
						          }	
				        },
				        {"sTitle":"用户名称", "mData":"userName", "sDefaultContent": "","sClass": "left",'width':'100px'},
				        {"sTitle":"登录名", "mData": "userId", "sDefaultContent": "", "sClass": "left",'width':'100px'},
				        {"sTitle":"手机", "mData": "phoneNum", "sDefaultContent": "", "sClass": "left",'width':'100px'},
				        {"sTitle":"是否启用", "mData": "enable", "sDefaultContent": "", "sClass": "left",'width':'80px',
				        	"mRender": function (data) {
					            if (data == true){
					            	return "是"
					            }else{
					            	return "否"
					              }
					          }	
				         }
				      ],
				      "initComplete": function(settings, json) {

						$( "#datatable-column").off('click', '.gridRow',rowEdit);
						$( "#datatable-column").on('click', '.gridRow',rowEdit);

						$( "#datatable-column").off('click', '.changePasswordRow',changePassword);
						$( "#datatable-column").on('click', '.changePasswordRow',changePassword);
						
				    }

				    });
		
	}
	
	function rowEdit(e){
		var dateId = $(e.currentTarget).attr("dateId");
		router.navigate("/system/user/edit/"+ dateId+"/" +  (new Date()).getTime());
	}
	
	function changePassword(e){
			var dateId = $(e.currentTarget).attr("dateId");
			var dateUserId = $(e.currentTarget).attr("dateUserId");
			var dateUserName = $(e.currentTarget).attr("dateUserName");
			
		    require(["../../javascript/systemSetting/user/changePassword"],function(changePassword){
		    	changePassword.initPage(dateId,dateUserId, dateUserName);
		    });
	}
	
	function addSearch(postData){
		if( $.trim($("#userName").val()).length > 0){
			postData.filter.filters.push({
                "field": "userName",
                "operator": "eq",
                "value": $("#userName").val().trim()
			});
		}
	}
	
	// 提供给外部调用的方法
	return {
		initPage:function (tab) {
			debugger;
			// 初始化内容区域
			initContent();			
			renderTable();
	    }
	
	}
});