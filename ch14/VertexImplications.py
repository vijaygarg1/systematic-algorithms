"""VertexImplications: if v is in the cover and (v,w) is an implication,
then w must also be in the cover.
forbidden(w): exists v with (v,w) in I and G[v]=1 and G[w]=0.
advance: G[w] := 1.

The implications arrive as reverse adjacency lists: inI[w] holds the v
with (v,w) in I, which is exactly the set the forbidden condition
quantifies over. A dense n x n matrix would make the guard scan all n
candidates for every w, so a sweep would cost Theta(n^2) however few
implications there are -- and reading the matrix is Theta(n^2) before
the first guard is even evaluated.

Auto-generated from VertexImplications.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class VertexImplications:
    def VertexImplications(self, inI, G):
        self.inI = inI
        self.G = G
        self.n = len(self.inI)
        _changed = True
        while _changed:
            _changed = False
            for w in range(0, ((self.n - 1)) + 1):
                if any((self.G[v] and (not self.G[w])) for v in self.inI[w]):
                    self.G[w] = True
                    _changed = True


