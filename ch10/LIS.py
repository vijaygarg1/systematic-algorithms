"""Classical O(n²) LIS DP.

Auto-generated from LIS.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LIS:
    def solve(self, A):
        self.A = A
        self.n = len(self.A)
        self.dp = [0] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.dp[i] = 1
        for i in range(1, ((self.n - 1)) + 1):
            for j in range(0, ((i - 1)) + 1):
                if (self.A[j] < self.A[i]):
                    self.dp[i] = max(self.dp[i], (self.dp[j] + 1))
        return self.dp


if __name__ == '__main__':
    A = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = LIS()
    result = prog.solve(A)
    print(result)

