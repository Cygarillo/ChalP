package ch.hsr.data;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class OutputFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private String Id;
	private String url;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
