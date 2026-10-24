"""ResourceBound: composition program enforcing a resource budget B on
the shortest-path relaxation chain.  rho[j] is the total resource
consumed along the chain that established G[j]; the constraint is
rho[j] <= B.  Composed onto LLP-Dijkstra via predicate conjunction:
[ LLP-Dijkstra-Res(s, w, r, G, rho) && ResourceBound(rho, B) ].

Auto-generated from ResourceBound.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class ResourceBound:
    def ResourceBound(self, rho, B, G):
        self.rho = rho
        self.B = B
        self.G = G
        self.n = len(self.rho)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if (self.rho[j] > self.B):
                    return None
                    _changed = True
        return self.G


if __name__ == '__main__':
    rho = [0, 0, 1, 2]
    B = 0
    G = [0, 0, 1, 1]
    prog = ResourceBound()
    result = prog.ResourceBound(rho, B, G)
    print(result)

