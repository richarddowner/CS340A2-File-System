/**
 * CS320 Assignment 2
 * File System
 * @author rdow035 1414352
 */
// Creation Date Ordered F Object Storage 
class FList extends FileSystem {
	private F[] list;
	private int currentPosition; // End of the array
	public FList(){
		list = new F[1];
		currentPosition = 0; // -1
	}
	// Insert F object in list, if list is full; resize.
	public void insert(F f) {
		if(currentPosition == list.length) resizeList();
		list[currentPosition] = f;
		// System.out.println("f object inserted into position: " + currentPosition);
		currentPosition++;
	}
	private void resizeList(){
		int size = list.length;
		F[] tmp = new F[size*2];
		for(int i=0; i<size; i++){
			tmp[i]=list[i];
		}
		list=tmp;
	}
	// Remove F object from the list
	public void remove(F f) {
		
		int position = search(f);
		// could resize here if 1/4 full, half the size

		if(list.length>1) {

			F[] tmp = new F[list.length-1];
			
			for(int i=0; i<position; i++){
				tmp[i] = list[i];
			}
			
			if(tmp.length!=1) {
				
				for(int i=position; i<tmp.length; i++) {
					tmp[i] = list[i+1];
				}
			}
			
			list = tmp;
			currentPosition--;
		} else {
			// last object is root, we cant remove this
		}
		// System.out.println("list length after remove: " + list.length);
		// currentPosition--;
	}
	public int search(F f){
		int left = 0;
		int right = currentPosition; // -1 ?
		return binarySearch(f, left, right);
	}
	private int binarySearch(F f, int left, int right){
		if(right < left) return -1; // Not found
		int mid = (left + right) >>> 1;
		if(f.getCreationTime() > list[mid].getCreationTime()) {
			return binarySearch(f, mid+1, right);
		} else if(f.getCreationTime() < list[mid].getCreationTime()) {
			return binarySearch(f, left, mid - 1);
		} else {
			return mid;
		}
	}
}