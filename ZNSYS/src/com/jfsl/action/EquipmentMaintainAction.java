package com.jfsl.action;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.EquipmentDAO;
import com.jfsl.dao.EquipmentMaintainDAO;
import com.jfsl.dao.PushDAO;
import com.jfsl.pojo.*;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results( {
		@Result(name = "doList", location = "jsp/equipmentmanage/ListEquipmentMaintain.jsp")
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class EquipmentMaintainAction extends BaseAction
{

	private int id;
	private int equipmentid;
	private String equipmentmaintainname;
	private String equipmentmaintaincontent;
	private String maintainpeople;
	private String maintaintime;
	private String memo;
	
	private EquipmentMaintain equipmentmaintain;
	private Equipment equipment;
	private List<Equipment> equipments;
	private List<EquipmentMaintain> equipmentmaintains;
	
	/*添加推送服务*/
	private Push push;
	private List<Push> pushs;
	
	private PushDAO pdao;
	
	private EquipmentMaintainDAO emdao = new EquipmentMaintainDAO();
	private EquipmentDAO edao = new EquipmentDAO();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public String getEuipmentmaintainname() {
		return equipmentmaintainname;
	}

	public void setEuipmentmaintainname(String euipmentmaintainname) {
		this.equipmentmaintainname = euipmentmaintainname;
	}

	public String getEquipmentmaintaincontent() {
		return equipmentmaintaincontent;
	}

	public void setEquipmentmaintaincontent(String equipmentmaintaincontent) {
		this.equipmentmaintaincontent = equipmentmaintaincontent;
	}

	public String getMaintainpeople() {
		return maintainpeople;
	}

	public void setMaintainpeople(String maintainpeople) {
		this.maintainpeople = maintainpeople;
	}

	public String getMaintaintime() {
		return maintaintime;
	}

	public void setMaintaintime(String maintaintime) {
		this.maintaintime = maintaintime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
	}

	public EquipmentMaintainDAO getEmdao() {
		return emdao;
	}

	public void setEmdao(EquipmentMaintainDAO emdao) {
		this.emdao = emdao;
	}

	public EquipmentDAO getEdao() {
		return edao;
	}

	public void setEdao(EquipmentDAO edao) {
		this.edao = edao;
	}
	
	public List<EquipmentMaintain> getEquipmentmatains() {
		return equipmentmaintains;
	}

	public void setEquipmentmatains(List<EquipmentMaintain> equipmentmatains) {
		this.equipmentmaintains = equipmentmatains;
	}
	
	public EquipmentMaintain getEquipmentmaintain() {
		return equipmentmaintain;
	}

	public void setEquipmentmaintain(EquipmentMaintain equipmentmaintain) {
		this.equipmentmaintain = equipmentmaintain;
	}

	public List<EquipmentMaintain> getEquipmentmaintains() {
		return equipmentmaintains;
	}

	public void setEquipmentmaintains(List<EquipmentMaintain> equipmentmaintains) {
		this.equipmentmaintains = equipmentmaintains;
	}
	
	public int getEquipmentid() {
		return equipmentid;
	}

	public void setEquipmentid(int equipmentid) {
		this.equipmentid = equipmentid;
	}

	public String getEquipmentmaintainname() {
		return equipmentmaintainname;
	}

	public void setEquipmentmaintainname(String equipmentmaintainname) {
		this.equipmentmaintainname = equipmentmaintainname;
	}
	
	public Push getPush() {
		return push;
	}

	public void setPush(Push push) {
		this.push = push;
	}

	public List<Push> getPushs() {
		return pushs;
	}

	public void setPushs(List<Push> pushs) {
		this.pushs = pushs;
	}

	public PushDAO getPdao() {
		return pdao;
	}

	public void setPdao(PushDAO pdao) {
		this.pdao = pdao;
	}

	@Action(value = "ListEquipmentMaintain")
	public String doList()
	{
		return "doList";
	}

	@Action(value = "JsonEquipmentMaintain")
	public void doJson()
	{
		emdao.clearCurrentSession();
		equipmentmaintains = emdao.ListAll();
		this.jsonDataGrid(equipmentmaintains);
	}
	
	@Action(value = "JsonNeedMaintain")
	public void doJsonNeed()
	{
		emdao.clearCurrentSession();
		equipmentmaintains = emdao.ListMaintain();
		this.jsonDataGrid(equipmentmaintains);
	}

	@Action(value = "InsertEquipmentMaintain")
	public String doInsert()
	{
		try {
			equipment = edao.find(equipmentid);
			equipmentmaintain = new EquipmentMaintain();
			equipmentmaintain.setEquipment(equipment);
			equipmentmaintain.setEquipmentmaintaincontent(equipmentmaintaincontent);
			equipmentmaintain.setEquipmentmaintainname(equipmentmaintainname);
			equipmentmaintain.setMaintainpeople(maintainpeople);
			equipmentmaintain.setMaintaintime(maintaintime);
			equipmentmaintain.setMemo(memo);
			
			push = new Push();
			push.setKind("保养维护");
			push.setPublisher("保养维护");
			push.setPublishtime(maintaintime+" 00:00:00");
			push.setTitle(equipment.getEquipmentname()+"("+equipment.getEquipmentnumber()+")的保养维护");
			push.setReceiver("All");		//这里有问题，应该绑定好用户，向固定用户推送。
			push.setUrl("ListEquipmentMaintain");
			push.setContent("保养内容："+equipmentmaintaincontent);
			push.setDateline(maintaintime+" 23:59:59");
			try{
				PushAction paction = new PushAction();		//这里之前session冲突
				paction.InsertPush(push);
			}catch(Exception ex){
				ex.printStackTrace();
				System.out.println("###########################Push失败！#########################");
			}
			emdao.Add(equipmentmaintain);
			
			return "add";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "UpdateEquipmentMaintain")
	public String doUpdate()
	{
		try {
			equipmentmaintain = emdao.find(id);
			equipment = edao.find(equipmentid);
			
			equipmentmaintain.setEquipmentmaintaincontent(equipmentmaintaincontent);
			equipmentmaintain.setEquipmentmaintainname(equipmentmaintainname);
			equipmentmaintain.setMaintainpeople(maintainpeople);
			equipmentmaintain.setMaintaintime(maintaintime);
			equipmentmaintain.setEquipment(equipment);
			equipmentmaintain.setMemo(memo);
			
			emdao.Update(equipmentmaintain);
			return "update";
		} catch (Exception e) {
			return "error";
		}
	}
	
	@Action(value = "AddMemo")
	public String AddMemo()
	{
		try {
			equipmentmaintain = emdao.find(id);
			
			equipmentmaintain.setMemo("已保养");
			
			emdao.Update(equipmentmaintain);
			return "update";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "DeleteEquipmentMaintain", interceptorRefs = @InterceptorRef("validatePowerInterceptor"))
	public String doDelete()
	{
		try {
			emdao.delete(id);
			return "delete";	
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "ComboEquipment")
	public void doComboEquipment()
	{
		equipments = edao.ListAll();
		this.jsonCombobox(equipments);
	}
}
