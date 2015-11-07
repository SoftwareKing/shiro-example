package org.xujin.shiro.test;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class TestShiro {
	
	private Subject login(String username,String password) {
		SecurityManager manager = new IniSecurityManagerFactory("classpath:shiro.ini").getInstance();
		SecurityUtils.setSecurityManager(manager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
			return subject;
		} catch (UnknownAccountException e) {
			System.out.println("用户名不存在!");
		} catch (IncorrectCredentialsException e) {
			System.out.println("用户密码不存在!");
		}
		return null;
	}

	@Test
	public void testBase() {
		Subject subject = login("kh","123");
		PrincipalCollection ps = subject.getPrincipals();
		System.out.println(ps.asList());
		System.out.println(ps.getRealmNames());
		System.out.println(subject.getPrincipal());
	}
	
	@Test
	public void testRole() {
		Subject subject = login("kh","123");
		System.out.println(subject.hasRole("r1"));
		System.out.println(subject.hasAllRoles(Arrays.asList("r1","r2","r3")));
		System.out.println(subject.hasRoles(Arrays.asList("r1","r2","r3"))[1]);
		subject.checkRole("r3");
	}
	
	@Test
	public void testPermission1() {
		Subject subject = login("kh","123");
		System.out.println(subject.isPermitted("user:delete"));
		System.out.println(subject.isPermitted("topic:create"));
		System.out.println(subject.isPermitted("dep:delete"));
		System.out.println(subject.isPermitted("classroom:create"));
	}
	
	@Test
	public void testPermission2() {
		Subject subject = login("lisi","123");
		System.out.println(subject.isPermitted("admin:user:delete:1"));
		System.out.println(subject.isPermitted("test:user:view"));
	}
	
	@Test
	public void testMyPermission() {
		Subject subject = login("kh","123");
		System.out.println(subject.isPermitted("+user+delete"));
		System.out.println(subject.isPermitted("+topic+update"));
		System.out.println(subject.isPermitted("+topic+delete+1"));
		System.out.println(subject.isPermitted("test:add"));
		System.out.println(subject.isPermitted("classroom:add"));
	}
}
