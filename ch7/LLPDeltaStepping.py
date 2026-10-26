"""LLP-Delta-Stepping: bucket-based relaxation with light/heavy edge phases.
This algorithm has complex control flow (repeat/until, set comprehensions)
beyond the standard forbidden/advance pattern.
The forbidden predicates are:
forbidden_L(v): exists (u,v) in E_light : G[u] + w[u][v] < G[v]

Auto-generated from LLPDeltaStepping.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPDeltaStepping:
    def LLPDeltaStepping(self, pre, w, isLight, G, delta, s):
        self.pre = pre
        self.w = w
        self.isLight = isLight
        self.G = G
        self.delta = delta
        self.s = s
        self.n = len(self.pre)
        self.G[self.s] = 0
        changed = True
        while changed:
            changed = False
            j = 0
            while (j < len(self.G)):
                if self.relaxLight(j, self.pre, self.w, self.isLight, self.G, self.delta):
                    changed = True
                j = (j + 1)
            j = 0
            while (j < len(self.G)):
                if self.relaxHeavy(j, self.pre, self.w, self.isLight, self.G, self.delta):
                    changed = True
                j = (j + 1)

    def relaxLight(self, v, pre, w, isLight, G, delta):
        relaxed = False
        k = 0
        while (k < len(pre[v])):
            u = pre[v][k]
            if (isLight[u][v] and ((G[u] + w[u][v]) < G[v])):
                G[v] = (G[u] + w[u][v])
                relaxed = True
            k = (k + 1)
        return relaxed

    def relaxHeavy(self, v, pre, w, isLight, G, delta):
        relaxed = False
        k = 0
        while (k < len(pre[v])):
            u = pre[v][k]
            if ((not isLight[u][v]) and ((G[u] + w[u][v]) < G[v])):
                G[v] = (G[u] + w[u][v])
                relaxed = True
            k = (k + 1)
        return relaxed


if __name__ == '__main__':
    pre = [[], [0], [0, 1], [0, 1, 2], [0, 1, 2, 3], [0, 1, 2, 3, 4], [0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 5, 6]]
    w = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    isLight = [[True, False, False, False, False, False, False, False], [False, True, False, False, False, False, False, False], [False, False, True, False, False, False, False, False], [False, False, False, True, False, False, False, False], [False, False, False, False, True, False, False, False], [False, False, False, False, False, True, False, False], [False, False, False, False, False, False, True, False], [False, False, False, False, False, False, False, True]]
    G = [3, 1, 6, 1, 6, 3, 6, 4]
    delta = 0
    prog = LLPDeltaStepping()
    prog.LLPDeltaStepping(pre, w, isLight, G, delta, 0)
    print(pre)
    print(w)
    print(isLight)
    print(G)

