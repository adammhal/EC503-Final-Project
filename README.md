1. **Prepare Your Own Device To Receive A Laptop**
```bash
ssh -L 9000:localhost:9000 -L 4000:localhost:4000 USERNAME@scc1.bu.edu
```
Replace `USERNAME` with your BU login name. This command connects to the SCC and listens for ports 9000 and 4000, necessary to open the webpage.

2. **Navigate to Correct Folder**  
```bash
cd /projectnb/ec504/students/adammhal/finalProject/EC504-Final-Project/
```
In a **NEW** SCC window,  navigate to where the deliverables are stored so it can be run. 

3. **Run The `run.sh` Script**
```bash
./run.sh
```
The run.sh script kills any existing servers and runs RBServer.java on the ports opened earlier. This is necessary to host the script to see the website.

4. **Open the Webpage**
The script should print out a webpage it is being hosted on, from which you should open up the link provided in your browser. However, if it did not, then navigate to your browser and go to http://0.0.0.0:4000/. From there, the simulation should be up and running!
   
5. **To Validate RedBlackTree.java** (Through TreeSet)
```bash
cd cd /projectnb/ec504/students/adammhal/finalProject/EC504-Final-Project/
javac RedBlackTreeTest.java && java RedBlackTreeTest
```
RedBlackTreeTest compiles and runs a small harness that:
- Builds your RedBlackTree and Java’s built-in TreeSet (which is itself a proven Red-Black Tree) with the same sequence of values.
- After each insertion it compares the in-order traversal of the two structures to ensure the keys and ordering are identical.
- Runs a lightweight checker that verifies the Red-Black invariants (root black, no consecutive red nodes, equal black height across all paths).
- Executes a couple of positive/negative contains queries to confirm search correctness.
- Prints “All tests passed” (plus a JSON dump) if every assertion succeeds; otherwise it throws an assertion error pinpointing the mismatch.