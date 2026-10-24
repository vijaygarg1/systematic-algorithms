"""LLP-Antichain: maximum antichain by advancing chain indices to dominate-free positions.

Auto-generated from LLPAntichain.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPAntichain:
    def LLPAntichain(self, chains, len_, leq):
        self.chains = chains
        self.len_ = len_
        self.leq = leq
        self.n = len(self.chains)
        self.G = [0] * (self.n)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if ((self.G[j] < self.len_[j]) and any(((k != j) and self.leq[self.chains[j][self.G[j]]][self.chains[k][self.G[k]]]) for k in range(0, ((self.n - 1)) + 1))):
                    self.G[j] = (self.G[j] + 1)
                    _changed = True
        return self.G


if __name__ == '__main__':
    chains = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    len_ = [3, 1, 6, 1, 6, 3, 6, 4]
    leq = [[True, False, False, False, False, False, False, False], [False, True, False, False, False, False, False, False], [False, False, True, False, False, False, False, False], [False, False, False, True, False, False, False, False], [False, False, False, False, True, False, False, False], [False, False, False, False, False, True, False, False], [False, False, False, False, False, False, True, False], [False, False, False, False, False, False, False, True]]
    prog = LLPAntichain()
    result = prog.LLPAntichain(chains, len_, leq)
    print(result)

