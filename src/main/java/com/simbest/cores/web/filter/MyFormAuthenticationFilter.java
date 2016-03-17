package com.simbest.cores.web.filter;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.dao.DataAccessException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.simbest.cores.admin.authority.model.ShiroUser;
import com.simbest.cores.admin.authority.model.SysPermission;
import com.simbest.cores.admin.authority.model.SysUser;
import com.simbest.cores.admin.authority.service.ISysPermissionService;
import com.simbest.cores.admin.authority.service.ISysUserAdvanceService;
import com.simbest.cores.admin.syslog.model.SysLoginInfo;
import com.simbest.cores.admin.syslog.service.ISysLoginInfoService;
import com.simbest.cores.exceptions.Exceptions;
import com.simbest.cores.utils.Constants;
import com.simbest.cores.utils.configs.CoreConfig;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-3-3
 * <p>Version: 1.0
 * 
 * (一) 记录登陆信息
 * 
 * (二) 记录各模块首页，取决于SysPermission中配置的submenu地址
 * 
 * @author lishuyi
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	public final Log log = LogFactory.getLog(getClass());
	
	@Resource(name = "sysLoginInfoService")
	private ISysLoginInfoService sysLoginInfoService;
	
	@Autowired
	private ISysUserAdvanceService sysUserService;
	
	@Resource(name = "sysPermissionService")
	private ISysPermissionService sysPermissionService;
	
	private Boolean record;
	
	@Autowired
	private void initRecord(CoreConfig coreConfig){
		record = Boolean.valueOf(coreConfig.getValue("app.record.log"));
	}
	
	/**
     * 访问任何资源没有权限时进入
     */
    @Override    
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if(request.getAttribute(getFailureKeyAttribute()) != null) {
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }
    
    /**
     * 拥有权限访问资源时进入，根据applicationContext-shiro.xml跳转successUrl
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
    	Subject currentUser = SecurityUtils.getSubject();
    	ShiroUser principal = (ShiroUser)currentUser.getPrincipal();
    	if (!currentUser.hasRole("Supervisor")) {
    		if(principal != null){ // 已登陆成功不再登陆	
    			if(record){
		    		//记录登陆信息
					SysUser sysUser = sysUserService.getByUnique(principal.loginName);
					SysLoginInfo record = new SysLoginInfo();
					record.setSessionid(currentUser.getSession().getId().toString());
					record.setLoginip(currentUser.getSession().getHost());
					record.setLogintime(new Date());
					record.setUseraccount(sysUser.getLoginName());
					record.setUsername(sysUser.getUsername());
					try{
						sysLoginInfoService.insert(record);
					}catch(DataAccessException e){
		    			log.error(Exceptions.getStackTraceAsString(e));
		    		}
    			}
    		}
    	}
				
		if(principal != null){ // 已登陆成功不再登陆			
			//记录模块首页信息
			List<SysPermission> modules = sysPermissionService.getByMoudle(principal.loginName);
			Session session = currentUser.getSession();
			// 获取登录用户所拥有权限的所有模块
			for(SysPermission module : modules){
				// 获取登录用户所拥有权限的各模块菜单(按模块父类查找submenu菜单)
				List<SysPermission> menus = sysPermissionService.getByParentMenu(module.getId(), principal.loginName);			
				for(SysPermission menu : menus){
					// 获取登录用户所拥有权限的各模块各菜单各子菜单
					List<SysPermission> submenus = sysPermissionService.getByParentMenu(menu.getId(), principal.loginName);
					//一旦找到第一个子菜单，则跳出该模块
					for(SysPermission submenu : submenus){	
						//放置各模块的<B> submenu </B>首页后，跳出循环
						//变量 adminmodule  hrmodule
						session.setAttribute(StringUtils.remove(module.getName(), Constants.COLON), submenu.getUrl());
						break;
					}
					break;
				}
			}
		}		
		
		issueSuccessRedirect(request, response);
		//we handled the success redirect directly, prEvent the chain from continuing:
		return false;
	} 
}
