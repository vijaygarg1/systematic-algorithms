"""Par-CRT2: descending parallel Chinese Remainder Theorem.
Searches for the largest solution below M.

Auto-generated from ParCRT2.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class ParCRT2:
    def ParCRT2(self, m, b, M):
        self.m = m
        self.b = b
        self.M = M
        self.n = len(self.m)
        self.G = [0] * (len(self.m))
        self.j = 0
        while (self.j < len(self.m)):
            r = ((self.M - 1) % self.m[self.j])
            if (r >= self.b[self.j]):
                self.G[self.j] = (((self.M - 1) - r) + self.b[self.j])
            else:
                self.G[self.j] = ((((self.M - 1) - r) + self.b[self.j]) - self.m[self.j])
            self.j = (self.j + 1)
        changed = True
        while changed:
            changed = False
            self.j = 0
            while (self.j < len(self.m)):
                minVal = self.G[0]
                i = 1
                while (i < len(self.m)):
                    if (self.G[i] < minVal):
                        minVal = self.G[i]
                    i = (i + 1)
                if (self.G[self.j] > minVal):
                    diff = (self.G[self.j] - minVal)
                    steps = int(((diff + self.m[self.j]) - 1) / self.m[self.j])
                    self.G[self.j] = (self.G[self.j] - (steps * self.m[self.j]))
                    changed = True
                self.j = (self.j + 1)
        return self.G


if __name__ == '__main__':
    m = [3, 5, 7, 11]
    b = [0, 0, 1, 2]
    M = 0
    prog = ParCRT2()
    result = prog.ParCRT2(m, b, M)
    print(result)

