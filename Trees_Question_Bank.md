# 🌳 Trees — Company-Targeted Question Bank
**For**: Adesh | Target: Walmart · PhonePe · Razorpay · Groww · Meesho | ₹20+ LPA Jan–Feb 2027

> **How to use this file**:
> - Questions are grouped by company priority (highest-frequency first).
> - Each question shows: pattern label, difficulty, and which companies ask it.
> - No solutions here. Just problems + pattern hint. Solve, then verify on LeetCode.
> - If a question appears at 3+ companies → solve it **before anything else**.
> - Do NOT skip to a harder tier until the easier tier feels automatic.

---

> [!IMPORTANT]
> **Trees frequency across your target companies:**
> - Walmart: 68% 🔴 CRITICAL
> - Groww: 65% 🔴 CRITICAL
> - Meesho: 60% 🟠 HIGH
> - Razorpay: 60% 🟠 HIGH
> - PhonePe: 55% 🟠 HIGH
>
> Trees is a HIGH-PRIORITY topic for ALL 5 companies. A strong trees performance can compensate
> for a weaker performance on other topics. Do not under-prepare this.

---

---

## 🔴 TIER 0 — Multi-Company Repeaters (Solve These FIRST — Maximum ROI)

> These questions appear at **3 or more** of your 5 target companies.
> One question, multiple companies covered. Highest ROI of anything in this file.

| # | Problem | LeetCode # | Pattern (Brief) | Companies | Difficulty |
|---|---------|-----------|----------------|-----------|------------|
| 1 | Binary Tree Level Order Traversal | [#102](https://leetcode.com/problems/binary-tree-level-order-traversal/) | BFS + queue, snapshot level size at loop start | Walmart (38%), Groww, Meesho | Medium |
| 2 | Binary Tree Right Side View | [#199](https://leetcode.com/problems/binary-tree-right-side-view/) | BFS, take last node of each level | Walmart (52%), Meesho, Groww | Medium |
| 3 | Binary Tree Maximum Path Sum | [#124](https://leetcode.com/problems/binary-tree-maximum-path-sum/) | Postorder + global max; local answer ≠ return value | Razorpay (40%), Groww, PhonePe | Hard |
| 4 | Validate Binary Search Tree | [#98](https://leetcode.com/problems/validate-binary-search-tree/) | Pass min/max bounds down; do NOT just check neighbors | Walmart, Razorpay, Groww | Medium |
| 5 | Lowest Common Ancestor of Binary Tree | [#236](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) | Postorder; if both subtrees return non-null → current is LCA | Walmart, PhonePe, Razorpay, Groww | Medium |
| 6 | Serialize and Deserialize Binary Tree | [#297](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/) | Preorder traversal + null markers; queue for deserialize | Walmart, Razorpay, Groww | Hard |

---

---

## 🟠 TIER 1 — Walmart (68% frequency — Your Easiest Company, Highest Fit)

> Walmart loves **BFS / level-order** and **tree views**. Their tree questions are Medium.
> Their OA typically has 1 tree problem — usually BFS-based or path-based.

### Must-Solve for Walmart

| # | Problem | LeetCode # | Pattern (Brief) | Difficulty |
|---|---------|-----------|----------------|------------|
| W1 | Binary Tree Right Side View | [#199](https://leetcode.com/problems/binary-tree-right-side-view/) | BFS, last node per level | Medium |
| W2 | Binary Tree Level Order Traversal | [#102](https://leetcode.com/problems/binary-tree-level-order-traversal/) | BFS core template | Medium |
| W3 | Binary Tree Zigzag Level Order Traversal | [#103](https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/) | BFS + alternate add direction per level | Medium |
| W4 | Maximum Depth of Binary Tree | [#104](https://leetcode.com/problems/maximum-depth-of-binary-tree/) | Postorder height OR BFS level counter | Easy |
| W5 | Symmetric Tree | [#101](https://leetcode.com/problems/symmetric-tree/) | Two-tree mirror recursion | Easy |
| W6 | Path Sum | [#112](https://leetcode.com/problems/path-sum/) | Preorder; subtract target as you go down; check at leaf | Easy |
| W7 | Lowest Common Ancestor of Binary Tree | [#236](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) | Postorder; return non-null up; both non-null = LCA | Medium |
| W8 | Validate Binary Search Tree | [#98](https://leetcode.com/problems/validate-binary-search-tree/) | Pass valid range [min, max] down — do NOT just compare neighbors | Medium |
| W9 | Kth Smallest Element in a BST | [#230](https://leetcode.com/problems/kth-smallest-element-in-a-bst/) | Inorder = sorted; count k nodes | Medium |
| W10 | Serialize and Deserialize Binary Tree | [#297](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/) | Preorder + null markers; queue for deserialize | Hard |

> **Walmart tree pattern**: BFS-heavy. If you see "level", "view", "width", "layer" in the problem → BFS.
> If you see "path", "sum", "LCA" → DFS.

---

---

## 🟠 TIER 2 — Razorpay (60% — Hard Questions, High Quality)

> Razorpay loves **hard tree problems** and **path-sum variants**.
> Max Path Sum (#124) appears in ~40% of their interviews. Know it cold.
> They also ask N-ary tree variants — generalize binary tree patterns to N children.

### Must-Solve for Razorpay

| # | Problem | LeetCode # | Pattern (Brief) | Difficulty |
|---|---------|-----------|----------------|------------|
| R1 | Binary Tree Maximum Path Sum | [#124](https://leetcode.com/problems/binary-tree-maximum-path-sum/) | Postorder; global = left+node+right; return = node + max(left,right) | Hard |
| R2 | Lowest Common Ancestor of Binary Tree | [#236](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) | Postorder; propagate non-null result upward | Medium |
| R3 | Diameter of Binary Tree | [#543](https://leetcode.com/problems/diameter-of-binary-tree/) | Postorder; diameter at node = leftH + rightH; return height | Easy |
| R4 | Balanced Binary Tree | [#110](https://leetcode.com/problems/balanced-binary-tree/) | Postorder; return -1 as sentinel if unbalanced; propagate up | Easy |
| R5 | Construct Binary Tree from Preorder and Inorder | [#105](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) | Preorder[0] = root; find root in inorder; split both arrays | Medium |
| R6 | Serialize and Deserialize Binary Tree | [#297](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/) | Preorder + null markers | Hard |
| R7 | Path Sum II | [#113](https://leetcode.com/problems/path-sum-ii/) | Preorder + backtracking; snapshot list at leaf | Medium |
| R8 | Recover Binary Search Tree | [#99](https://leetcode.com/problems/recover-binary-search-tree/) | Inorder of BST = sorted; two nodes are swapped — find and fix them | Hard |
| R9 | Binary Tree Cameras | [#968](https://leetcode.com/problems/binary-tree-cameras/) | Postorder greedy; each node returns one of 3 states (covered, has-camera, needs-camera) | Hard |

> **Razorpay tree pattern**: They test postorder depth. If you can solve #124 cleanly and explain
> why "local answer ≠ return value", you've already differentiated yourself from most candidates.

---

---

## 🟡 TIER 3 — Groww (65% — DP on Trees + Expression Trees)

> Groww has a unique twist: they ask **expression tree** problems (financial context — parsing formulas).
> Also heavy on **BST + sorted property** problems. Groww loves DP-on-tree hybrids.

### Must-Solve for Groww

| # | Problem | LeetCode # | Pattern (Brief) | Difficulty |
|---|---------|-----------|----------------|------------|
| G1 | Binary Tree Maximum Path Sum | [#124](https://leetcode.com/problems/binary-tree-maximum-path-sum/) | Postorder; global vs return split | Hard |
| G2 | Binary Tree Level Order Traversal | [#102](https://leetcode.com/problems/binary-tree-level-order-traversal/) | BFS core template | Medium |
| G3 | Validate Binary Search Tree | [#98](https://leetcode.com/problems/validate-binary-search-tree/) | Pass bounds down | Medium |
| G4 | Kth Smallest Element in BST | [#230](https://leetcode.com/problems/kth-smallest-element-in-a-bst/) | Inorder = sorted | Medium |
| G5 | Convert Sorted Array to BST | [#108](https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/) | Mid of array = root; recurse left half and right half | Easy |
| G6 | Construct Binary Tree from Preorder and Inorder | [#105](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) | Preorder[0] = root; split via inorder | Medium |
| G7 | Count Good Nodes in Binary Tree | [#1448](https://leetcode.com/problems/count-good-nodes-in-binary-tree/) | Preorder; carry max-so-far down; node is "good" if val >= max | Medium |
| G8 | Path Sum III | [#437](https://leetcode.com/problems/path-sum-iii/) | Preorder + prefix sum hashmap; currSum - target = how many valid paths end here | Medium |
| G9 | Find Duplicate Subtrees | [#652](https://leetcode.com/problems/find-duplicate-subtrees/) | Postorder; serialize each subtree; use map to detect duplicates | Medium |
| G10 | Lowest Common Ancestor of Binary Tree | [#236](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) | Postorder propagation | Medium |

> **Groww tree pattern**: They love combining trees with other structures (hashmap, DP state).
> "Convert expression to binary tree" is custom/verbal — know how to parse operators as internal nodes
> and operands as leaves using a stack.

---

---

## 🟡 TIER 4 — PhonePe (55% — BST + Hard Tree DP)

> PhonePe has the highest Hard% (40%) of all 5 companies.
> Their tree questions tend to be hard-medium. They may ask tree DP variants.
> BST-heavy: kth element, validation, augmented BSTs.

### Must-Solve for PhonePe

| # | Problem | LeetCode # | Pattern (Brief) | Difficulty |
|---|---------|-----------|----------------|------------|
| P1 | Binary Tree Maximum Path Sum | [#124](https://leetcode.com/problems/binary-tree-maximum-path-sum/) | Postorder; global vs return split | Hard |
| P2 | Lowest Common Ancestor of Binary Tree | [#236](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) | Postorder; return non-null up | Medium |
| P3 | All Nodes Distance K in Binary Tree | [#863](https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/) | BFS after converting tree to graph (add parent pointers) | Medium |
| P4 | Serialize and Deserialize Binary Tree | [#297](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/) | Preorder + null markers | Hard |
| P5 | Construct Binary Tree from Preorder and Inorder | [#105](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) | Preorder[0] = root; split inorder | Medium |
| P6 | Flatten Binary Tree to Linked List | [#114](https://leetcode.com/problems/flatten-binary-tree-to-linked-list/) | Postorder reverse (right → left → node); link to prev | Medium |
| P7 | BST Iterator | [#173](https://leetcode.com/problems/binary-search-tree-iterator/) | Stack-based lazy inorder; push leftmost path, pop and push right's leftmost | Medium |
| P8 | Recover Binary Search Tree | [#99](https://leetcode.com/problems/recover-binary-search-tree/) | Inorder must be sorted; track two violation points | Hard |
| P9 | Longest ZigZag Path in Binary Tree | [#1372](https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/) | Postorder; return (left-count, right-count, max) from each node | Medium |

> **PhonePe tree pattern**: They favor problems where the solution is not immediately obvious.
> Know the "tree → graph" conversion (parent pointers) for distance-based problems.
> BST Iterator is a sneaky favorite — tests lazy evaluation thinking.

---

---

## 🟢 TIER 5 — Meesho (60% — Medium-Friendly, BFS-Heavy)

> Meesho has the most accessible DSA round. Their tree questions are Medium difficulty.
> Focus on BFS variants and basic DFS. No hard tree questions typically.

### Must-Solve for Meesho

| # | Problem | LeetCode # | Pattern (Brief) | Difficulty |
|---|---------|-----------|----------------|------------|
| Me1 | Binary Tree Level Order Traversal | [#102](https://leetcode.com/problems/binary-tree-level-order-traversal/) | BFS queue template | Medium |
| Me2 | Binary Tree Right Side View | [#199](https://leetcode.com/problems/binary-tree-right-side-view/) | BFS, last per level | Medium |
| Me3 | Maximum Depth of Binary Tree | [#104](https://leetcode.com/problems/maximum-depth-of-binary-tree/) | Postorder height | Easy |
| Me4 | Balanced Binary Tree | [#110](https://leetcode.com/problems/balanced-binary-tree/) | Postorder; -1 sentinel | Easy |
| Me5 | Symmetric Tree | [#101](https://leetcode.com/problems/symmetric-tree/) | Mirror recursion | Easy |
| Me6 | Path Sum | [#112](https://leetcode.com/problems/path-sum/) | Preorder; subtract target; check at leaf | Easy |
| Me7 | Lowest Common Ancestor of BST | [#235](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/) | If both < node → go left; if both > node → go right; else → this is LCA | Easy |
| Me8 | Convert Sorted Array to BST | [#108](https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/) | Mid = root; recurse halves | Easy |
| Me9 | Average of Levels in Binary Tree | [#637](https://leetcode.com/problems/average-of-levels-in-binary-tree/) | BFS; sum per level / level-size | Easy |
| Me10 | Diameter of Binary Tree | [#543](https://leetcode.com/problems/diameter-of-binary-tree/) | Postorder; leftH + rightH = diameter at this node | Easy |

> **Meesho tree pattern**: If you've done the foundation problems in Trees_Mastery.md,
> Meesho's tree round is already handled. No surprises here.

---

---

## 🔴 TIER 6 — Hard Problems (For Razorpay/PhonePe Differentiation)

> These are hard-level tree problems that appear specifically at Razorpay (~45% Hard round)
> and PhonePe (~40% Hard round). Solve these only after Tiers 0–5 feel solid.

| # | Problem | LeetCode # | Pattern (Brief) | Company |
|---|---------|-----------|----------------|---------|
| H1 | Binary Tree Cameras | [#968](https://leetcode.com/problems/binary-tree-cameras/) | Postorder greedy; 3 states per node (needs-cover, has-camera, covered) | Razorpay |
| H2 | Vertical Order Traversal of a Binary Tree | [#987](https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/) | BFS with (col, row) coordinates; sort by col then row then val | Razorpay, Groww |
| H3 | Recover Binary Search Tree | [#99](https://leetcode.com/problems/recover-binary-search-tree/) | Inorder must be sorted in BST; track prev; find two violation nodes, swap | Razorpay, PhonePe |
| H4 | LCA of Deepest Leaves | [#1123](https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/) | Postorder; return (depth, lca_node) pair; if depths equal → current is LCA | PhonePe |
| H5 | Maximum Width of Binary Tree | [#662](https://leetcode.com/problems/maximum-width-of-binary-tree/) | BFS with node index (left=2i, right=2i+1); width = last_idx - first_idx + 1 | Groww |
| H6 | Find Duplicate Subtrees | [#652](https://leetcode.com/problems/find-duplicate-subtrees/) | Postorder serialization into hashmap; same serialization = duplicate | Groww |
| H7 | Serialize and Deserialize Binary Tree | [#297](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/) | Preorder + null markers for serialize; queue-based for deserialize | All 5 |

---

---

## 📋 THE MASTER SEQUENCE — In What Order to Solve Everything

> Follow this exact order. Do NOT jump ahead.

### Phase 1 — Foundation (Do This Before Any Company Targeting)
These 6 problems from Tier 0 appear at 3+ companies. Maximum ROI.

```
1. [#102] Binary Tree Level Order Traversal      — BFS core
2. [#199] Binary Tree Right Side View            — BFS last per level
3. [#236] Lowest Common Ancestor of BT           — postorder LCA
4. [#98]  Validate Binary Search Tree            — pass bounds down
5. [#124] Binary Tree Maximum Path Sum           — postorder hard
6. [#297] Serialize and Deserialize Binary Tree  — preorder + null markers
```

### Phase 2 — Walmart + Meesho (Apply Month 2 — Oct 2026)

```
7.  [#104] Maximum Depth of Binary Tree
8.  [#101] Symmetric Tree
9.  [#103] Binary Tree Zigzag Level Order
10. [#112] Path Sum
11. [#543] Diameter of Binary Tree
12. [#110] Balanced Binary Tree
13. [#108] Convert Sorted Array to BST
14. [#637] Average of Levels in Binary Tree
15. [#230] Kth Smallest Element in BST
16. [#235] LCA of Binary Search Tree
```

### Phase 3 — Groww + Razorpay (Apply Month 3 — Nov 2026)

```
17. [#105] Construct BT from Preorder + Inorder
17. [#106] Construct BT from Inorder + Postorder
18. [#113] Path Sum II
19. [#437] Path Sum III
20. [#1448] Count Good Nodes in Binary Tree
21. [#1372] Longest ZigZag Path in Binary Tree
22. [#652] Find Duplicate Subtrees
23. [#450] Delete Node in BST
24. [#99]  Recover Binary Search Tree
```

### Phase 4 — PhonePe Hard (Apply Month 4 — Dec 2026)

```
25. [#863] All Nodes Distance K in BT
26. [#114] Flatten Binary Tree to Linked List
27. [#173] BST Iterator
28. [#968] Binary Tree Cameras
29. [#987] Vertical Order Traversal of BT
30. [#662] Maximum Width of Binary Tree
31. [#1123] LCA of Deepest Leaves
```

---

---

## 🧭 QUICK PATTERN LOOKUP — "What is this question asking?"

When you read a tree problem, match it here first:

```
"Level by level" / "layer" / "breadth" / "width"
→ BFS with queue. Template: snapshot levelSize, inner for-loop.

"View from right/left/top/bottom"
→ BFS, take first or last node per level.

"Path sum" / "root to leaf sum" / "any path"
→ Root-to-leaf: preorder, subtract target, check at leaf.
→ Any path: postorder, global max = left+node+right, return = node + max(left,right).

"Height" / "depth" / "balanced" / "diameter"
→ Postorder. Compute height of left, height of right. Combine.

"Common ancestor" / "LCA" / "deepest common"
→ Postorder. Return non-null up. Both sides non-null = this is LCA.

"Build tree from arrays" / "construct"
→ Preorder/Postorder gives root. Inorder splits left/right.
→ Preorder: root = arr[0]. Postorder: root = arr[last].

"BST" + "sorted" / "kth" / "range"
→ Inorder = sorted. Count k during inorder for kth-smallest.
→ Validate: pass min/max bounds down.

"Serialize" / "encode" / "decode" / "store"
→ Preorder + null markers. Queue for deserialize.
```

---

## 📊 Summary Stats

| Tier | Questions | Companies Covered | Priority |
|------|-----------|------------------|---------|
| Tier 0 — Multi-company | 6 | All 5 | 🔴 MUST — solve first |
| Tier 1 — Walmart | 10 | Walmart | 🔴 HIGH |
| Tier 2 — Razorpay | 9 | Razorpay | 🔴 HIGH |
| Tier 3 — Groww | 10 | Groww | 🟠 HIGH |
| Tier 4 — PhonePe | 9 | PhonePe | 🟠 HIGH |
| Tier 5 — Meesho | 10 | Meesho | 🟡 MEDIUM |
| Tier 6 — Hard (differentiation) | 7 | Razorpay, PhonePe | 🟡 IF TIME |
| **Unique problems total** | **~31** | All 5 | — |

> Note: Several problems appear in multiple tiers because they're asked at multiple companies.
> The 31 unique questions cover all 5 companies at all difficulty levels.

---

*File: `Trees_Question_Bank.md` · Linked to: `Trees_Mastery.md` + `company_analysis.md`*
*Target: ₹20+ LPA | Jan–Feb 2027 | Walmart → Meesho → Groww → Razorpay → PhonePe*
