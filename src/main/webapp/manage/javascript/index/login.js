$(function(){ 
		// 判断是否需要直接登录
	var holdlogin = getCookie("shipping-manager-vsdfdsf");
	var userId = getCookie("shipping-manager-u3f45g");
	var password = getCookie("shipping-manager-unmm876");
	
	if(holdlogin == "true"){
	        $.ajax({
	            type: "POST",
	            async:false,
	            dataType:'json',
	            url: "common/login/autoLogin.json",
	            data: JSON.stringify({
	            	userId:userId,
	            	password:password
	            }),
	            contentType: "application/json;charset=utf-8",
	            cache: false,
	            success: function(data){
	            	if(data){
	            		location.href="index.html";
	            	}
	            	
	            },
		        error: function( XMLHttpRequest, textStatus, errorThrown ) {
		        }
	        });
		
	}else if(holdlogin =="false"){
			// $("#userId").val()   $("#password").val()
			$("#userId").val(userId) ;
			$("#password").val("&*(@#-$%");
			$("#cpassword").val(password);
	}else{}

	
	
}); 


function setCookie(arrs){
    var items = [];
    (arrs instanceof Array) ? items = arrs : items.push(arrs);
    $.each(items, function(index,item){
        $.cookie(item.key, item.value, item.options);
    })
}

function getCookie(key){
	return $.cookie(key);
}

function removeCookies(key){
	$.removeCookie(key);
}

	function holdLogin(key){
		var holdLogin = $("input[id='holdLogin']").is(':checked');
		// 如果保持登录状态, 加到cookie
		if(holdLogin){
			var array = [
			{
					key:"shipping-manager-vsdfdsf",
					value:"true",
					options:{expires:7} // 有效期7天
			},
			{
				key:"shipping-manager-u3f45g",
				value:$("#userId").val(),
				options:{expires:7}
			},
			{
					key:"shipping-manager-unmm876",
					value:key,
					options:{expires:7}
			},
			
		];
			
			setCookie(array);
		}
		// 如果不保持,删除cookie
		else{
			removeCookies("shipping-manager-vsdfdsf");
			removeCookies("shipping-manager-u3f45g");
			removeCookies("shipping-manager-unmm876");
		}		
	}

	function login(){

        $.ajax({
            type: "POST",
            async:false,
            dataType:'json',
            url: "common/login/auth.json",
            data: JSON.stringify({
            	userId:$("#userId").val(),
            	password:$("#password").val(),
            	new_password:$("#cpassword").val()
            }),
            contentType: "application/json;charset=utf-8",
            cache: false,
            success: function(data){
//           	 	content = data;
           	 	
//           	 	if(content._backcode && content._backcode != "200"){
//           	 		
//           	 	}
           	 	if(data.result == "true"){
           	 			// 判断是否需要加入cookie
           	 			holdLogin(data.key);
           	 			location.href="index.html";
           	 	}else{
//    	        	$(".top-general-alert").removeClass("alert-success").addClass("alert-danger");
//            	 	$(".top-general-alert span").text("用户名或密码不正确");
//        			$('.top-general-alert').delay(800).slideDown('medium');
//        			$('.top-general-alert .close').click( function() {
//        				$('.top-general-alert').hide();
//        				$(".top-general-alert span").text("");
//        			});
//        			$('.top-general-alert').delay(800).fadeOut(3000,function(){
//        				$(".top-general-alert span").text("");
//        			});
           	 		
           	 		$("#error").html("用户名或密码不正确");
           	 		$("#error").addClass("alert-danger");
	           	 	$('#error').delay(800).slideDown('medium');
	           	 	$('#error').delay(800).fadeOut(3000,function(){
	           	 	$("#error").removeClass("alert-danger");
	    				$("#error").text("");
	    			});
           	 		
           	 	}
           	 	
            },
	        error: function( XMLHttpRequest, textStatus, errorThrown ) {
	        }
        });
           
	}