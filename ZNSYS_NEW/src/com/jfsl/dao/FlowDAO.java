package com.jfsl.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**java bean*/
import com.jfsl.pojo.Flow;

public class FlowDAO extends DAOImp {
	/** check all */
	@SuppressWarnings("unchecked")
	public List<Flow> ListAll() {
		// System.out.println("***************");
		Session session = this.getOracleCurrentSession();
		Query query = session.createQuery("from Flow");
		// System.out.println("list all");
		List<Flow> ls = query.list();
		return ls;
	}

	/** check id */
	@SuppressWarnings("unchecked")
	public List<Flow> Find(int id) {
		if (id == 0)
			return this.ListAll();
		Query query = this.getOracleCurrentSession().createQuery(
				"select f from Flow as f where f.id=:id");
		query.setInteger("id", id);
		List<Flow> ls = query.list();
		return ls;
	}

	/** check title */
	public Flow Find(String title) {
		if (title == "" || title == null) {
			return null;
		}
		
		Query query = this.getOracleCurrentSession().createQuery(
				"select f from Flow as f where f.title=:title");
		query.setString("title", title);
		@SuppressWarnings("unchecked")
		List<Flow> flow = query.list();
		if (flow.size() == 0) {
			return null;
		} else {
			return flow.get(0);
		}
	}

	/** delete Flow by Flowid */
	public void DeleteFlow(int id) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getOracleCurrentSession();
			Flow flow = (Flow) session.load(Flow.class, id);
			tx = session.beginTransaction();
			session.delete(flow);
			tx.commit();
		} catch (Exception e) {
			System.out.println("delete Flow error!");
		}
	}
}
