"""Fulkerson reduction: glue chains via matched edges, contract by pointer-jumping.

Auto-generated from ChainCoverFromMatching.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class ChainCoverFromMatching:
    def ChainCoverFromMatching(self, matchPartner):
        self.matchPartner = matchPartner
        self.n = len(self.matchPartner)
        self.C = [0] * (self.n)
        self.i = 0
        while (self.i < self.n):
            self.C[self.i] = self.i
            self.i = (self.i + 1)
        u = 0
        while (u < self.n):
            v = self.matchPartner[u]
            if (v != (0 - 1)):
                self.C[u] = v
            u = (u + 1)
        self.parentPointerJumping(self.C)
        return self.C

    def parentPointerJumping(self, C):
        n = len(C)
        changed = True
        while changed:
            changed = False
            i = 0
            while (i < n):
                p = C[i]
                if (C[p] != p):
                    C[i] = C[p]
                    changed = True
                i = (i + 1)


if __name__ == '__main__':
    matchPartner = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = ChainCoverFromMatching()
    result = prog.ChainCoverFromMatching(matchPartner)
    print(result)

