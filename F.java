// File/Folder Storage Object
/**
 * CS320 Assignment 2
 * File System
 * @author rdow035 1414352
 */
class F {
	private String name;
	private int creationTime;
	private String lastModified;
	private String owner;
	private String fileType;
	private int fileSize;
	private int permissions;
	private Link[] referenceList;
	//private Link[] parentReferenceList; // deleteall
	private int currentPosition;
	private int linkCount;
	private String fileData;
	private String localPath;
	public F(String localPath, int creationTime, String lastModified, String owner, String fileType, int permissions){
		this.referenceList = new Link[1];
		//this.parentReferenceList = new Link[1]; //deleteall
		this.currentPosition = 0;
		this.name = localPath;
		this.lastModified = lastModified;
		this.owner = owner;
		this.fileType = fileType;
		this.permissions = permissions;
		this.linkCount=0;
		this.creationTime = creationTime;
		this.fileSize = 0;
		this.localPath = localPath;
		if(fileType.equals("file")) {
			fileData = "";
		} else {
			fileData = null;
		}
	}
	
	
	public void addReference(Link l) {
		if(currentPosition == referenceList.length) resizeList();
		referenceList[currentPosition] = l;
		currentPosition++;
		linkCount++;
	} 
	public void removeReference(Link l) {
		if(referenceList.length>1) {
			for(int i=0; i<referenceList.length; i++) {
				if(referenceList[i].getLocalPath().equals(l.getLocalPath())) {
					Link[] tmp = new Link[referenceList.length-1];
					for(int j=0; j<i; j++) {
						tmp[j] = referenceList[j];
					}
					for(int k=i; k<tmp.length; k++) {
						tmp[k] = referenceList[k+1];
					}
					referenceList = tmp;
				}
			}
			// currentPosition--;
		} else {
			referenceList[0]=null;
		}
		currentPosition--;
	}
	public Link[] getReferenceList() {
		return referenceList;
	}
	
	
	 private void resizeList(){
		int size = referenceList.length;
		Link[] tmp = new Link[size*2];
		for(int i=0; i<size; i++){
			tmp[i]=referenceList[i];
		}
		referenceList=tmp;
	}
	 
	public void setFileSize() {
		this.fileSize = fileData.length();
		
	}
	public int getCreationTime() {
		return creationTime;
	}
	public String getFileType() {
		return fileType;
	}
	public void setCreationTime(int creationTime) {
		this.creationTime = creationTime;
	}
	public int getLinkCount() {
		return linkCount;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void reduceLinkCount() {
		linkCount--;
	}
	public void increaseLinkCount() {
		linkCount++;
	}
	public void append(String data) {
		if(fileType.equals("file")) {
			fileData += data;
			setFileSize();
		} else {
			System.out.println(localPath + " is not a directory!");
		}
	}
	public String show() {
		return fileData;
	}
}