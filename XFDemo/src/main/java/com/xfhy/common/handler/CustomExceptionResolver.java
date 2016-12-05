package com.xfhy.common.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.xfhy.common.exception.BaseAppRuntimeException;
import com.xfhy.common.utils.JsonUtils;



@Component
public class CustomExceptionResolver  implements  HandlerExceptionResolver{
	/** 业务异常状态码 */
	private static final Integer EXCEPTION_CODE = 406;
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		Throwable catchedException = ex;
		try{
			// 处理业务运行时异常
			if ( catchedException instanceof BaseAppRuntimeException) {
				return this.handleAppException(request, response, (BaseAppRuntimeException) catchedException);
			}
		}catch(final Throwable e){
			
		}

		
		return new ModelAndView();
	}
	
	/**
	 * 处理业务异常。
	 * 
	 * @param request HTTP请求
	 * @param response HTTP应答
	 * @param ex 异常
	 * @return 模型视图
	 * @throws IOException HTTP应答信息写入异常
	 */
	protected ModelAndView handleAppException(final HttpServletRequest request,
			final HttpServletResponse response, final BaseAppRuntimeException ex) throws IOException {
		
		this.setResponseBody(response, CustomExceptionResolver.EXCEPTION_CODE,
				ex.getMessage());
		return new ModelAndView();
	}
	
	/**
	 * HTTP请求参数为{@link Model}类型的HTTP应答内容设定。
	 * 
	 * @param response HTTP应答
	 * @param status 状态码
	 * @param messages 异常消息
	 * @throws IOException 写HTTP应答内容时抛出的异常
	 */
	protected void setResponseBody(final HttpServletResponse response, final int status, final Object messages)
			throws IOException {
		response.setStatus(status);
		final String json = JsonUtils.pojoToJson(messages);
		response.getWriter().write(json);
	}
	


}
