"""Fulkerson reduction: glue chains via matched edges, contract by pointer-jumping.

Auto-generated from ChainCoverFromMatching.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class ChainCoverFromMatching:
    def ChainCoverFromMatching(self, matchPartner):
        self.matchPartner = matchPartner
        self.n = len(self.matchPartner)
        self.C = [0] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.C[i] = i
        for u in range(0, ((self.n - 1)) + 1):
            v = self.matchPartner[u]
            if (v != (0 - 1)):
                self.C[u] = v
        self.parentPointerJumping(self.C)
        return self.C

    def parentPointerJumping(self, C):
        n = len(C)
        changed = True
        while changed:
            changed = False
            for i in range(0, ((n - 1)) + 1):
                p = C[i]
                if (C[p] != p):
                    C[i] = C[p]
                    changed = True


if __name__ == '__main__':
    matchPartner = [3, 1, 6, 1, 6, 3, 6, 4]
    prog = ChainCoverFromMatching()
    result = prog.ChainCoverFromMatching(matchPartner)
    print(result)

