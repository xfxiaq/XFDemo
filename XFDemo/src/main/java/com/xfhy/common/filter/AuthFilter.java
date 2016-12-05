package com.xfhy.common.filter;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xfhy.common.dto.UserDTO;
import com.xfhy.common.utils.LoginUserUtils;



/**
 * 认证过滤器。
 * 
 * @author zxl
 * @version 1.0
 */
public class AuthFilter extends OncePerRequestFilter {

	//排除过滤部分
	private Pattern[] excludePatterns = {};
	
	private String loginRedirect = "/manage/login.jsp";
	
	private String authRedirect = "/manage/auth.jsp";

	/** logger */
	private static final Log LOGGER = LogFactory.getLog(AuthFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String servletPath = request.getServletPath();
		
		LOGGER.debug("==================================" + servletPath);
		UserDTO userDTO = LoginUserUtils.getSessionLoginUser();
		
		if(userDTO == null){//session 不存在情况
			if(isExclude(servletPath)){//排除过滤的情况
				filterChain.doFilter(request, response);
			}else{//范围内的情况
				if (isAjaxRequest(request)) { //ajax请求
					response.setCharacterEncoding("UTF-8");
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				}else{//普通请求
					response.sendRedirect(loginRedirect);
				}
			}
		}else{
			userDTO.set_backmes(new Date().toString());
			// 判断是否有权限访问该URL
			if (checkFunctionAuth(userDTO, servletPath)) {
				filterChain.doFilter(request, response);
			} else {
				// 如果没有权限, 那么跳转到权限页面
				if (isAjaxRequest(request)) {
					response.setCharacterEncoding("UTF-8");
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				} else {
					response.sendRedirect(authRedirect);
				}
			}
		}
	}

	/**
	 * 判断是否是ajax请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		return request.getRequestURI().contains(".json");
	}

	private Boolean checkFunctionAuth(UserDTO userDTO, String requestUrl) {

		if (!requestUrl.contains("auth_menu_")) {
			return true;
		}

		long beginTime = System.currentTimeMillis();

		boolean isPermistion = false;
		String allFunctions = userDTO.getFunctionUrls();

		// 如果 权限集合不空
		if (allFunctions != null && !"".equals(allFunctions)) {
			// 校验是否拥有当前请求的权限
			String[] arrayFunctions = allFunctions.split(",");
			for (String function : arrayFunctions) {
				if (requestUrl.contains(function)) {
					isPermistion = true;
					break;
				}
			}

		} else { // 如果权限集合为空
			isPermistion = false;
		}

		return isPermistion;
	}

	private boolean isExclude(String servletPath){
		for(Pattern p : excludePatterns){
			Matcher matcher = p.matcher(servletPath);
			if(matcher.matches()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 设置不需要做过滤的URLs。
	 * 
	 * @param excludeUrls
	 *            不需要做过滤的URLs
	 */
	public void setExcludePatterns(final String excludePatterns) {
        String[] excludeStrs = UrlMatcher.splitUrls(excludePatterns);
        this.excludePatterns = new Pattern[excludeStrs.length];
        for(int i = 0; i < excludeStrs.length; i++){
        	this.excludePatterns[i] = Pattern.compile(excludeStrs[i]);
        }
	}

	public void setLoginRedirect(String loginRedirect) {
		this.loginRedirect = loginRedirect;
	}

	public void setAuthRedirect(String authRedirect) {
		this.authRedirect = authRedirect;
	}
	
	
}
