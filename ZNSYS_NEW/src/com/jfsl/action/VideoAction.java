package com.jfsl.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.jfsl.dao.VideoDAO;
import com.jfsl.pojo.Video;

@SuppressWarnings({ "serial", "unused" })
@ParentPackage("default")
@Results( {	
		@Result(name = "doList", location = "jsp/video/ListVideo.jsp"),
		@Result(name = "doBrowse", location = "jsp/video/BrowseVideo.jsp"),
		@Result(name = "doShow", location = "jsp/video/ShowVideo.jsp"),
})
@InterceptorRefs( { @InterceptorRef("defaultStack"),
		@InterceptorRef("validateUserInterceptor"),
		@InterceptorRef("operateLogInterceptor") 
})

public class VideoAction extends BaseAction
{
	private int id;
	private String code;
	private String caption;
	private String url;
	private String memo;
	
	private String areaid;
	
	private Video video;
	private List<Video> videos;
	private VideoDAO vdao = new VideoDAO();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String Getcode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getmemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	@Action(value = "JsonVideo")
	public void doJson()
	{
		vdao.clearOracleCurrentSession();
		videos = vdao.ListAll();
		this.jsonDataGrid(videos);
	}

	@Action(value = "ListVideo")
	public String doList()
	{
		return "doList";
	}
	
	@Action(value = "ShowVideo")
	public String doShow()
	{
		return "doShow";
	}

	@Action(value = "BrowseVideo")
	public String doBrowse()
	{
		return "doBrowse";
	}
	
	@Action(value = "InsertVideo")
	public String doInsert()
	{
		try {
			video = new Video();
			video.setCode(code);
			video.setCaption(caption);
			video.setUrl(url);
			video.setMemo(memo);
			vdao.OracleAdd(video);
			return "add";
		} catch (Exception e) {
			return "error";
		}
	}

	@Action(value = "UpdateVideo")
	public String doUpdate()
	{
		try {
			video=vdao.find(id);
			video.setCode(code);
			video.setCaption(caption);
			video.setUrl(url);
			video.setMemo(memo);
			
			vdao.OracleUpdate(video);

			return "update";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Action(value = "DeleteVideo")
	public String doDelete()
	{
		try {
			video = vdao.find(id);
			vdao.OracleDelete(video);
			return "delete";
		} catch (Exception e) {
			return "error";
		}
	}

	
	/*@SuppressWarnings("unchecked")
	@Action(value = "DisplayOneVideo")
	public void doDisplayOne()
	{
		//先根据行政区划查找该地区的站点
		Station station=new StationDAO().find(stationid);
		videos = vdao.query(stationid);
		//根据站点匹配摄像头
		List<Map> rs=new ArrayList<Map>();
		Map map;

		map=new HashMap();
		map.put("id",station.getStcd());
		map.put("text",station.getStnm());
		map.put("iconCls", "icon-zone");
		map.put("attributes",null);
		List<Map> rs1=new ArrayList<Map>();
		Map map1,map2;
		for(Video v:videos)
		{
			map1=new HashMap();
			map1.put("id",v.getId());
			map1.put("text",v.getCaption());
			map1.put("iconCls","icon-video");
			map2=new HashMap();
			map2.put("url",v.getUrl());
			map1.put("attributes",map2);
			rs1.add(map1);
		}
		if (rs1.size()>0) map.put("children",rs1);
		rs.add(map);

		this.jsonArray(rs);
	}
	
	@SuppressWarnings("unchecked")
	@Action(value = "DisplayAllVideo")
	public void doDisplayAll()
	{
		adao.clearCurrentSession();
		//先根据行政区划查找该地区的站点
		List<Station> stations=new StationDAO().Query(null,areaid);
		videos = vdao.ListAll();
		//根据站点匹配摄像头
		List<Map> rs=new ArrayList<Map>();
		Map map;
		for(Station st:stations)
		{
			List<Map> rs1=new ArrayList<Map>();
			Map map1,map2;
			for(Video v:videos)
			{
				if (v.getStation().getStcd().equals(st.getStcd()))
				{
					map1=new HashMap();
					map1.put("id",v.getId());
					map1.put("text",v.getCaption());
					map1.put("iconCls","icon-video");
					map2=new HashMap();
					map2.put("url",v.getUrl());
					map1.put("attributes",map2);
					rs1.add(map1);
				}
			}
			if (rs1.size()>0)
			{
				map=new HashMap();
				map.put("id",st.getStcd());
				map.put("text",st.getStnm());
				map.put("iconCls", "icon-zone");
				map.put("state", "closed");
				map.put("attributes",null);
				map.put("children",rs1);
				rs.add(map);
			}
		}
		this.jsonArray(rs);
	}*/
}
