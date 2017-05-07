package models;

import java.io.Serializable;

public class Customer implements Serializable {
  
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String duetime;
	private String jointime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDuetime() {
		return duetime;
	}
	public void setDuetime(String duetime) {
		this.duetime = duetime;
	}
	public String getJointime() {
		return jointime;
	}
	public void setJointime(String jointime) {
		this.jointime = jointime;
	}

}