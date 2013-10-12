/**
 * CS320 Assignment 2
 * File System
 * @author rdow035 1414352
 */
class Link {
	private Link parent;
	private Link[] children;
	private int currentPosition;
	private String absPath;
	private String localPath;
	private F referenceF;
	private String type;
	private int folderSize;
	private boolean seen;
	
	public Link(String absPath, String localPath, String type) {
		children = new Link[1];
		currentPosition=0;
		this.absPath = absPath;
		this.localPath = localPath;
		this.type = type;
		this.folderSize = 0;
		this.seen = false;
	}
	
	public void addChild(Link child) {
		if(currentPosition == children.length) resizeList();
		//System.out.println("absPath="+absPath);
		//System.out.println("children.length="+children.length);
		//System.out.println("currentPosition="+currentPosition);
		children[currentPosition] = child;
		//System.out.println("After");
		currentPosition++;
	}
	public void removeChild(Link l) {
		// only do this if the array is greater than 1 don't want to totally remove our array 
		if(children.length>1) { // new
			for(int i=0; i<children.length; i++) {
				
				if(children[i]!=null && children[i].getLocalPath().equals(l.getLocalPath())) {
					
					Link[] tmp = new Link[children.length-1];
					for(int j=0; j<i; j++) {
						tmp[j] = children[j];
					}
					for(int k=i; k<tmp.length; k++) {
						tmp[k] = children[k+1];
					}
					children = tmp;
				}
			}
			// currentPosition--;
		} else {
			children[0]=null;
		}
		currentPosition--;
	}
	private void resizeList(){
		int size = children.length;
		Link[] tmp = new Link[size*2];
		for(int i=0; i<size; i++){
			tmp[i]=children[i];
		}
		children=tmp;
	}
	public void setFolderSize() {
		int size = 0;
		for(int i=0; i<children.length; i++) {
			if(children[i] != null) {
				size += children[i].getLocalPath().length()+1;
			}
		}
		this.folderSize = size;
	}
	public int getFolderSize() {
		return this.folderSize;
	}
	public void setReference(F ref) {
		referenceF = ref;
	}
	public void setAbsPath(String path) {
		absPath = path;
	}
	public void setLocalPath(String path) {
		localPath = path;
	}
	public void setParent(Link parent) {
		this.parent = parent;
		// when the parent is set, the absolute path of this link becomes
		// the path of the parent + this links local path
		updateAbsPath();
		
	}
	public void updateAbsPath() {
		String newAbsPath;
		if(parent!=null) {
			if(parent.getAbsPath().equals("/")) {
				// parent is root
				newAbsPath = parent.getAbsPath() + this.localPath;
			} else {
				newAbsPath = parent.getAbsPath() + "/" + this.localPath;
			}
			setAbsPath(newAbsPath);
		}
	}
	public F getReference() {
		return referenceF;
	}
	public String getAbsPath() {
		return absPath;
	}
	public String getLocalPath() {
		return localPath;
	}
	public Link getParent() {
		return parent;
	}
	public Link[] getChildrenList() {
		return children;
	}
	/*
	public String toString() {
		return this.localPath;
	}*/
	public String getType() {
		return type;
	}
	public boolean getSeen() {
		return seen;
	}
	public void setSeen(boolean seen) {
		this.seen = seen;
	}
}