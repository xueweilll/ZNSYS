package com.jfsl.dao;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jfsl.pojo.*;

public class UserOperationDAO extends DAOImp
{
	public void add(String username)
	{
		String[] functions = new String[10];
		int i = 0;
		UserDAO userdao = new UserDAO();
		User user = userdao.getUserDepartment(username);
		for (Role r : user.getRoles())
		{
			for (Function f : r.getFunctions())
			{
				if (Arrays.binarySearch(functions, f.getId()) < 0)
				{
					functions[i++] = f.getId();
					UserOperation uo = new UserOperation();
					uo.setUsername(username);
					uo.setFunction(f);
					uo.setOperations(63);
					uo.setDatascopes(user.getDepartment().getId());
					this.Add(uo);
				}
			}
		}
	}

	public void delete(String username)
	{
		Session session = this.getCurrentSession();
		Transaction tx = null;
		Query query = session
				.createQuery("delete from UserOperation as uo where uo.username=:username");
		query.setString("username", username);
		tx = session.beginTransaction();
		query.executeUpdate();
		tx.commit();

	}

	@SuppressWarnings("unchecked")
	public List<UserOperation> getOperations(String username)
	{
		Session session = this.getCurrentSession();
		Query query = session
				.createQuery("select uo from UserOperation as uo join uo.function as f where uo.username=:username order by uo.function.id");
		query.setString("username", username);
		return query.list();
	}

	public UserOperation findOperation(int id)
	{
		Session session = this.getCurrentSession();
		UserOperation uo = (UserOperation) session
				.load(UserOperation.class, id);
		return uo;
	}

	public void deleteOperation(int id)
	{
		Session session = this.getCurrentSession();
		Transaction tx = session.beginTransaction();
		UserOperation uo = (UserOperation) session
				.load(UserOperation.class, id);
		session.delete(uo);
		tx.commit();
	}

	public String[] findScopes(List<UserOperation> ls, User user, String functionname)
	{
		String[] result = null;
		String name;
		for (UserOperation uo : ls)
		{
			name=uo.getFunction().getName();
			if ((name!=null)&&(name.equals(functionname)))
			{
				result = uo.getDatascopes().split(",");
			}

		}
		if (result==null)
		{
			result=new String[1];
			result[0]=user.getDepartment().getId();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List getFunctions(String username)
	{
		this.clearCurrentSession();
		Session session=this.getCurrentSession();
		Query query=session
				.createSQLQuery("select id,name,displayname,formurl,icon,inframe,showhint from V_SA_UserOperations where username=:username order by id")
				.addEntity(Function.class);
		query.setString("username", username);
		return query.list();
	}
}
