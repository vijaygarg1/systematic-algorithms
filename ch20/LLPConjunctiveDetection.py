"""Conjunctive predicate detection: forbidden when G[j] -> G[i] (happened-before);
advance increments G[j] to the next local state.

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
                if self.happenedBefore(j, self.G, self.vc):
                    if (self.G[j] >= self.T[j]):
                        return None
                    else:
                        self.G[j] = (self.G[j] + 1)
                    _changed = True
        return self.G

    def happenedBefore(self, j, G, vc):
        n = len(G)
        for i in range(0, ((n - 1)) + 1):
            if ((i != j) and (vc[i][G[i]][j] >= G[j])):
                return True
        return False


