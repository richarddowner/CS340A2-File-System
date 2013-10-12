import java.util.ArrayList;
import java.util.Stack;
/**
 * CS320 Assignment 2
 * File System
 * @author rdow035 1414352
 */
class DirectoryTree extends FileSystem{
	//private L[] collection; // remove this there is no need.
	private int currentPosition;
	private Link currentNode; // when traversing the graph from the enter command, update this variable to successful directory enters
	private Link rootNode;
	
	public DirectoryTree() {
		//collection = new L[1];
		currentPosition = 0;
	}
	
	
	public Link searchV2(String path, String type, boolean lookingForParent) {
		
		int pathStart;
		
		Link node;
		
		// Need to know if searching from root node
		if(path.startsWith("/")) {
			// search from root node
			node = rootNode;
			pathStart = 1;
		} else if(currentNode.equals(rootNode)) {
			node = rootNode;
			pathStart = 0;
		} else {
			// search from current node
			node = currentNode;
			pathStart = 0;
		}
		
		String paths[] = path.split("/");
		String searchType; // in case looking for parent
		
		
		boolean nodeFound = false;
		
		// start at 1, assume root node always first
		for(int i=pathStart; i<paths.length; i++) { // paths = { [ ], [ doc ], [ folder ] } length = 3, check 2 times because we skip root
			// Look at the nodes children if it is not null
			if(node.getChildrenList() != null) {
				for(int j=0; j<node.getChildrenList().length; j++) {
					
					if(type.equals("file")) {
						// if the type is a file.
						// search directories until the last value in paths
						// then search for file
						// Reason for doing this, is when we create a file, f1/f2/file.txt we need it to check the directories f1/f2 then file.txt
						if((i==paths.length-1)&&!lookingForParent) {
							searchType = "file";
						} else {
							searchType = "folder";
						}
						if((node.getChildrenList()[j] != null) && (node.getChildrenList()[j].getLocalPath().equals(paths[i])) && (node.getChildrenList()[j].getType().equals(searchType))) {
							// found directory we are after
							// set this to be node, and if there are more directories to check, break and go again
							node = node.getChildrenList()[j];
							nodeFound = true;
							break;
						}
					} else {
					
						// check if child = the directory we are after
						if((node.getChildrenList()[j] != null) && (node.getChildrenList()[j].getLocalPath().equals(paths[i])) && (node.getChildrenList()[j].getType().equals(type))) {
							// found directory we are after
							// set this to be node, and if there are more directories to check, break and go again
							node = node.getChildrenList()[j];
							nodeFound = true;
							break;
						}
						
					}
				}
				if(!nodeFound) {
					// if there was no match we return null
					return null; // we return null here because even though children existed, none where the correct node we where after, therefore requested node does not exist
				}
			} else {
				// no children exist - array is null
				return null; // we return null here because we have paths to check says the path variable, but there are no children, therefore requested node does not exist
			}
			
		}
		
		// If the search found parent directories for example /doc but not the folder directory from /doc/folder, then the node will
		// be set to the previously found parent in this case, /doc. However folder was not found, so node needs to be null.
		// Compare the leaf child with the last found node local directory. if they match, the leaf was found, if not, return null.
		if(node.getLocalPath().equals(paths[paths.length-1])) {
			return node;
		} else {
			return null;
		}
		
	}
	
	// Take a path and return the node
	// or if node doesn't exist, return null 
	public Link search(String path, String type, boolean lookingForParent) {
		
		String paths[] = path.split("/");
		String searchType;
		Link node = rootNode;
		
		boolean nodeFound = false;
		
		// start at 1, assume root node always first
		for(int i=1; i<paths.length; i++) { // paths = { [ ], [ doc ], [ folder ] } length = 3, check 2 times because we skip root
			// Look at the nodes children if it is not null
			if(node.getChildrenList() != null) {
				for(int j=0; j<node.getChildrenList().length; j++) {
					
					if(type.equals("file")) {
						// if the type is a file.
						// search directories until the last value in paths
						// then search for file
						// Reason for doing this, is when we create a file, f1/f2/file.txt we need it to check the directories f1/f2 then file.txt
						if((i==paths.length-1)&&!lookingForParent) {
							searchType = "file";
						} else {
							searchType = "folder";
						}
						if((node.getChildrenList()[j] != null) && (node.getChildrenList()[j].getLocalPath().equals(paths[i])) && (node.getChildrenList()[j].getType().equals(searchType))) {
							// found directory we are after
							// set this to be node, and if there are more directories to check, break and go again
							node = node.getChildrenList()[j];
							nodeFound = true;
							break;
						}
					} else {
					
						// check if child = the directory we are after
						if((node.getChildrenList()[j] != null) && (node.getChildrenList()[j].getLocalPath().equals(paths[i])) && (node.getChildrenList()[j].getType().equals(type))) {
							// found directory we are after
							// set this to be node, and if there are more directories to check, break and go again
							node = node.getChildrenList()[j];
							nodeFound = true;
							break;
						}
						
					}
				}
				if(!nodeFound) {
					// if there was no match we return null
					return null; // we return null here because even though children existed, none where the correct node we where after, therefore requested node does not exist
				}
			} else {
				// no children exist - array is null
				return null; // we return null here because we have paths to check says the path variable, but there are no children, therefore requested node does not exist
			}
			
		}
		
		// If the search found parent directories for example /doc but not the folder directory from /doc/folder, then the node will
		// be set to the previously found parent in this case, /doc. However folder was not found, so node needs to be null.
		// Compare the leaf child with the last found node local directory. if they match, the leaf was found, if not, return null.
		if(node.getLocalPath().equals(paths[paths.length-1])) {
			return node;
		} else {
			return null;
		}
	}

	// Add Node
	public boolean addNode(Link l, String command, boolean fromLocal, Link linkParent) { // probably want to add a file or folder value
		// if linkParent == null, it means we are creating a file and link, if not null, then we are creating
		// a link to a file that already exists.
		String[] pathContent = command.split("/");
		String absPathFromCommand;
		
		
		// instead of getting the abs path from the l object, get it from the command path
		// two situations, adding from mkdir/create or adding from link
		// get absPath differently because link has two params in the command
		
		// System.out.println("command="+command);
		if(rootNode!=null) {
			if(searchV2(command, l.getType(), false)!=null) {
				//System.out.println("SEARCHV2: THE NODE EXISTED");
			} else {
				//System.out.println("SEARCHV2: THE NODE DID NOT EXIST");
			}
		}
		
		Link parent;
		F f;
		
		if(currentPosition == 0) { // if root node being inserted (at run time)
			//collection[0] = l;
			rootNode = l;
			l.setParent(null);
			currentPosition++;
			currentNode = rootNode;
			return true;
		} else {
			// Check if file already exists
			// if(search(l.getAbsPath(), l.getType(), false)!=null) {
			if(searchV2(command, l.getType(), false)!=null) {
				// file already exists
				System.out.println(l.getLocalPath()+" already exists!");
				return false;
			}
			// Non root node being inserted
			// If list is full, resize it
			if(pathContent.length == 1) {
				// command would have been mkdir doc
				// add l to the child list of currentNode
				// set l's parent to be currentNode
				currentNode.addChild(l);
				l.setParent(currentNode);
				// create file object and insert into fileStorage
				if(linkParent==null) {
					f = new F(l.getLocalPath(), creationTime++, "12-12-2012", "user", l.getType(), 755);
					fileStorage.insert(f);
					// Set f and l references to each other
					//currentNode.getReference().addReference(l); // Incorrect 
				} else {
					f = linkParent.getReference();
				}
				f.addReference(l);
				// currentNode.getReference().setFolderSize();
				currentNode.setFolderSize();
				//f.addReference(l);
				l.setReference(f);
				//currentPosition++; // dont need ?
				return true;
			} else if(pathContent.length == 2 && !fromLocal) {
				// command would have been mkdir /doc 
				// add l to the child list of root node 
				// set l's parent to be root node 
				rootNode.addChild(l);
				l.setParent(rootNode);
				// create file object and insert into fileStorage
				if(linkParent==null) {
					f = new F(l.getLocalPath(), creationTime++, "12-12-2012", "user", l.getType(), 755);
					fileStorage.insert(f);
				} else {
					f = linkParent.getReference();
				}
				// Set f and l references to each other
				// rootNode.getReference().addReference(l); // Incorrect
				f.addReference(l);
				//rootNode.getReference().setFolderSize();
				rootNode.setFolderSize();
				//f.addReference(l);
				l.setReference(f);
				//currentPosition++; // dont need ?
				return true;
			} else if(pathContent.length >= 2 && fromLocal) {
				// command would have been mkdir f1/f2 OR f1/f2/f3
				//String parentPath = l.getAbsPath().substring(0, l.getAbsPath().length()-(l.getLocalPath().length()+1));
				// NEW WAY: Parent path doesnt have to be absolute, search now allows for locals
				// System.out.println("parentPath BEFORE: "+parentPath);
				String parentPath = command.substring(0, command.length()-(l.getLocalPath().length()+1));
				
				//System.out.println("command="+command);
				//System.out.println("parentPath AFTER: "+parentPath);
				
				// check if parent path exists
				if((parent = searchV2(parentPath, l.getType(), true)) != null) {
					parent.addChild(l);
					l.setParent(parent);
					// create file object and insert into fileStorage
					if(linkParent==null) {
						f = new F(l.getLocalPath(), creationTime++, "12-12-2012", "user", l.getType(), 755);
						fileStorage.insert(f);
					} else {
						f = linkParent.getReference();
					}
					// Set f and l references to each other
					// parent.getReference().addReference(l); // Incorrect
					f.addReference(l);
					// parent.getReference().setFolderSize();
					parent.setFolderSize();
					//f.addReference(l);
					l.setReference(f);
					return true;
				} else {
					System.out.println("Error parent directories do not exist!"); // PROBLEM WITH LINK HERE
					return false;
				}
			} else if(pathContent.length > 2) { //TODO: is this the same as above?
				// command would have been mkdir /doc/folder/new
				// search the graph for /doc/folder
				// add l to the child list of /doc/folder 
				// set l's parent to be /doc/folder
				
				//String parentPath = command.substring(0, l.getAbsPath().length()-(l.getLocalPath().length()+1));
				// NEW WAY
				String parentPath = command.substring(0, command.length()-(l.getLocalPath().length()+1));
				
				parent = searchV2(parentPath, l.getType(), true);
				// need to make sure the parent exists
				if(parent != null) {
					parent.addChild(l);
					l.setParent(parent);
					// create file object and insert into fileStorage
					if(linkParent==null) {
						f = new F(l.getLocalPath(), creationTime++, "12-12-2012", "user", l.getType(), 755);
						fileStorage.insert(f);
					} else {
						f = linkParent.getReference();
					}
					// Set f and l references to each other
					// parent.getReference().addReference(l); // Incorrect
					f.addReference(l);
					// parent.getReference().setFolderSize();
					parent.setFolderSize();
					//f.addReference(l);
					l.setReference(f);
					//currentPosition++; // dont need ?
					return true;
				} else {
					System.out.println("Error parent directories do not exist!");
					return false;
				}
			} 
		}
		return false;
	}
	
	public int removeAll(Link linkToRemove) {
		
		// Link link = search(path, "temp", false);
		F f = linkToRemove.getReference();
		
		
		// loop over the F referenceLink array removing each L object from graph
		// the removeNode method will take care of removing the F object itself.
		for(int i=0; i<f.getReferenceList().length; i++) {
			// removeNode(f.getReferenceList()[i].getAbsPath());
			Link currNode = rootNode;
			if(f.getReferenceList()[i] != null) {
				Link tmp;
				
				deleteAllDFS(rootNode, f.getReferenceList()[i]);
	
			}
			

		}
		
		return -1;
	}
	
	// Remove Node
	public boolean removeNode(Link linkToDelete, Link parentLink) {
		// removes link to a file given by the file path name. 
		// if the link is the only one, the actual file is deleted.
		// deleting a directory also deletes all of its contents
		// Link parent = linkToDelete.getParent();
		
		//parent.removeChild(linkToDelete);
		parentLink.removeChild(linkToDelete);
		parentLink.setFolderSize();
		linkToDelete.setParent(null);
		
		return true;
	}
	
	
	public Link getCurrentNode() {
		return currentNode;
	}
	public void setCurrentNode(Link node) {
		currentNode = node;
	}

	public Link getRootNode() {
		return rootNode;
	}
	
	public void setRootNode(Link node) {
		this.rootNode = node;
	}
	
	public void deleteAllDFS(Link node, Link nodeToFind) {
		
		ArrayList<Link> tmp = new ArrayList<Link>();
		Stack<Link> s = new Stack<Link>();
		
		node.setSeen(true);
		s.push(node);
		// System.out.println("Pushing: "+node.getLocalPath());
		node.updateAbsPath();
		tmp.add(node);
		
		
		Link nextNode;
		Link parent;
		int pos = -1;
		
		while(!s.isEmpty()) {
			// get an unvisited vertex adjacent to the stack top
			nextNode = getAdjacentUnvisitedNode((Link)s.peek());
			if(nextNode == null) {
				
				s.pop();
			} else {
				
				nextNode.setSeen(true);
				
				if(nextNode.equals(nodeToFind)) {
					if(pos == -1) {
						parent = rootNode;
					} else {
						parent = tmp.get(pos);
					}
					
					removeNode(nextNode, parent);
				}
				
				pos++;
				
				s.push(nextNode);
				tmp.add(nextNode);
			}
			
		}
		
		for(int i=0; i<tmp.size(); i++) {
			tmp.get(i).setSeen(false);
		}
		
	}
	
	
	public void DFS(Link node) {
		
		ArrayList<Link> tmp = new ArrayList<Link>();
		Stack<Link> s = new Stack<Link>();
		
		node.setSeen(true);
		s.push(node);
		// System.out.println("Pushing: "+node.getLocalPath());
		node.updateAbsPath();
		tmp.add(node);
		
		
		Link nextNode;
		
		while(!s.isEmpty()) {
			// get an unvisited vertex adjacent to the stack top
			nextNode = getAdjacentUnvisitedNode((Link)s.peek());
			if(nextNode == null) {
				s.pop();
			} else {
				nextNode.setSeen(true);
				// System.out.println("Pushing: "+nextNode.getLocalPath());
				nextNode.updateAbsPath();
				s.push(nextNode);
				tmp.add(nextNode);
			}
			
		}
		
		for(int i=0; i<tmp.size(); i++) {
			tmp.get(i).setSeen(false);
		}
	}
	
	public Link getAdjacentUnvisitedNode(Link node) {
		
		Link[] children = node.getChildrenList();
		
		if(children != null) {
			if(children[0] != null) {
				for(int i=0; i<children.length; i++) {
					if(children[i].getSeen() == false) {
						return children[i];
					}
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		
		return null;
	}
	
	
}