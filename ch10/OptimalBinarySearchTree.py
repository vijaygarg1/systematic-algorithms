"""Optimal BST: O(n³) DP over interval [i, j] picking the root r."""

import math


def solve(prob):
    n = len(prob)
    dp = [[0.0] * n for _ in range(n)]
    s  = [[0.0] * n for _ in range(n)]
    for i in range(n):
        dp[i][i] = prob[i]
        s[i][i]  = prob[i]
    for length in range(1, n):
        for lo in range(n - length):
            hi = lo + length
            s[lo][hi] = s[lo][hi - 1] + prob[hi]
            best = math.inf
            for r in range(lo, hi + 1):
                left  = dp[lo][r - 1] if r > lo else 0.0
                right = dp[r + 1][hi] if r < hi else 0.0
                cost  = s[lo][hi] + left + right
                if cost < best:
                    best = cost
            dp[lo][hi] = best
    return dp


if __name__ == "__main__":
    prob = [0.25, 0.20, 0.30, 0.25]
    dp = solve(prob)
    print(f"optimum BST cost = {dp[0][len(prob) - 1]:.3f}")
