# EC504 Final Project - Adam, Alex, Rita

Instructions for Running the Code

1. **Load source on SCC**

```bash
cd ~/FinalProject/src
javac RedBlackTree.java RBServer.java
```

2. **Start the server** (default port 8080)

```bash
java RBServer
```

Console prints: Server listening on http://localhost:8080

3. **Open the interface**
   Open ~/FinalProject/src/index.html in a browser (or use the URL if port-forwarded).

4. **To Validate RedBlackTree.java** (Through TreeSet)

```bash
javac RedBlackTreeTest.java && java RedBlackTreeTest
```

RedBlackTreeTest compiles and runs a small harness that:

- Builds your RedBlackTree and Java’s built-in TreeSet (which is itself a proven Red-Black Tree) with the same sequence of values.
- After each insertion it compares the in-order traversal of the two structures to ensure the keys and ordering are identical.
- Runs a lightweight checker that verifies the Red-Black invariants (root black, no consecutive red nodes, equal black height across all paths).
- Executes a couple of positive/negative contains queries to confirm search correctness.
- Prints “All tests passed” (plus a JSON dump) if every assertion succeeds; otherwise it throws an assertion error pinpointing the mismatch.
