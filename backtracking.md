# 🔙 Backtracking Foundation — Complete Playbook

> **Read Recursion_Foundation_ArrayPatterns.md first.**
Backtracking IS recursion — just recursion that knows how to undo its choices.
Every sub-pattern here builds directly on the array patterns you already know.
> 

---

## What is Backtracking? — The Core Idea

Backtracking = **Recursion + Undo**

```
Regular recursion:  go forward → get result → done
Backtracking:       go forward → if dead end → UNDO → try a different path
```

The three-step rhythm that EVERY backtracking problem follows:

```
1. CHOOSE   → Make a choice (add to current, place a queen, mark visited)
2. EXPLORE  → Recurse deeper with that choice made
3. UNCHOOSE → Undo the choice (remove from current, unplace queen, unmark)
```

You repeat this for every possible choice at the current step. The undo step is what makes you “backtrack” — you return to the exact state you were in before making the choice, so you can try the next option.

---

## The 5 Backtracking Sub-Patterns

```
BACKTRACKING
│
├── PATTERN 1: Include / Exclude  (Binary Choice)
│   "At each element: TAKE IT or LEAVE IT — exactly 2 recursive calls."
│   No for loop. No undo needed (state is passed by value or rebuilt).
│   → Subsets, Power Set, Subset Sum, 0/1 Knapsack
│
├── PATTERN 2: For-Loop Choice  (Multiple Choices)
│   "At each step: try ALL available choices using a for loop."
│   One recursive call inside the loop. Undo after each call.
│   → Permutations, Combinations, Letter Combos, Combination Sum
│
├── PATTERN 3: Constraint Placement  (Slot Filling)
│   "Fill one slot per recursive call. Only place if the slot is VALID."
│   → N-Queens, Sudoku, Crossword, Color Graph
│
├── PATTERN 4: Grid / Matrix DFS  (2D Exploration)
│   "Move in 4 directions on a 2D grid. Mark visited, recurse, unmark."
│   → Word Search, Number of Islands, Rat in a Maze, Flood Fill
│
└── PATTERN 5: Partition / Segmentation  (Cut Points)
    "At each index, try all ways to cut/split. Check if cut is valid."
    → Palindrome Partitioning, Word Break II, Restore IP Addresses
```

---

## The Universal Backtracking Template

```java
void backtrack(
    INPUT,           // the original data (array, string, grid — never changes)
    int index,       // where we are (what position/slot we're deciding for)
    CurrentChoice,   // what we've chosen SO FAR (list, board, path)
    ResultCollection // where we store complete answers
) {

    // ─── GATE 1: BASE CASE ─────────────────────────────────────────
    // "Have I finished making all decisions?"
    // → Record the current answer, then return (DON'T continue)
    if (decisionsComplete) {
        resultCollection.add(copy(currentChoice));  // ALWAYS copy, never add reference
        return;
    }

    // ─── GATE 2: PRUNING (optional but powerful) ───────────────────
    // "Is this path already invalid? Stop early."
    if (currentPathIsInvalid) {
        return;
    }

    // ─── GATE 3: CHOOSE → EXPLORE → UNCHOOSE ──────────────────────
    for (each valid Option at this index) {

        currentChoice.add(option);          // 1. CHOOSE
        backtrack(INPUT, index+1, ...);     // 2. EXPLORE
        currentChoice.removeLast();         // 3. UNCHOOSE (backtrack)
    }
}
```

**The single most important line**: `resultCollection.add(copy(currentChoice))` — you MUST make a copy. If you add the list itself, every answer in your result will point to the same (now-empty) list.

---

## WHY YOU MUST COPY — The Most Common Backtracking Bug

```java
// WRONG — adds a reference, not a snapshot
result.add(current);  // current will be modified later → all results become []

// CORRECT — adds a snapshot of the current state
result.add(new ArrayList<>(current));
```

Trace of the bug:

```
current = [1, 2]  → result.add(current) → result = [[1,2]]
current = [1, 3]  → result.add(current) → result = [[1,3], [1,3]]  ← WRONG!
                                           (both entries point to same object)
```

---

---

# SUB-PATTERN 1 — Include / Exclude (Binary Choice)

## Core Idea

At each position, you have **exactly two choices**:
- **INCLUDE** this element → add it to current, move to next index
- **EXCLUDE** this element → don’t add it, move to next index

This creates a **binary decision tree**. Every path from root to leaf is one complete subset.

```
Input: [1, 2, 3]

                          []      ← start (nothing decided yet)
                    /           \
               [1]               []        ← include 1  OR  exclude 1
              /   \             /   \
           [1,2]  [1]        [2]    []    ← include 2  OR  exclude 2
           / \    / \        / \    / \
        [1,2,3][1,2][1,3][1] [2,3][2][3] [] ← include 3 OR exclude 3
        ↑    ↑   ↑   ↑   ↑   ↑   ↑  ↑  ↑
        All 8 subsets (2^3 = 8 leaves)
```

**The Tree has 2^n leaves. There are exactly 2^n subsets of an n-element set.**

## The Template

```java
void includeExclude(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {

    // BASE CASE: made decisions for all elements
    if (index == nums.length) {
        result.add(new ArrayList<>(current));  // snapshot this complete subset
        return;
    }

    // CHOICE 1: INCLUDE nums[index]
    current.add(nums[index]);                           // choose
    includeExclude(nums, index + 1, current, result);  // explore
    current.remove(current.size() - 1);                // unchoose

    // CHOICE 2: EXCLUDE nums[index]
    // (no add needed, just skip to next index)
    includeExclude(nums, index + 1, current, result);  // explore (without adding)
}
// call: includeExclude(nums, 0, new ArrayList<>(), result)
```

**Notice**: The “exclude” branch has no add/remove because we’re choosing to NOT modify current. This is the simplest form of choose-explore-unchoose — the unchoose is implicit (you never changed state in the first place).

---

## Foundation Problems — Include/Exclude

### B1-F01 · Generate ALL subsets of [1, 2, 3] — TRACE IT

```java
void subsets(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {
    if (index == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    // INCLUDE
    current.add(nums[index]);
    subsets(nums, index + 1, current, result);
    current.remove(current.size() - 1);
    // EXCLUDE
    subsets(nums, index + 1, current, result);
}
```

**MANDATORY EXERCISE**: Run this on [1,2,3]. Draw the tree on paper. Write down what
`current` looks like at each base case hit. You should get 8 subsets:
[], [3], [2], [2,3], [1], [1,3], [1,2], [1,2,3]

---

### B1-F02 · Count total number of subsets (no list needed)

```java
int countSubsets(int[] nums, int index) {
    if (index == nums.length) return 1;           // 1 empty subset at the leaf

    int withCurrent = countSubsets(nums, index + 1);    // include
    int withoutCurrent = countSubsets(nums, index + 1); // exclude

    return withCurrent + withoutCurrent;
}
// Expected for n elements: always 2^n
```

**This teaches**: the base case returning `1` means “yes, this is a valid subset.” Every leaf = one complete subset.

---

### B1-F03 · Print only subsets of a specific size k

```java
void subsetsOfSizeK(int[] nums, int index, List<Integer> current, int k, List<List<Integer>> result) {
    // BASE CASE: if we've collected k elements already → record and stop exploring
    if (current.size() == k) {
        result.add(new ArrayList<>(current));
        return;
    }
    // PRUNING: not enough elements remaining to reach size k → stop
    if (index == nums.length) return;

    // INCLUDE
    current.add(nums[index]);
    subsetsOfSizeK(nums, index + 1, current, k, result);
    current.remove(current.size() - 1);

    // EXCLUDE
    subsetsOfSizeK(nums, index + 1, current, k, result);
}
```

**Why pruning matters**: Without the pruning line, you’d still get correct results, but much slower. Pruning cuts branches of the call tree that can NEVER lead to a valid answer.

---

### B1-F04 · Check if ANY subset sums to target (boolean — early exit)

```java
boolean subsetSumExists(int[] nums, int index, int remaining) {
    // BASE CASE: remaining == 0 → found a valid subset!
    if (remaining == 0) return true;
    // BASE CASE: no elements left but remaining != 0 → this path failed
    if (index == nums.length) return false;
    // PRUNING: remaining went negative → can't fix this with more elements
    if (remaining < 0) return false;

    // INCLUDE: subtract current element from remaining
    boolean include = subsetSumExists(nums, index + 1, remaining - nums[index]);
    if (include) return true;  // early exit — found it!

    // EXCLUDE: don't subtract
    boolean exclude = subsetSumExists(nums, index + 1, remaining);
    return exclude;
}
// call: subsetSumExists(nums, 0, target)
```

---

### B1-F05 · Find ALL subsets that sum to target

```java
void subsetSumAll(int[] nums, int index, int remaining, List<Integer> current, List<List<Integer>> result) {
    if (remaining == 0) {
        result.add(new ArrayList<>(current));  // found one valid subset
        return;
    }
    if (index == nums.length || remaining < 0) return;  // dead end

    // INCLUDE nums[index]
    current.add(nums[index]);
    subsetSumAll(nums, index + 1, remaining - nums[index], current, result);
    current.remove(current.size() - 1);

    // EXCLUDE nums[index]
    subsetSumAll(nums, index + 1, remaining, current, result);
}
```

---

### B1-F06 · Can array be divided into two equal-sum parts?

```java
// Idea: find subset with sum = totalSum/2
boolean canDivide(int[] nums, int index, int remaining) {
    if (remaining == 0) return true;
    if (index == nums.length || remaining < 0) return false;

    return canDivide(nums, index + 1, remaining - nums[index])  // include
        || canDivide(nums, index + 1, remaining);               // exclude
}

boolean canPartition(int[] nums) {
    int total = 0;
    for (int n : nums) total += n;
    if (total % 2 != 0) return false;  // odd sum → impossible
    return canDivide(nums, 0, total / 2);
}
```

---

### B1-F07 · Generate all binary strings of length n (0 or 1 at each position)

```java
void binaryStrings(int n, StringBuilder current, List<String> result) {
    if (current.length() == n) {
        result.add(current.toString());
        return;
    }
    // INCLUDE '0'
    current.append('0');
    binaryStrings(n, current, result);
    current.deleteCharAt(current.length() - 1);

    // INCLUDE '1'
    current.append('1');
    binaryStrings(n, current, result);
    current.deleteCharAt(current.length() - 1);
}
```

**This is include/exclude for binary digits. 2 choices at each step. 2^n results.**

---

### B1-F08 · Maximum sum among ALL subsets

```java
int maxSubsetSum(int[] nums, int index, int currentSum) {
    if (index == nums.length) return currentSum;  // complete subset → return its sum

    int include = maxSubsetSum(nums, index + 1, currentSum + nums[index]);
    int exclude = maxSubsetSum(nums, index + 1, currentSum);

    return Math.max(include, exclude);
}
// call: maxSubsetSum(nums, 0, 0)
```

---

## Include/Exclude — Decision Guide

| Question | Answer |
| --- | --- |
| How many recursive calls? | Always exactly **2** (include and exclude) |
| Is there a for loop? | **No** — the two calls replace the loop |
| When does base case fire? | When `index == nums.length` (all decisions made) |
| What’s the output size? | Always **2^n** leaves in the call tree |
| When to prune? | When sum goes negative, or current size already exceeds k |
| Why copy when adding? | The list is shared — without copy, all entries become the same |

---

---

# SUB-PATTERN 2 — For-Loop Choice (Multiple Choices)

## Core Idea

At each recursive call, you have **more than 2 options**. Instead of two hardcoded calls (include/exclude), you loop through all valid options and try each one.

The three-step rhythm is the same, but now it’s inside a loop:

```java
for (each option) {
    choose(option);    // modify state
    recurse();         // explore
    unchoose(option);  // restore state
}
```

**The key difference from Include/Exclude**:
- Include/Exclude: 2 hardcoded calls. No for loop. No explicit undo.
- For-Loop: N choices in a loop. Explicit `add` before, `remove` after.

## Where this pattern naturally appears

```
Permutations:    at each position, try every UNUSED number (n choices at step 1)
Combinations:    at each position, try numbers from start..n (shrinking choices)
Letter combos:   at each digit, try each mapped letter (3-4 choices)
Combo Sum:       at each step, try every number >= current start (reuse allowed)
```

## The Template

```java
void forLoopBacktrack(
    int[] nums, int start, List<Integer> current, List<List<Integer>> result
) {
    // BASE CASE: a complete valid answer is built
    if (goalReached(current)) {
        result.add(new ArrayList<>(current));
        return;
    }

    // TRY EVERY VALID OPTION from 'start' to end
    for (int i = start; i < nums.length; i++) {

        // 1. CHOOSE
        current.add(nums[i]);

        // 2. EXPLORE
        forLoopBacktrack(nums, i + 1, current, result);  // i+1 = no reuse
        // OR: forLoopBacktrack(nums, i, current, result); // i = allow reuse

        // 3. UNCHOOSE
        current.remove(current.size() - 1);
    }
}
```

**The `start` parameter is the most important**: it controls what you can pick next.
- `i + 1` → no reuse, order doesn’t matter (combinations)
- `i` → reuse allowed (combination sum)
- No `start`, use `visited[]` → permutations (order matters)

---

## Foundation Problems — For-Loop Choice

### B2-F01 · Generate all permutations of [1, 2, 3] — TRACE IT

```java
void permutations(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }

    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;          // skip already-used elements

        used[i] = true;                 // 1. CHOOSE: mark as used
        current.add(nums[i]);
        permutations(nums, used, current, result);  // 2. EXPLORE
        current.remove(current.size() - 1);         // 3. UNCHOOSE
        used[i] = false;                // restore: mark as unused
    }
}
```

**Call tree for [1,2,3] (partial)**:

```
Level 0: current=[]    → try 1, 2, 3
Level 1: current=[1]   → try 2, 3 (1 used)
Level 2: current=[1,2] → try 3 (1,2 used)
Level 3: current=[1,2,3] → base case → add [1,2,3]
Level 2: unchoose 3, current=[1,2]
Level 1: unchoose 2, current=[1]
Level 1: current=[1]   → try 3 (1 used, 2 explored)
Level 2: current=[1,3] → try 2 (1,3 used)
Level 3: current=[1,3,2] → base case → add [1,3,2]
...
Final: [1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]  (3! = 6 results)
```

---

### B2-F02 · Generate all combinations of size k from [1..n]

```java
void combinations(int n, int k, int start, List<Integer> current, List<List<Integer>> result) {
    if (current.size() == k) {
        result.add(new ArrayList<>(current));
        return;
    }

    for (int i = start; i <= n; i++) {
        current.add(i);                               // 1. CHOOSE
        combinations(n, k, i + 1, current, result);  // 2. EXPLORE (i+1 = no reuse)
        current.remove(current.size() - 1);           // 3. UNCHOOSE
    }
}
// call: combinations(n, k, 1, new ArrayList<>(), result)
```

**Why `i+1` (not `i`)**: Order doesn’t matter in combinations. [1,2] = [2,1]. Using `start` ensures we only go forward, never backward.

**Why no `used[]` array**: Since we always move forward (i+1), we can’t accidentally pick an already-used index.

---

### B2-F03 · Combination Sum — numbers can be REUSED

```java
void combinationSum(int[] candidates, int target, int start, List<Integer> current, List<List<Integer>> result) {
    if (target == 0) {
        result.add(new ArrayList<>(current));  // found valid combination
        return;
    }
    if (target < 0) return;  // pruning: overshot

    for (int i = start; i < candidates.length; i++) {
        current.add(candidates[i]);                                    // 1. CHOOSE
        combinationSum(candidates, target - candidates[i], i, current, result);  // 2. EXPLORE (i not i+1 = reuse allowed)
        current.remove(current.size() - 1);                            // 3. UNCHOOSE
    }
}
```

**The critical difference**: `i` not `i+1` in the recursive call = same element can be picked again.

---

### B2-F04 · Letter combinations of a phone number

```java
String[] phone = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

void letterCombos(String digits, int index, StringBuilder current, List<String> result) {
    if (index == digits.length()) {
        result.add(current.toString());
        return;
    }

    String letters = phone[digits.charAt(index) - '0'];  // letters for this digit
    for (char c : letters.toCharArray()) {
        current.append(c);                                // 1. CHOOSE
        letterCombos(digits, index + 1, current, result); // 2. EXPLORE
        current.deleteCharAt(current.length() - 1);       // 3. UNCHOOSE
    }
}
```

**3 or 4 choices per step** (depending on the digit). Classic for-loop pattern.

---

### B2-F05 · Generate all permutations with DUPLICATES (skip duplicate choices)

```java
void permutationsWithDups(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }

    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        // SKIP DUPLICATES: if nums[i] == nums[i-1] and nums[i-1] was NOT used at this level,
        // then we'd generate a duplicate permutation
        if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;

        used[i] = true;
        current.add(nums[i]);
        permutationsWithDups(nums, used, current, result);
        current.remove(current.size() - 1);
        used[i] = false;
    }
}
// Remember to sort nums first!
```

---

### B2-F06 · Subsets with DUPLICATES (avoid duplicate subsets)

```java
void subsetsWithDups(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
    result.add(new ArrayList<>(current));  // add current state EVERY CALL (not just at base)

    for (int i = start; i < nums.length; i++) {
        // SKIP DUPLICATES at the same recursion level
        if (i > start && nums[i] == nums[i - 1]) continue;

        current.add(nums[i]);
        subsetsWithDups(nums, i + 1, current, result);
        current.remove(current.size() - 1);
    }
}
// Sort nums first!
```

**The dedup trick explained**:

```
nums = [1, 2, 2]  (sorted)
At level 0 (start=0): try i=0 (val=1), i=1 (val=2), i=2 (val=2)
When i=2: nums[2]==nums[1] AND i>start → SKIP
→ This avoids generating [1,2] twice (once picking index 1, once picking index 2)
```

---

### B2-F07 · Generate parentheses (constrained choices)

```java
void generateParens(int n, int open, int close, StringBuilder current, List<String> result) {
    if (current.length() == 2 * n) {
        result.add(current.toString());
        return;
    }

    // CHOICE 1: add '(' — only if we haven't used all open parens yet
    if (open < n) {
        current.append('(');
        generateParens(n, open + 1, close, current, result);
        current.deleteCharAt(current.length() - 1);
    }

    // CHOICE 2: add ')' — only if close count < open count (must close after opening)
    if (close < open) {
        current.append(')');
        generateParens(n, open, close + 1, current, result);
        current.deleteCharAt(current.length() - 1);
    }
}
// call: generateParens(n, 0, 0, new StringBuilder(), result)
```

**This is for-loop with heavy pruning** — instead of a literal for loop, we have 2 conditional branches (one per valid choice). The constraints eliminate most paths early.

---

## For-Loop vs Include/Exclude — Side by Side

| Feature | Include/Exclude | For-Loop |
| --- | --- | --- |
| Choices per step | Always 2 | 2 to n (varies) |
| Has for loop | No | Yes |
| Explicit unchoose | Usually not needed | Always needed |
| `start` parameter | No | Yes (to avoid duplicates) |
| `used[]` array | No | Yes (permutations only) |
| Call tree shape | Perfect binary tree | Varies |
| Total leaves | Always 2^n | n!, C(n,k), etc. |
| Problems | Subsets, subset sum | Permutations, combinations, combo sum |

---

---

# SUB-PATTERN 3 — Constraint Placement (Slot Filling)

## Core Idea

You have N **slots** to fill (rows, cells, positions). At each recursive call, you fill **one slot** — but only if the placement is **valid** according to constraints. If no valid placement exists, you backtrack.

```
Recursion level = which slot you're filling
For loop = which value to try for this slot
Validity check = the pruning gate
```

## The Template

```java
void solve(Board board, int slot) {

    // BASE CASE: all slots filled → solution found!
    if (slot == totalSlots) {
        recordSolution(board);
        return;
    }

    // TRY EVERY POSSIBLE VALUE for this slot
    for (Value v : possibleValues) {

        if (isValid(board, slot, v)) {    // only proceed if placement is legal

            place(board, slot, v);         // 1. CHOOSE — place value
            solve(board, slot + 1);        // 2. EXPLORE — fill next slot
            remove(board, slot, v);        // 3. UNCHOOSE — undo placement
        }
    }
}
```

---

## Foundation Problems — Constraint Placement

### B3-F01 · Place N non-attacking rooks on an NxN board

```java
// Rooks attack same row or column. One rook per row.
void placeRooks(int n, boolean[] colUsed, int row, int[][] board, List<int[][]> result) {
    if (row == n) {
        result.add(copyBoard(board));
        return;
    }

    for (int col = 0; col < n; col++) {
        if (!colUsed[col]) {              // column is free

            board[row][col] = 1;          // 1. PLACE
            colUsed[col] = true;
            placeRooks(n, colUsed, row + 1, board, result);  // 2. EXPLORE next row
            board[row][col] = 0;          // 3. REMOVE
            colUsed[col] = false;
        }
    }
}
```

**This is simpler than N-Queens** (no diagonal check) — great first constraint-placement problem.

---

### B3-F02 · N-Queens — Place N queens, none attacking each other

```java
void solveNQueens(int n, boolean[] colUsed, boolean[] diag1Used, boolean[] diag2Used,
                  char[][] board, int row, List<List<String>> result) {
    if (row == n) {
        // Convert board to list of strings
        List<String> solution = new ArrayList<>();
        for (char[] r : board) solution.add(new String(r));
        result.add(solution);
        return;
    }

    for (int col = 0; col < n; col++) {
        // Queens attack: same column, same diagonal (row-col), same anti-diagonal (row+col)
        if (colUsed[col] || diag1Used[row - col + n] || diag2Used[row + col]) continue;

        board[row][col] = 'Q';            // 1. PLACE
        colUsed[col] = true;
        diag1Used[row - col + n] = true;
        diag2Used[row + col] = true;

        solveNQueens(n, colUsed, diag1Used, diag2Used, board, row + 1, result); // 2. EXPLORE

        board[row][col] = '.';            // 3. REMOVE
        colUsed[col] = false;
        diag1Used[row - col + n] = false;
        diag2Used[row + col] = false;
    }
}
```

**One queen per row** — the outer recursion is rows. For loop is columns. Validity checks 3 constraints simultaneously.

---

### B3-F03 · Color a graph with K colors (Graph Coloring)

```java
void colorGraph(int[][] graph, int[] colors, int node, int k, int n, List<int[]> result) {
    if (node == n) {
        result.add(colors.clone());
        return;
    }

    for (int color = 1; color <= k; color++) {
        if (canColor(graph, colors, node, color, n)) {
            colors[node] = color;                           // 1. PLACE
            colorGraph(graph, colors, node + 1, k, n, result);  // 2. EXPLORE
            colors[node] = 0;                               // 3. REMOVE
        }
    }
}

boolean canColor(int[][] graph, int[] colors, int node, int color, int n) {
    for (int neighbor = 0; neighbor < n; neighbor++) {
        if (graph[node][neighbor] == 1 && colors[neighbor] == color) return false;
    }
    return true;
}
```

---

### B3-F04 · Sudoku Solver (partial — one cell per call)

```java
boolean solveSudoku(char[][] board) {
    // Find the next empty cell
    for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
            if (board[row][col] == '.') {

                for (char c = '1'; c <= '9'; c++) {
                    if (isValidSudoku(board, row, col, c)) {

                        board[row][col] = c;              // 1. PLACE
                        if (solveSudoku(board)) return true; // 2. EXPLORE
                        board[row][col] = '.';            // 3. REMOVE (backtrack)
                    }
                }
                return false;  // no valid digit → backtrack
            }
        }
    }
    return true;  // no empty cell found → solved!
}
```

**Note**: This returns `boolean` — if placing a digit leads to a solution, we return true immediately. If all 9 digits fail, we return false (triggering backtrack in the caller).

---

## Constraint Placement — Key Insight

The “constraint” is what separates this from blind for-loop backtracking:

```
For-Loop Pattern:     try all choices, no validity check
Constraint Placement: try all choices, CHECK VALIDITY first, skip invalid
```

The validity check IS the pruning. N-Queens is fast not because of clever code — it’s fast because the constraint (no two queens attack each other) eliminates most branches before they’re explored.

---

---

# SUB-PATTERN 4 — Grid / Matrix DFS (2D Exploration)

## Core Idea

You’re on a 2D grid. From any cell, you can move in 4 directions. You need to explore paths, find words, count regions, or mark visited areas.

**The critical new element**: you must **mark cells as visited** before recursing and **unmark them** after. Otherwise you’ll loop forever (or revisit cells in the same path).

## The Template

```java
// Direction arrays — move Up, Down, Left, Right
int[] dr = {-1, 1, 0, 0};
int[] dc = {0, 0, -1, 1};

void dfs(char[][] grid, int row, int col, boolean[][] visited, /* other params */) {

    // BASE CASE 1: out of bounds
    if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) return;

    // BASE CASE 2: already visited OR invalid cell
    if (visited[row][col] || grid[row][col] == '#') return;

    // MARK: we're visiting this cell now
    visited[row][col] = true;

    // DO WORK: process this cell
    doSomething(grid[row][col]);

    // EXPLORE: try all 4 directions
    for (int d = 0; d < 4; d++) {
        dfs(grid, row + dr[d], col + dc[d], visited, /* params */);
    }

    // UNMARK: backtrack (if path-based — for flood fill, you may skip this)
    visited[row][col] = false;
}
```

**When to unmark**:
- **Unmark** if you’re finding PATHS (each path needs its own clean visited state)
- **Don’t unmark** if you’re marking REGIONS (once a cell is part of a region, it’s done)

---

## 2D Grid — Understanding the Coordinates

```
grid = [
    ['A','B','C'],   ← row 0
    ['D','E','F'],   ← row 1
    ['G','H','I']    ← row 2
]

grid[0][0] = 'A'    grid[0][1] = 'B'    grid[0][2] = 'C'
grid[1][0] = 'D'    grid[1][1] = 'E'    grid[1][2] = 'F'
grid[2][0] = 'G'    grid[2][1] = 'H'    grid[2][2] = 'I'

From 'E' (row=1, col=1):
    Up    = grid[0][1] = 'B'
    Down  = grid[2][1] = 'H'
    Left  = grid[1][0] = 'D'
    Right = grid[1][2] = 'F'
```

---

## Foundation Problems — Grid/Matrix DFS

### B4-F01 · Flood Fill — change connected region to new color

```java
void floodFill(int[][] image, int row, int col, int oldColor, int newColor) {
    // Boundary check
    if (row < 0 || row >= image.length || col < 0 || col >= image[0].length) return;
    // Only fill cells that have the original color
    if (image[row][col] != oldColor || image[row][col] == newColor) return;

    image[row][col] = newColor;                    // MARK (fill with new color)

    // 4-directional DFS — NO unmark because we permanently fill
    floodFill(image, row - 1, col, oldColor, newColor);  // up
    floodFill(image, row + 1, col, oldColor, newColor);  // down
    floodFill(image, row, col - 1, oldColor, newColor);  // left
    floodFill(image, row, col + 1, oldColor, newColor);  // right
}
```

**No unmark here** — once filled, the cell stays filled. This is region-marking, not path-finding.

---

### B4-F02 · Count connected regions (islands)

```java
int countIslands(char[][] grid) {
    int count = 0;
    for (int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[r][c] == '1') {
                sink(grid, r, c);    // DFS marks entire island as visited
                count++;
            }
        }
    }
    return count;
}

void sink(char[][] grid, int r, int c) {
    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return;
    if (grid[r][c] != '1') return;

    grid[r][c] = '0';   // MARK as visited by "sinking" (no unmark — permanent)

    sink(grid, r - 1, c);
    sink(grid, r + 1, c);
    sink(grid, r, c - 1);
    sink(grid, r, c + 1);
}
```

---

### B4-F03 · Find a path through a maze (does path exist?)

```java
boolean findPath(int[][] maze, int r, int c, int destR, int destC, boolean[][] visited) {
    // Out of bounds or wall or visited
    if (r < 0 || r >= maze.length || c < 0 || c >= maze[0].length) return false;
    if (maze[r][c] == 1 || visited[r][c]) return false;  // 1 = wall

    // Reached destination!
    if (r == destR && c == destC) return true;

    visited[r][c] = true;   // MARK

    // Try all 4 directions
    boolean found = findPath(maze, r-1, c, destR, destC, visited)
                 || findPath(maze, r+1, c, destR, destC, visited)
                 || findPath(maze, r, c-1, destR, destC, visited)
                 || findPath(maze, r, c+1, destR, destC, visited);

    visited[r][c] = false;  // UNMARK (path finding — need to explore other paths)

    return found;
}
```

**UNMARK here** because we’re looking for any path — if this direction fails, another starting point should be able to use this cell.

---

### B4-F04 · Print all paths through a maze

```java
void allPaths(int[][] maze, int r, int c, int destR, int destC, boolean[][] visited, List<String> path, List<List<String>> result) {
    if (r < 0 || r >= maze.length || c < 0 || c >= maze[0].length) return;
    if (maze[r][c] == 1 || visited[r][c]) return;

    path.add("(" + r + "," + c + ")");
    visited[r][c] = true;                                 // MARK

    if (r == destR && c == destC) {
        result.add(new ArrayList<>(path));                // found a complete path
    } else {
        allPaths(maze, r-1, c, destR, destC, visited, path, result);  // up
        allPaths(maze, r+1, c, destR, destC, visited, path, result);  // down
        allPaths(maze, r, c-1, destR, destC, visited, path, result);  // left
        allPaths(maze, r, c+1, destR, destC, visited, path, result);  // right
    }

    path.remove(path.size() - 1);
    visited[r][c] = false;                                // UNMARK
}
```

---

### B4-F05 · Word Search — find a word starting from any cell

```java
boolean wordSearch(char[][] board, String word, int r, int c, int k, boolean[][] visited) {
    // SUCCESS: matched all characters
    if (k == word.length()) return true;

    // INVALID: out of bounds, visited, or wrong character
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) return false;
    if (visited[r][c] || board[r][c] != word.charAt(k)) return false;

    visited[r][c] = true;    // MARK

    boolean found = wordSearch(board, word, r-1, c, k+1, visited)
                 || wordSearch(board, word, r+1, c, k+1, visited)
                 || wordSearch(board, word, r, c-1, k+1, visited)
                 || wordSearch(board, word, r, c+1, k+1, visited);

    visited[r][c] = false;   // UNMARK (must be available for other starting positions)

    return found;
}

boolean search(char[][] board, String word) {
    boolean[][] visited = new boolean[board.length][board[0].length];
    for (int r = 0; r < board.length; r++) {
        for (int c = 0; c < board[0].length; c++) {
            if (wordSearch(board, word, r, c, 0, visited)) return true;
        }
    }
    return false;
}
```

---

### B4-F06 · Find the size of the largest island

```java
int largestIsland(char[][] grid) {
    int maxSize = 0;
    for (int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[r][c] == '1') {
                int size = islandSize(grid, r, c);
                maxSize = Math.max(maxSize, size);
            }
        }
    }
    return maxSize;
}

int islandSize(char[][] grid, int r, int c) {
    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return 0;
    if (grid[r][c] != '1') return 0;

    grid[r][c] = '0';   // mark visited

    return 1
        + islandSize(grid, r-1, c)
        + islandSize(grid, r+1, c)
        + islandSize(grid, r, c-1)
        + islandSize(grid, r, c+1);
}
```

---

## Grid DFS — Mark vs Unmark Decision

| Situation | Mark? | Unmark? | Why |
| --- | --- | --- | --- |
| Counting regions (islands) | Yes | No | Each cell belongs to exactly one region |
| Flood fill | Yes (paint it) | No | Permanent color change |
| Finding if ANY path exists | Yes | Yes | Other paths may need to use this cell |
| Finding ALL paths | Yes | Yes | Each path must independently decide which cells to use |
| Word Search | Yes | Yes | Same word can start from multiple positions |

---

---

# SUB-PATTERN 5 — Partition / Segmentation

## Core Idea

You have a string or array. At each position, you try **all possible ways to cut** from here to some future position. The cut is only valid if the piece satisfies a condition.

```
Input: "aab"
Partition into palindromic pieces:

Position 0 → try cuts: "a"|"ab", "aa"|"b", "aab"|""
  cut "a" (is palindrome?) YES → recurse on "ab" from index 1
    Position 1 → try: "a"|"b", "ab"|""
      cut "a" → recurse on "b" from index 2
        Position 2 → cut "b" → recurse from index 3 (done) → add ["a","a","b"]
      cut "ab" (palindrome?) NO → skip
  cut "aa" (palindrome?) YES → recurse on "b" from index 2
    cut "b" → done → add ["aa","b"]
  cut "aab" (palindrome?) NO → skip
```

## The Template

```java
void partition(String s, int start, List<String> current, List<List<String>> result) {

    // BASE CASE: consumed the entire string
    if (start == s.length()) {
        result.add(new ArrayList<>(current));
        return;
    }

    // TRY all possible end positions for the current piece
    for (int end = start + 1; end <= s.length(); end++) {
        String piece = s.substring(start, end);

        if (isValid(piece)) {                          // PRUNING: only valid pieces
            current.add(piece);                        // 1. CHOOSE
            partition(s, end, current, result);        // 2. EXPLORE (from end, not end+1)
            current.remove(current.size() - 1);        // 3. UNCHOOSE
        }
    }
}
```

**The `end` index goes from `start+1` to `s.length()`** — you try every possible “first piece” from the current position.

---

## Foundation Problems — Partition/Segmentation

### B5-F01 · Palindrome Partitioning — split string into all-palindrome parts

```java
void palindromePartition(String s, int start, List<String> current, List<List<String>> result) {
    if (start == s.length()) {
        result.add(new ArrayList<>(current));
        return;
    }

    for (int end = start + 1; end <= s.length(); end++) {
        if (isPalindrome(s, start, end - 1)) {
            current.add(s.substring(start, end));               // 1. CHOOSE
            palindromePartition(s, end, current, result);       // 2. EXPLORE
            current.remove(current.size() - 1);                 // 3. UNCHOOSE
        }
    }
}

boolean isPalindrome(String s, int l, int r) {
    while (l < r) {
        if (s.charAt(l++) != s.charAt(r--)) return false;
    }
    return true;
}
```

---

### B5-F02 · All ways to split string into dictionary words (Word Break II)

```java
void wordBreak(String s, Set<String> dict, int start, List<String> current, List<String> result) {
    if (start == s.length()) {
        result.add(String.join(" ", current));
        return;
    }

    for (int end = start + 1; end <= s.length(); end++) {
        String word = s.substring(start, end);
        if (dict.contains(word)) {
            current.add(word);
            wordBreak(s, dict, end, current, result);
            current.remove(current.size() - 1);
        }
    }
}
```

---

### B5-F03 · Restore IP Addresses — split into 4 valid parts

```java
void restoreIP(String s, int start, int part, List<String> current, List<String> result) {
    if (part == 4 && start == s.length()) {
        result.add(String.join(".", current));
        return;
    }
    if (part == 4 || start == s.length()) return;

    for (int len = 1; len <= 3; len++) {
        if (start + len > s.length()) break;
        String segment = s.substring(start, start + len);
        if (isValidIP(segment)) {
            current.add(segment);
            restoreIP(s, start + len, part + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
}

boolean isValidIP(String seg) {
    if (seg.length() > 1 && seg.charAt(0) == '0') return false;  // no leading zeros
    int val = Integer.parseInt(seg);
    return val >= 0 && val <= 255;
}
```

---

---

# THE MASTER DECISION FLOWCHART — Which Pattern?

```
NEW BACKTRACKING PROBLEM
        │
        ▼
Q1: Am I on a 2D GRID, moving between cells?
    │
    YES → PATTERN 4 (Grid DFS)
    │     • 4 directions, mark/unmark visited
    │     • Mark permanent? → No unmark
    │     • Finding paths? → Unmark after
    │
    NO
    │
    ▼
Q2: Am I FILLING SLOTS one at a time (N-Queens, Sudoku, coloring)?
    │
    YES → PATTERN 3 (Constraint Placement)
    │     • Outer recursion = which slot
    │     • For loop = which value to try
    │     • isValid() check before placing
    │
    NO
    │
    ▼
Q3: Am I CUTTING a string/array into valid pieces?
    │
    YES → PATTERN 5 (Partition)
    │     • For loop: try all cut positions
    │     • isValid() check on each piece
    │     • Base case: start == s.length()
    │
    NO
    │
    ▼
Q4: At each element, do I have exactly 2 choices (take it or skip it)?
    │
    YES → PATTERN 1 (Include/Exclude)
    │     • No for loop — 2 hardcoded calls
    │     • No explicit undo for exclude branch
    │     • 2^n leaves in call tree
    │
    NO (multiple choices at each step)
    │
    ▼
    PATTERN 2 (For-Loop)
          • For loop through choices
          • Explicit add before recurse, remove after
          • Use start index for combinations (no repeat)
          • Use used[] for permutations (order matters)
```

---

---

# LEETCODE PROBLEMS — Sub-Pattern Wise

---

## Pattern 1: Include / Exclude

### 🟢 Easy

#### LC-1863 · Sum of All Subset XOR Totals

🔗 https://leetcode.com/problems/sum-of-all-subset-xor-totals/**Pattern**: Include/Exclude. At each element: include it (XOR it in) or exclude it.
Generate all subsets, compute XOR total of each, sum them all.

```
subsetXOR(nums, index, currentXOR):
    if index == nums.length → return currentXOR
    return subsetXOR(index+1, currentXOR ^ nums[index])   // include
         + subsetXOR(index+1, currentXOR)                  // exclude
```

---

#### LC-2044 · Count Number of Maximum Bitwise-OR Subsets

🔗 https://leetcode.com/problems/count-number-of-maximum-bitwise-or-subsets/**Pattern**: Include/Exclude. Generate all subsets, track running OR value, count those achieving max.
Same structure as F01 but compute OR instead of adding elements.

---

### 🟡 Medium

#### LC-78 · Subsets

🔗 https://leetcode.com/problems/subsets/**Pattern**: Pure Include/Exclude. The canonical Pattern 1 problem.
Generate all 2^n subsets. Implement using the B1-F01 template exactly.

---

#### LC-416 · Partition Equal Subset Sum

🔗 https://leetcode.com/problems/partition-equal-subset-sum/**Pattern**: Include/Exclude with target sum. Can we find a subset summing to total/2?
Implement B1-F06 exactly. (DP solution exists but backtracking is the foundation.)

---

#### LC-698 · Partition to K Equal Sum Subsets

🔗 https://leetcode.com/problems/partition-to-k-equal-sum-subsets/**Pattern**: Extended subset sum — partition into k groups, each with sum = total/k.
Include/Exclude with extra state: which bucket current element goes into.

---

### 🔴 Hard

#### LC-1255 · Maximum Score Words Formed by Letters

🔗 https://leetcode.com/problems/maximum-score-words-formed-by-letters/**Pattern**: Include/Exclude over words. For each word: include it (if letters available) or exclude it.
Track remaining letter counts as state.

---

---

## Pattern 2: For-Loop Choice

### 🟢 Easy

#### LC-784 · Letter Case Permutation

🔗 https://leetcode.com/problems/letter-case-permutation/**Pattern**: For-Loop with 2 choices at each letter (upper or lower case).
Digits have no choice. Letters branch into 2 options.

---

#### LC-401 · Binary Watch

🔗 https://leetcode.com/problems/binary-watch/**Pattern**: Choose which lights are ON. Given n lights on, generate all valid times.
Include/Exclude on 10 binary bits (4 hours + 6 minutes), filter valid times.

---

### 🟡 Medium

#### LC-46 · Permutations

🔗 https://leetcode.com/problems/permutations/**Pattern**: Pure For-Loop. At each position, try every unused number.
Implement B2-F01 exactly with used[] array.

---

#### LC-77 · Combinations

🔗 https://leetcode.com/problems/combinations/**Pattern**: For-Loop with start index. Choose k numbers from 1..n.
Implement B2-F02 exactly.

---

#### LC-39 · Combination Sum

🔗 https://leetcode.com/problems/combination-sum/**Pattern**: For-Loop where same element can be reused (pass `i` not `i+1`).
Implement B2-F03 exactly.

---

#### LC-22 · Generate Parentheses

🔗 https://leetcode.com/problems/generate-parentheses/**Pattern**: Constrained For-Loop. Two choices (‘(’ and ‘)’) with validity constraints.
Implement B2-F07 exactly.

---

#### LC-17 · Letter Combinations of a Phone Number

🔗 https://leetcode.com/problems/letter-combinations-of-a-phone-number/**Pattern**: For-Loop over mapped letters for each digit.
Implement B2-F04 exactly.

---

### 🔴 Hard

#### LC-37 · Sudoku Solver

🔗 https://leetcode.com/problems/sudoku-solver/**Pattern**: Constraint Placement (Pattern 3) but listed here because many solve it with for-loop.
Try digits 1-9 for each empty cell. Validity: row, column, 3x3 box all unique.
Implement B3-F04 exactly.

---

#### LC-40 · Combination Sum II (no reuse, has duplicates)

🔗 https://leetcode.com/problems/combination-sum-ii/**Pattern**: For-Loop with dedup trick. Sort first, skip `nums[i]==nums[i-1] && i>start`.
Combines B2-F03 (combo sum) and B2-F06 (dedup trick).

---

---

## Pattern 3: Constraint Placement

### 🟡 Medium

#### LC-526 · Beautiful Arrangement

🔗 https://leetcode.com/problems/beautiful-arrangement/**Pattern**: Constraint Placement. Fill positions 1..n. A number i fits at position k if i%k==0 or k%i==0.
Count all valid arrangements.

---

#### LC-1307 · Verbal Arithmetic Puzzle

🔗 https://leetcode.com/problems/verbal-arithmetic-puzzle/**Pattern**: Assign digit 0-9 to each letter. Constraint: equation must hold.
(Advanced — attempt after N-Queens is solid.)

---

### 🔴 Hard

#### LC-51 · N-Queens

🔗 https://leetcode.com/problems/n-queens/**Pattern**: Pure Constraint Placement. One queen per row. Check column + both diagonals.
Implement B3-F02 exactly. **The canonical constraint placement problem.**

---

#### LC-52 · N-Queens II

🔗 https://leetcode.com/problems/n-queens-ii/**Pattern**: Same as LC-51 but only count solutions, don’t store board.
Simpler to implement — same constraint check, just increment a counter at base case.

---

---

## Pattern 4: Grid / Matrix DFS

### 🟢 Easy

#### LC-733 · Flood Fill

🔗 https://leetcode.com/problems/flood-fill/**Pattern**: Grid DFS, no unmark. Permanent color fill.
Implement B4-F01 exactly. **First grid DFS problem to solve.**

---

#### LC-695 · Max Area of Island

🔗 https://leetcode.com/problems/max-area-of-island/**Pattern**: Grid DFS counting cells per region. No unmark.
Implement B4-F06 exactly (find largest island size).

---

#### LC-1020 · Number of Enclaves

🔗 https://leetcode.com/problems/number-of-enclaves/**Pattern**: Sink (DFS) all islands touching the border. Count remaining land cells.
Two-pass approach: first sink border-connected islands, then count.

---

### 🟡 Medium

#### LC-200 · Number of Islands

🔗 https://leetcode.com/problems/number-of-islands/**Pattern**: Grid DFS, count connected components of ’1’s.
Implement B4-F02 exactly. **The canonical grid DFS problem.**

---

#### LC-79 · Word Search

🔗 https://leetcode.com/problems/word-search/**Pattern**: Grid DFS with string matching. Mark + unmark (path finding).
Implement B4-F05 exactly.

---

#### LC-130 · Surrounded Regions

🔗 https://leetcode.com/problems/surrounded-regions/**Pattern**: Sink all ‘O’ regions connected to the border. Flip remaining ‘O’ to ‘X’.
DFS from border cells. No unmark (region marking).

---

#### LC-417 · Pacific Atlantic Water Flow

🔗 https://leetcode.com/problems/pacific-atlantic-water-flow/**Pattern**: Two separate DFS passes (one from Pacific border, one from Atlantic).
Find cells reachable in both passes. No unmark.

---

### 🔴 Hard

#### LC-212 · Word Search II

🔗 https://leetcode.com/problems/word-search-ii/**Pattern**: Grid DFS + Trie pruning. Search multiple words simultaneously.
Standard word search but prune paths not matching any word prefix.

---

#### LC-329 · Longest Increasing Path in a Matrix

🔗 https://leetcode.com/problems/longest-increasing-path-in-a-matrix/**Pattern**: Grid DFS with memoization. No mark/unmark needed (strictly increasing = no cycles).
DFS from each cell, cache the longest path starting at each cell.

---

---

## Pattern 5: Partition / Segmentation

### 🟡 Medium

#### LC-131 · Palindrome Partitioning

🔗 https://leetcode.com/problems/palindrome-partitioning/**Pattern**: Pure Partition. Try all cut positions, check if piece is palindrome.
Implement B5-F01 exactly. **The canonical partition problem.**

---

#### LC-93 · Restore IP Addresses

🔗 https://leetcode.com/problems/restore-ip-addresses/**Pattern**: Partition into exactly 4 valid IP segments (0-255, no leading zeros).
Implement B5-F03 exactly.

---

#### LC-139 · Word Break (decision version)

🔗 https://leetcode.com/problems/word-break/**Pattern**: Partition — can we cut the string so every piece is in the dictionary?
Return true/false. B5-F02 simplified to boolean.

---

### 🔴 Hard

#### LC-140 · Word Break II (all ways)

🔗 https://leetcode.com/problems/word-break-ii/**Pattern**: Partition — find ALL ways to split string into dictionary words.
Implement B5-F02 exactly. (Add memoization to handle large inputs.)

---

---

# MASTER SUMMARY TABLE

| Pattern | Core Mechanic | Template Shape | Call Tree Shape | Problems |
| --- | --- | --- | --- | --- |
| **1 Include/Exclude** | 2 hardcoded calls | No loop, 2 calls | Perfect binary | Subsets, Subset Sum |
| **2 For-Loop** | Loop + choose/unchoose | For loop + recurse | Varies (n!, C(n,k)) | Permutations, Combinations |
| **3 Constraint Placement** | Place if valid, fill slots | For loop + isValid | Heavily pruned | N-Queens, Sudoku |
| **4 Grid DFS** | 4-directional move, mark/unmark | 4 recursive calls | Region or path tree | Islands, Word Search |
| **5 Partition** | Try all cut points, check validity | For loop on end index | Varies | Palindrome split, Word Break |

---

## The 4 Rules That Apply to ALL Patterns

```
RULE 1: Always COPY when adding to results.
        result.add(new ArrayList<>(current))  ← NOT result.add(current)

RULE 2: Always UNCHOOSE after recursion (except in Pattern 1's exclude branch).
        The state before and after a for-loop iteration must be identical.

RULE 3: Prune early. Check constraints BEFORE recursing, not after.
        Bad:  recurse() → check if valid → discard invalid
        Good: check if valid → recurse() (only for valid branches)

RULE 4: For Grid DFS — mark before exploring, decide whether to unmark based on
        whether you're doing region-marking (no unmark) or path-finding (unmark).
```

---

> **After mastering this file**: open `Recursion_Mastery.md` for the LeetCode problem index.
Then move to **Phase 4: Trees** — tree DFS is just Grid DFS without directions (use children).
N-Queens → Tree Construction → Graph DFS: they all use the same Choose-Explore-Unchoose rhythm.
>