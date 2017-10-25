package com.jfsl.dao;

import com.jfsl.pojo.Pojo;

public interface DAO
{
	/*public void Add(Pojo pojo);
	public void Update(Pojo pojo);
	public void Delete(Pojo pojo);*/
	public void OracleAdd(Pojo pojo);
	public void OracleUpdate(Pojo pojo);
	public void OracleDelete(Pojo pojo);
}
