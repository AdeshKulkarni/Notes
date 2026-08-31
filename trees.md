# 🌳 Trees Mastery — Foundation to Advanced

### Phase 4 of the Perfect DSA Path · Prerequisite: Recursion_Mastery.md

> **How to use this file**:
1. Read every section **top to bottom**. Do not jump.
2. When you see `📹 Course Video`, that is the exact video to watch in your premium course if you are stuck.
3. After each section’s theory, solve the practice problems in order.
4. **Do NOT move to the next level until the current one feels automatic.**
> 

---

---

# 🧱 PART 1 — THE FOUNDATION (Read This Before Anything Else)

---

## What Is a Tree?

A **tree** is a hierarchical data structure where:
- There is exactly **one root** (the top node)
- Every node except the root has **exactly one parent**
- Every node can have **zero or more children**
- There are **no cycles**

```
            1           ← root (depth 0)
          /   \
         2     3        ← depth 1
        / \     \
       4   5     6      ← depth 2
```

📹 **Course Video**: `Section 1 → Video 1: Trees Introduction` + `Video 2: TreeNode class`

---

## Why Do We Need Trees?

| Problem | Array / LinkedList | Tree |
| --- | --- | --- |
| Search in sorted data | O(n) linear | O(log n) — BST |
| Represent hierarchy (file system, org chart) | Impossible naturally | Natural fit |
| Expression parsing (`3 + 4 * 2`) | Ambiguous | Expression Tree |
| Autocomplete / Spell check | O(n × L) | Trie — O(L) per query |
| Database indexing | O(n) | B-Tree — O(log n) |

---

## Tree Terminology

| Term | Meaning |
| --- | --- |
| **Root** | Topmost node. Has no parent. |
| **Leaf** | A node with no children. |
| **Parent** | Node directly above. |
| **Child** | Node directly below. |
| **Depth of a node** | Number of edges from root to that node. Root depth = 0. |
| **Height of a node** | Longest path from that node down to any leaf. Leaf height = 0. |
| **Height of tree** | Height of the root. |
| **Subtree** | A node and all its descendants. |

---

## Types of Trees

```
Binary Tree  → each node has AT MOST 2 children (left and right)
BST          → Binary Tree where left < node < right for every node
Full BT      → every node has 0 or 2 children (never 1)
Complete BT  → all levels filled except possibly the last (filled left to right)
Perfect BT   → all internal nodes have 2 children, all leaves at same level
Degenerate   → every node has only 1 child (basically a linked list — worst BST)
```

---

## The Node Structure (Java)

```java
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}
```

📹 **Course Video**: `Section 1 → Video 2: TreeNode class`

---

## The Recursion-Tree Connection

Every array recursion you mastered looked like this:

```java
ReturnType solve(arr, index) {
    if (index == arr.length) return identity;
    // do something with arr[index]
    return solve(arr, index + 1);
}
```

**Tree recursion is EXACTLY the same — just two directions instead of one:**

```java
ReturnType solve(TreeNode node) {
    if (node == null) return identity;    // null = "past the end"
    // do something with node.val
    solve(node.left);                     // go left
    solve(node.right);                    // go right
}
```

**The only two things that changed:**
1. Base case is `node == null` instead of `index == arr.length`
2. You recurse in **two** directions (left AND right) instead of one

That is the entire conceptual leap. Everything else is just choosing when to process the node.

---

## The Universal Tree Template

```java
ReturnType solve(TreeNode node) {

    // GATE 1 — BASE CASE
    if (node == null) return IDENTITY_VALUE;

    // [PREORDER POSITION] — optional
    // Do something with node.val HERE if you need to process BEFORE going deeper

    ReturnType leftResult  = solve(node.left);

    // [INORDER POSITION] — optional
    // Do something with node.val HERE if you need it BETWEEN left and right

    ReturnType rightResult = solve(node.right);

    // [POSTORDER POSITION] — optional
    // Do something with node.val HERE after BOTH children returned

    return combine(leftResult, rightResult);
}
```

---

## Identity Values for Trees

| You are computing | null node returns |
| --- | --- |
| Height | `0` |
| Sum of values | `0` |
| Count of nodes | `0` |
| Min value | `Integer.MAX_VALUE` |
| Max value | `Integer.MIN_VALUE` |
| Is valid BST / Is sorted | `true` |
| Path exists | `false` |
| A node reference | `null` |

---

---

# 🚨 THE TRAVERSAL CONFUSION — SOLVED ONCE AND FOR ALL

> This is the section that resolves your confusion. Read it slowly. Read it twice.
> 

---

## Preorder vs Inorder vs Postorder — The Simple Truth

There is only **ONE rule** that determines which traversal to use:

```
At what moment do you need to USE the current node's data?

BEFORE going into children → PREORDER
BETWEEN left and right     → INORDER
AFTER both children return → POSTORDER
```

That’s it. Everything else follows from this.

---

## The Three Traversals — Side by Side

```
Tree:       1
           / \
          2   3
         / \
        4   5
```

| Traversal | When you process node | Output | Code shape |
| --- | --- | --- | --- |
| **Preorder** | BEFORE recursing | 1, 2, 4, 5, 3 | `process → left → right` |
| **Inorder** | BETWEEN left and right | 4, 2, 5, 1, 3 | `left → process → right` |
| **Postorder** | AFTER both children | 4, 5, 2, 3, 1 | `left → right → process` |

```java
// PREORDER: Root first, then children
void preorder(TreeNode node) {
    if (node == null) return;
    process(node.val);        // ← FIRST
    preorder(node.left);
    preorder(node.right);
}

// INORDER: Left first, then node, then right
void inorder(TreeNode node) {
    if (node == null) return;
    inorder(node.left);
    process(node.val);        // ← IN THE MIDDLE
    inorder(node.right);
}

// POSTORDER: Children first, then node
void postorder(TreeNode node) {
    if (node == null) return;
    postorder(node.left);
    postorder(node.right);
    process(node.val);        // ← LAST
}
```

📹 **Course Video**: `Section 2 → Video 7: Traversals in Binary Tree`

---

## THE MASTER TABLE — When to Use Which Traversal

This table is your permanent reference. Bookmark it mentally.

| Question type | Which traversal | Why |
| --- | --- | --- |
| **Print all nodes** | Any (preorder is most natural) | All nodes visited in all traversals |
| **Copy / clone a tree** | Preorder | You need to create the root BEFORE its children |
| **Serialize a tree** (tree → string to store) | Preorder | Root first = easy to reconstruct later |
| **Build a tree from preorder + inorder** | Preorder tells you the ROOT | See Pattern 6 |
| **BST: get sorted output** | Inorder | BST inorder = sorted ascending — this is the BST superpower |
| **BST: validate** | Inorder (or bounds passing down) | Check if output is sorted |
| **BST: kth smallest** | Inorder | Count during inorder traversal |
| **Delete a tree** | Postorder | Delete children before parent (or parent has no one to delete) |
| **Compute height / size / sum** | Postorder | You need BOTH children’s values BEFORE you can compute current node’s answer |
| **Diameter** | Postorder | Need left-height AND right-height before computing at current node |
| **Check if balanced** | Postorder | Need left-height AND right-height before checking balance at current node |
| **Path sum (root to leaf)** | Preorder | You carry info DOWN (subtract from target as you go deeper) |
| **LCA (lowest common ancestor)** | Postorder | Decision at node depends on what left and right subtrees RETURNED |
| **Flatten tree to linked list** | Postorder | Flatten children BEFORE flattening current node |
| **Level-order (BFS)** | None of the above | Uses a QUEUE, not recursion. Process level by level. |

---

## The Two Information Flows — Visualized

```
PREORDER (info flows DOWN):
  ┌─ root makes decision / carries value
  │      ↓             ↓
  └── passes to left   passes to right
  Example: Path sum (carry remaining target down), Count good nodes (carry max-so-far down)

POSTORDER (info flows UP):
  left returns something ─┐
                           ├─ root uses BOTH to compute its own answer → returns to grandparent
  right returns something ┘
  Example: Height, diameter, balanced, LCA, max path sum
```

---

## The Confusion About “Which is Harder”

**Preorder** questions are usually about passing something **downward** — simple.
**Postorder** questions are usually about combining two subtree results — slightly harder, but same template.
**Inorder** questions are almost always BST questions — because BST inorder is sorted.

If a question says “binary tree” (not BST), it’s almost never inorder.
If a question says “BST” + anything about sorted order or kth element, it’s inorder.

---

---

# 🗺️ THE SUB-PATTERNS MAP

```
TREES
│
├── PATTERN 1: DFS Traversals (preorder / inorder / postorder)
│
├── PATTERN 2: BFS / Level-Order (queue-based)
│
├── PATTERN 3: Path Sum (info flows DOWN via preorder)
│
├── PATTERN 4: Height / Diameter / Balance (info flows UP via postorder)
│
├── PATTERN 5: Lowest Common Ancestor (LCA)
│
├── PATTERN 6: Tree Construction / Serialization
│   (when do you use preorder vs inorder vs postorder to BUILD a tree)
│
└── PATTERN 7: Binary Search Tree (BST) — inorder = sorted
```

---

---

# 🟢 LEVEL 0 — Foundation Problems

> **Goal**: Get completely comfortable with tree recursion. You should be able to write the null-check + left + right skeleton in your sleep.
> 

📹 **Watch First**:
- `Section 1 → Video 3: Take Input and Print - Recursive`
- `Section 1 → Video 4: Take Input - Level wise`
- `Section 2 → Video 2: Take Tree Input and Print Recursively`
- `Section 2 → Video 3: Take Tree Input Iteratively`

---

## PATTERN 1 — DFS Traversals (The Three Orderings)

### Preorder — Recursive and Iterative

```java
// Recursive
void preorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    result.add(node.val);              // ROOT FIRST
    preorder(node.left, result);
    preorder(node.right, result);
}

// Iterative (stack — push right before left because LIFO)
List<Integer> preorderIterative(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(root);
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        result.add(node.val);
        if (node.right != null) stack.push(node.right);  // right pushed first
        if (node.left != null) stack.push(node.left);    // left comes out first
    }
    return result;
}
```

### Inorder — Recursive and Iterative

```java
// Recursive
void inorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    inorder(node.left, result);
    result.add(node.val);              // BETWEEN left and right
    inorder(node.right, result);
}

// Iterative (go left as far as possible, then process, then go right)
List<Integer> inorderIterative(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) { stack.push(curr); curr = curr.left; }
        curr = stack.pop();
        result.add(curr.val);
        curr = curr.right;
    }
    return result;
}
```

### Postorder — Recursive and Iterative

```java
// Recursive
void postorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    postorder(node.left, result);
    postorder(node.right, result);
    result.add(node.val);              // AFTER both children
}

// Iterative (reverse of root→right→left, then reverse the list)
List<Integer> postorderIterative(TreeNode root) {
    LinkedList<Integer> result = new LinkedList<>();
    if (root == null) return result;
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(root);
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        result.addFirst(node.val);     // insert at front = reverse order
        if (node.left != null) stack.push(node.left);
        if (node.right != null) stack.push(node.right);
    }
    return result;
}
```

📹 **Course Video**: `Section 2 → Video 7: Traversals in Binary Tree`

---

### Level 0 Foundation Code Problems

**F-01 · Count nodes** (postorder — need both sides first)

```java
int countNodes(TreeNode node) {
    if (node == null) return 0;
    return 1 + countNodes(node.left) + countNodes(node.right);
}
```

📹 **Course Video**: `Section 2 → Video 4: Count Nodes in Binary Tree`

**F-02 · Find height** (postorder — max of both children + 1)

```java
int height(TreeNode node) {
    if (node == null) return 0;
    int leftH = height(node.left);
    int rightH = height(node.right);
    return 1 + Math.max(leftH, rightH);
}
```

📹 **Course Video**: `Section 1 → Video 7: Find Height`

**F-03 · Sum of all values**

```java
int sumNodes(TreeNode node) {
    if (node == null) return 0;
    return node.val + sumNodes(node.left) + sumNodes(node.right);
}
```

**F-04 · Find maximum value**

```java
int maxNode(TreeNode node) {
    if (node == null) return Integer.MIN_VALUE;
    return Math.max(node.val, Math.max(maxNode(node.left), maxNode(node.right)));
}
```

📹 **Course Video**: `Section 1 → Video 6: Node with largest data`

**F-05 · Find depth/level of a node** (preorder — carry depth DOWN)

```java
int findDepth(TreeNode node, int target, int depth) {
    if (node == null) return -1;
    if (node.val == target) return depth;
    int left = findDepth(node.left, target, depth + 1);
    if (left != -1) return left;
    return findDepth(node.right, target, depth + 1);
}
// call: findDepth(root, target, 0)
```

📹 **Course Video**: `Section 1 → Video 8: Depth of a node`

**F-06 · Count leaf nodes** (postorder — check after recursing)

```java
int countLeaves(TreeNode node) {
    if (node == null) return 0;
    if (node.left == null && node.right == null) return 1;   // it IS a leaf
    return countLeaves(node.left) + countLeaves(node.right);
}
```

📹 **Course Video**: `Section 1 → Video 9: Hint Count leaf nodes`

**F-07 · Check if two trees are identical**

```java
boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null) return true;
    if (p == null || q == null) return false;
    if (p.val != q.val) return false;
    return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
}
```

**F-08 · Check if symmetric**

```java
boolean isSymmetric(TreeNode root) { return isMirror(root, root); }
boolean isMirror(TreeNode l, TreeNode r) {
    if (l == null && r == null) return true;
    if (l == null || r == null) return false;
    return (l.val == r.val) && isMirror(l.left, r.right) && isMirror(l.right, r.left);
}
```

---

### Level 0 · DFS Traversal — LeetCode Practice

**Easy — 3 problems**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| E1 | Binary Tree Inorder Traversal | #94 | `Sec 2 → Video 7: Traversals in BT` |
| E2 | Binary Tree Preorder Traversal | #144 | `Sec 2 → Video 7: Traversals in BT` |
| E3 | Binary Tree Postorder Traversal | #145 | `Sec 2 → Video 7: Traversals in BT` |

**Medium — 2 problems**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| M1 | Same Tree | #100 | No specific video — use F-07 template above |
| M2 | Symmetric Tree | #101 | No specific video — use F-08 template above |

---

## PATTERN 2 — BFS / Level-Order Traversal

### Core Idea

Level-order does NOT use recursion. It uses a **Queue** (FIFO) to process level by level.

```
Tree:       1              Level 0 → 1
           / \
          2   3            Level 1 → 2, 3
         / \
        4   5              Level 2 → 4, 5
```

**The Template — burn this into memory:**

```java
List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int levelSize = queue.size();           // snapshot: how many nodes at THIS level
        List<Integer> level = new ArrayList<>();

        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        result.add(level);
    }
    return result;
}
```

**Key**: `queue.size()` at the START of each iteration = count of nodes at current level.

📹 **Course Video**: `Section 1 → Video 4: Take Input - Level wise`
📹 **Also**: Section in advanced folder → `Video 10: Levelwise Input Binary Tree` + `Video 11: Print Levelwise Binary Tree`

---

### BFS Variants

**Variant A — Zigzag Level Order**

```java
// Alternate direction: even levels L→R, odd levels R→L
boolean leftToRight = true;
LinkedList<Integer> level = new LinkedList<>();

for (int i = 0; i < levelSize; i++) {
    TreeNode node = queue.poll();
    if (leftToRight) level.addLast(node.val);
    else level.addFirst(node.val);
    // ... add children
}
leftToRight = !leftToRight;
```

**Variant B — Right Side View**

```java
// Only the last node of each level
if (i == levelSize - 1) result.add(node.val);
```

---

### Level 0 · BFS — LeetCode Practice

**Easy — 3 problems**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| E1 | Binary Tree Level Order Traversal | #102 | `Section 1 → Video 4: Take Input - Level wise` |
| E2 | Maximum Depth of Binary Tree | #104 | `Section 1 → Video 7: Find Height` |
| E3 | Minimum Depth of Binary Tree | #111 | Use BFS — first leaf = minimum depth |

**Medium — 4 problems**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| M1 | Binary Tree Zigzag Level Order | #103 | Variant A above |
| M2 | Binary Tree Right Side View | #199 | Variant B above |
| M3 | Average of Levels in Binary Tree | #637 | Sum per level / levelSize |
| M4 | Maximum Width of Binary Tree | #662 | Index nodes: left=2i, right=2i+1 |

---

---

# 🟡 LEVEL 1 — Path Sum Patterns (Preorder — Info Flows DOWN)

> **Goal**: Learn how to carry information from root downward.
These problems use **preorder** style — you do work BEFORE going deeper.
You subtract from target, accumulate a path, or carry a running maximum.
> 

📹 **Watch First**: Advanced folder → `Video: Path Sum Root to Leaf` (4.0, 4.1, 4.2 videos)

---

## PATTERN 3 — Path Sum

### Why Preorder Here?

You need to know “how much of the target is still left” at each node. You compute this BEFORE going into children (you pass the updated value down). This is information flowing top → down = preorder.

---

### Template: Root-to-Leaf Path Sum

```java
boolean hasPathSum(TreeNode node, int target) {
    if (node == null) return false;

    // At a LEAF: remaining target must equal leaf's value
    if (node.left == null && node.right == null) {
        return node.val == target;
    }

    int remaining = target - node.val;     // CARRY DOWN: subtract current value
    return hasPathSum(node.left, remaining) || hasPathSum(node.right, remaining);
}
```

**Recognition signal**: “Does a root-to-leaf path sum to X?” → subtract and pass down → check at leaf.

---

### Variant A — All Root-to-Leaf Paths Matching Sum (Backtracking)

```java
void pathSumAll(TreeNode node, int target, List<Integer> current, List<List<Integer>> result) {
    if (node == null) return;

    current.add(node.val);                          // ADD to path (preorder)

    if (node.left == null && node.right == null && target == node.val) {
        result.add(new ArrayList<>(current));       // found a valid path
    }

    pathSumAll(node.left, target - node.val, current, result);
    pathSumAll(node.right, target - node.val, current, result);

    current.remove(current.size() - 1);            // REMOVE from path (backtrack)
}
```

This is the **exact same backtracking template** from your Recursion phase.

---

### Variant B — Maximum Path Sum (Any Node to Any Node) — The Hard One

This is the most important tree problem at this level. The path can go through ANY node.

```java
int maxSum = Integer.MIN_VALUE;   // global maximum

int maxPathHelper(TreeNode node) {
    if (node == null) return 0;

    // Don't take a negative contribution — take 0 instead
    int leftGain = Math.max(0, maxPathHelper(node.left));
    int rightGain = Math.max(0, maxPathHelper(node.right));

    // The BEST path going THROUGH this node uses both sides
    int pathThroughNode = node.val + leftGain + rightGain;
    maxSum = Math.max(maxSum, pathThroughNode);    // update global answer

    // What we RETURN to parent: can only pick ONE direction (not both — no fork allowed going up)
    return node.val + Math.max(leftGain, rightGain);
}
```

**THE KEY INSIGHT**: What you use to update the global answer (both sides) is DIFFERENT from what you return to parent (only one side). This is the “local answer vs return value” split that appears in all hard tree problems.

---

### Variant C — Count Paths with Target Sum (Any Node)

```java
// Use prefix sum: at every node, check if (currentSum - target) was seen before
int pathSumIII(TreeNode root, int targetSum) {
    Map<Long, Integer> prefixCount = new HashMap<>();
    prefixCount.put(0L, 1);
    return dfs(root, 0, targetSum, prefixCount);
}

int dfs(TreeNode node, long currSum, int target, Map<Long, Integer> map) {
    if (node == null) return 0;
    currSum += node.val;
    int count = map.getOrDefault(currSum - target, 0);  // how many prefixes give target
    map.put(currSum, map.getOrDefault(currSum, 0) + 1);
    count += dfs(node.left, currSum, target, map);
    count += dfs(node.right, currSum, target, map);
    map.put(currSum, map.get(currSum) - 1);             // backtrack
    return count;
}
```

---

### Level 1 · Path Sum — LeetCode Practice

**Easy — 2 problems**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| E1 | Path Sum | #112 | `Advanced → Video 4.0: Path Sum Root to Leaf` |
| E2 | Sum of Root To Leaf Binary Numbers | #1022 | Carry accumulated binary value down |

**Medium — 3 problems**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| M1 | Path Sum II | #113 | `Advanced → Video 4.1/4.2: Path Sum Solution` |
| M2 | Sum Root to Leaf Numbers | #129 | Pass (accumulated × 10 + digit) down |
| M3 | Path Sum III | #437 | Variant C above: prefix sum trick |

**Hard — 1 problem**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| H1 | Binary Tree Maximum Path Sum | #124 | Variant B above: local ≠ return |

---

---

# 🟡 LEVEL 2 — Height / Diameter / Balance (Postorder — Info Flows UP)

> **Goal**: Master the “postorder + global update” pattern — the most important advanced tree technique.
Information here flows UPWARD: children return their results, then the parent uses BOTH results.
> 

📹 **Watch First**:
- `Section 2 → Video 5: Diameter Binary Tree`
- `Section 2 → Video 6: Diameter Binary Tree Better`
- Advanced folder → `Video 4: Check If Binary Tree Is Balanced`
- Advanced folder → `Video 6: Check balanced improved - Explain`
- Advanced folder → `Video 7: Check Balanced - Improved`

---

## PATTERN 4 — Height / Diameter / Balance

### Why Postorder Here?

For height, diameter, and balance — you need the height of the LEFT subtree AND the height of the RIGHT subtree BEFORE you can compute anything at the current node. That means children go first → postorder.

---

### Height (The Foundation)

```java
int height(TreeNode node) {
    if (node == null) return 0;
    int leftH = height(node.left);      // LEFT first
    int rightH = height(node.right);    // RIGHT second
    return 1 + Math.max(leftH, rightH); // CURRENT last → postorder
}
```

---

### Diameter of Binary Tree

The diameter at any node = left height + right height (the path going THROUGH that node).
The overall diameter = max of all such values across all nodes.

```java
int diameter = 0;  // global answer

int diameterHelper(TreeNode node) {
    if (node == null) return 0;

    int leftH  = diameterHelper(node.left);
    int rightH = diameterHelper(node.right);

    diameter = Math.max(diameter, leftH + rightH);  // update global answer

    return 1 + Math.max(leftH, rightH);             // return HEIGHT to parent
}
```

**Notice**: The function’s return value (height) is DIFFERENT from what it contributes to the answer (diameter). Same split as max path sum.

📹 **Course Video**: `Section 2 → Video 5 and 6: Diameter Binary Tree (Better approach)`
📹 **Also**: Advanced folder → `Video 8: Diameter Of Binary Tree` + `Video 9.0, 9.1, 9.2`

---

### Is Balanced — The Sentinel Trick

```java
// Returns height if balanced, -1 if NOT balanced (use -1 as a "signal")
int checkBalance(TreeNode node) {
    if (node == null) return 0;

    int leftH = checkBalance(node.left);
    if (leftH == -1) return -1;          // already broken → pass -1 up

    int rightH = checkBalance(node.right);
    if (rightH == -1) return -1;

    if (Math.abs(leftH - rightH) > 1) return -1;   // unbalanced HERE

    return 1 + Math.max(leftH, rightH);  // balanced, return real height
}

boolean isBalanced(TreeNode root) {
    return checkBalance(root) != -1;
}
```

**Why -1?** It’s a “sentinel” — a special value that signals “something is wrong, pass it up, don’t compute further.” This avoids needing a global flag.

📹 **Course Video**: Advanced folder → `Video 4: Check If Binary Tree Is Balanced` + `Video 6 and 7: Check Balanced Improved`

---

### Mirror Binary Tree

```java
// Check if mirror (symmetric):
boolean isMirror(TreeNode left, TreeNode right) {
    if (left == null && right == null) return true;
    if (left == null || right == null) return false;
    return (left.val == right.val)
        && isMirror(left.left, right.right)
        && isMirror(left.right, right.left);
}

// Create mirror (flip the tree — swap left and right at every node):
void makeMirror(TreeNode node) {
    if (node == null) return;
    makeMirror(node.left);                 // postorder: fix children first
    makeMirror(node.right);
    TreeNode temp = node.left;
    node.left = node.right;
    node.right = temp;
}
```

📹 **Course Video**: Advanced folder → `Video 3.0: Mirror Binary Tree` + `3.1, 3.2 Solution videos`

---

### Level 2 · Height / Diameter / Balance — LeetCode Practice

**Easy — 2 problems**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| E1 | Maximum Depth of Binary Tree | #104 | `Sec 1 → Video 7: Find Height` |
| E2 | Diameter of Binary Tree | #543 | `Sec 2 → Video 5/6: Diameter BT Better` |

**Medium — 4 problems**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| M1 | Balanced Binary Tree | #110 | `Advanced → Video 4, 6, 7: Check Balanced` |
| M2 | Symmetric Tree | #101 | `Advanced → Video 3.0: Mirror Binary Tree` |
| M3 | Count Good Nodes in Binary Tree | #1448 | Pass max-so-far DOWN (preorder style) |
| M4 | Longest ZigZag Path in a Binary Tree | #1372 | Return direction + length from each subtree |

**Hard — 1 problem**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| H1 | Binary Tree Maximum Path Sum | #124 | Local answer ≠ return value — see Level 1 Variant B |

---

---

# 🟠 LEVEL 3 — Lowest Common Ancestor (LCA)

> **Goal**: Master LCA — one of the most tested tree algorithms.
Uses postorder: both subtrees report what they found, then the current node decides.
> 

---

## PATTERN 5 — Lowest Common Ancestor

### What Is LCA?

The **Lowest Common Ancestor** of nodes p and q = the deepest node that has BOTH p and q as descendants.

```
          3
         / \
        5   1
       / \ / \
      6  2 0  8
        / \
       7   4

LCA(5, 1) = 3     → p and q are in different subtrees of 3
LCA(5, 4) = 5     → 5 is an ancestor of 4; so 5 itself is the LCA
LCA(7, 4) = 2
```

---

### The Algorithm

```java
TreeNode lca(TreeNode node, TreeNode p, TreeNode q) {
    if (node == null) return null;      // not found here
    if (node == p || node == q) return node;  // found one of them

    TreeNode left  = lca(node.left, p, q);
    TreeNode right = lca(node.right, p, q);

    if (left != null && right != null) return node;   // p in one side, q in other → THIS is LCA
    return (left != null) ? left : right;             // both in same side → pass up the non-null
}
```

**Why does this work?**
- If both left and right return non-null: p is in one subtree, q is in the other → this node is the LCA.
- If only one returns non-null: both p and q are in the same subtree → bubble that result up.

---

### LCA in BST (Simpler — Use the Ordering)

```java
TreeNode lcaBST(TreeNode node, TreeNode p, TreeNode q) {
    if (node == null) return null;
    if (p.val < node.val && q.val < node.val) return lcaBST(node.left, p, q);   // both left
    if (p.val > node.val && q.val > node.val) return lcaBST(node.right, p, q);  // both right
    return node;  // one on each side → this IS the LCA
}
```

---

### Print Nodes at Distance K from a Given Node

This is a classic LCA-adjacent problem from the course.

```java
// Two-part approach:
// Part 1: Print nodes at distance k BELOW the target node (simple recursion)
void printKBelow(TreeNode node, int k) {
    if (node == null || k < 0) return;
    if (k == 0) { System.out.println(node.val); return; }
    printKBelow(node.left, k - 1);
    printKBelow(node.right, k - 1);
}

// Part 2: Use postorder to find target, then print k-distant from ancestors too
// (see course video for full solution)
```

📹 **Course Video**: Advanced folder → `Video 5.0, 5.1, 5.2: Print nodes at distance k from node`

---

### Level 3 · LCA — LeetCode Practice

**Easy — 1 problem**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| E1 | LCA of a Binary Search Tree | #235 | Use BST property — simpler version above |

**Medium — 4 problems**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| M1 | LCA of Binary Tree | #236 | Core LCA algorithm above |
| M2 | All Nodes Distance K in Binary Tree | #863 | `Advanced → Video 5.0: Print nodes at dist k` |
| M3 | Smallest Subtree with Deepest Nodes | #865 | LCA of all deepest nodes |
| M4 | Maximum Difference Between Node and Ancestor | #1026 | Pass min/max from ancestor down (preorder) |

**Hard — 1 problem**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| H1 | LCA of Deepest Leaves | #1123 | Return (depth, lca_node) pair from postorder |

---

---

# 🟠 LEVEL 4 — Tree Construction / Serialization

> **Goal**: Learn to BUILD a tree from traversal arrays, and to encode/decode a tree.
This is where the traversal confusion usually hits hardest — this section will fix it permanently.
> 

📹 **Watch First**:
- Advanced folder → `Video 13: Build Tree Using Inorder And Preorder`
- Advanced folder → `Video 14: Inorder Prorder Postorder` (the confusion-clearing video)
- Advanced folder → `Video 15, 15.1, 16: Construct Tree Using Inorder and Preorder (Solution + Code)`
- Advanced folder → `Video 17.0, 17.1: Construct Tree Using Inorder and PostOrder`
- `Section 2 → Video 8: Construct Tree From Preorder and Inorder`
- `Section 2 → Video 9: Construct Tree From Preorder and Inorder - Solution`

---

## PATTERN 6 — Construction / Serialization

### What Each Traversal Gives You When READING Arrays

This is the table that kills the confusion:

| If you have | What it tells you |
| --- | --- |
| **Preorder array** | The FIRST element is ALWAYS the current root |
| **Postorder array** | The LAST element is ALWAYS the current root |
| **Inorder array** | Once you know the root (from preorder/postorder): everything LEFT of root in inorder = left subtree. Everything RIGHT of root in inorder = right subtree. |

**Why you need TWO arrays to reconstruct a tree:**
- Preorder alone: you know the root, but can’t tell where the left subtree ends
- Inorder alone: you can split left/right, but don’t know which element is the root
- Together: preorder gives you the root → use inorder to split left and right

---

### Visualized: Build from Preorder [3,9,20,15,7] + Inorder [9,3,15,20,7]

```
Preorder: [3, 9, 20, 15, 7]
           ↑
           Root = 3

Inorder:  [9, 3, 15, 20, 7]
               ↑
               Find 3 → left of 3 = {9} = left subtree
                          right of 3 = {15,20,7} = right subtree

Left subtree: preorder [9], inorder [9] → root = 9, no children
Right subtree: preorder [20,15,7], inorder [15,20,7]
               → root = 20, left = {15}, right = {7}

Final tree:
        3
       / \
      9   20
         /  \
        15    7
```

---

### Code: Build from Preorder + Inorder

```java
TreeNode buildTree(int[] preorder, int[] inorder) {
    Map<Integer, Integer> inMap = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) inMap.put(inorder[i], i);
    return helper(preorder, 0, preorder.length - 1, 0, inorder.length - 1, inMap);
}

TreeNode helper(int[] pre, int preL, int preR, int inL, int inR, Map<Integer, Integer> inMap) {
    if (preL > preR) return null;

    int rootVal = pre[preL];                  // ROOT = first in preorder range
    TreeNode root = new TreeNode(rootVal);

    int inRootIdx = inMap.get(rootVal);       // find root in inorder
    int leftSize = inRootIdx - inL;           // how many nodes are in left subtree

    root.left  = helper(pre, preL + 1, preL + leftSize, inL, inRootIdx - 1, inMap);
    root.right = helper(pre, preL + leftSize + 1, preR, inRootIdx + 1, inR, inMap);

    return root;
}
```

📹 **Course Video**: `Section 2 → Video 8 and 9: Construct Tree From Preorder and Inorder`

---

### Code: Build from Inorder + Postorder

```java
TreeNode buildTreePost(int[] inorder, int[] postorder) {
    Map<Integer, Integer> inMap = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) inMap.put(inorder[i], i);
    return helperPost(postorder, 0, postorder.length - 1, 0, inorder.length - 1, inMap);
}

TreeNode helperPost(int[] post, int postL, int postR, int inL, int inR, Map<Integer, Integer> inMap) {
    if (postL > postR) return null;

    int rootVal = post[postR];                // ROOT = LAST in postorder range
    TreeNode root = new TreeNode(rootVal);

    int inRootIdx = inMap.get(rootVal);
    int leftSize = inRootIdx - inL;

    root.left  = helperPost(post, postL, postL + leftSize - 1, inL, inRootIdx - 1, inMap);
    root.right = helperPost(post, postL + leftSize, postR - 1, inRootIdx + 1, inR, inMap);

    return root;
}
```

📹 **Course Video**: Advanced folder → `Video 17.0, 17.1: Construct Tree Using Inorder and PostOrder`

---

### Code: Serialize + Deserialize (Preorder with Null Markers)

```java
// WHY PREORDER? Root comes first → when deserializing, first element is always root → natural

String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    serHelper(root, sb);
    return sb.toString();
}

void serHelper(TreeNode node, StringBuilder sb) {
    if (node == null) { sb.append("#,"); return; }
    sb.append(node.val).append(",");
    serHelper(node.left, sb);
    serHelper(node.right, sb);
}

TreeNode deserialize(String data) {
    Queue<String> q = new LinkedList<>(Arrays.asList(data.split(",")));
    return desHelper(q);
}

TreeNode desHelper(Queue<String> q) {
    String val = q.poll();
    if (val.equals("#")) return null;
    TreeNode node = new TreeNode(Integer.parseInt(val));
    node.left = desHelper(q);
    node.right = desHelper(q);
    return node;
}
```

---

### Find Duplicate Subtrees

```java
// Postorder serialize each subtree → if same serialization seen twice → duplicate
Map<String, Integer> seen = new HashMap<>();
List<TreeNode> result = new ArrayList<>();

String findDup(TreeNode node) {
    if (node == null) return "#";
    String left = findDup(node.left);
    String right = findDup(node.right);
    String serial = left + "," + right + "," + node.val;  // postorder: children first
    seen.put(serial, seen.getOrDefault(serial, 0) + 1);
    if (seen.get(serial) == 2) result.add(node);
    return serial;
}
```

---

### Level 4 · Construction / Serialization — LeetCode Practice

**Medium — 3 problems**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| M1 | Construct BT from Preorder + Inorder | #105 | `Sec 2 → Video 8/9` + `Advanced → Video 15/16` |
| M2 | Construct BT from Inorder + Postorder | #106 | `Advanced → Video 17.0/17.1` |
| M3 | Find Duplicate Subtrees | #652 | Postorder serialization into map |

**Hard — 2 problems**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| H1 | Serialize and Deserialize Binary Tree | #297 | Preorder with “#” for null nodes |
| H2 | Recover Binary Search Tree | #99 | Inorder should be sorted in BST; two nodes swapped |

---

---

# 🔴 LEVEL 5 — Binary Search Tree (BST)

> **Goal**: Master BST by exploiting its ordering property.
**The #1 BST fact**: Inorder traversal of a BST gives sorted ascending output.
Every BST problem either exploits the ordering to go left/right, OR exploits the inorder = sorted property.
> 

📹 **Watch First**: Advanced folder → BST section videos (Create, Insert, Min/Max, Level order, Path sum)

---

## PATTERN 7 — Binary Search Tree

### The BST Property

```
        5
       / \
      3   7
     / \ / \
    2  4 6   9

Rule: for every node N, ALL values in left subtree < N.val < ALL values in right subtree
Inorder: 2, 3, 4, 5, 6, 7, 9   ← SORTED. Always.
```

---

### Search

```java
TreeNode search(TreeNode node, int target) {
    if (node == null) return null;             // not found
    if (node.val == target) return node;
    if (target < node.val) return search(node.left, target);   // must be in left
    else return search(node.right, target);                    // must be in right
}
```

📹 **Course Video**: Advanced → `Video 1.0: Create and Insert Duplicate Node` (covers insert logic)

---

### Insert

```java
TreeNode insert(TreeNode node, int val) {
    if (node == null) return new TreeNode(val);  // found the right spot
    if (val < node.val) node.left = insert(node.left, val);
    else if (val > node.val) node.right = insert(node.right, val);
    return node;
}
```

---

### Delete (3 Cases)

```java
TreeNode delete(TreeNode node, int key) {
    if (node == null) return null;

    if (key < node.val) { node.left = delete(node.left, key); }
    else if (key > node.val) { node.right = delete(node.right, key); }
    else {
        // FOUND the node — 3 cases:
        if (node.left == null && node.right == null) return null;  // leaf
        if (node.left == null) return node.right;                  // one child
        if (node.right == null) return node.left;                  // one child

        // Two children: replace with inorder successor (smallest in right subtree)
        TreeNode succ = findMin(node.right);
        node.val = succ.val;
        node.right = delete(node.right, succ.val);
    }
    return node;
}

TreeNode findMin(TreeNode node) {
    while (node.left != null) node = node.left;
    return node;
}
```

---

### Validate BST — The Classic Trap

```java
// WRONG: just checking node > left.val and node < right.val
// This fails for:
//        5
//       / \
//      1   4
//         / \
//        3   6
// Node 4 has right child 6, which is > 5 (the ancestor). Should be invalid. But local check passes it.

// CORRECT: pass a valid range [min, max] down
boolean isValidBST(TreeNode node, long min, long max) {
    if (node == null) return true;
    if (node.val <= min || node.val >= max) return false;
    return isValidBST(node.left, min, node.val)     // left must be in (min, node.val)
        && isValidBST(node.right, node.val, max);   // right must be in (node.val, max)
}
// call: isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE)
```

---

### Kth Smallest in BST

```java
// Inorder = sorted → kth element in inorder = kth smallest
int[] counter = {0}, result = {0};

void kthSmallest(TreeNode node, int k) {
    if (node == null) return;
    kthSmallest(node.left, k);    // go left first (smaller values)
    counter[0]++;
    if (counter[0] == k) { result[0] = node.val; return; }
    kthSmallest(node.right, k);
}
```

---

### Minimum and Maximum in BST

```java
// Minimum: go left as far as possible
int findMin(TreeNode node) {
    if (node.left == null) return node.val;
    return findMin(node.left);
}

// Maximum: go right as far as possible
int findMax(TreeNode node) {
    if (node.right == null) return node.val;
    return findMax(node.right);
}
```

📹 **Course Video**: Advanced → `Video 2.0, 2.1: Minimum and Maximum in the Binary Tree`

---

### Level 5 · BST — LeetCode Practice

**Easy — 3 problems**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| E1 | Search in a Binary Search Tree | #700 | Go left or right based on comparison |
| E2 | Insert into a Binary Search Tree | #701 | Find null spot, insert |
| E3 | Range Sum of BST | #938 | Prune whole subtrees outside [low, high] |

**Medium — 5 problems**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| M1 | Validate Binary Search Tree | #98 | Pass min/max bounds down |
| M2 | Kth Smallest Element in BST | #230 | Inorder = sorted; count k |
| M3 | Convert Sorted Array to BST | #108 | Mid = root, recurse left/right halves |
| M4 | Delete Node in a BST | #450 | 3 cases: leaf, one child, two children |
| M5 | BST Iterator | #173 | Inorder lazy evaluation with stack |

**Hard — 1 problem**

| # | Problem | LeetCode # | Video if stuck |
| --- | --- | --- | --- |
| H1 | Recover Binary Search Tree | #99 | Inorder must be sorted; find 2 swapped nodes |

---

---

# 📊 COMPLETE QUESTION SUMMARY

## Questions by Sub-Pattern

| Pattern | Level | Easy | Medium | Hard | Total |
| --- | --- | --- | --- | --- | --- |
| DFS Traversals | 0 | 3 | 2 | 0 | **5** |
| BFS / Level-Order | 0 | 3 | 4 | 0 | **7** |
| Path Sum | 1 | 2 | 3 | 1 | **6** |
| Diameter / Height / Balance | 2 | 2 | 4 | 1 | **7** |
| LCA | 3 | 1 | 4 | 1 | **6** |
| Construction / Serialization | 4 | 0 | 3 | 2 | **5** |
| BST | 5 | 3 | 5 | 1 | **9** |
| **TOTAL** |  | **14** | **25** | **6** | **45** |

---

## Master Problem List (Sequential Order)

### 🟢 LEVEL 0 — DFS Traversals

| # | Problem | Diff | LeetCode | Course Video If Stuck |
| --- | --- | --- | --- | --- |
| 1 | Binary Tree Inorder Traversal | Easy | #94 | Sec 2 → V7: Traversals in BT |
| 2 | Binary Tree Preorder Traversal | Easy | #144 | Sec 2 → V7: Traversals in BT |
| 3 | Binary Tree Postorder Traversal | Easy | #145 | Sec 2 → V7: Traversals in BT |
| 4 | Same Tree | Med | #100 | F-07 template in this file |
| 5 | Symmetric Tree | Med | #101 | F-08 template in this file |

### 🟢 LEVEL 0 — BFS / Level-Order

| # | Problem | Diff | LeetCode | Course Video If Stuck |
| --- | --- | --- | --- | --- |
| 6 | Binary Tree Level Order Traversal | Easy | #102 | Sec 1 → V4: Take Input Level wise |
| 7 | Maximum Depth of Binary Tree | Easy | #104 | Sec 1 → V7: Find Height |
| 8 | Minimum Depth of Binary Tree | Easy | #111 | BFS: first leaf = min depth |
| 9 | Binary Tree Zigzag Level Order | Med | #103 | BFS Variant A in this file |
| 10 | Binary Tree Right Side View | Med | #199 | BFS Variant B in this file |
| 11 | Average of Levels in Binary Tree | Med | #637 | Sum / levelSize per level |
| 12 | Maximum Width of Binary Tree | Med | #662 | Index nodes: left=2i right=2i+1 |

### 🟡 LEVEL 1 — Path Sum

| # | Problem | Diff | LeetCode | Course Video If Stuck |
| --- | --- | --- | --- | --- |
| 13 | Path Sum | Easy | #112 | Advanced → V4.0: Path Sum Root to Leaf |
| 14 | Sum Root To Leaf Binary Numbers | Easy | #1022 | Pass accumulated binary value down |
| 15 | Path Sum II | Med | #113 | Advanced → V4.1/4.2: Path Sum Solution |
| 16 | Sum Root to Leaf Numbers | Med | #129 | Carry (sum×10 + digit) down |
| 17 | Path Sum III | Med | #437 | Prefix sum trick (Variant C) |
| 18 | Binary Tree Maximum Path Sum | Hard | #124 | Local ≠ return (Variant B) |

### 🟡 LEVEL 2 — Height / Diameter / Balance

| # | Problem | Diff | LeetCode | Course Video If Stuck |
| --- | --- | --- | --- | --- |
| 19 | Maximum Depth of Binary Tree | Easy | #104 | Sec 1 → V7: Find Height |
| 20 | Diameter of Binary Tree | Easy | #543 | Sec 2 → V5/V6: Diameter BT Better |
| 21 | Balanced Binary Tree | Med | #110 | Advanced → V4, V6, V7: Check Balanced |
| 22 | Symmetric Tree | Med | #101 | Advanced → V3.0: Mirror Binary Tree |
| 23 | Count Good Nodes in Binary Tree | Med | #1448 | Pass max-so-far down |
| 24 | Longest ZigZag Path in BT | Med | #1372 | Return direction + length |
| 25 | Binary Tree Maximum Path Sum | Hard | #124 | See Level 1 H1 above |

### 🟠 LEVEL 3 — LCA

| # | Problem | Diff | LeetCode | Course Video If Stuck |
| --- | --- | --- | --- | --- |
| 26 | LCA of BST | Easy | #235 | BST version in this file |
| 27 | LCA of Binary Tree | Med | #236 | Core LCA algorithm in this file |
| 28 | All Nodes Distance K in BT | Med | #863 | Advanced → V5.0: Print nodes at dist k |
| 29 | Smallest Subtree with Deepest Nodes | Med | #865 | LCA of all deepest nodes |
| 30 | Max Difference Between Node and Ancestor | Med | #1026 | Pass min/max ancestor down |
| 31 | LCA of Deepest Leaves | Hard | #1123 | Return (depth, lca) pair |

### 🟠 LEVEL 4 — Construction / Serialization

| # | Problem | Diff | LeetCode | Course Video If Stuck |
| --- | --- | --- | --- | --- |
| 32 | Build BT from Preorder + Inorder | Med | #105 | Sec 2 → V8/9 + Advanced → V15/16 |
| 33 | Build BT from Inorder + Postorder | Med | #106 | Advanced → V17.0/17.1 |
| 34 | Find Duplicate Subtrees | Med | #652 | Postorder serialize subtrees |
| 35 | Serialize and Deserialize BT | Hard | #297 | Preorder with # for null |
| 36 | Recover Binary Search Tree | Hard | #99 | Inorder must be sorted in BST |

### 🔴 LEVEL 5 — BST

| # | Problem | Diff | LeetCode | Course Video If Stuck |
| --- | --- | --- | --- | --- |
| 37 | Search in BST | Easy | #700 | Go left or right |
| 38 | Insert into BST | Easy | #701 | Advanced → V1.0: Create & Insert |
| 39 | Range Sum of BST | Easy | #938 | Prune subtrees outside range |
| 40 | Validate BST | Med | #98 | Pass min/max bounds down |
| 41 | Kth Smallest in BST | Med | #230 | Inorder = sorted; count k |
| 42 | Convert Sorted Array to BST | Med | #108 | Mid = root |
| 43 | Delete Node in BST | Med | #450 | 3 delete cases |
| 44 | BST Iterator | Med | #173 | Lazy inorder with stack |
| 45 | Recover Binary Search Tree | Hard | #99 | Advanced → V17 |

---

---

# 🧭 THE RECOGNITION GUIDE — Which Pattern Is This?

When you see a new tree problem, ask these questions in order:

```
STEP 1: Is it a BST (does the problem say sorted property / BST)?
    YES → use BST pattern (Pattern 7)
           Does it need sorted output or kth smallest? → inorder
           Can you prune subtrees by going left/right? → BST search/insert/delete

STEP 2: Does it say "level by level" or "level order" or "breadth-first"?
    YES → BFS with queue (Pattern 2)

STEP 3: Does the answer depend only on what comes BELOW the current node?
    (height, diameter, sum of subtree, is balanced, LCA)
    YES → postorder (Pattern 4 or Pattern 5)
           children return → parent combines

STEP 4: Does it carry information FROM THE ROOT downward?
    (path sum, count good nodes, is node present, pass max-so-far)
    YES → preorder style (Pattern 3)
           parent passes updated value down to children

STEP 5: Does it involve BUILDING a tree from arrays?
    YES → Pattern 6
           Preorder/Postorder gives root, Inorder splits left/right

STEP 6: Do you just need to visit every node in some order?
    YES → DFS traversal (Pattern 1)
```

---

## The 3 Rules of Tree Recursion

> **Rule 1 — Trust the recursion.** Your function returns the correct answer for any subtree. Don’t trace in your head. Just define what it returns and use that.
> 

> **Rule 2 — Null is your base case.** Almost every tree function starts with `if (node == null) return IDENTITY`. Know your identity values table.
> 

> **Rule 3 — Local answer vs return value.** In hard problems (diameter, max path sum, LCA), what you update the global answer with is DIFFERENT from what you return to parent. When you see a mismatch, this is the pattern.
> 

---

## The Traversal Confusion — Final Summary Card

```
USE PREORDER when:
  • You need to process/copy/serialize a node BEFORE its children
  • You are carrying info from root DOWN (path sum, good nodes, depth)
  • You are printing/cloning the tree

USE INORDER when:
  • You are dealing with a BST and need sorted order
  • Kth smallest/largest in BST
  • Validating a BST (inorder must be strictly increasing)

USE POSTORDER when:
  • You need BOTH subtree results BEFORE processing current node
  • Height, size, sum computations
  • Diameter, balance, max path sum
  • LCA (need to know what came back from left and right)
  • Deleting or transforming a tree (children first)

USE BFS (queue) when:
  • Level-by-level processing
  • Shortest path in unweighted tree
  • Right/left side view
  • Minimum depth
```

---

## Trees → Graphs: The Bridge

When you finish all 45 problems and start Graphs (Phase 7):
- Graphs = trees but cycles are allowed → you need a `visited` set
- BFS on graph = same queue template you already know
- DFS on graph = same recursive pattern you already know
- The only new thing is: mark nodes visited before recursing to avoid infinite loops

Everything transfers directly.

---

*File: `Trees_Mastery.md` · Phase 4 of the Perfect DSA PathPrerequisite: `Recursion_Mastery.md` | Next: `Trie_Mastery.md`*