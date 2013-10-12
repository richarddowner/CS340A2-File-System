import java.util.*;
/**
 * CS320 Assignment 2
 * File System
 * @author rdow035 1414352
 */
// Command Line Interface 
public class CLI extends FileSystem {
	private String currentPath; 
	private String prompt = "$ ";
	
	public CLI(){
		home();
	}
	
	public String getCurrentPath(){
		return currentPath;
	}
	
	public void setCurrentPath(String path) { currentPath = path; }
	public String getPrompt(){ return prompt; }
	public void missingOperand(String cmd){ System.out.println(cmd+": Missing Operand"); }
	
	public void home() { 
		currentPath = "/"; 
		dirTree.setCurrentNode(dirTree.getRootNode());
	}
	
	public void enter(String command){
		Link currentNode;

		if(command.substring(6).equals("/")) {
			home();
		} else if(command.substring(6, 7).equals("/")) {
			
			if((currentNode = dirTree.searchV2(command.substring(6), "folder", false)) != null) {
				currentPath=command.substring(6);
				dirTree.setCurrentNode(currentNode);
			} else {
				System.out.println("Directory '" +command.substring(6)+"' does not exist!" );
			}
		} else {
			// if the path requested was like: folder
			command=command.substring(6);
			currentNode = dirTree.getCurrentNode();
			String path;
			
			path = command;
			
			if(currentNode.equals(dirTree.getRootNode())) {
				currentPath="/"+command;
			} else {
				currentPath+="/"+command;
			}
			
			if((currentNode = dirTree.searchV2(path, "folder", false)) != null) {
				//currentPath = path;
				dirTree.setCurrentNode(currentNode);
			} else {
				System.out.println("Directory '" +path+"' does not exist!" );
			}
		}
	};
	

	public void mkdir(String command) {
		command = command.substring(6).trim();
		String localPath;
		String[] paths = command.split("/");
		localPath = paths[paths.length-1];
		boolean fromLocal; // weather or not the dir is being made by /f1/f2 or f1/f2
		
		String path;
		
		if(command.startsWith("/")) {
			// if the L we are trying to make is at the root it would be mkdir /folder - no need to append current path
			path = command;
			fromLocal = false;
		} else { // else if current node is not the root
			// if the L we are trying to make is not at the root it would be mkdir folder
			path = command;
			fromLocal = true;
		}

		if(dirTree.addNode(new Link(path, localPath, "folder"), command, fromLocal, null)) {
			// if the node addition was successful, ie node did not already exist etc
		}

	}
	
	public void create(String command) {
		command = command.substring(6).trim();
		
		String localPath;
		String[] paths = command.split("/");
		localPath = paths[paths.length-1];
		boolean fromLocal; // weather or not the dir is being made by /f1/f2 or f1/f2
		
		String path;
		
		if(command.startsWith("/")) {
			// if the L we are trying to make is at the root it would be mkdir /folder - no need to append current path
			path = command;
			fromLocal = false;
		} else { // else if current node is not the root
			// if the L we are trying to make is not at the root it would be mkdir folder
			path = command;
			fromLocal = true;
		}

		if(dirTree.addNode(new Link(path, localPath, "file"), command, fromLocal, null)) {
			
		}
	}
	
	public void listfiles() {
		
		System.out.println();
				
		Link l = dirTree.getCurrentNode();
		F f = l.getReference();

		Link[] lChildList = l.getChildrenList(); 
		
		List<String> sortedList = new ArrayList<String>();
		
		String name;
		String size;
		String displaySize;
		
		if(l.equals(dirTree.getRootNode())) {
			System.out.println("=== "+currentPath+" ===");
		} else {
			System.out.println("=== "+currentPath+"/ ===");
		}
		

		for(int i=0; i<lChildList.length; i++) { 

			if(lChildList[i] != null) { 
				displaySize = "";

				name = lChildList[i].getLocalPath(); 
				
				for(int j=name.length(); j<20; j++) {
					name = name+" ";
				}
				

				if(lChildList[i].getType().equals("folder")) { 
					name+="d";
					size = ""+lChildList[i].getFolderSize();
				} else {
					name+=" ";
					size = ""+lChildList[i].getReference().getFileSize();
				}
				
				
				for(int k=size.length(); k<11; k++) {
					displaySize += " ";
				}
				
				displaySize+=size;
				name+=displaySize;
				
				sortedList.add(name);
			}
		}
		
		Collections.sort(sortedList, 
				 new Comparator<String>() {
				 	public int compare(String s1, String s2) {
				 		s1 = s1.substring(0, 20);
				 		s2 = s2.substring(0, 20);
				 		return s1.toString().compareTo(s2.toString());
				 	}
				 });
		
		for(int i=0; i<sortedList.size(); i++) {
			if(sortedList.get(i) != null) {
				System.out.println(sortedList.get(i));
			}
		}
				
		System.out.println();
			
	}
	
	public void append(String command) {
		try {
			String[] params = command.split(" ");
			String data;
			String path;
			String datawords;
			Link l;
			
			datawords = command.substring(command.indexOf('"'), command.length());
			datawords = datawords.substring(1, datawords.length());
			datawords = datawords.substring(0, datawords.indexOf('"'));
			
			path = params[params.length-1];
			
			if((l = dirTree.searchV2(path, "file", false)) != null) {
				l.getReference().append(datawords);
			} else {
				System.out.println("File: "+path+ " not found!");
			}

		} catch(Exception e) {
			System.out.println("Incomplete command!");
			System.out.println(e);
		}
		
	}
	
	public void show(String command) {
		try {
			String[] params = command.split(" ");
			String path = params[1];
			Link l;
			
			if((l = dirTree.searchV2(path, "file", false)) != null) {
				System.out.println(l.getReference().show());
			} else {
				System.out.println("File: "+path+ " not found!");
			}

		} catch(Exception e) {
			System.out.println("Incomplete command!");
		}
	}
	
	public void comment(String command) {
		command = command.substring(1,command.length()-1);
		System.out.println(command);
	}
	
	public boolean move(String command) {
		try {
			String[] params = command.split(" ");
			String origin = params[1];
			String destination = params[2];
			String absOrigin;
			String absDestination;
			String[] destinationDirs;
			String absDestinationParent;
			String localDestination;
			String fileType;
			Link originLink;
			Link destinationLink;
			Link destinationParentLink;
			Link originParentLink;

			if(origin.contains(".")) {
				fileType = "file";
			} else {
				fileType = "folder";
			}

			String originParentLinkPath;
			if((originLink = dirTree.searchV2(origin, fileType, false)) != null) {

				String localSource;
				String sourceParent;
				String[] sourceDirs = origin.split("/");

				if(sourceDirs.length==1) {
					// parent is currentNode
					originParentLink = dirTree.getCurrentNode();
				} else if((sourceDirs.length==2)&&(sourceDirs[0].equals(""))) {
					originParentLink = dirTree.getRootNode();
				} else {
					localSource = sourceDirs[sourceDirs.length-1];

					sourceParent = origin.substring(0, (origin.length()-(localSource.length()+1)));	

					if((originParentLink= dirTree.searchV2(sourceParent, "folder", false)) != null) {
						System.out.println("Parent Folder: "+sourceParent+ " not found!");
						return false;
					}
				}

			} else {
				System.out.println(fileType+": "+origin+ " not found!");
				return false;
			}

			destinationDirs = destination.split("/");

			if(destinationDirs.length==1) {
				destinationParentLink = dirTree.getCurrentNode();
			} else if((destinationDirs.length==2)&&(destinationDirs[0].equals(""))) {

				destinationParentLink = dirTree.getRootNode();
			} else {
				localDestination = destinationDirs[destinationDirs.length-1];
				absDestinationParent = destination.substring(0,(destination.length()-(localDestination.length()+1)));

				if((destinationParentLink = dirTree.searchV2(absDestinationParent, "folder", false)) != null) {

				} else {
					System.out.println("Parent Folder: "+absDestinationParent+ " not found!");
					return false;
				}
			}
			

			if((dirTree.searchV2(destination, fileType, false)) == null) {

			} else {
				System.out.println(fileType+": "+destination+ " already exists!");
				return false;
			}
			// if we get here, all the correct parents and files exist and don't exist where needed.
			// do the re-linking
			originLink.setParent(destinationParentLink);
			destinationParentLink.addChild(originLink); // Problem here Array out of bounds exception for move f.txt /f.txt
			destinationParentLink.getReference().addReference(originLink);
			destinationParentLink.setFolderSize();
			originParentLink.removeChild(originLink);
			originParentLink.getReference().removeReference(originLink);
			originParentLink.setFolderSize();

			dirTree.DFS(originLink);
			
			return true;
			
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}	
	}
	
	public boolean link(String command) {
		try {
			String[] params = command.split(" ");
			String linkFileType;
			String existingLinkFileType;
			String linkPath;
			String localLinkPath;
			String existingLinkPath;
			String absLinkPath;
			String absExistingLinkPath;
			Link link;
			Link existingLink;
			boolean fromLocal;
			
			command = command.substring(5);
			
			linkPath = params[1];
			existingLinkPath = params[2];
			
			if(linkPath.contains(".")) {
				linkFileType = "file";
			} else {
				linkFileType = "folder";
			}
			
			if(existingLinkPath.contains(".")) {
				existingLinkFileType = "file";
			} else {
				existingLinkFileType = "folder";
			}
			
			// get absLinkPath
			if(linkPath.startsWith("/")) {
				absLinkPath = linkPath;
				fromLocal = false;
			} else {
				absLinkPath = linkPath;
				fromLocal = true;
			}
			
			// get localLinkPath
			String[] localPaths = absLinkPath.split("/");
			localLinkPath = localPaths[localPaths.length-1];
			// System.out.println("localLinkPath=" + localLinkPath);
			
			// get absExistingLinkPath
			absExistingLinkPath = existingLinkPath; 
			
			// check if existingLinkPath exists - want it to
			if((existingLink = dirTree.searchV2(absExistingLinkPath, existingLinkFileType, false)) != null) {
				// existingLinkPath exists
				// System.out.println("existingLink exists");
			} else {
				System.out.println(existingLinkFileType+": " + absExistingLinkPath + " does not exist!");
				return false;
			}

			dirTree.addNode(existingLink, linkPath, fromLocal, existingLink);
			
			return true;
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public boolean delete(String command) {
		try {
			
			String[] paths = command.split(" ");
			String pathToDelete = paths[1];
			String fileType;
			Link linkToDelete;
			Link linkToDeleteParent;
			
			if(pathToDelete.contains(".")) {
				fileType="file";
			} else {
				fileType="folder";
			}
			
			
			// check if the absPathToDelete exists. We want it to.
			if((linkToDelete=dirTree.searchV2(pathToDelete, fileType, false)) != null) {
				
				// Get its parent, we need this so we can remove the link from the parents child list
				String localPathToDelete;
				String pathToDeleteParent;
				String[] pathToDeleteDirs = pathToDelete.split("/");
				if(pathToDeleteDirs.length==1) {
					// parent is currentNode
					linkToDeleteParent = dirTree.getCurrentNode();
				} else if((pathToDeleteDirs.length==2)&&(pathToDeleteDirs[0].equals(""))) {
					// parent is rootNode
					linkToDeleteParent = dirTree.getRootNode();
				} else {
					localPathToDelete = pathToDeleteDirs[pathToDeleteDirs.length-1];
					pathToDeleteParent = pathToDelete.substring(0, (pathToDelete.length()-(localPathToDelete.length()+1)));
					
					if((linkToDeleteParent=dirTree.searchV2(pathToDeleteParent, "folder", false)) != null) {
						System.out.println("Parent Folder: "+pathToDelete+ " not found!");
						return false;
					}
				}

			} else {
				System.out.println(fileType+": "+pathToDelete+" does not exist!");
				return false;
			}	
			
			// removes the link to the file
			// if the link is the only one, the actual file is deleted
			// if there is more than one link to the file the other links still exist and can access the file as usual
			// Deleting a directory also deletes all of its contents
			
			if(linkToDelete.getReference().getReferenceList().length>1) { 

				dirTree.removeNode(linkToDelete, linkToDeleteParent);

				linkToDelete.getReference().removeReference(linkToDelete);
			} else {
				dirTree.removeNode(linkToDelete, linkToDeleteParent);
				fileStorage.remove(linkToDelete.getReference()); 
			}
			
			return true;
			
		} catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public boolean deleteall(String command) {
		try {
			
			boolean notYetImplemented = true;
			
			if(notYetImplemented) {
				System.out.println("deleteall has not been implemented");
			} else {
				String[] paths = command.split(" ");
				String pathToDelete = paths[1];
				String absPathToDelete;
				String fileType;
				Link linkToDelete;
				
				if(pathToDelete.contains(".")) {
					fileType="file";
				} else {
					fileType="folder";
				}
				
				if((linkToDelete=dirTree.searchV2(pathToDelete, fileType, false)) != null) {
					if(linkToDelete.getReference().getReferenceList().length>1) {
						dirTree.removeAll(linkToDelete);
						fileStorage.remove(linkToDelete.getReference());
					} else {
						dirTree.removeAll(linkToDelete);
						fileStorage.remove(linkToDelete.getReference());
					}
				}
				
			}
			return true;
			
		} catch(Exception e) {
			System.out.println("Incomplete command!");
			return false;
		}
	}
}