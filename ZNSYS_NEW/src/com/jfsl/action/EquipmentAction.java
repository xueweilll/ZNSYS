package com.jfsl.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.EquipmentDAO;
import com.jfsl.dao.EquipmentMaintainDAO;
import com.jfsl.pojo.*;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "doList", location = "jsp/equipmentmanage/ListEquipment.jsp")
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class EquipmentAction extends BaseAction
{
	private Integer id;
	private String equipmentname;
	private String equipmentnumber;
	private String buytime;
	private Float worth;
	private String brand;
	private String admin;
	private String description;
	private String memo;
	
	private String field;
	private String value;
	
	private Equipment equipment;
	private List<Equipment> equipments;
	private EquipmentDAO equipmentdao = new EquipmentDAO();
	
	private EquipmentMaintain emaintain;
	private List<EquipmentMaintain> emaintains;
	private EquipmentMaintainDAO emaintaindao = new EquipmentMaintainDAO();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEquipmentname() {
		return equipmentname;
	}

	public void setEquipmentname(String equipmentname) {
		this.equipmentname = equipmentname;
	}

	public String getEquipmentnumber() {
		return equipmentnumber;
	}

	public void setEquipmentnumber(String equipmentnumber) {
		this.equipmentnumber = equipmentnumber;
	}

	public String getBuytime() {
		return buytime;
	}

	public void setBuytime(String buytime) {
		this.buytime = buytime;
	}

	public Float getWorth() {
		return worth;
	}

	public void setWorth(Float worth) {
		this.worth = worth;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public EquipmentDAO getEquipmentdao() {
		return equipmentdao;
	}

	public void setEquipmentdao(EquipmentDAO equipmentdao) {
		this.equipmentdao = equipmentdao;
	}
	
	public EquipmentMaintain getEmaintain() {
		return emaintain;
	}

	public void setEmaintain(EquipmentMaintain emaintain) {
		this.emaintain = emaintain;
	}

	public List<EquipmentMaintain> getEmaintains() {
		return emaintains;
	}

	public void setEmaintains(List<EquipmentMaintain> emaintains) {
		this.emaintains = emaintains;
	}

	public EquipmentMaintainDAO getEmaintaindao() {
		return emaintaindao;
	}

	public void setEmaintaindao(EquipmentMaintainDAO emaintaindao) {
		this.emaintaindao = emaintaindao;
	}

	@Action(value = "ListEquipment")
	public String doList()
	{
		return "doList";
	}

	@Action(value = "JsonEquipment")
	public void doJson()
	{
		equipmentdao.clearOracleCurrentSession();
		equipments = equipmentdao.ListAll();
		this.jsonDataGrid(equipments);
	}

	@Action(value = "InsertEquipment")
	public String doInsert()
	{
		try {
			equipment = new Equipment();
				
			equipment.setEquipmentname(equipmentname);
			equipment.setEquipmentnumber(equipmentnumber);
			equipment.setBuytime(buytime);
			equipment.setWorth(worth);
			equipment.setBrand(brand);
			equipment.setAdmin(admin);
			equipment.setMemo(memo);
			equipment.setDescription(description);
				
			equipmentdao.OracleAdd(equipment);
			return "add";
		}catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "UpdateEquipment")
	public String doUpdate()
	{
		try {
				equipment = equipmentdao.find(id);
				
				equipment.setEquipmentname(equipmentname);
				equipment.setEquipmentnumber(equipmentnumber);
				equipment.setBuytime(buytime);
				equipment.setWorth(worth);
				equipment.setBrand(brand);
				equipment.setAdmin(admin);
				equipment.setMemo(memo);
				equipment.setDescription(description);
				
				equipmentdao.OracleUpdate(equipment);
				return "update";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "DeleteEquipment", interceptorRefs = @InterceptorRef("validatePowerInterceptor"))
	public String doDelete()
	{
		try {
			equipmentdao.delete(id);
			return "delete";	
		} catch (Exception e) {
			return "error";
		}
	}
	
	@Action(value = "QueryEquipmentMaintain")
	public void doMaintain(){
		//鍙栧嚭闇�淇濆吇鐨勮褰�
		emaintaindao.clearOracleCurrentSession();
		emaintains = emaintaindao.ListMaintain();
		
		equipments = new ArrayList<Equipment>();
		
		//寰幆鍙栧嚭璁惧淇℃伅鏀惧埌Equipments閲岄潰
		for(EquipmentMaintain e : emaintains){
			equipment = e.getEquipment();
			//绌哄�寮傚父鎺掗櫎
			if(equipment != null){
				//閲嶅寮傚父鎺掗櫎
				Equipment temp = new Equipment();
				int i;
				for(i=0;i<equipments.size();i++){
					temp = equipments.get(i);
					if(temp.equals(equipment))
						break;
				}
				if(i==equipments.size())
					equipments.add(equipment);
			}
		}
		this.jsonDataGrid(equipments);
	}
	
	@Action(value = "JsonEquipmentQuery")
	public void doQuery(){
		
		if(field!=null&&value!=null){
			equipmentdao.clearOracleCurrentSession();
			equipments = equipmentdao.query(field,value);
			this.jsonDataGrid(equipments);
		}else{
			equipmentdao.clearOracleCurrentSession();
			equipments = equipmentdao.ListAll();
			this.jsonDataGrid(equipments);
		}
	}
}
