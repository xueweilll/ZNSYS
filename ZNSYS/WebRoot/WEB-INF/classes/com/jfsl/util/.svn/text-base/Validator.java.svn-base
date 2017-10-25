/*对登录系统的权限进行验证的类
 * 
 * 
 */
package com.jfsl.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jfsl.pojo.User;

public class Validator
{	
	@SuppressWarnings("unchecked")
	public static boolean validatePower(HttpServletRequest request,String action)
	{
		List<String> ls_operation=(List<String>)request.getSession().getAttribute("operations");
		if (ls_operation.indexOf(action)<0) 
			return false;
		else 
			return true;
	}
	
	public static boolean validateUser(HttpServletRequest request)
	{
		User user=(User)request.getSession().getAttribute("user");
		if (user!=null)
			return true;
		else 
			return false;
	}
}
