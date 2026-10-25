"""LLP-BellmanFord: when some G[j] is improvable, run one Jacobi-style relaxation.
The advance leaves G[0] alone -- the source distance is pinned at 0 -- and
recomputes every other G[k] from its predecessors, as in the book's box.

The book writes the weight as w(i,j), with parentheses, to say that it
comes from the edge and not from an n x n array -- an array would cost
Theta(n^2) to build before Bellman-Ford's own O(nm) could start. So the
edges arrive as two parallel ragged arrays: pre[j] lists j's predecessors
and pw[j][t] is the weight of the edge pre[j][t] -> j. Both are O(m).
LLPJohnson.llp uses the same pair.

G is double, so an unreached predecessor contributes infinity + w =
infinity under IEEE arithmetic and never wins the min. (The int version
of this pattern overflows instead -- see LLPBFS.llp.)

Auto-generated from LLPBellmanFord.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPBellmanFord:
    def LLPBellmanFord(self, pre, pw):
        self.pre = pre
        self.pw = pw
        self.n = len(self.pre)
        self.G = [float('inf')] * (self.n)
        self.G[0] = 0
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if any((self.G[j] > (self.G[self.pre[j][t]] + self.pw[j][t])) for t in range(0, ((len(self.pre[j]) - 1)) + 1)):
                    for k in range(1, ((self.n - 1)) + 1):
                        self.G[k] = min(((self.G[self.pre[k][t]] + self.pw[k][t]) for t in range(0, ((len(self.pre[k]) - 1)) + 1)), default=float('inf'))
                    _changed = True
        return self.G


if __name__ == '__main__':
    pre = [[], [0], [0, 1], [0, 1, 2], [0, 1, 2, 3], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 5, 6]]
    pw = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    prog = LLPBellmanFord()
    result = prog.LLPBellmanFord(pre, pw)
    print(result)

