define([
        "text!../../pages/index/left.html",
        '../../javascript/index/left',
        'util',
        'router'
        ], 
		
function (left,index,util,router) {
	
	/**
	 * 初始化头部信息.头像,职位
	 */
	function initHeaderContent(){
		debugger;
		var userInfo = util.getJsonData({
			uri:"common/user/getLoginUserInfo.json",
			data:{}
		});		
		
		if(userInfo){
			if(userInfo.avatarId && userInfo.avatarId !=="" && userInfo.avatarId !=="null"){
				$("#user-avatar").attr("src", "common/file/download/" + userInfo.avatarId);
			}
			else{
				// assets/img/user6.jpg  默认图片
				$("#user-avatar").attr("src", "assets/img/user1.png")
			}
			if(userInfo.userName !=="" && userInfo.userName !=="null"){
				$("#_username").html(userInfo.userName);
			}
		}
		
	}

	/**
	 *  初始化左侧菜单,左侧菜单就不单独写一个文件了，直接写到本文件中。
	 */
	function initLeftSidebar(){
		
		util.loadPageContent({
			contentId:"left-sidebar",
			template:left,
			EventConfig:leftEventConfig
		})
		
		// 动态生成左侧菜单.
		var leftMenu = util.getJsonData({
			uri:"systemSetting/function/getPersionalFunctionList.json",
			data:{}
		});		
		debugger;

		var leftMenuStr="";
		 $.each(leftMenu,function(n,value) { 
			 
			 leftMenuStr += '    <li >    ';
			 if(value.isParent == true && (value.children && value.children.length > 0)){
				 leftMenuStr += '    		<a href="javascript:void(0);" class="js-sub-menu-toggle" handler="' +value.functionUrl +'" data-navigate="'+ value.urlRouter +'">    ';
			 }else if(value.isParent == true && (value.children && value.children.length == 0)){
				 leftMenuStr += '    		<a href="javascript:void(0);" class="js-sub-menu-toggle no-children" handler="' +value.functionUrl +'" data-navigate="'+ value.urlRouter +'">    ';
			 }else{
				 leftMenuStr += '    		<a href="javascript:void(0);" class="left-menu" handler="' +value.functionUrl +'" data-navigate="'+ value.urlRouter +'">    ';
			 }
			 
			 if(value.ico && value.ico != null && value.ico != ""){
				 leftMenuStr += '    		<i class="' + value.ico +'"></i>   ';
			 }else{
				 leftMenuStr += '    		<i class="fa fa-calculator fa-fw"></i>   ';
			 }			 
			 leftMenuStr += '    		<span class="text">' + value.functionName +'</span>    ';
			 
			 if(value.isParent == true && (value.children && value.children.length > 0)){
				 leftMenuStr += '    		<i class="toggle-icon fa fa-angle-left"></i>    ';
			 }
			 leftMenuStr += '     		</a>   ';
			 

			 
			 if(value.children && value.children.length > 0){
				 leftMenuStr += '   <ul class="sub-menu "  >    ';				     
						 $.each(value.children,function(m,value2) { 
							 leftMenuStr += '    <li>   ';
							 leftMenuStr += '    		<a href="javascript:void(0);" class="left-menu" handler="' +value2.functionUrl +'" data-navigate="'+ value2.urlRouter +'">    ';
							 leftMenuStr += '     				<span class="text">' + value2.functionName +'</span>   ';
								 leftMenuStr += '    		</a>    ';
								 leftMenuStr += '    </li>    ';
								 
								 
						 })
						 leftMenuStr += '  </ul>   ';		
			 }
			 
			 leftMenuStr += '    </li>    ';
		 });
		 leftMenuStr += '     <script src="assets/js/king-common.js"></script>    ';
		
		 $('#left_menu').html(leftMenuStr);
		 
		
	}
	
	
	function event(){
		/**
		 * 左侧菜单绑定事件
		 */
			$(".main-menu").on('click','li',function(e){
//				$(".main-menu li").removeClass("active");
//				$(".main-menu li .sub-menu").css("display","none");
				var childrenAClass = $(e.currentTarget).children("a").attr("class");
				var childrenClassArray = childrenAClass.split(" ");
				if (childrenClassArray.length > 1 && childrenClassArray[1] == 'no-children'){
					
				}
				else {
					$(".no-children").parent("li").attr("class","");
				}
//				$(this).addClass("active");
//				$(this).find(".sub-menu").css("display","block");
			});
			
			$( ".main-menu").on('click','.left-menu',function(e) {
				$(".main-nav a").removeClass("menu_bg");
				 $(this).addClass("menu_bg");
				var handler = $(this).attr("handler");			
				var navigateUrl = $(this).attr("data-navigate");
				router.navigate(navigateUrl  +"/" +  (new Date()).getTime());
			});
			
			$( ".main-menu").on('click','.no-children',function(e) {
				if ($(this).parent("li").attr("class") == "active"){
					var handler = $(this).attr("handler");		
					$(".main-nav a").removeClass("menu_bg");
					var navigateUrl = $(this).attr("data-navigate");
					router.navigate(navigateUrl  +"/" +  (new Date()).getTime());
				}
			});

			// 用户设置按钮事件
			$( ".logged-user").on('click','#user_setting',function(e) {
				router.navigate("/userInfo/profile"+"/" +  (new Date()).getTime());
			});
			
			// 用户退出按钮事件
			$( ".logged-user").on('click','#user_logout',function(e) {
				
				var vsdfdsf = util.getCookie("shipping-manager-vsdfdsf");
				if(vsdfdsf){
					var array = [
						 			{
						 					key:"shipping-manager-vsdfdsf",
						 					value:"false",
						 					options:{expires:7} // 有效期7天
						 			}]
						util.setCookies(array);
				}
				
				templateData = util.getJsonData({
					uri:"/common/login/logout.json",
					data:{}
				});
				
				location.href="login.jsp";
				
			});
			
			$("#content").on("click","table td",function(){
				var _this = $(this).parent("tr");
//				console.log("click:",_this.parent("tr").prev());
				_this.parent("tbody").children("tr").css({color:"#55555"});
				_this.css({ color: "#428BCA"});
			});
	}

	
	/**
	 * 左侧菜单事件
	 * //没用到,怕左边太重
	 */
	var leftEventConfig = {
			_dashboardEvent:function(e){
					alert("_dashboardEvent")
			},
			_widgetsEvent:function(e){
				alert("_widgetsEvent")
			}
		};
	
	// 提供给外部调用的方法
	return {
		initPage:function (tab) {
			initHeaderContent();
			initLeftSidebar();// 初始化左侧菜单
			event();
		}
	}
});