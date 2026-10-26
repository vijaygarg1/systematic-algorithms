"""LLP weighted interval scheduling. The book states B as two families of
inequalities, G[j] >= G[j-1] and G[j] >= w[j] + G[p[j]], which is
exactly what one `ensure` over their maximum says; the forbidden
predicate and advance are then read off B rather than restated.
Index 0 is pinned at 0, so the guard ranges over 1..n.

Auto-generated from LLPWeightedIntervalScheduling.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPWeightedIntervalScheduling:
    def LLPWeightedIntervalScheduling(self, w, p):
        self.w = w
        self.p = p
        self.n = len(self.w)
        self.G = [0] * (self.n)
        _changed = True
        while _changed:
            _changed = False
            for j in range(1, ((self.n - 1)) + 1):
                if (not (self.G[j] >= max(self.G[(j - 1)], (self.w[j] + self.G[self.p[j]])))):
                    self.G[j] = max(self.G[(j - 1)], (self.w[j] + self.G[self.p[j]]))
                    _changed = True
        return self.G


if __name__ == '__main__':
    w = [0, 0, 1, 2]
    p = [0, 0, 1, 1]
    prog = LLPWeightedIntervalScheduling()
    result = prog.LLPWeightedIntervalScheduling(w, p)
    print(result)

