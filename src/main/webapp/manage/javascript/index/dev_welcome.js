define([
        "text!../../pages/index/dev_welcome.html",
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

		};

	
	// 提供给外部调用的方法
	return {
		initPage:function (tab) {

			// 初始化内容区域
			initContent();
			
			renderTable();
	    }
	
	}
});