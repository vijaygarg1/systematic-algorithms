"""LLP driver for the least mincut satisfying a lattice-linear side predicate.

Auto-generated from LLPMincut.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPMincut:
    def LLPMincut(self, G):
        self.G = G
        self.n = len(self.G)
        self.done = False
        while (not self.done):
            changed = True
            while changed:
                changed = False
                j = 0
                while (j < self.n):
                    if self.forbiddenForB(j, self.G):
                        if self.G[j]:
                            return None
                        self.G[j] = True
                        changed = True
                    j = (j + 1)
            if self.Bcheck(self.G):
                self.done = True
            else:
                next_ = self.nextMincut(self.G)
                if (next_ == None):
                    return None
                self.G = next_
        return self.G

    def Bcheck(self, G):
        return True

    def forbiddenForB(self, j, G):
        return False

    def nextMincut(self, G):
        return G


if __name__ == '__main__':
    G = [False, False, False, False, False, False, False, False]
    prog = LLPMincut()
    result = prog.LLPMincut(G)
    print(result)

