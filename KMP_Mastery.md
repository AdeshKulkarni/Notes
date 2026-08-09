# 🧬 KMP (Knuth–Morris–Pratt) — Mastery Guide

> **Goal**: Understand the ONE idea behind KMP so deeply that you never need to memorize the
> code again. Every "KMP problem" — easy or hard — is just this one idea applied in a slightly
> different costume.

> This file is the LeetCode-focused companion to Pattern 2 in `String_Algorithms_Tracker.md`
> (which covers KMP through CSES + Codeforces). Use that file for CSES/CF practice, use this
> one for interview-style LeetCode practice, Google/Amazon/Adobe-relevant.

---

## 📌 Part 1 — What KMP Actually Is

**KMP (Knuth–Morris–Pratt)** is an algorithm (not a data structure, not a "pattern" in the design
sense — a concrete algorithm) that answers one question:

> *"Does pattern `P` occur inside text `T`? Where? How many times?"*

...in **O(|T| + |P|)** time, instead of the naive **O(|T| × |P|)**.

That's it. It's a string-matching algorithm. Everything else you'll see it used for (borders,
periods, palindrome tricks) is a clever *reuse* of the same underlying array it builds.

---

## 🎯 Part 2 — The Core Idea (Read This Twice — This Is the Whole Algorithm)

### The problem with the naive approach

To check if `P` occurs in `T`, the naive way slides `P` across `T` one position at a time and
re-compares from scratch every time there's a mismatch.

```
T = a a a a a a a a b
P = a a a a b
```

At every shift, you re-confirm characters you *already knew* matched. That's the waste. If you
matched 4 characters of `P` before failing, you already know `T`'s last 4 characters were
`"aaaa"` — throwing that away and starting over from position+1 is pure waste.

### The one insight that fixes everything

> **When a mismatch happens, don't ask "where do I restart the whole match?" Ask instead:
> "Given everything I've already matched, how much of it can I keep?"**

The answer to "how much can I keep" depends *only on the pattern itself*, not on the text. So we
can precompute it once, before ever looking at the text.

### The LPS array (aka "failure function" / "prefix function")

For the pattern `P`, build an array `lps` where:

```
lps[i] = length of the longest proper prefix of P[0..i] that is ALSO a suffix of P[0..i]
```

("proper" = not the whole string itself.)

Example: `P = "ababac"`

```
index:   0  1  2  3  4  5
char:    a  b  a  b  a  c
lps:     0  0  1  2  3  0
```

Read `lps[4] = 3` as: *"the substring `ababa` has a prefix of length 3 (`aba`) that's also its
suffix."* That's the ONLY fact this array stores, at every position.

### Why this array alone gives you O(n+m) matching

While scanning the text, the moment you hit a mismatch after matching `k` characters of `P`, you
don't restart the pattern pointer at 0 — you jump it to `lps[k-1]`. Why is this safe? Because
`lps[k-1]` characters of the pattern are *guaranteed* to already be sitting in the text right
before your current position (that's literally the definition of `lps`). You're not re-checking
anything you don't already know.

### The one invariant that makes it fast

> **The pointer on the TEXT never moves backward. Ever.**

Only the pointer on the *pattern* jumps around (using `lps`). Since the text pointer only ever
moves forward, across the whole algorithm it moves at most `|T|` times — giving you the O(n+m)
bound. This single invariant is the entire proof of why KMP is linear. You don't need to memorize
more than this.

---

## 🏗️ Part 3 — How To Apply KMP (The Recipe)

**Step 1 — Build the LPS array for the pattern** (this is 90% of "writing KMP"):

```java
int[] buildLPS(String p) {
    int n = p.length();
    int[] lps = new int[n];
    int len = 0; // length of the previous longest prefix-suffix
    for (int i = 1; i < n; i++) {
        while (len > 0 && p.charAt(i) != p.charAt(len)) {
            len = lps[len - 1];   // fall back using lps itself
        }
        if (p.charAt(i) == p.charAt(len)) {
            len++;
        }
        lps[i] = len;
    }
    return lps;
}
```

**Step 2 — Scan the text using the LPS array to know where to fall back on mismatch:**

```java
List<Integer> kmpSearch(String t, String p) {
    int[] lps = buildLPS(p);
    List<Integer> matches = new ArrayList<>();
    int i = 0, j = 0; // i = text pointer, j = pattern pointer
    while (i < t.length()) {
        if (t.charAt(i) == p.charAt(j)) {
            i++; j++;
            if (j == p.length()) {
                matches.add(i - j);   // found a match
                j = lps[j - 1];
            }
        } else if (j > 0) {
            j = lps[j - 1];           // fall back, DON'T move i
        } else {
            i++;                      // no fallback possible, move i
        }
    }
    return matches;
}
```

**Step 3 — Recognize the "self-matching trick" for harder problems.** Many hard LeetCode
problems don't ask you to match two *different* strings — they build ONE combined string and run
`buildLPS` on it:

```
Longest Happy Prefix   → lps[n-1] of the string itself IS the answer.
Shortest Palindrome    → build s + '#' + reverse(s), run buildLPS, read the last value.
```

This is the single trick that makes both "hard" problems in this list easy once you see it: the
failure function isn't just for searching — it's a general "how much self-overlap exists here?"
tool.

---

## 🔎 Part 4 — How To Identify a KMP Problem

| Signal in the problem statement | Why it's KMP |
|---|---|
| "Find all occurrences of X in Y" | Direct pattern matching |
| "Does X appear as a substring of Y?" | Direct pattern matching |
| "Shortest string that needs to be added to the front/back to make this a palindrome" | Self-matching trick |
| "Longest prefix which is also a suffix" (worded exactly like this) | It literally IS the LPS array |
| "How many times must you repeat A so that B fits inside it?" | Pattern matching against a virtually-repeated string |
| "The k-repeating value of a word inside a sequence" | Pattern matching, count consecutive matches |
| Two "landmark" substrings must occur within distance `k` of each other | Run KMP twice (once per pattern), then merge with two pointers |
| "Self-overlap" / "period" / "border" of a string | LPS array read directly |

### ❌ When It's NOT KMP
| Trap | Why |
|---|---|
| You need to compare MANY different pattern strings against one text | That's Aho-Corasick, not plain KMP |
| The string gets updated (edits) and you need repeated matching | That's Hashing + Segment Tree |
| You need info about ALL substrings at once (counting, ranking) | That's Suffix Array territory |
| Order/count of characters matters but not their positions/sequence | Not a matching problem at all — probably a frequency-count or two-pointer problem |

---

## 📐 Part 5 — Constraints That Scream KMP

| Constraint pattern | What it tells you |
|---|---|
| `1 <= text.length <= 10^5` or `10^6`, and you need EVERY occurrence | Naive O(n·m) will TLE — you need O(n+m) |
| Pattern length up to `10^4`–`10^5` combined with a large text | Same reasoning — brute force multiplies these together |
| A problem gives you TWO strings and asks about a relationship between their prefixes/suffixes | Strongly hints at building an LPS array over a combined string |
| Small constraints (`n <= 1000` or less) | KMP still works, but brute-force substring checks are also acceptable — don't over-engineer |

> **Rule of thumb**: if `|T| × |P|` exceeds roughly `10^7`–`10^8`, brute force will time out and
> you need KMP (or hashing). If it's comfortably under that, either approach passes — but solve it
> with KMP anyway while you're practicing, that's the whole point right now.

---
---

## 🧪 Part 6 — Practice Sheet (LeetCode Only)

> Solve in order. Easy tier = pure, unmistakable KMP. Medium = KMP mixed with another skill
> (two pointers, counting). Hard = the self-matching trick from Part 3, Step 3.

### 🟢 EASY — Pure KMP Fundamentals

- [ ] **28. Find the Index of the First Occurrence in a String** — https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/
  *The canonical problem. Build LPS, run the search, return the first match index.*
- [ ] **459. Repeated Substring Pattern** — https://leetcode.com/problems/repeated-substring-pattern/
  *Uses the "period" reading of the LPS array: `s` is built from repeats of a substring iff `n % (n - lps[n-1]) == 0`.*
- [ ] **796. Rotate String** — https://leetcode.com/problems/rotate-string/
  *Check if `goal` is a substring of `s + s` — direct KMP search application.*
- [ ] **1408. String Matching in an Array** — https://leetcode.com/problems/string-matching-in-an-array/
  *Run pattern search of every word against every other word.*
- [ ] **1668. Maximum Repeating Substring** — https://leetcode.com/problems/maximum-repeating-substring/
  *Small constraints (brute force also passes) — but solve it by matching `word` inside `sequence` with KMP for practice.*

### 🟡 MEDIUM — KMP Combined With Another Skill

- [ ] **686. Repeated String Match** — https://leetcode.com/problems/repeated-string-match/
  *Figure out the bound on how many repeats you could ever need, then KMP-search inside that virtual string.*
- [ ] **1764. Form Array by Concatenating Subarrays of Another Array** — https://leetcode.com/problems/form-array-by-concatenating-subarrays-of-another-array/
  *Constraints are small enough for brute force, but treat each `groups[i]` as a pattern to match against `nums` — good "sequence matching" practice, not just character strings.*
- [ ] **3006. Find Beautiful Indices in the Given Array I** — https://leetcode.com/problems/find-beautiful-indices-in-the-given-array-i/
  *Run KMP twice (once for pattern `a`, once for pattern `b`), then merge the two occurrence lists with two pointers using the distance constraint `k`. Great "combine KMP with another pattern" problem.*

### 🔴 HARD — The Self-Matching Trick (only 2 — as requested)

- [ ] **1392. Longest Happy Prefix** — https://leetcode.com/problems/longest-happy-prefix/
  *This problem IS the LPS array. Build it for the given string, `lps[n-1]` is your answer length.*
- [ ] **214. Shortest Palindrome** — https://leetcode.com/problems/shortest-palindrome/
  *Build `s + '#' + reverse(s)`, run `buildLPS`, the last value tells you the longest palindromic prefix of `s` — everything after it (reversed) needs to be prepended.*

### 🎁 Optional Stretch (only if you want more — not required)

- [ ] **3008. Find Beautiful Indices in the Given Array II** — same idea as 3006, tighter constraints (`10^5` vs `10^5` but tighter time limit forcing true O(n) KMP instead of any shortcuts).
- [ ] **2223. Sum of Scores of Built Strings** — this is actually the **Z-function**, KMP's close sibling. Good bridge problem once you start Pattern 3 (Z-function) in the tracker.

---

## ✅ Part 7 — How We'll Run This

1. Solve the 5 Easy problems first — don't move on until `buildLPS` feels like muscle memory
   *conceptually* (you should be able to explain what `lps[i]` means out loud without looking at code).
2. Solve the 3 Medium problems — these test whether you can combine KMP with two-pointer merging.
3. Solve the 2 Hard problems — these test whether you internalized the self-matching trick, not
   whether you can write more code.
4. Come back after each tier and tell me how it went (stuck / solved / TLE'd) — we debug together
   before moving to the next tier.
5. Once this file is fully checked off, we return to `String_Algorithms_Tracker.md` and move to
   Pattern 3 (Z-function) — where you'll re-solve a few of these same problems with a different
   technique, to feel the difference.

> Ready when you are — start with **28. Find the Index of the First Occurrence in a String**.
