"""Classical O(n²) LIS DP."""

def solve(A):
    n = len(A)
    dp = [1] * n
    for i in range(1, n):
        for j in range(i):
            if A[j] < A[i] and dp[j] + 1 > dp[i]:
                dp[i] = dp[j] + 1
    return dp


if __name__ == "__main__":
    A = [3, 10, 2, 1, 20, 4]
    dp = solve(A)
    print(f"A = {A}")
    print(f"dp = {dp}, LIS length = {max(dp)}")
