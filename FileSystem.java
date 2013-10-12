import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * CS320 Assignment 2
 * File System
 * @author rdow035 1414352
 */
public class FileSystem {
	
	protected static FList fileStorage;
	protected static DirectoryTree dirTree;
	protected static int creationTime;
	protected static CLI cli;
	
	public static void main(String[] args) {
		
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		String command;
		
		// Initialize Data structures
		fileStorage = new FList();
		dirTree = new DirectoryTree();
		
		// Create root folder
		Link rootNodeLink = new Link("/", "", "folder");
		dirTree.addNode(rootNodeLink, "", true, null); 
		dirTree.setCurrentNode(rootNodeLink);
		
		F rootNodeFile = new F("", creationTime++, "12-12-2012", "root", "folder", 700);
		
		fileStorage.insert(rootNodeFile);
		// rootNodeFile.addReference(rootNodeLink);
		rootNodeLink.setReference(rootNodeFile);
		
		cli = new CLI();
		
		//System.out.println("args.length="+args.length);
		try {	
			
			Scanner s = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
			ArrayList<String> commandList = new ArrayList<String>();
			
			// String str = in.readLine();
			
			
			
			if(System.in.available() != 0) {
				
				while(s.hasNext()) {
					commandList.add(s.nextLine());
				}
				
				for(int i=0; i<commandList.size(); i++) {
					
					//System.out.println(commandList.get(i));
					// System.out.print(cli.getCurrentPath()+cli.getPrompt());
					System.out.print("> ");
					// command = inputReader.readLine().trim();
					command = commandList.get(i);
					if(command.equals("quit")) break;
					else if(command.equals("home")) cli.home();
					else if(command.equals("listfiles")) cli.listfiles();
					else if(command.startsWith("\"")) cli.comment(command);
					else if(command.length()>=6 && command.substring(0, 6).equals("enter ")) cli.enter(command);
					else if(command.length()>=5 && command.substring(0, 5).equals("enter")) cli.missingOperand(command.substring(0, 5));
					else if(command.length()>=6 && command.substring(0, 6).equals("mkdir ")) cli.mkdir(command);
					else if(command.length()>=5 && command.substring(0, 5).equals("mkdir")) cli.missingOperand(command.substring(0, 5));
					else if(command.length()>=7 && command.substring(0, 7).equals("create ")) cli.create(command);
					else if(command.length()>=6 && command.substring(0, 6).equals("create")) cli.missingOperand(command.substring(0, 6));
					else if(command.length()>=7 && command.substring(0, 7).equals("append ")) cli.append(command);
					else if(command.length()>=6 && command.substring(0, 6).equals("append")) cli.missingOperand(command.substring(0, 6));
					else if(command.length()>=10 && command.substring(0, 10).equals("deleteall ")) cli.deleteall(command);
					else if(command.length()>=9 && command.substring(0, 9).equals("deleteall")) cli.missingOperand(command.substring(0, 9));
					else if(command.length()>=7 && command.substring(0, 7).equals("delete ")) cli.delete(command);
					else if(command.length()>=6 && command.substring(0, 6).equals("delete")) cli.missingOperand(command.substring(0, 6));
					else if(command.length()>=5 && command.substring(0, 5).equals("link ")) cli.link(command);
					else if(command.length()>=4 && command.substring(0, 4).equals("link")) cli.missingOperand(command.substring(0, 4));
					else if(command.length()>=5 && command.substring(0, 5).equals("show ")) cli.show(command);
					else if(command.length()>=4 && command.substring(0, 4).equals("show")) cli.missingOperand(command.substring(0, 4));
					else if(command.length()>=5 && command.substring(0, 5).equals("move ")) cli.move(command);
					else if(command.length()>=4 && command.substring(0, 4).equals("move")) cli.missingOperand(command.substring(0, 4));
					else if(command.equals("")) cli.setCurrentPath(cli.getCurrentPath()); 
					else System.out.println("No command '"+command+"' found");
				}
				
			} else {
				while(true) {
					try {
						//System.out.print(cli.getCurrentPath()+cli.getPrompt());
						System.out.print("> ");
						command = inputReader.readLine().trim();
						if(command.equals("quit")) break;
						else if(command.equals("home")) cli.home();
						else if(command.equals("listfiles")) cli.listfiles();
						else if(command.startsWith("\"")) cli.comment(command);
						else if(command.length()>=6 && command.substring(0, 6).equals("enter ")) cli.enter(command);
						else if(command.length()>=5 && command.substring(0, 5).equals("enter")) cli.missingOperand(command.substring(0, 5));
						else if(command.length()>=6 && command.substring(0, 6).equals("mkdir ")) cli.mkdir(command);
						else if(command.length()>=5 && command.substring(0, 5).equals("mkdir")) cli.missingOperand(command.substring(0, 5));
						else if(command.length()>=7 && command.substring(0, 7).equals("create ")) cli.create(command);
						else if(command.length()>=6 && command.substring(0, 6).equals("create")) cli.missingOperand(command.substring(0, 6));
						else if(command.length()>=7 && command.substring(0, 7).equals("append ")) cli.append(command);
						else if(command.length()>=6 && command.substring(0, 6).equals("append")) cli.missingOperand(command.substring(0, 6));
						else if(command.length()>=10 && command.substring(0, 10).equals("deleteall ")) cli.deleteall(command);
						else if(command.length()>=9 && command.substring(0, 9).equals("deleteall")) cli.missingOperand(command.substring(0, 9));
						else if(command.length()>=7 && command.substring(0, 7).equals("delete ")) cli.delete(command);
						else if(command.length()>=6 && command.substring(0, 6).equals("delete")) cli.missingOperand(command.substring(0, 6));
						else if(command.length()>=5 && command.substring(0, 5).equals("link ")) cli.link(command);
						else if(command.length()>=4 && command.substring(0, 4).equals("link")) cli.missingOperand(command.substring(0, 4));
						else if(command.length()>=5 && command.substring(0, 5).equals("show ")) cli.show(command);
						else if(command.length()>=4 && command.substring(0, 4).equals("show")) cli.missingOperand(command.substring(0, 4));
						else if(command.length()>=5 && command.substring(0, 5).equals("move ")) cli.move(command);
						else if(command.length()>=4 && command.substring(0, 4).equals("move")) cli.missingOperand(command.substring(0, 4));
						else if(command.equals("")) cli.setCurrentPath(cli.getCurrentPath()); 
						else System.out.println("No command '"+command+"' found");
					} catch(Exception e) {
						System.out.println(e);
					}
				}
			}

		} catch(Exception e) {
			System.out.println(e);
		}
	}
}






