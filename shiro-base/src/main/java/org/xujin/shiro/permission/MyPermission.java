package org.xujin.shiro.permission;

import org.apache.shiro.authz.Permission;

public class MyPermission implements Permission {
	private String resourceId;
	private String operator;
	private String instanceId;
	
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public MyPermission() {
	}
	
	@Override
	public String toString() {
		return "MyPermission [resourceId=" + resourceId + ", operator="
				+ operator + ", instanceId=" + instanceId + "]";
	}

	public MyPermission(String permissionStr) {
		String[] strs = permissionStr.split("\\+");
		if(strs.length>1) {
			this.setResourceId(strs[1]);
		}
		if(this.getResourceId()==null||"".equals(this.getResourceId())) {
			this.setResourceId("*");
		}
		
		if(strs.length>2) {
			this.setOperator(strs[2]);
		}
		
		if(strs.length>3) {
			this.setInstanceId(strs[3]);
		}
		
		if(this.getInstanceId()==null||"".equals(this.getInstanceId())) {
			this.setInstanceId("*");
		}
	}

	public boolean implies(Permission p) {
		if(!(p instanceof MyPermission)) return false;
		MyPermission mp = (MyPermission)p;
		if(!this.getResourceId().equals("*")&&!mp.getResourceId().equals(this.getResourceId()))
			return false;
		if(!this.getOperator().equals("*")&&!mp.getOperator().equals(operator))
			return false;
		if(!this.getInstanceId().equals("*")&&!mp.getInstanceId().equals(instanceId))
			return false;
		return true;
	}

}
