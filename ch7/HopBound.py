"""HopBound: composition program enforcing a hop budget on the
shortest-path relaxation chain.  h[j] is the number of edges along
the chain that established G[j]; the constraint is h[j] <= k.
Composed onto LLP-Dijkstra via predicate conjunction:
[ LLP-Dijkstra-Hops(s, w, G, h) && HopBound(h, k) ].

Auto-generated from HopBound.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class HopBound:
    def HopBound(self, h, k, G):
        self.h = h
        self.k = k
        self.G = G
        self.n = len(self.h)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if (self.h[j] > self.k):
                    return None
                    _changed = True
        return self.G


if __name__ == '__main__':
    h = [0, 0, 1, 2]
    k = 0
    G = [0, 0, 1, 1]
    prog = HopBound()
    result = prog.HopBound(h, k, G)
    print(result)

