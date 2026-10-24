"""LLP-OptimalBinarySearchTree: ensure G[i][j] >= optimal cost over interval [i..j].

setPriority, not setContainer: the key j-i is fixed, every index the
scheduler offers is already forbidden, and the advance only ever raises
G[i][j] to a value it must reach anyway -- so the answer is the same in
any order. Shortest intervals first merely reaches the fixpoint in one
sweep instead of several.

PRECONDITION on the index pair: G[i][j] is the cost of the key range
[i..j], which only names a range when i <= j. The guard says so
explicitly, because the fixpoint scans the full (i, j) square: without
the i <= j conjunct it also fired on the empty ranges below the
diagonal, where optCost's forall over k in [i..j] iterates zero times and
returns its seed, writing that seed into G[i][j] as though it were a
cost. Those cells are never read -- every reference to G is guarded by
k > i or k < j -- so the answers above the diagonal were right either
way, but the lower triangle came back full of sentinel values.

Auto-generated from LLPOptimalBinarySearchTree.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPOptimalBinarySearchTree:
    def LLPOptimalBinarySearchTree(self, p, G):
        self.p = p
        self.G = G
        self.n = len(self.p)
        _changed = True
        while _changed:
            _changed = False
            for i in range(0, (self.n - 1) + 1):
                for j in range(0, (self.n - 1) + 1):
                    if ((i <= j) and (self.G[i][j] < self.optCost(self.p, self.G, i, j))):
                        self.G[i][j] = self.optCost(self.p, self.G, i, j)
                        _changed = True

    def optCost(self, p, G, i, j):
        best = float('inf')
        s = 0.0
        for l in range(i, (j) + 1):
            s = (s + p[l])
        for k in range(i, (j) + 1):
            leftCost = 0.0
            if (k > i):
                leftCost = G[i][(k - 1)]
            rightCost = 0.0
            if (k < j):
                rightCost = G[(k + 1)][j]
            cost = ((leftCost + s) + rightCost)
            best = min(best, cost)
        return best


if __name__ == '__main__':
    p = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]
    G = [[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0], [9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0], [17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0], [25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0], [33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0], [41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0], [49.0, 50.0, 51.0, 52.0, 53.0, 54.0, 55.0, 56.0], [57.0, 58.0, 59.0, 60.0, 61.0, 62.0, 63.0, 64.0]]
    prog = LLPOptimalBinarySearchTree()
    prog.LLPOptimalBinarySearchTree(p, G)
    print(p)
    print(G)

