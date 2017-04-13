package biology;

import java.lang.String;

public abstract class Biology {
	private String sciName;
	private String locName;
	private String describe;
	
	public Biology(String sciName,String locName,String describe) {
		this.sciName=sciName;
		this.locName=locName;
		this.describe=describe;
	}
	
	public String getSciName() {
		return sciName;
	}
	
	public void setSciName(String sciName) {
		this.sciName = sciName;
	}
	
	public String getLocName() {
		return locName;
	}
	
	public void setLocName(String locName) {
		this.locName = locName;
	}
	
}
