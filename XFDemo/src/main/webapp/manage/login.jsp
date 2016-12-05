<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie ie9" lang="zh-CN" class="no-js"> <![endif]-->
<!--[if !(IE)]><!-->
<html lang="zh-CN" class="no-js">
<!--<![endif]-->

<head>
	<title>货运管理平台</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="description" content="KingAdmin Dashboard">
	<meta name="author" content="The Develovers">
	<link href="assets/img/favicon.ico" rel="icon" type="image/x-icon" />

	<!-- CSS -->
	<link href="assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="assets/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="assets/css/main.css" rel="stylesheet" type="text/css">

	<!--[if lte IE 9]>
		<link href="assets/css/main-ie.css" rel="stylesheet" type="text/css" />
		<link href="assets/css/main-ie-part2.css" rel="stylesheet" type="text/css" />
	<![endif]-->

	<!-- Fav and touch icons -->
	<!-- <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/kingadmin-favicon144x144.png">
	<link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/kingadmin-favicon114x114.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/kingadmin-favicon72x72.png">
	<link rel="apple-touch-icon-precomposed" sizes="57x57" href="assets/ico/kingadmin-favicon57x57.png">
	<link rel="shortcut icon" href="assets/img/tran.png"> -->
	
		<!-- Javascript -->
	<script src="assets/js/jquery/jquery-2.1.0.min.js"></script>
	<script src="assets/js/bootstrap/bootstrap.js"></script>
	<script src="assets/js/plugins/modernizr/modernizr.js"></script>
	<script src="assets/js/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="javascript/index/login.js" language="javascript"></script>

<style>
.login-bg{
			/*此部分支持chrome，应该也支持firefox*/
            background: rgb(26,28,29);
            background: url('assets/img/login_bg0505.jpg') no-repeat center fixed;
            background-attachment: fixed;
            background-size: 100% 100%;
			/*以下是IE部分，使用滤镜*/
            filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='/assets/img/login_bg01.jpg',sizingMethod='scale');
            background-repeat: no-repeat;
            background-positon: 100%, 100%;
            font: normal 12px tahoma, arial, verdana, sans-serif;
            margin: 0;
            padding: 0;
            border: 0 none;
            overflow: hidden;
            height: 100%;
        }
        
        .full-page-wrapper .inner-page {
		    padding-top: 10%;
		}
		
		.page-login .login-box {
		    width: 25%;
		    margin-right: 290px;
		}
		
		.page-login .login-box {
		    padding: 10px 25px 25px;
		    background-color: #FFFFFF;
		}
		
		.btn-blue {
			background-color: #72B4DE;
   	 		border-color: #72B4DE;
   	 		color: #FFFFFF;
		}
		
		.btn:hover, .btn:focus {
		    color: #FFFFFF;
		}
		
		.input-group-addon {
		    color: #ccc;
		}
		
</style>

</head>

<body class="login-bg">
	<div class="wrapper full-page-wrapper page-auth page-login text-center">
		<div class="inner-page">
			<div class="logo">
				<!-- <h2>货运管理平台</h2> -->
				
			</div>
			
			
			<div class="login-box center-block">
			<h4 id="error"></h4>
				<form class="form-horizontal" role="form">
					<p class="title">请输入登录信息</p>
					<div class="form-group">
						<label for="userId" class="control-label sr-only">用户名</label>
						<div class="col-sm-12">
							<div class="input-group">
								<input type="text" placeholder="用户名" id="userId" class="form-control" style="background-color: #f7f8f8;">
								<span class="input-group-addon"><i class="fa fa-user"></i></span>
							</div>
						</div>
					</div>
					<label for="password" class="control-label sr-only">密码</label>
					<div class="form-group">
						<div class="col-sm-12">
							<div class="input-group">
								<input type="password" placeholder="密码" id="password" class="form-control" style="background-color: #f7f8f8;">
								<input type="hidden"  id="cpassword" class="form-control" >
								<span class="input-group-addon"><i class="fa fa-lock"></i></span>
							</div>
						</div>
					</div>

					<label class="fancy-checkbox">
						<input type="checkbox" id="holdLogin">
						<span>保持登录</span>
					</label>

					<button type="button" class="btn btn-blue btn-block" onclick="login()">登录</button>
				</form>
<!-- 
				<div class="links" style="margin-top:30px">
					<p><a href="#">忘记密码?</a></p>
					<p><a href="#">创建新帐号</a></p>
				</div>
 -->
			</div>
		</div>
	</div>

	<footer class="footer">&copy; 2014-2015 The Develovers</footer>


</body>


</html>

