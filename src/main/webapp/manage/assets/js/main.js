require.config({
    urlArgs: 'rand=' + (new Date()).getTime(), // 在开发中这很有用，但请记得在部署到生成环境之前移除它
    waitSeconds: 60,
    baseUrl:'assets/js',
    paths: {
		kendo:'kendo/kendo.web.min',
		util:'util',
		text:'requireJs/text',
		router:'router'
    },
 shim: {
    
 
 }
});


//Start the main app logic.
require([
         '../../javascript/index/index' ,
         'kendo',
         'router'
         ],
function (index,kendo,router) {	
	router.initRouter();
	index.initPage();
});

