package mani.droid.androidremote;

public class PcFileDetail {

	private String name;
	private String path;
	private boolean isFile;
	
	public PcFileDetail(String name, String path, boolean isFile) {
		super();
		this.setName(name);
		this.path = path;
		this.isFile = isFile;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isFile() {
		return isFile;
	}
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
