"""Companion-only: book shows LLP-FloydWarshall only as LaTeX pseudocode,
no .llp counterpart to sync from -- see COMPANION_ONLY_STEMS in
sync-code-from-lang.py or a sync run will delete this file.
k stages the intermediate vertex 0..n-1; each stage's forbidden/advance
(i,j) fixpoint must sit directly in the k-stage body, not nested inside

Auto-generated from LLPFloydWarshall.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPFloydWarshall:
    def LLPFloydWarshall(self, A):
        self.A = A
        self.k = 0
        self.n = len(self.A)
        self.G = [[0.0] * (self.n) for _ in range(self.n)]
        for i in range(0, ((self.n - 1)) + 1):
            for j in range(0, ((self.n - 1)) + 1):
                self.G[i][j] = self.A[i][j]
        while (self.k < self.n):
            _changed = True
            while _changed:
                _changed = False
                for i in range(0, (self.n - 1) + 1):
                    for j in range(0, ((self.n - 1)) + 1):
                        if (not (self.G[i][j] <= (self.G[i][self.k] + self.G[self.k][j]))):
                            self.G[i][j] = (self.G[i][self.k] + self.G[self.k][j])
                            _changed = True
            self.k = (self.k + 1)
        return self.G


if __name__ == '__main__':
    A = [[1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0], [9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0], [17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0], [25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0], [33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0], [41.0, 42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0], [49.0, 50.0, 51.0, 52.0, 53.0, 54.0, 55.0, 56.0], [57.0, 58.0, 59.0, 60.0, 61.0, 62.0, 63.0, 64.0]]
    prog = LLPFloydWarshall()
    result = prog.LLPFloydWarshall(A)
    print(result)

