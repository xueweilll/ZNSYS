package com.jfsl.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_SA_Functions")
public class Function extends Pojo implements Comparable<Function>
{
	@Id
	@Column(name = "ID")
	private String id;
	@Column(name = "Name")
	private String name;
	@Column(name = "FormURL")
	private String formurl;
	@Column(name = "DisplayName")
	private String displayname;
	@Column(name = "Icon")
	private String icon;
	@Column(name = "InFrame")
	private String inframe;
	@Column(name = "ShowHint")
	private String showhint;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getFormurl()
	{
		return formurl;
	}

	public void setFormurl(String formurl)
	{
		this.formurl = formurl;
	}

	public String getDisplayname()
	{
		return displayname;
	}

	public void setDisplayname(String displayname)
	{
		this.displayname = displayname;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public void setInframe(String inframe) {
		this.inframe = inframe;
	}

	public void setShowhint(String showhint) {
		this.showhint = showhint;
	}

	public String getShowhint() {
		return showhint;
	}

	public String getInframe() {
		return inframe;
	}

	public int compareTo(Function f)
	{
		return id.compareTo(f.id);
	}
}
