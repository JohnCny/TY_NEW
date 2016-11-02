package com.wicresoft.jrad.base.web.interceptor;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wicresoft.jrad.base.auth.IAuthMgr;
import com.wicresoft.jrad.base.auth.IResource;
import com.wicresoft.jrad.base.auth.IResourceMgr;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.IUserLogMgr;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.auth.JRadOperationHelper;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.enviroment.GlobalSetting;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.message.Errors;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;

public class AuthInterceptor extends HandlerInterceptorAdapter implements JRadConstants {

	private List<String> exclude = new ArrayList<String>();
	
	private List<String> excludeContains = new ArrayList<String>();

	private String loginPage;

	private String mainPage;

	private String noAccessRightPage;

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public void setNoAccessRightPage(String noAccessRightPage) {
		this.noAccessRightPage = noAccessRightPage;
	}

	public List<String> getExcludeContains() {
		return excludeContains;
	}

	public void setExcludeContains(List<String> excludeContains) {
		this.excludeContains = excludeContains;
	}

	public void init() {
	}

	/**
	 * the exclude pages don't need authentication
	 */
	public void setExclude(List<String> exclude) {
		this.exclude = exclude;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		//排除不需要过滤的
		for(String pathMatch : excludeContains){
			if(request.getServletPath().contains(pathMatch)){
				//TODO ipad权限
				return true;
			}
		}
		
		if (exclude.contains(request.getServletPath())) {
			return true;
		}

		LoginManager loginService = Beans.get(LoginManager.class);
		IUser user = loginService.getLoggedInUser(request);

		if (user == null) {
//			doAuthFail(request, response, "system.session.timeout", servletPath);
			response.sendRedirect(request.getContextPath() + loginPage);
			return false;
		}

		/* ***************************** */
		// Only used for develop, will be auto closed for release
		if (Beans.get(GlobalSetting.class).isSuperAdminMode(request)) {
			return true;
		}
		/* ***************************** */

		return checkUserAccessRight(user, request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);

		// Set module name
		if (modelAndView != null) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Object object = handlerMethod.getBean();

			if (object instanceof BaseController) {
				BaseController controller = (BaseController) object;
				String moduleName = controller.getModuleName();
				modelAndView.addObject(MODULE_NAME, moduleName);

				JRadOperation operAnno = (JRadOperation) handlerMethod.getMethod().getAnnotation(
						JRadOperation.class);
				if (operAnno != null) {
					modelAndView.addObject(OPERATION, operAnno.value());

					String pageTitle = Beans.get(I18nHelper.class).getMessageNotNull(
							moduleName + "." + JRadOperationHelper.getOperationName(operAnno.value()));
					modelAndView.addObject(PAGE_TITLE, pageTitle);
				}
			}

			RequestMapping reqMapping = (RequestMapping) handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
			if (reqMapping != null) {
				String modulePattern = reqMapping.value()[0].replace("*", "");
				modelAndView.addObject(MODULE_PATH, request.getContextPath() + modulePattern);
			}
		}
	}
	
	protected boolean checkUserAccessRight(IUser user, HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		JRadModule resource = ((HandlerMethod) handler).getBeanType().getAnnotation(JRadModule.class);
		JRadOperation action = ((HandlerMethod) handler).getMethod().getAnnotation(JRadOperation.class);

		if (resource != null && action != null) {
			String resourceValue = resource.value();
			long actionValue = action.value();

			boolean passed = Beans.get(IAuthMgr.class).hasAccessRight(user.getId(), resourceValue, actionValue);
			if (!passed) {
				HandlerMethod method = (HandlerMethod) handler;
				Class<?> returnType = method.getReturnType().getParameterType();

				if (returnType.isAssignableFrom(JRadModelAndView.class)) {
//					response.sendRedirect(request.getContextPath() + noAccessRightPage);
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "");
				} else if (returnType.isAssignableFrom(JRadReturnMap.class)) {
					JRadReturnMap map = new JRadReturnMap();
					map.setSuccess(false);
					map.setErrors(new Errors().addGlobalError("system.noaccessright"));

					OutputStream os = response.getOutputStream();
					os.write(JSONObject.fromObject(map).toString().getBytes());
					os.flush();
					os.close();
				}
			}

			return passed;
		}
		return true;
	}

/*	protected void doAuthFail(HttpServletRequest request, HttpServletResponse response, String message, String servletPath) throws Exception {
		if (servletPath.equals(mainPage)) {
			response.sendRedirect(request.getContextPath() + loginPage);
		} else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, Beans.get(I18nHelper.class).getMessage(message));
		}
	}
*/
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// 开发模式不记录操作日志
		if(Beans.get(GlobalSetting.class).isDevelopMode()){
			return;
		}
		writeUserLog(request, response, handler, ex);
	}

	private void writeUserLog(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

		// Write DB log
		// messageSourceHelper.getMessage(code, args)

		// Write log4j log

		try {
			LoginManager loginMgr = Beans.get(LoginManager.class);
			IResourceMgr resMgr = Beans.get(IResourceMgr.class);
			IUserLogMgr userLogMgr = Beans.get(IUserLogMgr.class);
			
			if (userLogMgr == null || resMgr == null) {
				return;
			}

			IUser user = loginMgr.getLoggedInUser(request);
			if (user != null) {
				if (ex == null) {
					if (resMgr != null && userLogMgr != null) {

						I18nHelper i18n = Beans.get(I18nHelper.class);
						JRadModule moduleAnno = ((HandlerMethod) handler).getBeanType().getAnnotation(JRadModule.class);
						JRadOperation operAnno = ((HandlerMethod) handler).getMethod().getAnnotation(JRadOperation.class);

						if (moduleAnno != null && operAnno != null) {
							String module = moduleAnno.value();
							long operation = operAnno.value();

							String userId = user.getId();
							String userLogin = user.getLogin();
							String ipAddress = request.getRemoteAddr();

							IResource resource = resMgr.getResourceBySystemName(module);
							String moduleDisplayName = resource != null ? resource.getDisplayName() : module;
							String operationName = JRadOperationHelper.getOperationName(operation);
							String operationDisplayName = i18n.getMessage("operation." + operationName);
							String operationResult = "成功";

							userLogMgr.asyncAddUserLog(userId, userLogin, moduleDisplayName.replace("所有资源-", ""), operationDisplayName, operationResult, ipAddress);
						}
					}
				} else {
					if (resMgr != null && userLogMgr != null) {
						I18nHelper i18n = Beans.get(I18nHelper.class);
						JRadModule moduleAnno = ((HandlerMethod) handler).getBeanType().getAnnotation(JRadModule.class);
						JRadOperation operAnno = ((HandlerMethod) handler).getMethod().getAnnotation(JRadOperation.class);

						if (moduleAnno != null && operAnno != null) {
							String module = moduleAnno.value();
							long operation = operAnno.value();

							String userId = user.getId();
							String userLogin = user.getLogin();
							String ipAddress = request.getRemoteAddr();

							IResource resource = resMgr.getResourceBySystemName(module);
							String moduleDisplayName = resource != null ? resource.getDisplayName() : module;
							String operationName = JRadOperationHelper.getOperationName(operation);
							String operationDisplayName = i18n.getMessage("operation." + operationName);
							String operationResult = "失败";

							userLogMgr.asyncAddUserLog(userId, userLogin, moduleDisplayName, operationDisplayName, operationResult, ipAddress);
						}
					}
				}
			}
		} catch (Exception e0) {
			e0.printStackTrace();
		}
	}

}
