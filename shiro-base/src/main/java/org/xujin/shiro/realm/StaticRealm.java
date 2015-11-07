package org.xujin.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.xujin.shiro.permission.MyPermission;

public class StaticRealm extends AuthorizingRealm {
	/**
	 * 用来判断授权的
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRole("r1");
		info.addRole("r2");
		info.addStringPermission("+user+*");
		info.addObjectPermission(new MyPermission("+topic+create"));
		info.addObjectPermission(new MyPermission("+topic+delete+1"));
		info.addObjectPermission(new WildcardPermission("test:*"));
		return info;
	}

	/**
	 * 用来判断认证的
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = token.getPrincipal().toString();
		String password = new String((char[])token.getCredentials());
		if(!"kh".equals(username)) throw new UnknownAccountException("用户名不存在!");
		if(!"123".equals(password)) throw new IncorrectCredentialsException("用户密码错误");
		return new SimpleAuthenticationInfo("ynkonghao@gmail.com", password, getName());
	}

}
