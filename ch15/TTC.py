"""Gale's Top Trading Cycle (TTC) algorithm for the housing market,
matching bxx-housing.tex Algorithm TTC exactly: each stage (1) every
unfixed agent advances its wish pointer past houses already owned by
fixed agents, building the top-choice graph; (2) ALL node-disjoint
cycles in that graph are found at once (every unfixed node has
out-degree 1, so each agent either lies on a cycle or on a tail
leading into one); (3) every agent on ANY cycle this stage trades
and is fixed -- not a single pointer-chasing walk that resolves one
cycle at a time and rebuilds the whole graph for each one.

Steps 1 and 3 are `forall`s, since each agent is handled independently
of the others; only the walk of step 2 is inherently sequential, and
even there the two sweeps over the recorded path are `forall`s.

Auto-generated from TTC.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class TTC:
    def TTC(self, pref):
        self.pref = pref
        self.n = len(self.pref)
        self.house = [0] * (self.n)
        self.fixed = [False] * (self.n)
        self.G = [0] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.fixed[i] = False
        for i in range(0, ((self.n - 1)) + 1):
            self.G[i] = 0
        numFixed = 0
        while (numFixed < self.n):
            for i in range(0, ((self.n - 1)) + 1):
                if (not self.fixed[i]):
                    while self.fixed[self.pref[i][self.G[i]]]:
                        self.G[i] = (self.G[i] + 1)
            state = [0] * (self.n)
            inCycle = [False] * (self.n)
            path = [0] * (self.n)
            a = 0
            while (a < self.n):
                if ((not self.fixed[a]) and (state[a] == 0)):
                    pathLen = 0
                    cur = a
                    while (state[cur] == 0):
                        state[cur] = 1
                        path[pathLen] = cur
                        pathLen = (pathLen + 1)
                        cur = self.pref[cur][self.G[cur]]
                    if (state[cur] == 1):
                        start = 0
                        while (path[start] != cur):
                            start = (start + 1)
                        for p in range(start, ((pathLen - 1)) + 1):
                            inCycle[path[p]] = True
                    for p in range(0, ((pathLen - 1)) + 1):
                        state[path[p]] = 2
                a = (a + 1)
            for i in range(0, ((self.n - 1)) + 1):
                if inCycle[i]:
                    self.house[i] = self.pref[i][self.G[i]]
                    self.fixed[i] = True
                    numFixed = (numFixed + 1)
        return self.house


if __name__ == '__main__':
    pref = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    prog = TTC()
    result = prog.TTC(pref)
    print(result)

