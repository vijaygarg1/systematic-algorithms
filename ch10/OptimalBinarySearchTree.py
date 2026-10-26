"""Optimal BST: O(n³) DP over interval [i, j] picking the root r.

Auto-generated from OptimalBinarySearchTree.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class OptimalBinarySearchTree:
    def solve(self, prob):
        self.prob = prob
        self.n = len(self.prob)
        self.dp = [[0.0] * (self.n) for _ in range(self.n)]
        self.s = [[0.0] * (self.n) for _ in range(self.n)]
        for i in range(0, ((self.n - 1)) + 1):
            self.dp[i][i] = self.prob[i]
            self.s[i][i] = self.prob[i]
        for len_ in range(1, ((self.n - 1)) + 1):
            for lo in range(0, (((self.n - 1) - len_)) + 1):
                hi = (lo + len_)
                self.s[lo][hi] = (self.s[lo][(hi - 1)] + self.prob[hi])
                self.dp[lo][hi] = float('inf')
                for r in range(lo, (hi) + 1):
                    left = 0.0
                    right = 0.0
                    if (r > lo):
                        left = self.dp[lo][(r - 1)]
                    if (r < hi):
                        right = self.dp[(r + 1)][hi]
                    cost = ((self.s[lo][hi] + left) + right)
                    self.dp[lo][hi] = min(self.dp[lo][hi], cost)
        return self.dp


if __name__ == '__main__':
    prob = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]
    prog = OptimalBinarySearchTree()
    result = prog.solve(prob)
    print(result)

