"""Floyd-Warshall APSP: triple loop on intermediate vertex, matching the
book's three nested \For loops (bxx-shortestPath.tex, alg:floyd-warshall).

Auto-generated from FloydWarshall.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class FloydWarshall:
    def FloydWarshall(self, G):
        self.G = G
        self.n = len(self.G)
        for k in range(0, ((self.n - 1)) + 1):
            for i in range(0, ((self.n - 1)) + 1):
                for j in range(0, ((self.n - 1)) + 1):
                    if ((self.G[i][k] != 2147483647) and (self.G[k][j] != 2147483647)):
                        if ((self.G[i][k] + self.G[k][j]) < self.G[i][j]):
                            self.G[i][j] = (self.G[i][k] + self.G[k][j])


if __name__ == '__main__':
    G = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    prog = FloydWarshall()
    prog.FloydWarshall(G)
    print(G)

