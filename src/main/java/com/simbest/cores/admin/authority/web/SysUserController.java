package com.simbest.cores.admin.authority.web;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.simbest.cores.admin.authority.model.DynamicUserTreeNode;
import com.simbest.cores.admin.authority.model.ShiroUser;
import com.simbest.cores.admin.authority.model.SysOrg;
import com.simbest.cores.admin.authority.model.SysUser;
import com.simbest.cores.admin.authority.service.ISysOrgAdvanceService;
import com.simbest.cores.admin.authority.service.ISysPermissionAdvanceService;
import com.simbest.cores.admin.authority.service.ISysRoleAdvanceService;
import com.simbest.cores.admin.authority.service.ISysUserAdvanceService;
import com.simbest.cores.admin.authority.service.ISysUserService;
import com.simbest.cores.cache.cqengine.search.SysUserSearch;
import com.simbest.cores.exceptions.Exceptions;
import com.simbest.cores.shiro.AppUserSession;
import com.simbest.cores.utils.AppCodeGenerator;
import com.simbest.cores.utils.Constants;
import com.simbest.cores.utils.Digests;
import com.simbest.cores.utils.Encodes;
import com.simbest.cores.utils.StringUtil;
import com.simbest.cores.utils.annotations.LogAudit;
import com.simbest.cores.utils.editors.SysOrgEditor;
import com.simbest.cores.utils.enums.SNSLoginType;
import com.simbest.cores.web.BaseController;
import com.simbest.cores.web.filter.SNSAuthenticationToken;

@Controller
@RequestMapping(value = {"/action/sso/admin/authority/sysuser", //SSO跳转，Shrio不拦截
	"/action/admin/authority/sysuser", "/action/api/admin/authority/sysuser"}) //后台管理跳转，Shrio拦截校验权限
public class SysUserController extends BaseController<SysUser, Integer>{
	public final Log log = LogFactory.getLog(SysUserController.class);
	
	@Autowired
	private ISysUserAdvanceService sysUserAdvanceService;

	@Autowired
	private ISysOrgAdvanceService sysOrgAdvanceService;

	@Autowired
	private ISysRoleAdvanceService sysRoleAdvanceService;
	
	@Autowired
	private ISysPermissionAdvanceService sysPermissionAdvanceService;
	
	@Autowired
	private AppUserSession appUserSession;

    @Autowired
    private SysUserSearch userSearch;

	public SysUserController() {
		super(SysUser.class, "/action/admin/authority/sysuser/sysUserList", "/action/admin/authority/sysuser/sysUserForm");
	}	
	
	@PostConstruct
	private void initService() {
		setService(sysUserAdvanceService);
	}
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		super.initBinder(binder);
		binder.registerCustomEditor(SysOrg.class, new SysOrgEditor(sysOrgAdvanceService));
	}

	@RequiresPermissions("admin:authority:sysuser:query")
	@RequestMapping(value = "/sysUserList", method = RequestMethod.GET)
	public ModelAndView openListView(Date ssDate, Date eeDate) throws Exception {
		return super.openListView(ssDate, eeDate);
	}

	@RequiresPermissions(value={"admin:authority:sysuser:create","admin:authority:sysuser:update"},logical=Logical.OR)
	@RequestMapping(value = "/sysUserForm", method = RequestMethod.GET)
	public ModelAndView openFormView(Date ssDate, Date eeDate) throws Exception {
		return super.openFormView(ssDate, eeDate);
	}

	@RequestMapping(value = "/getCurrentUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCurrentUser() throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		ShiroUser o = appUserSession.getCurrentUser();
		log.debug(o.toFullString());
		map.put("message", o != null ? "":"操作失败!");
		map.put("responseid", o != null ? 1:0);
		map.put("data", o != null ? o:null);
		return map;
	}
	
	@RequestMapping(value = "/getCurrentUserDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCurrentUserDetail() throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		ShiroUser o = appUserSession.getCurrentUser();
		SysUser user = sysUserAdvanceService.loadByKey(o.getUserId());
		log.debug(user);
		map.put("message", o != null ? "":"操作失败!");
		map.put("responseid", o != null ? 1:0);
		map.put("data", o != null ? user:null);
		return map;
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	@LogAudit
	public Map<String, Object> get(@RequestBody SysUser o) throws Exception {
		return super.get(o.getId());
	}
	
	@RequestMapping(value = "/getById", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody	
	public Map<String, Object> getById(@RequestBody SysUser o) throws Exception {
		return super.get(o.getId());
	}

	/**
	 * 仅提供给前端用户使用，后端管理用户不可直接使用注册账号
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createOrGetByPhone", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> createOrGetByPhone(@RequestBody SysUser o) throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		if(StringUtils.isEmpty(o.getPhone()) || o.getPhone().length() != 11){
			map.put("message", "请输入正确手机号码!");
			map.put("responseid", 0);
			map.put("data", null);
		}else{
			Map<String,Object> params = Maps.newHashMap();
			params.put("phone", o.getPhone());
			params.put("userType", Integer.valueOf(config.getValue("app.usertype.frontend")));
			SysUser sysUser = null;
			Collection<SysUser> users = sysUserAdvanceService.queryAnyway(params);
			if(users.isEmpty()){
				o.setPhone(o.getPhone());
				String nickName = "user_"+AppCodeGenerator.nextRandomInt(6);
				o.setUsername(nickName);
				o.setNickname(nickName);
				o.setUserType(Integer.valueOf(config.getValue("app.usertype.frontend"))); //外部用户类型				
				o.setSysOrg(sysOrgAdvanceService.loadByKey(Integer.valueOf(config.getValue("app.frontend.org")))); //外部用户组织	
				o.setAccesstoken(Digests.encryptMD5(AppCodeGenerator.getDevelopToken()+o.toString()));
				int ret = sysUserAdvanceService.createOrUpdateViaAdmin(o);
				log.debug(ret);
				if(ret > 0){
					sysUser = o;
				}
			}else{
				sysUser = users.iterator().next();
				if(!sysUser.validate()){ //微信取消关注后，用户被逻辑删除
					int ret = sysUserAdvanceService.createOrUpdateViaAdmin(sysUser);
					log.debug(ret);
				}
			}
			try{
				SNSAuthenticationToken snsToken = new SNSAuthenticationToken(sysUser.getAccesstoken(), SNSLoginType.accesstoken);
				SecurityUtils.getSubject().login(snsToken);
				map.put("responseid", 1);
				map.put("data", sysUser);
			}catch(AuthenticationException e){
				map.put("responseid", 0);
			}
		}
		return map;
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	@LogAudit
	@Override
	public Map<String, Object> query(@RequestBody SysUser o) throws Exception {
		Map<String, Object> result = Maps.newHashMap();
		Collection<SysUser> list = sysUserAdvanceService.getAll(o);
		Map<String, Object> dataMap = super.wrapQueryResult((List<SysUser>) list);
		result.put("data", dataMap);
		result.put("message", "");
		result.put("responseid", 1);
		return result;
	}
	
	@RequiresPermissions("admin:authority:sysuser:create")
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	@LogAudit
	@Override
	public Map<String, Object> create(@RequestBody SysUser o) throws Exception {
		return super.create(o);				
	}

	@RequiresPermissions("admin:authority:sysuser:update")
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	@LogAudit
	@Override
	public Map<String, Object> update(@RequestBody SysUser o) throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		if(null == o.getId()){
			map.put("responseid", 0);
			map.put("message", "用户标识不可为空!");
		}else if(config.getValue("app.anonymous.user.id").equals(o.getId())){
			map.put("responseid", 0);
			map.put("message", "匿名用户不可修改!");
		}else{
			map = super.update(o);
		}
		return map;
	}

	@RequestMapping(value = "/updateMyInfo", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> updateMyInfo(@RequestBody SysUser o) throws Exception {
		o.setUsername(StringUtil.filterEmoji(o.getUsername()));
		o.setNickname(StringUtil.filterEmoji(o.getNickname()));
		o.setSignature(StringUtil.filterEmoji(o.getSignature()));
		ShiroUser user = appUserSession.getCurrentUser();
		o.setId(user.getUserId());
		return super.update(o);		
	}
	
	@RequiresPermissions("admin:authority:sysrole:delete")
	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	@LogAudit
	public Map<String, Object> delete(@RequestBody SysUser o) throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		try {
			int ret = getService().delete(o);
			map.put("message", ret > 0  ? "操作成功!":"操作失败!"); //返回值兼容批量更新
			map.put("responseid", ret > 0 ? 1:ret);
		}catch (Exception e) {
			map.put("responseid", 0);
			map.put("message", "操作异常!");
			log.error(Exceptions.getStackTraceAsString(e));
		}
		return map;
	}
	
	@RequiresPermissions("admin:authority:sysuser:delete")
	@RequestMapping(value = "/deletes", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
	@ResponseBody
	@LogAudit
	@Override
	public Map<String, Object> deletes(@RequestBody Integer[] ids) throws Exception {
		return super.deletes(ids);
	}
	
	/**
	 * 用户自己修改密码
	 * @param yuanPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "/submitUpdatePassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitUpdatePassword(
            @RequestParam("yuanPassword") String yuanPassword,
            @RequestParam("newPassword") String newPassword){
		Map<String, Object> map = Maps.newHashMap();
		ShiroUser currentUser = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
		SysUser user = sysUserAdvanceService.getByUnique(currentUser.loginName);
		byte[] salt = Encodes.decodeHex(user.getSalt());
		byte[] encodePassword = Digests.sha1(yuanPassword.getBytes(), salt, ISysUserService.HASH_INTERATIONS);
		String password = Encodes.encodeHex(encodePassword);
		if(password.equals(user.getPassword())){
			user.setPassword(newPassword);
			int ret = sysUserAdvanceService.updatePassword(user);
			map.put("responseid", ret==1 ? 1:0);
			map.put("message", ret==1 ? "密码修改成功!":"密码修改失败!");
		}else{
			map.put("responseid", 0);
			map.put("message", "原密码输入不正确!");
		}
		return map;
	}

	/**
	 * 管理员为用户初始化密码
	 * @param id
	 * @param newpassword
	 * @return
	 */
	@RequestMapping(value = "/saveNewKeys", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveNewKeys(@RequestParam("id") String id,
            @RequestParam("newpassword") String newpassword){
		Map<String, Object> map = Maps.newHashMap();
		SysUser user = sysUserAdvanceService.getById(Integer.valueOf(id));
		user.setPassword(newpassword);
		int ret = sysUserAdvanceService.updatePassword(user);
		map.put("responseid", ret==1 ? 1:0);
		map.put("message", ret==1 ? "密码修改成功!":"密码修改失败!");
		return map;
	}

	/**
	 * 用户角色授权
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions(value={"admin:authority:sysuser:create","admin:authority:sysuser:update"},logical=Logical.OR)
	@RequestMapping(value = "/userRoleView", method = RequestMethod.GET)
	public ModelAndView userRoleView() throws Exception {
		return new ModelAndView("/action/admin/authority/sysuser/userRoleView");
	}

    /**
     * 一. 穷举遍历递归部门用户树
     * 递归层层向下查询子部门，直到所有部门加载完成。查询部门时，把该部门所有用户也作为叶子节点加载
     * @param userType
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/userRole/getUsersTreeData/{userType}")
	@ResponseBody
	public Map<String, Object> getUsersTreeData(@PathVariable("userType")Integer userType) throws Exception {		
		return sysUserAdvanceService.getUsersTreeData(userType);
	}

    /**
     * 二. 穷举遍历递归部门用户树[需要关联角色]
     * 递归层层向下查询子部门，直到所有部门加载完成。查询部门时，把该部门所有用户也作为叶子节点加载，并显示用户角色
     * @param roleId
     * @param userType
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/userRole/getUsersRoleTreeData/{userType}/{roleId}")
	@ResponseBody
	public Map<String, Object> getUsersRoleTreeData(@PathVariable("roleId") Integer roleId, @PathVariable("userType") Integer userType) throws Exception {
		return sysUserAdvanceService.getUsersRoleTreeData(roleId, userType);
	}

	/**
	 * 提交保存用户角色(Http+JSON请求)
	 * @param data
	 * @return
	 */
	@RequiresPermissions("admin:authority:sysuser:userRole:update")
	@RequestMapping(value = "/userRole/createUserRole", produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> createUserRole(@RequestBody Map<String, String> data) {
		return createUserRole(Integer.valueOf(data.get("roleId")), data.get("userIds").toString());
	}

	/**
	 * 提交保存用户角色(平台内请求)
	 * @param roleId 角色Id
	 * @param userIds 用户userIds
	 * @return
	 */
	@RequiresPermissions("admin:authority:sysuser:userRole:update")
	@RequestMapping(value = "/userRole/submit")
	@ResponseBody
	public Map<String, Object> createUserRole(@RequestParam("roleId") Integer roleId, @RequestParam("userIds") String userIds) {
		SecurityUtils.getSubject().checkRole("Administrator");
		Map<String, Object> map = Maps.newHashMap();
		try {
			if (roleId == null) {				
				map.put("responseid", 0);
				map.put("message", "请选择有效角色!");
			} else {
				if(StringUtils.isEmpty(userIds)){
					sysRoleAdvanceService.deleteSysUserRoleByRoleId(roleId); //根据roleId删除拥有该角色的所有用户信息
				}else{
					String[] userIdStrings = userIds.split(Constants.SPACE);				
					if (userIdStrings != null && userIdStrings.length > 0) {
						Set<String> userIdSet = new HashSet<String>();
						sysRoleAdvanceService.deleteSysUserRoleByRoleId(roleId);
						for (String add : userIdStrings) {
							if (add != null && add.length() > 0) {
								userIdSet.add(add);
							}
						}
						for (String userId : userIdSet) {
							sysRoleAdvanceService.createSysUserRole(Integer.parseInt(userId), roleId);
						}
					}
				}
			}
			map.put("responseid", 1);
			map.put("message", "保存用户角色成功!");
		} catch (Exception e) {
			map.put("responseid", 0);
			map.put("message", "保存用户角色失败!");
			log.error(Exceptions.getStackTraceAsString(e));
		}
		return map;
	}

    /**
     * 三. 穷举遍历用户权限树，用于对用户直接授权
     * @param userId
     * @return
     * @throws Exception
     */
	@RequiresPermissions(value={"admin:authority:sysuser:create","admin:authority:sysuser:update"},logical=Logical.OR)
	@RequestMapping(value = "/userPermission/getPermissionsTreeData/{userId}")
	@ResponseBody
	public Map<String, Object> getUserPermissionsTreeData(@PathVariable("userId") Integer userId) throws Exception {
		SecurityUtils.getSubject().checkRole("Administrator");
		return sysUserAdvanceService.getUserPermissionsTreeData(userId);
		
	}
	
	@RequiresPermissions("admin:authority:sysuser:userPermission:update")
	@RequestMapping(value = "/createSysUserPermission", method = RequestMethod.POST)
	@ResponseBody
	@LogAudit
	public Map<String, Object> createSysUserPermission(@RequestParam("id") Integer userId, @RequestParam("permissionIds") String permissionIds) throws Exception {
		SecurityUtils.getSubject().checkRole("Administrator");
		String[] permissionStrings = permissionIds.split(Constants.SPACE);
		if (userId != null && permissionStrings != null && permissionStrings.length > 0) {
			sysPermissionAdvanceService.deleteSysUserPermissionByUserId(userId);
			Set<String> permissionSet = new HashSet<String>();
			for (String add : permissionStrings) {
				permissionSet.add(add); //使用Set接口过滤去重权限
			}
			for (String permission : permissionSet) {
				sysPermissionAdvanceService.createSysUserPermission(userId, Integer.parseInt(permission));
			}
		}
		Map<String, Object> result = Maps.newHashMap();
		result.put("message", "保存用户权限成功!");
		result.put("responseid", 1);
		return result;
	}

    /**
     * 四. 根据所选组织，动态自定义构建组织成员及下级组织的树形菜单（含首节点）
     * @param orgId
     * @param userType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFirstDynamicUserTree/{orgId}/{userType}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getFirstDynamicUserTree(@PathVariable("orgId")Integer orgId, @PathVariable("userType")Integer userType) throws Exception {
        Map<String, Object> result = Maps.newHashMap();
        List<DynamicUserTreeNode> resultList = sysUserAdvanceService.getFirstDynamicUserTree(orgId, userType);
        Map<String, Object> dataMap = super.wrapQueryResult(resultList);
        result.put("data", dataMap);
        result.put("message", "");
        result.put("responseid", 1);
        return result;
    }

    /**
     * 四. 根据所选组织，动态自定义构建组织成员及下级组织的树形菜单
     * @param orgId
     * @param userType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getChoseDynamicUserTree/{orgId}/{userType}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getChoseDynamicUserTree(@PathVariable("orgId")Integer orgId, @PathVariable("userType")Integer userType) throws Exception {
        Map<String, Object> result = Maps.newHashMap();
        List<DynamicUserTreeNode> resultList = sysUserAdvanceService.getChoseDynamicUserTree(orgId, userType);
        Map<String, Object> dataMap = super.wrapQueryResult(resultList);
        result.put("data", dataMap);
        result.put("message", "");
        result.put("responseid", 1);
        return result;
    }

    /**
     * 五.动态搜索用户属性，构建当前登录人组织成员及父类所有组织树
     * @param u
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/searchCurrentUserDynamicUserTree")
    @ResponseBody
    public Map<String, Object> searchCurrentUserDynamicUserTree(SysUser u) throws Exception {
        ShiroUser o = appUserSession.getCurrentUser();
        Map<String, Object> result = Maps.newHashMap();
        List<DynamicUserTreeNode> resultList = sysUserAdvanceService.searchDynamicUserTree(u.getLoginName(),o.getOwnerOrgId(),u.getPosition());
        Map<String, Object> dataMap = super.wrapQueryResult(resultList);
        result.put("data", dataMap);
        result.put("message", "");
        result.put("responseid", 1);
        return result;
    }

}
