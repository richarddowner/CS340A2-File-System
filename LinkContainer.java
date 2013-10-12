public class LinkContainer {

	private Link l;
	private String absPath;
	private String fileType;
	
	public LinkContainer(Link l, String absPath, String fileType) {
		this.l = l;
		this.absPath = absPath;
		this.fileType = fileType;
	}
	
	public Link getLink(){
		return this.l;
	}
	
	public String getAbsPath() {
		return this.absPath;
	}
	
	public String getFileType() {
		return this.fileType;
	}
	
	public void setLink(Link l) {
		this.l = l;
	}
	
	public void setAbsPath(String absPath) {
		this.absPath = absPath;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
}
