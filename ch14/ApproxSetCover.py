"""Greedy H_n-approximation for Set Cover: repeatedly pick the set covering the most uncovered elements.

Auto-generated from ApproxSetCover.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class ApproxSetCover:
    def ApproxSetCover(self, mem, inc):
        self.mem = mem
        self.inc = inc
        self.m = len(self.mem)
        self.n = len(self.inc)
        self.C = [False] * (self.m)
        self.covered = [False] * (self.n)
        self.cnt = [0] * (self.m)
        self.remaining = self.n
        for s in range(0, ((self.m - 1)) + 1):
            self.cnt[s] = len(self.mem[s])
        while (self.remaining > 0):
            bestIdx = 0
            bestCover = (0 - 1)
            s = 0
            while (s < self.m):
                if (self.cnt[s] > bestCover):
                    bestCover = self.cnt[s]
                    bestIdx = s
                s = (s + 1)
            self.C[bestIdx] = True
            k = 0
            while (k < len(self.mem[bestIdx])):
                e = self.mem[bestIdx][k]
                if (not self.covered[e]):
                    self.covered[e] = True
                    self.remaining = (self.remaining - 1)
                    t = 0
                    while (t < len(self.inc[e])):
                        self.cnt[self.inc[e][t]] = (self.cnt[self.inc[e][t]] - 1)
                        t = (t + 1)
                k = (k + 1)
        return self.C


if __name__ == '__main__':
    mem = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    inc = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    prog = ApproxSetCover()
    result = prog.ApproxSetCover(mem, inc)
    print(result)

