define([
    'kendo'],
    function (kendo) {

        var Util = function () {
        };


        Util.prototype.setCookies = function(arrs){
            var items = [];
            (arrs instanceof Array) ? items = arrs : items.push(arrs);
            $.each(items, function(index,item){
                $.cookie(item.key, item.value, item.options);
            })
        };

        Util.prototype.getCookie = function(key){
            return $.cookie(key);
        };        


        Util.prototype.removeCookies = function(key){
        	$.removeCookie(key);
        };

        Util.prototype.getJsonData = function(options){
            var content = null;
            $.ajax({
             type: "POST",
             async:false,
             dataType:'json',
             url: options.uri,
             data:kendo.stringify(options.data)||{},
             contentType: "application/json;charset=utf-8",
             cache: false,
             success: function(data){
            	 	content = data;
            	 	
            	 	if(content._backcode && content._backcode != "200" && content._backcode != "0" ){

//            	 		$(".top-general-alert").removeClass("alert-success").addClass("alert-danger");
//                	 	$(".top-general-alert span").text(content._backmes);
//            			$('.top-general-alert').delay(800).slideDown('medium');
//            			$('.top-general-alert .close').click( function() {
//        				$('.top-general-alert').hide();
//        				$(".top-general-alert span").text("");
//            			});
//            			$('.top-general-alert').delay(800).fadeOut(6000,function(){
//            				$(".top-general-alert span").text("");
//            			});
//            			content = null;
            	 	}else{
//            	 		$(".top-general-alert").removeClass("alert-danger").addClass("alert-success");
//                	 	$(".top-general-alert span").text(content._backmes);
//            			$('.top-general-alert').delay(800).slideDown('medium');
//            			$('.top-general-alert .close').click( function() {
//            				$('.top-general-alert').hide();
//            				$(".top-general-alert span").text("");
//            			});
//            			$('.top-general-alert').delay(800).fadeOut(3000,function(){
//            				$(".top-general-alert span").text("");
//            			});
            	 	}
            	 	},
 	        error: function( XMLHttpRequest, textStatus, errorThrown ) {

	        }
           });
            

            return content;
        	
        };
        
        Util.prototype.showSuccessMessage = function(msg){
        	$(".top-general-alert").removeClass("alert-danger").addClass("alert-success");
    	 	$(".top-general-alert span").text(msg);
			$('.top-general-alert').delay(800).slideDown('medium');
			$('.top-general-alert .close').click( function() {
				$('.top-general-alert').hide();
				$(".top-general-alert span").text("");
			});
			$('.top-general-alert').delay(800).fadeOut(3000,function(){
				$(".top-general-alert span").text("");
			});
        };
        
        Util.prototype.showErroMessage = function(msg){
        	$(".top-general-alert").removeClass("alert-success").addClass("alert-danger");
    	 	$(".top-general-alert span").text(msg);
			$('.top-general-alert').delay(800).slideDown('medium');
			$('.top-general-alert .close').click( function() {
				$('.top-general-alert').hide();
				$(".top-general-alert span").text("");
			});
			$('.top-general-alert').delay(800).fadeOut(3000,function(){
				$(".top-general-alert span").text("");
			});
        };
        
        
        Util.prototype.showBackMessage = function(data){
        	
        	
        	if(data && data._backcode && (data._backcode == '200' || data._backcode == '0')){
        		var successMesage = "操作成功。";
        		if(data._backmes && data._backmes.length>0){
        			successMesage = data._backmes;
        		}
        		
        		
            	$(".top-general-alert").removeClass("alert-danger").addClass("alert-success");
        	 	$(".top-general-alert span").text(successMesage);
    			$('.top-general-alert').delay(800).slideDown('medium');
    			$('.top-general-alert .close').click( function() {
    				$('.top-general-alert').hide();
    				$(".top-general-alert span").text("");
    			});
    			$('.top-general-alert').delay(800).fadeOut(3000,function(){
    				$(".top-general-alert span").text("");
    			});
        		
        	}else{
        		var errorMessage = "操作失败。";
        		if(data && data._backmes){
        			errorMessage = data._backmes;
	        	}
        		
            	$(".top-general-alert").removeClass("alert-success").addClass("alert-danger");
        	 	$(".top-general-alert span").text(errorMessage);
    			$('.top-general-alert').delay(800).slideDown('medium');
    			$('.top-general-alert .close').click( function() {
    				$('.top-general-alert').hide();
    				$(".top-general-alert span").text("");
    			});
    			$('.top-general-alert').delay(800).fadeOut(3000,function(){
    				$(".top-general-alert span").text("");
    			});
        	}
        };
        
        
        Util.prototype.loadPageContent = function(options){
        	var _options={};
        	var id = options.contentId || "content";
			var templateData = options.templateData || {};
        	var template = kendo.template(options.template);

        	$("#"+id).html(template(templateData));
        	if(options.templateData){
        		$.extend(_options,options.templateData);
        	}
        	if(options.EventConfig){
        		$.extend(_options,options.EventConfig);
        	}
        	var viewModel = kendo.observable(_options);
        	kendo.bind($("#"+id), viewModel);
        	return viewModel;
            };
        
        return new Util();
});