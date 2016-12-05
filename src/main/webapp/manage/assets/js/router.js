define([
        "kendo"
        ], 
		
function (addPage,util) {
	var router = new kendo.Router(
			{
				routeMissing : function(e){
				router.navigate("/home");
			}
	});
	var loadRouter = function(){
		
		// 首页  默认显示的页面
		   router.route("/", function() {    	
			   require(['../../javascript/cargoManager/order/mainPage'], function (index) {
					index.initPage();
				});
		    });	

	   router.route("/home", function() {    	
		   require(['../../javascript/index/index'], function (index) {
			   index.initPage();
		   });
	    });
	   // 系统设置
	   router.route("/system/function(/:time)", function() {    	
		   require(['../../javascript/systemSetting/function/functionList'], function (index) {
				index.initPage();
			});
	    });
	   router.route("/system/role(/:time)", function() {    	
		   require(['../../javascript/systemSetting/role/roleList'], function (index) {
				index.initPage();
			});
	    });
	   router.route("/system/user(/:time)", function() {    	
		   require(['../../javascript/systemSetting/user/userList'], function (index) {
				index.initPage();
			});
	    });

		   

	   // 用户基本信息
	   router.route("/userInfo/profile(/:time)", function() {
		   require(['../../javascript/userProfile/profile'], function (profile) {
			   profile.initPage();
			});
	    });
	   
	   // 系统设置用户编辑
	   router.route("/system/user/edit/:userId(/:time)", function(userId) {    	
		    require(["../../javascript/systemSetting/user/userEdit"],function(edit){
		    	edit.initPage(userId);
		    });
	    });
	  
	   // 启动
	   router.start();
	}
	
	/**
	 * urlPath: /aaa
	 * navigateJs: '../../javascript/contract/contract-list'  
	 */
	var addRouter = function(urlPath,navigateJs){
		 router.route(urlPath, function() {		    	
				require([navigateJs], function (pageJs) {									
					pageJs.initPage();
				});
		 });		
	}
	
	var navigate= function(urlPath){
		//console.log(urlPath);
		router.navigate(urlPath);
	}
	
	return {
		initRouter:function () {			
			// 加载list页面
			 loadRouter();
	    },
	    addRouter:function(urlPath,navigateJs){
	    	addRouter(urlPath,navigateJs);
	    },
		navigateParam:function(urlPath){
			if(!urlPath){
				return false;
			}else if(arguments.length > 1){
				urlPath = urlPath + (/\/$/.test(urlPath)? '': '/');
				for(var i = 1;i<arguments.length;i++){
					urlPath = urlPath + arguments[i] + '/'
				}
			}
			this.navigate(urlPath.replace(/\/$/,''));
		},
	    navigate:function(urlPath){
	    	navigate(urlPath);
	    }
	
	}
	
});