package com.jfsl.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.*;
import com.jfsl.pojo.*;

@SuppressWarnings("serial")
@ParentPackage("default")
@Results({
		@Result(name = "ListProduce", location = "jsp/produce/ListProduce.jsp"),
		@Result(name = "listbefore", location = "jsp/produce/FilterProcess.jsp"), })
@InterceptorRefs({ @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") })
public class ProduceAction extends BaseAction {
	// 需要用到的属性
	private int produceid;
	private int flowid;
	private int page, rows, total;

	// 需要用到的pojo
	private DataDictionary datadictionary;
	private List<DataDictionary> DataDictionarys;
	private Flow flow;
	private List<Flow> flows;

	// 需要用到的dao
	private DataDictionaryDAO ddao = new DataDictionaryDAO();
	private FlowDAO flowdao = new FlowDAO();
	private OracleDAO od = new OracleDAO();
	private ProduceDAO pdao = new ProduceDAO();

	public int getProduceid() {
		return produceid;
	}

	public void setProduceid(int produceid) {
		this.produceid = produceid;
	}

	public int getFlowid() {
		return flowid;
	}

	public void setFlowid(int flowid) {
		this.flowid = flowid;
	}

	public DataDictionary getDatadictionary() {
		return datadictionary;
	}

	public void setDatadictionary(DataDictionary datadictionary) {
		this.datadictionary = datadictionary;
	}

	public List<DataDictionary> getDataDictionarys() {
		return DataDictionarys;
	}

	public void setDataDictionarys(List<DataDictionary> dataDictionarys) {
		DataDictionarys = dataDictionarys;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public List<Flow> getFlows() {
		return flows;
	}

	public void setFlows(List<Flow> flows) {
		this.flows = flows;
	}

	public DataDictionaryDAO getDdao() {
		return ddao;
	}

	public void setDdao(DataDictionaryDAO ddao) {
		this.ddao = ddao;
	}

	public FlowDAO getFlowdao() {
		return flowdao;
	}

	public void setFlowdao(FlowDAO flowdao) {
		this.flowdao = flowdao;
	}

	public OracleDAO getOd() {
		return od;
	}

	public void setOd(OracleDAO od) {
		this.od = od;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Action(value = "ListProduce")
	public String ListProduce() {
		produceid = 33;
		datadictionary = ddao.Find(produceid);
		return "ListProduce";
	}

	@Action(value = "JsonProcess")
	public void jsonprocess() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		page = (page == 0 ? 1 : page);
		rows = (rows == 0 ? 15 : rows);
		int start = (page - 1) * rows;
		rows = start + rows;
		start++;
		String strWhere = "";
		boolean b = false;

		try {
			String jcxm = java.net.URLDecoder.decode(ServletActionContext
					.getRequest().getParameter("jcxm"), "utf-8");
			/*String csname = java.net.URLDecoder.decode(ServletActionContext
					.getRequest().getParameter("csname"), "utf-8");*/
			String area = java.net.URLDecoder.decode(ServletActionContext
					.getRequest().getParameter("area"), "utf-8");
			String ph = java.net.URLDecoder.decode(ServletActionContext
					.getRequest().getParameter("ph"), "utf-8");
			String well = java.net.URLDecoder.decode(ServletActionContext
					.getRequest().getParameter("well"), "utf-8");

			if (!jcxm.isEmpty()) {
				jcxm = " AND a.jcxm like '%" + jcxm + "%'";
				b = true;
			}

			/*if (!csname.isEmpty()) {
				csname = " AND a.csname like '%" + csname + "%'";
				b = true;
			}*/

			if (!area.isEmpty()) {
				area = " AND a.DQ like '%" + area + "%'";
				b = true;
			}

			if (!ph.isEmpty()) {
				ph = " AND a.YPPH like '%" + ph + "%'";
				b = true;
			}

			if (!well.isEmpty()) {
				well = " AND a.jh like '%" + well + "%'";
				b = true;
			}

			if (b) {
				strWhere = "where 1=1" + jcxm + area + ph + well;
			}

			int pageCount = 0;
			pageCount = od.jsonProcessCount(strWhere);
			List<?> l = od.jsonProcess(start, rows, strWhere);
			JSONObject jo = new JSONObject();
			jo.put("total", pageCount);
			JSONArray ja = new JSONArray();
			for (int i = 0; i < l.size(); i++) {
				JSONObject jjo = new JSONObject();
				Object[] o = (Object[]) l.get(i);
				String DQ = (String) o[0];
				String JH = (String) o[1];
				String JCXM = (String) o[2];
				String YPPH = (String) o[3];
				BigDecimal YPSL = (BigDecimal) o[4];
				String SYRQ = (String) o[5];
				if (!SYRQ.equals("n")) {
					SYRQ = sdf.format(sdf.parse(SYRQ));
				}
				String JYRQ = (String) o[6];
				if (!JYRQ.equals("n")) {
					JYRQ = sdf.format(sdf.parse(JYRQ));
				}
				String FXRQ = (String) o[7];
				if (!FXRQ.equals("n")) {
					FXRQ = sdf.format(sdf.parse(FXRQ));
				}
				String JHRQ = (String) o[8];
				if (!JHRQ.equals("n")) {
					JHRQ = sdf.format(sdf.parse(JHRQ));
				}
				String SPRQ = (String) o[9];
				if (!SPRQ.equals("n")) {
					SPRQ = sdf.format(sdf.parse(SPRQ));
				}
				String GDRQ = (String) o[10];
				if (!GDRQ.equals("n")) {
					GDRQ = sdf.format(sdf.parse(GDRQ));
				}
				String SCRQ = (String) o[11];
				if (!SCRQ.equals("n")) {
					SCRQ = sdf.format(sdf.parse(SCRQ));
				}
				// String CSNAME=(String)o[12];
				jjo.put("YPPH", YPPH);
				jjo.put("DQ", DQ);
				jjo.put("JH", JH);
				jjo.put("JCXM", JCXM);
				jjo.put("YPSL", YPSL);
				jjo.put("WC", "");
				jjo.put("DDSY", SYRQ);
				jjo.put("JYR", JYRQ);
				jjo.put("JCR", FXRQ);
				jjo.put("JHR", JHRQ);
				jjo.put("SPR", SPRQ);
				jjo.put("BGJSF", GDRQ);
				jjo.put("SCF", SCRQ);
				// jjo.put("CSNAME", CSNAME);
				ja.add(jjo);
			}
			jo.put("rows", ja);
			// System.out.println("流程Json："+jo.toString());
			this.printJson(jo.toString());
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Action(value = "ListProduceBefore")
	public String be() {
		return "listbefore";
	}

	@Action(value = "searchProgram")
	public void searchprogram() throws UnsupportedEncodingException {
		// byte[]
		// search=ServletActionContext.getRequest().getParameter("searchProgram").getBytes("ISO-8859-1");
		// String serarchp=new String(search,"utf-8").replaceAll(" ","");
		String serarchp = java.net.URLDecoder.decode(ServletActionContext
				.getRequest().getParameter("searchProgram"), "utf-8");
		// String
		// search=ServletActionContext.getRequest().getParameter("searchProgram");
		// System.out.println("++"+ServletActionContext.getRequest().getParameter("searchProgram"));
		// System.out.println(serarchp+"!!!!---");
		String s = pdao.searchbyprogram(serarchp);
		// System.out.println(s);
		this.printJson(s);
	}

	@Action(value = "searchcs")
	public void searchcs() throws UnsupportedEncodingException {
		// byte[]
		// search=ServletActionContext.getRequest().getParameter("searchProgram").getBytes("ISO-8859-1");
		// String serarchp=new String(search,"utf-8").replaceAll(" ","");
		String searchProgram = java.net.URLDecoder.decode(ServletActionContext
				.getRequest().getParameter("searchProgram"), "utf-8");
		// String
		// search=ServletActionContext.getRequest().getParameter("searchProgram");
		// System.out.println("++"+ServletActionContext.getRequest().getParameter("searchProgram"));
		// System.out.println(serarchp+"!!!!---");
		String s = pdao.searchcsbyprogram(searchProgram);
		// System.out.println(s);
		this.printJson(s);
	}

	@Action(value = "searchArea")
	public void searcharea() throws UnsupportedEncodingException {
		/*
		 * byte[] search = ServletActionContext.getRequest()
		 * .getParameter("searchArea").getBytes("ISO-8859-1"); String area = new
		 * String(search, "utf-8").replaceAll(" ", "");
		 */
		String searchArea = java.net.URLDecoder.decode(ServletActionContext
				.getRequest().getParameter("searchArea"), "utf-8");
		// System.out.println(area);
		String s = pdao.searchAreabyprogram(searchArea);
		// System.out.println(s);
		this.printJson(s);
	}

	@Action(value = "searchWell")
	public void searchwell() throws UnsupportedEncodingException {
		/*
		 * byte[] search = ServletActionContext.getRequest()
		 * .getParameter("searchWell").getBytes("ISO-8859-1"); String well = new
		 * String(search, "utf-8").replaceAll(" ", "");
		 */
		// System.out.println(well);
		String searchWell = java.net.URLDecoder.decode(ServletActionContext
				.getRequest().getParameter("searchWell"), "utf-8");
		String s = pdao.searchWellbyprogram(searchWell);
		// System.out.println(s);
		this.printJson(s);
	}

	@Action(value = "searchPh")
	public void searchph() throws UnsupportedEncodingException {
		/*
		 * byte[] search = ServletActionContext.getRequest()
		 * .getParameter("searchPh").getBytes("ISO-8859-1"); String searchPh =
		 * new String(search, "utf-8").replaceAll(" ", "");
		 */
		// System.out.println(searchPh);
		String searchPh = java.net.URLDecoder.decode(ServletActionContext
				.getRequest().getParameter("searchPh"), "utf-8");
		String s = pdao.searchPhbyprogram(searchPh);
		// System.out.println(s);
		this.printJson(s);
	}

	@Action(value = "searchWellByArea")
	public void searchWellByArea() throws UnsupportedEncodingException {
		byte[] search = ServletActionContext.getRequest()
				.getParameter("searchWell").getBytes("ISO-8859-1");
		String well = java.net.URLDecoder.decode(new String(search, "utf-8"),
				"utf-8");
		// System.out.println(well);
		String s = pdao.searchWellbyArea(well);
		// System.out.println(s);
		this.printJson(s);
	}

	@Action(value = "searchPhByWell")
	public void searchPhByWell() throws UnsupportedEncodingException {
		byte[] search = ServletActionContext.getRequest()
				.getParameter("searchPh").getBytes("ISO-8859-1");
		String ph = java.net.URLDecoder.decode(new String(search, "utf-8"),
				"utf-8");
		// System.out.println(ph);
		String s = pdao.searchPhbyWell(ph);
		// System.out.println(s);
		this.printJson(s);
	}

	@Action(value = "searchProByPh")
	public void searchProByPh() throws UnsupportedEncodingException {
		String ph = ServletActionContext.getRequest()
				.getParameter("searchProgram").replaceAll(" ", "");
		// System.out.println(ph);
		String s = pdao.searchProByPh(ph);
		// System.out.println(s);
		this.printJson(s);
	}

	@Action(value = "JsonProcessByArea")
	public void JsonProcessByArea() throws UnsupportedEncodingException,
			SQLException {
		// System.out.println("通过地区获取过程JsonProcessByArea");
		String area = java.net.URLDecoder.decode(ServletActionContext
				.getRequest().getParameter("area"), "utf-8");
		// System.out.println("地区名："+area);
		List<?> l = od.jsonProcessByArea(area);
		this.jsonprocessByArea(l);
	}

	@Action(value = "JsonProcessByWell")
	public void JsonProcessByWell() throws UnsupportedEncodingException,
			SQLException {
		// System.out.println("通过地区获取过程JsonProcessByWell");
		String well = java.net.URLDecoder.decode(ServletActionContext
				.getRequest().getParameter("well"), "utf-8");
		// System.out.println("井号名："+well);
		List<?> l = od.jsonProcessByWell(well);
		this.jsonprocessByArea(l);
	}

	@Action(value = "JsonProcessByPh")
	public void JsonProcessByPh() throws UnsupportedEncodingException,
			SQLException {
		// System.out.println("通过地区获取过程JsonProcessByWell");
		String ph = ServletActionContext.getRequest().getParameter("ph")
				.replaceAll(" ", "");
		// System.out.println("批号名："+ph);
		List<?> l = od.jsonProcessByPh(ph);
		this.jsonprocessByArea(l);
	}

	@Action(value = "JsonProcessByJcxm")
	public void JsonProcessByJcxm() throws UnsupportedEncodingException,
			SQLException {
		// System.out.println("通过检测项目获取过程JsonProcessByWell");
		// byte[] search=ServletActionContext.getRequest().getParameter("jcxm");
		String jcxm = java.net.URLDecoder.decode(ServletActionContext
				.getRequest().getParameter("jcxm"), "utf-8");

		// System.out.println("检测项目："+jcxm);
		List<?> l = od.jsonProcessByJcxm(jcxm);
		this.jsonprocessByArea(l);
	}

	public void jsonprocessByArea(List<?> l) {
		try {
			System.out.println("JSON PROCESS...........");
			JSONObject jo = new JSONObject();
			jo.put("total", l.size());
			JSONArray ja = new JSONArray();
			for (int i = 0; i < l.size(); i++) {
				JSONObject jjo = new JSONObject();
				Object[] o = (Object[]) l.get(i);
				String DQ = (String) o[0];
				String JH = (String) o[1];
				String JCXM = (String) o[2];
				String YPPH = (String) o[3];
				BigDecimal YPSL = (BigDecimal) o[4];
				String JYR = (String) o[5];
				String JCR = (String) o[6];
				String JHR = (String) o[7];
				String SPR = (String) o[8];
				String BGJSF = (String) o[9];
				String SCF = (String) o[10];
				jjo.put("YPPH", YPPH);
				jjo.put("DQ", DQ);
				jjo.put("JH", JH);
				jjo.put("JCXM", JCXM);
				jjo.put("YPSL", YPSL);
				jjo.put("WC", "");
				jjo.put("JYR", JYR);
				jjo.put("JCR", JCR);
				jjo.put("JHR", JHR);
				jjo.put("SPR", SPR);

				if ("0".equals(BGJSF))
					jjo.put("BGJSF", "n");
				else
					jjo.put("BGJSF", "y");
				if ("0".equals(SCF))
					jjo.put("SCF", "n");
				else
					jjo.put("SCF", "y");
				jjo.put("DDSY", "y");
				ja.add(jjo);
			}
			jo.put("rows", ja);
			// System.out.println("流程Json："+jo.toString());
			this.printJson(jo.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
