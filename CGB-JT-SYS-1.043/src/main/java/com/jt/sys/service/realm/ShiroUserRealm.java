package com.jt.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.sys.dao.SysMenuDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysUser;
/**
 * Realm为Shiro框架中的核心组件之一
 * 通过此对象可以完成数据业务的获取及封装
 * @author 86188
 *
 */
@Service
public class ShiroUserRealm extends AuthorizingRealm{
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysMenuDao sysMenuDao;
	
	/**
	 * 设置凭证匹配器
	 * @param credentialsMatcher
	 */
	@Override
	public void setCredentialsMatcher(
CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher cMatcher= new HashedCredentialsMatcher();
		cMatcher.setHashAlgorithmName("MD5");
		super.setCredentialsMatcher(cMatcher);
	}

		/**
	 * 执行认证操作时,此方法用户获取用户认证信息
	 * 说明:此方法由认证管理器调用
	 */ 
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("获取客户端用户提交信息的 AuthenticationInfo() 方法");
		//1.获取客户端提交的客户信息
				UsernamePasswordToken upToken= (UsernamePasswordToken)token;
				String username=upToken.getUsername();
				//2.基于用户名从数据库查询用户信息
				SysUser user=sysUserDao.findUserByUserName(username);
				System.out.println("AuthenticationInfo-user:"+user);
				//3.校验用户信息是否存在
				if(user==null) 
					throw new AuthenticationException("用户不存在");
				if(user.getValid()==0) throw new AuthenticationException("用户已被禁用");
				//4.对用户信息进行封装
				System.out.println("username:"+username+",pwd:"+user.getPassword());
				ByteSource credentialsSalt=ByteSource.Util.bytes(user.getSalt());
				SimpleAuthenticationInfo info
				=new SimpleAuthenticationInfo(user,//用户身份 
						user.getPassword(),//用户加密的密码 
						credentialsSalt, //盐值
						this.getName());//realm name
				//5.返回封装结果（传给认证管理器）
				System.out.println(info.getPrincipals()+","+info.getCredentialsSalt());
				return info;
	}

		/**执行授权操作时，此方法用于获取用户的权限信息*/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("完成授权信息的获取以及封装AuthorizationInfo（） 方法");
		//1.获取登录用户的具有的权限信息
		//1.1 获取用户身份.此对应依赖与认证时封装的用户身份
		SysUser user = (SysUser)principals.getPrimaryPrincipal();
		//1.2 基于用 户id获取角色id
		List<Integer> roleIds=sysUserRoleDao.findRoleIdsByUserId(user.getId());
		//1.3 基于角色id获取菜单id
		Integer[] array= {};
		List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleId(roleIds.toArray(array));
		//1.4 基于菜单id获取权限标识(sys:user:update)
		List<String> permisssions=
				sysMenuDao.findPermissions(
						menuIds.toArray(array));
		//2对权限封装
		Set<String> pSet=
				new HashSet<String>();
		for(String permisssion:permisssions) {
			if(!StringUtils.isEmpty(permisssion)){
				pSet.add(permisssion);
			}//去重和空(null),空串
		}
		//3.返回封装结果(授权管理器)
		System.out.println("pSet="+pSet);
		SimpleAuthorizationInfo info=
		new SimpleAuthorizationInfo();
		info.setStringPermissions(pSet);
		return info;
	}
}
