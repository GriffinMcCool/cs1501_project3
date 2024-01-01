// author Griffin McCool
// DLBNode class & DLB implementation adapted from project 2
/**
* The code is a little confusing so to explain a little I basically have 2 PQ's
* (one for price and one for mileage) that contain all cars backed by a DLB that uses
* VIN's as keys and the nodes hold various information. This allows for O(1) access for
* finding an item in the PQ's, thus allowing for O(lg n) updates and removals. For the 
* make and model portion, I went back and added another DLB that uses "make:model" as 
* a key, and a valid make and model pair holds references to two more PQ's (those being
* a price and mileage PQ specific to that make & model). Then, these PQ's together have their
* own indirection DLB that allows for O(1) searching, and thus O(lg n) updates, removal, etc.
* With all of this combined, we can maintain all PQ's in O(lg n) and return low value in O(1)
*/
package cs1501_p3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class CarsPQ implements CarsPQ_Inter{
	private Car[] pricePQ = new Car[4];
	private Car[] mileagePQ = new Car[4];
	private int insertPosition;
	private DLBNode root;
	private DLBNode mmroot;
	private int lastpLoc;
	private int lastmLoc;

	// nodes used for indirection DLBs
	class DLBNode{
	
		/**
		* Letter represented by this DLBNode
		*/
		private char let;

		/**
		* Represents the end of a valid VIN, holds location in price and mileage PQ's
		*/
		private int pLoc;
		private int mLoc;

		/**
		* Used to keep track of the PQ's used for each make and model pair
		*/
		private Car[] mmPricePQ;
		private Car[] mmMileagePQ;

		/**
		* Current insert position in a make and model PQ
		*/
		private int insPos;

		/**
		* Root for DLB that holds VIN keys for each specific make and model PQ
		*/
		private DLBNode pairRoot;

		/**
		* Lead to other alternatives for current letter in the path
		*/
		private DLBNode right;

		/**
		* Leads to keys with prefixed by the current path
		*/	
		private DLBNode down;

		/**
		* Constructor that accepts the letter for the new node to represent
		*/
		public DLBNode(char let) {
			this.let = let;
			this.mLoc = 0;
			this.pLoc = 0;
			this.mmPricePQ = null;
			this.mmMileagePQ = null;
			this.right = null;
			this.down = null;
		}

		/**
		* Getter for the letter this DLBNode represents
		*
		* @return	The letter
		*/
		public char getLet() {
			return let;
		}

		/**
		* Getter for the next linked-list DLBNode
		*
		* @return	Reference to the right DLBNode
		*/
		public DLBNode getRight() {
			return right;
		}

		/**
		* Getter for the child DLBNode
		*
		* @return	Reference to the down DLBNode
		*/
		public DLBNode getDown() {
			return down;
		}

		/**
		* Setter for the next linked-list DLBNode
		*
		* @param	r DLBNode to set as the right reference
		*/
		public void setRight(DLBNode r) {
			right = r;
		}

		/**
		* Setter for the child DLBNode
		*
		* @param	d DLBNode to set as the down reference
		*/
		public void setDown(DLBNode d) {
			down = d;
		}

		/**
		* Setter for the location of an element in the price PQ
		*
		* @param	i location index in PQ
		*/
		public void setpLoc(int i){
			pLoc = i;
		}

		/**
		* Setter for the location of an element in the mileage PQ
		*
		* @param	i location index in PQ
		*/
		public void setmLoc(int i){
			mLoc = i;
		}

		/**
		* Getter for the location of an element in the price PQ
		*
		* @return	pLoc index in price PQ
		*/
		public int getpLoc(){
			return pLoc;
		}

		/**
		* Getter for the location of an element in the mileage PQ
		*
		* @return	mLoc index in mileage PQ
		*/
		public int getmLoc(){
			return mLoc;
		}

		public Car[] getmmPricePQ(){
			return mmPricePQ;
		}

		public Car[] getmmMileagePQ(){
			return mmMileagePQ;
		}

		public void setmmPricePQ(Car[] pq){
			mmPricePQ = pq;
		}

		public void setmmMileagePQ(Car[] pq){
			mmMileagePQ = pq;
		}

		public int getInsPos(){
			return insPos;
		}

		public void setInsPos(int i){
			insPos = i;
		}

		public DLBNode getpairRoot(){
			return pairRoot;
		}

		public void setpairRoot(DLBNode node){
			pairRoot = node;
		}

	}

	/**
	*	Constructor that takes a file and fills up the minPQ's
	*
	*/
    public CarsPQ(String filename){
		try {
			Car newCar;
			String newVIN;
			String newMake;
			String newModel;
			int newPrice;
			int newMileage;
			String newColor;
			File file = new File(filename);
			Scanner scan = new Scanner(file);
			// skips past first line in the file
			if (scan.hasNextLine()) scan.nextLine();
			scan.useDelimiter(":");
			while (scan.hasNextLine()){
				newVIN = scan.next();
				newMake = scan.next();
				newModel = scan.next();
				newPrice = scan.nextInt();
				newMileage = scan.nextInt();
				newColor = scan.nextLine();
				// takes out ':' before color
				newColor = newColor.substring(1, newColor.length());
				newCar = new Car(newVIN, newMake, newModel, newPrice, newMileage, newColor);
				add(newCar);
			}
			scan.close();
		} catch (FileNotFoundException fnfe){
			System.out.println("File not found.");
		}
    }

    /**
	 * Add a new Car to the data structure
	 * Should throw an `IllegalStateException` if there is already car with the
	 * same VIN in the datastructure.
	 *
	 * @param 	c Car to be added to the data structure
	 */
	public void add(Car c) throws IllegalStateException{
		if (c == null) return;
		try {
			Car check = get(c.getVIN());
			throw new IllegalStateException("Cannot add duplicate VIN");
		} catch (NoSuchElementException nsee){

		}
		// if we're out of space, resize
		if (insertPosition == pricePQ.length) resize(pricePQ, mileagePQ);
		// if there is nothing in the PQ, add at the front
		if (pricePQ[1] == null){
			pricePQ[1] = c;
			mileagePQ[1] = c;
			insertPosition = 2;
			// update indirections
			addDLB(c.getVIN(), 1, 1);
			addmmDLB(c.getMake() + ":" + c.getModel());
			addmm(c);
			return;
		}
		Car temp;
		// update pricePQ
		pricePQ[insertPosition] = c;
		int i = insertPosition;
		// while c can move up, keep swapping with parent
		while ((i > 1) && (c.getPrice() < pricePQ[i/2].getPrice())){
			temp = pricePQ[i/2];
			pricePQ[i/2] = c;
			pricePQ[i] = temp;
			// update indirection DLB for swapped car
			addDLB(pricePQ[i].getVIN(), i, 0);
			i = i/2;
		}
		// update mileagePQ
		mileagePQ[insertPosition] = c;
		int j = insertPosition;
		// while c can move up, keep swapping with parent
		while ((j > 1) && (c.getMileage() < mileagePQ[j/2].getMileage())){
			temp = mileagePQ[j/2];
			mileagePQ[j/2] = c;
			mileagePQ[j] = temp;
			// update indirection DLB for swapped car
			addDLB(mileagePQ[j].getVIN(), 0, j);
			j = j/2;
		}
		insertPosition++;
		// adds VIN to DLB to search for later
		addDLB(c.getVIN(), i, j);
		addmmDLB(c.getMake() + ":" + c.getModel());
		// add to the respective make and model PQ
		addmm(c);
    }

	/**
	* Adds a car into a mileage and price PQ made specifically for that model
	*
	* @param	c car to be added to PQ's
	*/
	private void addmm(Car c){
		DLBNode node = getmmNode(c.getMake() + ":" + c.getModel());
		Car[] ppq = node.getmmPricePQ();
		Car[] mpq = node.getmmMileagePQ();
		int insPos = node.getInsPos();

		// if we're out of space, resize
		if (insPos == ppq.length) resize(ppq, mpq);

		// if there is nothing in the PQ, add at the front
		if (ppq[1] == null){
			ppq[1] = c;
			mpq[1] = c;
			node.setInsPos(2);
			// update pair's indirection
			addPairDLB(c.getVIN(), 1, 1, node);
			return;
		}

		// update mm ppq
		Car temp;
		ppq[insPos] = c;
		int i = insPos;
		// while c can move up, keep swapping with parent
		while ((i > 1) && (c.getPrice() < ppq[i/2].getPrice())){
			temp = ppq[i/2];
			ppq[i/2] = c;
			ppq[i] = temp;
			// update pair's indirection DLB for swapped car
			addPairDLB(ppq[i].getVIN(), i, 0, node);
			i = i/2;
		}

		// update mm mpq
		mpq[insPos] = c;
		int j = insPos;
		// while c can move up, keep swapping with parent
		while ((j > 1) && (c.getMileage() < mpq[j/2].getMileage())){
			temp = mpq[j/2];
			mpq[j/2] = c;
			mpq[j] = temp;
			// update pair's indirection DLB for swapped car
			addPairDLB(mpq[j].getVIN(), 0, j, node);
			j = j/2;
		}
		node.setInsPos(insPos + 1);
		// adds VIN to DLB to search for later
		addPairDLB(c.getVIN(), i, j, node);
	}

	/**
	* Resizes the array and copies old elements over into new array
	*
	* @param	price pricePQ
	* @param	mileage mileagePQ
	*/
	private void resize(Car[] price, Car[] mileage){
		// makes a new array of twice the size and copies old elements back in
		pricePQ = new Car[2 * price.length];
		mileagePQ = new Car[2 * mileage.length];
		for (int i = 1; i < price.length; i++){
			pricePQ[i] = price[i];
			mileagePQ[i] = mileage[i];
		}
	}

	/**
	 * Adds a car to the make & model DLB that allows access to PQ's for specific makes and models
	 * (allows O(1) access to PQ, which also has it's own DLB for VIN's to allow for updates & removal
	 * in O(lg n))
	 *
	 * @param 	key New make & model pair to be added to the DLB
	 */	
	private void addmmDLB(String key){
		if (key != null){
            DLBNode cur = mmroot;
            DLBNode addedNode = null;
            // adds each character
            for (int i = 0; i < key.length(); i++){
                addedNode = addmmDown(key.charAt(i), cur, addedNode);
                cur = addedNode.getDown();
            }
			// add reference to specific mmPQ's (make and model PQ) at end of key
			if (addedNode.getmmPricePQ() == null){
				addedNode.setmmPricePQ(new Car[10]);
				addedNode.setmmMileagePQ(new Car[10]);
			}
        }
	}

	/**
	 * Helper method for addmmDLB() that adds node vertically
	 * 
     * @param   c       character we're adding
     * @param   node    current node
     * @param   parent  overall parent node
     *
	 * @return	DLBNode added node
	 */
	private DLBNode addmmDown(char c, DLBNode node, DLBNode parent){
        // if the current node is null, make a new node there with c
        if (node == null){
            // initialize the root
            if (node == mmroot){
                mmroot = new DLBNode(c);
                return mmroot;
            } else parent.setDown(new DLBNode(c));
            return parent.getDown();
        }
        // if node is full, add to right
        return addRight(c, node, null, parent);
    }

	/**
	 * Helper method for addmmDLB() that adds node horizontally
	 * 
     * @param   c       character we're adding
     * @param   cur     current node
     * @param   prev    previous node
     * @param   parent  overall parent node
     *
	 * @return	DLBNode added node or node that was found
	 */
	private DLBNode addmmRight(char c, DLBNode cur, DLBNode prev, DLBNode parent){
        // if we're at the end of the list, add there
        if (cur == null){
            prev.setRight(new DLBNode(c));
            return prev.getRight();
        }
        // if c is already in the list, just return that node
        if (c == cur.getLet()) return cur;
        // to maintain sorted order, we compare c to current node and see whether we need to insert right or left
        // if c is less than the current node's letter, we insert there
        if (c < cur.getLet()){
            DLBNode newNode = new DLBNode(c);
            // if we are inserting to the left of the root, we make the newNode the new root
            if (cur == mmroot) mmroot = newNode;
            // insert into list
            if (prev != null) prev.setRight(newNode);
            newNode.setRight(cur);
            // if we are inserting to the far left of a layer, update the parent's child 
            if ((prev == null) && (parent != null)) parent.setDown(newNode);
            return newNode;
        } else {
            return addRight(c, cur.getRight(), cur, parent);
        }
    }

	/**
	 * Add a new VIN to the main indirection DLB
	 *
	 * @param 	vin New VIN to be added to the indirection DLB
	 */	
	private void addDLB(String vin, int priceLoc, int mileageLoc){
        if (vin != null){
            DLBNode cur = root;
            DLBNode addedNode = null;
            // adds each character
            for (int i = 0; i < vin.length(); i++){
                addedNode = addDown(vin.charAt(i), cur, addedNode);
                cur = addedNode.getDown();
            }
            // adds ints in node that represent the location of the car in both PQ's
			if (priceLoc != 0) addedNode.setpLoc(priceLoc);
            if (mileageLoc != 0) addedNode.setmLoc(mileageLoc);
        }
    }

    /**
	 * Helper method for addDLB() that adds node vertically
	 * 
     * @param   c       character we're adding
     * @param   node    current node
     * @param   parent  overall parent node
     *
	 * @return	DLBNode added node
	 */
    private DLBNode addDown(char c, DLBNode node, DLBNode parent){
        // if the current node is null, make a new node there with c
        if (node == null){
            // initialize the root
            if (node == root){
                root = new DLBNode(c);
                return root;
            } else parent.setDown(new DLBNode(c));
            return parent.getDown();
        }
        // if node is full, add to right
        return addRight(c, node, null, parent);
    }

    /**
	 * Helper method for addDLB() that adds node horizontally
	 * 
     * @param   c       character we're adding
     * @param   cur     current node
     * @param   prev    previous node
     * @param   parent  overall parent node
     *
	 * @return	DLBNode added node or node that was found
	 */
    private DLBNode addRight(char c, DLBNode cur, DLBNode prev, DLBNode parent){
        // if we're at the end of the list, add there
        if (cur == null){
            prev.setRight(new DLBNode(c));
            return prev.getRight();
        }
        // if c is already in the list, just return that node
        if (c == cur.getLet()) return cur;
        // to maintain sorted order, we compare c to current node and see whether we need to insert right or left
        // if c is less than the current node's letter, we insert there
        if (c < cur.getLet()){
            DLBNode newNode = new DLBNode(c);
            // if we are inserting to the left of the root, we make the newNode the new root
            if (cur == root) root = newNode;
            // insert into list
            if (prev != null) prev.setRight(newNode);
            newNode.setRight(cur);
            // if we are inserting to the far left of a layer, update the parent's child 
            if ((prev == null) && (parent != null)) parent.setDown(newNode);
            return newNode;
        } else {
            return addRight(c, cur.getRight(), cur, parent);
        }
    }

	/**
	 * Add a new VIN to the pair's indirection DLB
	 *
	 * @param 	vin New VIN to be added to the pair's indirection DLB
	 */	
	private void addPairDLB(String vin, int priceLoc, int mileageLoc, DLBNode node){
        if (vin != null){
            DLBNode cur = node.getpairRoot();
            DLBNode addedNode = null;
            // adds each character
            for (int i = 0; i < vin.length(); i++){
                addedNode = addPairDown(vin.charAt(i), cur, addedNode, node);
                cur = addedNode.getDown();
            }
            // adds ints in node that represent the location of the car in both PQ's
			if (priceLoc != 0) addedNode.setpLoc(priceLoc);
            if (mileageLoc != 0) addedNode.setmLoc(mileageLoc);
        }
    }

	/**
	 * Helper method for addPairDLB() that adds node vertically
	 * 
     * @param   c       character we're adding
     * @param   node    current node
     * @param   parent  overall parent node
	 * @param	node2	root node for pair's DLB
     *
	 * @return	DLBNode added node
	 */
    private DLBNode addPairDown(char c, DLBNode node, DLBNode parent, DLBNode node2){
        // if the current node is null, make a new node there with c
        if (node == null){
            // initialize the root
            if (node == node2.getpairRoot()){
                node2.setpairRoot(new DLBNode(c));
                return node2.getpairRoot();
            } else parent.setDown(new DLBNode(c));
            return parent.getDown();
        }
        // if node is full, add to right
        return addPairRight(c, node, null, parent, node2);
    }

	/**
	 * Helper method for addPairDLB() that adds node horizontally
	 * 
     * @param   c       character we're adding
     * @param   cur     current node
     * @param   prev    previous node
     * @param   parent  overall parent node
	 * @param	node	root node for pair's DLB
     *
	 * @return	DLBNode added node or node that was found
	 */
    private DLBNode addPairRight(char c, DLBNode cur, DLBNode prev, DLBNode parent, DLBNode node){
        // if we're at the end of the list, add there
        if (cur == null){
            prev.setRight(new DLBNode(c));
            return prev.getRight();
        }
        // if c is already in the list, just return that node
        if (c == cur.getLet()) return cur;
        // to maintain sorted order, we compare c to current node and see whether we need to insert right or left
        // if c is less than the current node's letter, we insert there
        if (c < cur.getLet()){
            DLBNode newNode = new DLBNode(c);
            // if we are inserting to the left of the root, we make the newNode the new root
            if (cur == node.getpairRoot()) node.setpairRoot(newNode);
            // insert into list
            if (prev != null) prev.setRight(newNode);
            newNode.setRight(cur);
            // if we are inserting to the far left of a layer, update the parent's child 
            if ((prev == null) && (parent != null)) parent.setDown(newNode);
            return newNode;
        } else {
            return addPairRight(c, cur.getRight(), cur, parent, node);
        }
    }


	/**
	 * Helper method to get the PQ's for a specific make and model
	 *
	 * @param 	key make and model to search for
	 *
	 * @return	node that holds reference to PQ's for make and model
	 */
	private DLBNode getmmNode(String key){
		if (key == null) throw new NoSuchElementException(key + " not found");
		DLBNode cur = mmroot;
		DLBNode next = mmroot;
		// loop through each character and check to see if it's there
        for (int i = 0; i < key.length(); i++){
            cur = contains(key.charAt(i), next);
            // if character is not found, immediately return false
            if (cur == null) throw new NoSuchElementException(key + " not found");
            next = cur.getDown();
        }
		// if the string is found, make sure there is a valid PQ in the node
		if ((cur != null) && (cur.getmmPricePQ() != null)){
			return cur;
		}
        // if we get here, make and model was not found
		throw new NoSuchElementException(key + " not found");
	}

	/**
	 * Retrieve a new Car from the data structure
	 * Should throw a `NoSuchElementException` if there is no car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param 	vin VIN number of the car to be updated
	 *
	 * @return	car specified by VIN
	 */
	public Car get(String vin) throws NoSuchElementException{
		if (vin == null) throw new NoSuchElementException(vin + " not found");
		if (pricePQ[1] == null) throw new NoSuchElementException(vin + " not found");
		DLBNode cur = root;
		DLBNode next = root;
		// loop through each character and check to see if it's there
        for (int i = 0; i < vin.length(); i++){
            cur = contains(vin.charAt(i), next);
            // if character is not found, immediately return false
            if (cur == null) throw new NoSuchElementException(vin + " not found");
            next = cur.getDown();
        }
		// if the string is found, make sure there is a pLoc > 0 so it's not just a prefix and return that car
        if ((cur != null) && (cur.getpLoc() > 0)){
			lastpLoc = cur.getpLoc();
			lastmLoc = cur.getmLoc();
			return pricePQ[cur.getpLoc()];
		}
        // if we get here, vin was not found
		throw new NoSuchElementException(vin + " not found");
    }

	/**
	 * Retrieve the node that holds the position of the specified car in the mm PQ
	 *
	 * @param 	vin VIN number of the car to be updated
	 * @param 	node node to start search thru DLB at
	 *
	 * @return	found node
	 */
	private DLBNode getmmPosNode(String vin, DLBNode node) throws NoSuchElementException{
		if (vin == null) throw new NoSuchElementException(vin + " not found");
		DLBNode cur = node;
		DLBNode next = node;
		// loop through each character and check to see if it's there
        for (int i = 0; i < vin.length(); i++){
            cur = contains(vin.charAt(i), next);
            // if character is not found, immediately return false
            if (cur == null) throw new NoSuchElementException(vin + " not found");
            next = cur.getDown();
        }
		// if the string is found, make sure there is a pLoc > 0 so it's not just a prefix and return that node
        if ((cur != null) && (cur.getpLoc() > 0)){
			return cur;
		}
        // if we get here, vin was not found
		throw new NoSuchElementException(vin + " not found");
	}


	/**
	 * Helper method for searching by VIN
	 * 
     * @param   c       character we're searching for
     * @param   node    current node
     *
	 * @return	DLBNode node that VIN is found at (null if not found)
	 */
    private DLBNode contains(char c, DLBNode node){
        if (node == null) return null;
        if (c != node.getLet()){
            // if there is a node to the right, check recursively
            if (node.getRight() != null) return contains(c, node.getRight());
            else return null;
        }
        return node;
    }

	/**
	 * Update the price attribute of a given car
	 * Should throw a `NoSuchElementException` if there is no car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param 	vin VIN number of the car to be updated
	 * @param	newPrice The updated price value
	 */
	public void updatePrice(String vin, int newPrice) throws NoSuchElementException{
		if (pricePQ[1] == null) throw new NoSuchElementException(vin + " not found");
		Car c = get(vin);
		Car save = c;
		Car leastChild = null;
		Car temp;
		int leastChildIndex = 0;
		c.setPrice(newPrice);
		// i = location of car c
		int i = lastpLoc;
		// while c can move up, keep swapping with parent
		while ((i > 1) && (c.getPrice() < pricePQ[i/2].getPrice())){
			temp = pricePQ[i/2];
			pricePQ[i/2] = c;
			pricePQ[i] = temp;
			// update indirection DLB for swapped car
			addDLB(pricePQ[i].getVIN(), i, 0);
			i = i/2;
		}
		// updates c's location value in the DLB
		addDLB(c.getVIN(), i, 0);
		i = lastpLoc;
		c = pricePQ[lastpLoc];
		if (2 * i < insertPosition){
			// if both are valid indexes, take minimum
			if ((2 * i) + 1 < insertPosition){
				leastChild = (pricePQ[2 * i].getPrice() <= pricePQ[(2 * i) + 1].getPrice()) ? pricePQ[2 * i] : pricePQ[(2 * i) + 1];
				leastChildIndex = (pricePQ[2 * i].getPrice() <= pricePQ[(2 * i) + 1].getPrice()) ? (2 * i) : ((2 * i) + 1);
			}  else {
				leastChild = pricePQ[2 * i];
				leastChildIndex = 2 * i;
			}
		}
		// while c can move down in pricePQ, keep swapping with min child
		if (leastChild != null){
			while ((2 * i < insertPosition) && (c.getPrice() > leastChild.getPrice())){
				temp = leastChild;
				pricePQ[leastChildIndex] = c;
				pricePQ[i] = temp;
				// update indirection DLB for swapped car
				addDLB(pricePQ[i].getVIN(), i, 0);
				i = leastChildIndex;
				if (2 * i < insertPosition){
					// if both aren't null, take minimum
					if ((2 * i) + 1 < insertPosition){
						leastChild = (pricePQ[2 * i].getPrice() <= pricePQ[(2 * i) + 1].getPrice()) ? pricePQ[2 * i] : pricePQ[(2 * i) + 1];
						leastChildIndex = (pricePQ[2 * i].getPrice() <= pricePQ[(2 * i) + 1].getPrice()) ? (2 * i) : ((2 * i) + 1);
					}  else {
						leastChild = pricePQ[2 * i];
						leastChildIndex = 2 * i;
					}
				}
			}
		}
		// updates moved car's location value in the DLB
		if (pricePQ[i] != null) addDLB(pricePQ[i].getVIN(), i, 0);
		updatemmPrice(save);
    }

	/**
	 * Maintains the order in the mmPPQ
	 *
	 * @param 	c car to be updated in the mmPPQ
	 */
	private void updatemmPrice(Car c){
		DLBNode node = getmmNode(c.getMake() + ":" + c.getModel());
		Car[] ppq = node.getmmPricePQ();
		DLBNode node2 = getmmPosNode(c.getVIN(), node.getpairRoot());
		int ploc = node2.getpLoc();
		Car leastChild = null;
		Car temp;
		int insPos = node.getInsPos();
		int leastChildIndex = 0;
		// i = location of car c in make & model price priority queue (mmppq)
		int i = ploc;
		// while c can move up, keep swapping with parent
		while ((i > 1) && (c.getPrice() < ppq[i/2].getPrice())){
			temp = ppq[i/2];
			ppq[i/2] = c;
			ppq[i] = temp;
			// update indirection DLB for swapped car
			addPairDLB(ppq[i].getVIN(), i, 0, node);
			i = i/2;
		}
		// updates c's location value in the DLB
		addPairDLB(c.getVIN(), i, 0, node);

		i = ploc;
		c = ppq[ploc];
		if (2 * i < insPos){
			// if both are valid indexes, take minimum
			if ((2 * i) + 1 < insPos){
				leastChild = (ppq[2 * i].getPrice() <= ppq[(2 * i) + 1].getPrice()) ? ppq[2 * i] : ppq[(2 * i) + 1];
				leastChildIndex = (ppq[2 * i].getPrice() <= ppq[(2 * i) + 1].getPrice()) ? (2 * i) : ((2 * i) + 1);
			}  else {
				leastChild = ppq[2 * i];
				leastChildIndex = 2 * i;
			}
		}
		// while c can move down in ppq, keep swapping with min child
		if (leastChild != null){
			while ((2*i < insPos) && (c.getPrice() > leastChild.getPrice())){
				temp = leastChild;
				ppq[leastChildIndex] = c;
				ppq[i] = temp;
				// update indirection DLB for swapped car
				addPairDLB(ppq[i].getVIN(), i, 0, node);
				i = leastChildIndex;
				if (2 * i < insPos){
					// if both aren't null, take minimum
					if ((2 * i) + 1 < insPos){
						leastChild = (ppq[2 * i].getPrice() <= ppq[(2 * i) + 1].getPrice()) ? ppq[2 * i] : ppq[(2 * i) + 1];
						leastChildIndex = (ppq[2 * i].getPrice() <= ppq[(2 * i) + 1].getPrice()) ? (2 * i) : ((2 * i) + 1);
					}  else {
						leastChild = ppq[2 * i];
						leastChildIndex = 2 * i;
					}
				}
			}
		}
		// updates moved car's location value in the DLB
		if (ppq[i] != null) addPairDLB(ppq[i].getVIN(), i, 0, node);
	}

	/**
	 * Update the mileage attribute of a given car
	 * Should throw a `NoSuchElementException` if there is not car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param 	vin VIN number of the car to be updated
	 * @param	newMileage The updated mileage value
	 */
	public void updateMileage(String vin, int newMileage) throws NoSuchElementException{
		if (pricePQ[1] == null) throw new NoSuchElementException(vin + " not found");
		Car leastChild = null;
		Car temp;
		int leastChildIndex = 0;
		Car c = get(vin);
		Car save = c;
		c.setMileage(newMileage);
		// i = location of car c
		int i = lastmLoc;
		// while c can move up, keep swapping with parent
		while ((i > 1) && (c.getMileage() < mileagePQ[i/2].getMileage())){
			temp = mileagePQ[i/2];
			mileagePQ[i/2] = c;
			mileagePQ[i] = temp;
			// update indirection DLB for swapped car
			addDLB(mileagePQ[i].getVIN(), 0, i);
			i = i/2;
		}
		// updates c's location value in the DLB
		addDLB(c.getVIN(), 0, i);

		// j = location of car c
		int j = lastmLoc;
		// while c can move down, keep swapping with child
		if (2 * j < insertPosition){
			// if both aren't null, take minimum
			if ((2 * j) + 1 < insertPosition){
				leastChild = (mileagePQ[2 * j].getMileage() <= mileagePQ[(2 * j) + 1].getMileage()) ? mileagePQ[2 * j] : mileagePQ[(2 * j) + 1];
				leastChildIndex = (mileagePQ[2 * j].getMileage() <= mileagePQ[(2 * j) + 1].getMileage()) ? (2 * j) : ((2 * j) + 1);
			}  else {
				leastChild = mileagePQ[2 * j];
				leastChildIndex = 2 * j;
			}
		}
		// while c2 can move down in mileagePQ, keep swapping with min child
		if (leastChild != null){
			while ((2 * j < insertPosition) && (c.getMileage() > leastChild.getMileage())){
				temp = leastChild;
				mileagePQ[leastChildIndex] = c;
				mileagePQ[j] = temp;
				// update indirection DLB for swapped car
				addDLB(mileagePQ[j].getVIN(), 0, j);
				j = leastChildIndex;
				if (2 * j < insertPosition){
					// if both aren't null, take minimum 
					if ((2 * j) + 1 < insertPosition){
						leastChild = (mileagePQ[2 * j].getMileage() <= mileagePQ[(2 * j) + 1].getMileage()) ? mileagePQ[2 * j] : mileagePQ[(2 * j) + 1];
						leastChildIndex = (mileagePQ[2 * j].getMileage() <= mileagePQ[(2 * j) + 1].getMileage()) ? (2 * j) : ((2 * j) + 1);
					}  else {
						leastChild = mileagePQ[2 * j];
						leastChildIndex = 2 * j;
					}
				}
			}
		}
		if (mileagePQ[j] != null) addDLB(mileagePQ[j].getVIN(), 0, j);
		updatemmMileage(save);
    }

	/**
	 * Maintains the order in the mmMPQ
	 *
	 * @param 	c car to be updated in the mmMPQ
	 */
	private void updatemmMileage(Car c){
		DLBNode node = getmmNode(c.getMake() + ":" + c.getModel());
		Car[] mpq = node.getmmMileagePQ();
		DLBNode node2 = getmmPosNode(c.getVIN(), node.getpairRoot());
		int mloc = node2.getmLoc();
		Car leastChild = null;
		Car temp;
		int insPos = node.getInsPos();
		int leastChildIndex = 0;
		// i = location of car c in make & model mileage priority queue (mmmpq)
		int i = mloc;
		// while c can move up, keep swapping with parent
		while ((i > 1) && (c.getMileage() < mpq[i/2].getMileage())){
			temp = mpq[i/2];
			mpq[i/2] = c;
			mpq[i] = temp;
			// update indirection DLB for swapped car
			addPairDLB(mpq[i].getVIN(), 0, i, node);
			i = i/2;
		}
		// updates c's location value in the DLB
		addPairDLB(c.getVIN(), 0, i, node);

		i = mloc;
		c = mpq[mloc];
		if (2 * i < insPos){
			// if both are valid indexes, take minimum
			if ((2 * i) + 1 < insPos){
				leastChild = (mpq[2 * i].getMileage() <= mpq[(2 * i) + 1].getMileage()) ? mpq[2 * i] : mpq[(2 * i) + 1];
				leastChildIndex = (mpq[2 * i].getMileage() <= mpq[(2 * i) + 1].getMileage()) ? (2 * i) : ((2 * i) + 1);
			}  else {
				leastChild = mpq[2 * i];
				leastChildIndex = 2 * i;
			}
		}
		// while c can move down in ppq, keep swapping with min child
		if (leastChild != null){
			while ((2*i < insPos) && (c.getMileage() > leastChild.getMileage())){
				temp = leastChild;
				mpq[leastChildIndex] = c;
				mpq[i] = temp;
				// update indirection DLB for swapped car
				addPairDLB(mpq[i].getVIN(), 0, i, node);
				i = leastChildIndex;
				if (2 * i < insPos){
					// if both aren't null, take minimum
					if ((2 * i) + 1 < insPos){
						leastChild = (mpq[2 * i].getMileage() <= mpq[(2 * i) + 1].getMileage()) ? mpq[2 * i] : mpq[(2 * i) + 1];
						leastChildIndex = (mpq[2 * i].getMileage() <= mpq[(2 * i) + 1].getMileage()) ? (2 * i) : ((2 * i) + 1);
					}  else {
						leastChild = mpq[2 * i];
						leastChildIndex = 2 * i;
					}
				}
			}
		}
		// updates moved car's location value in the DLB
		if (mpq[i] != null) addPairDLB(mpq[i].getVIN(), 0, i, node);
	}

	/**
	 * Update the color attribute of a given car
	 * Should throw a `NoSuchElementException` if there is not car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param 	vin VIN number of the car to be updated
	 * @param	newColor The updated color value
	 */
	public void updateColor(String vin, String newColor) throws NoSuchElementException{
		Car c = get(vin);
		c.setColor(newColor);
    }

	/**
	 * Remove a car from the data structure
	 * Should throw a `NoSuchElementException` if there is not car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param 	vin VIN number of the car to be removed
	 */
	public void remove(String vin) throws NoSuchElementException{
		if (pricePQ[1] == null) throw new NoSuchElementException(vin + " not found");
		Car c = get(vin);
		Car save = c;
		Car c2;
		Car leastChild = null;
		Car temp;
		int leastChildIndex = 0;
		insertPosition--;
		// replace car to be removed with last car added
		pricePQ[lastpLoc] = pricePQ[insertPosition];
		// remove last car in pricePQ
		pricePQ[insertPosition] = null;
		// i = location of car c in pricePQ
		int i = lastpLoc;
		// j = location of car c in mileagePQ
		int j = lastmLoc;
		// remove c from the DLB
		addDLB(c.getVIN(), -1, -1);
		c = pricePQ[lastpLoc];
		if (2 * i < insertPosition){
			// if both are valid indexes, take minimum
			if ((2 * i) + 1 < insertPosition){
				leastChild = (pricePQ[2 * i].getPrice() <= pricePQ[(2 * i) + 1].getPrice()) ? pricePQ[2 * i] : pricePQ[(2 * i) + 1];
				leastChildIndex = (pricePQ[2 * i].getPrice() <= pricePQ[(2 * i) + 1].getPrice()) ? (2 * i) : ((2 * i) + 1);
			}  else {
				leastChild = pricePQ[2 * i];
				leastChildIndex = 2 * i;
			}
		}
		// while c can move down in pricePQ, keep swapping with min child
		while ((2*i < insertPosition) && (c.getPrice() > leastChild.getPrice())){
			temp = leastChild;
			pricePQ[leastChildIndex] = c;
			pricePQ[i] = temp;
			// update indirection DLB for swapped car
			addDLB(pricePQ[i].getVIN(), i, 0);
			i = leastChildIndex;
			if (2 * i < insertPosition){
				// if both aren't null, take minimum
				if ((2 * i) + 1 < insertPosition){
					leastChild = (pricePQ[2 * i].getPrice() <= pricePQ[(2 * i) + 1].getPrice()) ? pricePQ[2 * i] : pricePQ[(2 * i) + 1];
					leastChildIndex = (pricePQ[2 * i].getPrice() <= pricePQ[(2 * i) + 1].getPrice()) ? (2 * i) : ((2 * i) + 1);
				}  else {
					leastChild = pricePQ[2 * i];
					leastChildIndex = 2 * i;
				}
			}
		}
		// updates moved car's location value in the DLB
		if (pricePQ[i] != null) addDLB(pricePQ[i].getVIN(), i, 0);

		// replace car to be removed with last car added
		mileagePQ[lastmLoc] = mileagePQ[insertPosition];
		// remove last car in mileagePQ
		mileagePQ[insertPosition] = null;
		c2 = mileagePQ[lastmLoc];
		if (2 * j < insertPosition){
			// if both aren't null, take minimum
			if ((2 * j) + 1 < insertPosition){
				leastChild = (mileagePQ[2 * j].getMileage() <= mileagePQ[(2 * j) + 1].getMileage()) ? mileagePQ[2 * j] : mileagePQ[(2 * j) + 1];
				leastChildIndex = (mileagePQ[2 * j].getMileage() <= mileagePQ[(2 * j) + 1].getMileage()) ? (2 * j) : ((2 * j) + 1);
			}  else {
				leastChild = mileagePQ[2 * j];
				leastChildIndex = 2 * j;
			}
		}
		// while c2 can move down in mileagePQ, keep swapping with min child
		while ((j < (insertPosition/2)) && (c2.getMileage() > leastChild.getMileage())){
			temp = leastChild;
			mileagePQ[leastChildIndex] = c2;
			mileagePQ[j] = temp;
			// update indirection DLB for swapped car
			addDLB(mileagePQ[j].getVIN(), 0, j);
			j = leastChildIndex;
			if (2 * j < insertPosition){
				// if both aren't null, take minimum 
				if ((2 * j) + 1 < insertPosition){
					leastChild = (mileagePQ[2 * j].getMileage() <= mileagePQ[(2 * j) + 1].getMileage()) ? mileagePQ[2 * j] : mileagePQ[(2 * j) + 1];
					leastChildIndex = (mileagePQ[2 * j].getMileage() <= mileagePQ[(2 * j) + 1].getMileage()) ? (2 * j) : ((2 * j) + 1);
				}  else {
					leastChild = mileagePQ[2 * j];
					leastChildIndex = 2 * j;
				}
			}
		}
		if (mileagePQ[j] != null) addDLB(mileagePQ[j].getVIN(), 0, j);
        removemmPQs(save);
    }

	/**
	* Helper method for remove() to remove car from mm PQ's
	*
	* @param	c car to be removed
	*/
	private void removemmPQs(Car c){
		DLBNode node = getmmNode(c.getMake() + ":" + c.getModel());
		Car[] ppq = node.getmmPricePQ();
		Car[] mpq = node.getmmMileagePQ();
		// holds position of c in mm PQ's
		DLBNode node2 = getmmPosNode(c.getVIN(), node.getpairRoot());
		int insPos = node.getInsPos();
		// if we're removing the last element of the pq, just make it null
		if (insPos == 2){
			node.setmmPricePQ(null);
			node.setmmMileagePQ(null);
			node.setpairRoot(null);
			return;
		}
		// get the locations in the mm PQ's
		int ploc = node2.getpLoc();
		int mloc = node2.getmLoc();
		Car leastChild = null;
		Car temp;
		Car c2;
		int leastChildIndex = 0;
		node.setInsPos(insPos - 1);
		insPos--;
		// replace car to be removed with last car added
		ppq[ploc] = ppq[insPos];
		// remove last car in mm pricePQ
		ppq[insPos] = null;
		// i = location of car c in mm pricePQ
		int i = ploc;
		// j = location of car c in mm mileagePQ
		int j = mloc;
		// remove c from the DLB
		addPairDLB(c.getVIN(), -1, -1, node);
		c = ppq[ploc];
		if (2 * i < insPos){
			// if both are valid indexes, take minimum
			if ((2 * i) + 1 < insPos){
				leastChild = (ppq[2 * i].getPrice() <= ppq[(2 * i) + 1].getPrice()) ? ppq[2 * i] : ppq[(2 * i) + 1];
				leastChildIndex = (ppq[2 * i].getPrice() <= ppq[(2 * i) + 1].getPrice()) ? (2 * i) : ((2 * i) + 1);
			}  else {
				leastChild = ppq[2 * i];
				leastChildIndex = 2 * i;
			}
		}
		// while c can move down in mm pricePQ, keep swapping with min child
		while ((i < (insPos/2)) && (c.getPrice() > leastChild.getPrice())){
			temp = leastChild;
			ppq[leastChildIndex] = c;
			ppq[i] = temp;
			// update indirection DLB for swapped car
			addPairDLB(ppq[i].getVIN(), i, 0, node);
			i = leastChildIndex;
			if (2 * i < insPos){
				// if both aren't null, take minimum
				if ((2 * i) + 1 < insPos){
					leastChild = (ppq[2 * i].getPrice() <= ppq[(2 * i) + 1].getPrice()) ? ppq[2 * i] : ppq[(2 * i) + 1];
					leastChildIndex = (ppq[2 * i].getPrice() <= ppq[(2 * i) + 1].getPrice()) ? (2 * i) : ((2 * i) + 1);
				}  else {
					leastChild = ppq[2 * i];
					leastChildIndex = 2 * i;
				}
			}
		}
		// updates moved car's location value in the DLB
		if (ppq[i] != null) addPairDLB(ppq[i].getVIN(), i, 0, node);

		// replace car to be removed with last car added
		mpq[mloc] = mpq[insPos];
		// remove last car in mm mileagePQ
		mpq[insPos] = null;
		c2 = mpq[mloc];
		if (2 * j < insPos){
			// if both aren't null, take minimum
			if ((2 * j) + 1 < insPos){
				leastChild = (mpq[2 * j].getMileage() <= mpq[(2 * j) + 1].getMileage()) ? mpq[2 * j] : mpq[(2 * j) + 1];
				leastChildIndex = (mpq[2 * j].getMileage() <= mpq[(2 * j) + 1].getMileage()) ? (2 * j) : ((2 * j) + 1);
			}  else {
				leastChild = mpq[2 * j];
				leastChildIndex = 2 * j;
			}
		}
		// while c2 can move down in mm mileagePQ, keep swapping with min child
		while ((j < (insPos/2)) && (c2.getMileage() > leastChild.getMileage())){
			temp = leastChild;
			mpq[leastChildIndex] = c2;
			mpq[j] = temp;
			// update indirection DLB for swapped car
			addPairDLB(mpq[j].getVIN(), 0, j, node);
			j = leastChildIndex;
			if (2 * j < insPos){
				// if both aren't null, take minimum 
				if ((2 * j) + 1 < insPos){
					leastChild = (mpq[2 * j].getMileage() <= mpq[(2 * j) + 1].getMileage()) ? mpq[2 * j] : mpq[(2 * j) + 1];
					leastChildIndex = (mpq[2 * j].getMileage() <= mpq[(2 * j) + 1].getMileage()) ? (2 * j) : ((2 * j) + 1);
				}  else {
					leastChild = mpq[2 * j];
					leastChildIndex = 2 * j;
				}
			}
		}
		if (mpq[j] != null) addPairDLB(mpq[j].getVIN(), 0, j, node);
	}

	/**
	 * Get the lowest priced car (across all makes and models)
	 * Should return `null` if the data structure is empty
	 *
	 * @return	Car object representing the lowest priced car
	 */
	public Car getLowPrice(){
		if (pricePQ[1] == null) return null;
		return pricePQ[1];
    }

	/**
	 * Get the lowest priced car of a given make and model
	 * Should return `null` if the data structure is empty
	 *
	 * @param	make The specified make
	 * @param	model The specified model
	 * 
	 * @return	Car object representing the lowest priced car
	 */
	public Car getLowPrice(String make, String model){
		if (pricePQ[1] == null) return null;
		DLBNode node = getmmNode(make + ":" + model);
		Car[] ppq = node.getmmPricePQ();
        return ppq[1];
    }

	/**
	 * Get the car with the lowest mileage (across all makes and models)
	 * Should return `null` if the data structure is empty
	 *
	 * @return	Car object representing the lowest mileage car
	 */
	public Car getLowMileage(){
		if (mileagePQ[1] == null) return null;
		return mileagePQ[1];
    }

	/**
	 * Get the car with the lowest mileage of a given make and model
	 * Should return `null` if the data structure is empty
	 *
	 * @param	make The specified make
	 * @param	model The specified model
	 *
	 * @return	Car object representing the lowest mileage car
	 */
	public Car getLowMileage(String make, String model){
		if (mileagePQ[1] == null) return null;
        DLBNode node = getmmNode(make + ":" + model);
		Car[] mpq = node.getmmMileagePQ();
        return mpq[1];
    }
}