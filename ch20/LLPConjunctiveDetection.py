"""Conjunctive predicate detection (bxx-complexity.tex Alg LLP-ConjunctiveDetection).
Forbidden when P_j's selected state is INCOMPATIBLE with another: some i has
vc[i][G[i]][j] > vc[j][G[j]][j]. Restored 2026-10-26 -- `>= G[j]` compared an event
count against a filtered-trace index and rejected equality, losing real solutions.

Auto-generated from LLPConjunctiveDetection.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPConjunctiveDetection:
    def LLPConjunctiveDetection(self, vc, T):
        self.vc = vc
        self.T = T
        self.n = len(self.vc)
        self.G = [0] * (self.n)
        for k in range(0, ((self.n - 1)) + 1):
            self.G[k] = 1
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if self.incompatible(j, self.G, self.vc):
                    if (self.G[j] >= self.T[j]):
                        return None
                    else:
                        self.G[j] = (self.G[j] + 1)
                    _changed = True
        return self.G

    def incompatible(self, j, G, vc):
        n = len(G)
        for i in range(0, ((n - 1)) + 1):
            if ((i != j) and (vc[i][G[i]][j] > vc[j][G[j]][j])):
                return True
        return False


