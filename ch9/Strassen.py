"""Strassen's divide-and-conquer matrix multiplication over int[][].
Splitting each n x n operand into four n/2 quadrants, the product needs
only SEVEN recursive multiplications instead of the obvious eight, which
is what drops the exponent from 3 to log2(7) ~ 2.81. The saving comes
from M1..M7 below: each is one product of sums, and the four output
quadrants are recovered from them by addition alone.

Sizes are assumed to be powers of two, so every split is exact.

Auto-generated from Strassen.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Strassen:
    def multiply(self, A, B):
        self.n = len(A)
        if (self.n == 1):
            C1 = [[0] * (1) for _ in range(1)]
            C1[0][0] = (A[0][0] * B[0][0])
            return C1
        h = int(self.n / 2)
        A11 = self.quadrant(A, 0, 0, h)
        A12 = self.quadrant(A, 0, h, h)
        A21 = self.quadrant(A, h, 0, h)
        A22 = self.quadrant(A, h, h, h)
        B11 = self.quadrant(B, 0, 0, h)
        B12 = self.quadrant(B, 0, h, h)
        B21 = self.quadrant(B, h, 0, h)
        B22 = self.quadrant(B, h, h, h)
        M1 = self.multiply(self.add(A11, A22), self.add(B11, B22))
        M2 = self.multiply(self.add(A21, A22), B11)
        M3 = self.multiply(A11, self.sub(B12, B22))
        M4 = self.multiply(A22, self.sub(B21, B11))
        M5 = self.multiply(self.add(A11, A12), B22)
        M6 = self.multiply(self.sub(A21, A11), self.add(B11, B12))
        M7 = self.multiply(self.sub(A12, A22), self.add(B21, B22))
        C11 = self.add(self.sub(self.add(M1, M4), M5), M7)
        C12 = self.add(M3, M5)
        C21 = self.add(M2, M4)
        C22 = self.add(self.add(self.sub(M1, M2), M3), M6)
        return self.combine(C11, C12, C21, C22)

    def quadrant(self, M, r, c, h):
        Q = [[0] * (h) for _ in range(h)]
        for i in range(0, ((h - 1)) + 1):
            for j in range(0, ((h - 1)) + 1):
                Q[i][j] = M[(r + i)][(c + j)]
        return Q

    def add(self, X, Y):
        h = len(X)
        S = [[0] * (h) for _ in range(h)]
        for i in range(0, ((h - 1)) + 1):
            for j in range(0, ((h - 1)) + 1):
                S[i][j] = (X[i][j] + Y[i][j])
        return S

    def sub(self, X, Y):
        h = len(X)
        D = [[0] * (h) for _ in range(h)]
        for i in range(0, ((h - 1)) + 1):
            for j in range(0, ((h - 1)) + 1):
                D[i][j] = (X[i][j] - Y[i][j])
        return D

    def combine(self, C11, C12, C21, C22):
        h = len(C11)
        C = [[0] * ((2 * h)) for _ in range((2 * h))]
        for i in range(0, ((h - 1)) + 1):
            for j in range(0, ((h - 1)) + 1):
                C[i][j] = C11[i][j]
                C[i][(j + h)] = C12[i][j]
                C[(i + h)][j] = C21[i][j]
                C[(i + h)][(j + h)] = C22[i][j]
        return C


if __name__ == '__main__':
    A = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    B = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    prog = Strassen()
    result = prog.multiply(A, B)
    print(result)

