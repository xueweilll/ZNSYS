package com.jfsl.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.jfsl.dao.LogoutDAO;
import com.jfsl.pojo.User;

public class ListenUserSession implements HttpSessionListener
{

	public void sessionCreated(HttpSessionEvent arg0)
	{

	}

	public void sessionDestroyed(HttpSessionEvent arg0)
	{
		User user=(User)arg0.getSession().getAttribute("user");
		if (user!=null)
			new LogoutDAO().Logout(user.getUsername());
	}

}
