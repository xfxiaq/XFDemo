$(function(){  

	$(document).ajaxComplete( function(event, jqXHR, options){
	    //alert("处理函数1：请求的url为" + options.url);
		if(jqXHR.status == 401){
			//alert("tiaozhuan")
			location.href="login.jsp";
		}
		if(jqXHR.status == 403){
			//alert("tiaozhuan")
			location.href="auth.jsp";
		}
		if(jqXHR.status == 406){
			var responseJson = $.parseJSON(jqXHR.responseText);			
        	$(".top-general-alert").removeClass("alert-success").addClass("alert-danger");
    	 	$(".top-general-alert span").text(responseJson);
			$('.top-general-alert').delay(800).slideDown('medium');
			$('.top-general-alert .close').click( function() {
				$('.top-general-alert').hide();
				$(".top-general-alert span").text("");
			});
			$('.top-general-alert').delay(800).fadeOut(3000,function(){
				$(".top-general-alert span").text("");
			});
		}
	});
	

	
	
}); 