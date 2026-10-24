"""Mandatory-subset constraint: certain intervals must be selected.
S[j] is true iff interval j is mandatory.

Auto-generated from Mandatory.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Mandatory:
    def Mandatory(self, s, f, S, G):
        self.s = s
        self.f = f
        self.S = S
        self.G = G
        self.n = len(self.s)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if (self.S[j] and (not self.G[j])):
                    if self.overlaps(j, self.G, self.s, self.f):
                        return None
                    else:
                        self.G[j] = True
                    _changed = True
        return self.G

    def overlaps(self, j, G, s, f):
        result = False
        k = 0
        while (k < len(G)):
            if (((G[k] and (k != j)) and (s[k] < f[j])) and (s[j] < f[k])):
                result = True
            k = (k + 1)
        return result


if __name__ == '__main__':
    s = [0, 0, 1, 2]
    f = [0, 0, 1, 1]
    S = [False, False, False, False]
    G = [False, False, False, False]
    prog = Mandatory()
    result = prog.Mandatory(s, f, S, G)
    print(result)

