"""Precedences: composition program enforcing precedence constraints
pre(j) on job starts.  Each job j cannot start until every i in
pre(j) has finished (start G[i] plus processing time t[i]).
Composed onto LLP-MinMaxLateness via predicate conjunction:
[ LLP-MinMaxLateness(t, d, G) && Precedences(pre, t, G) ].

Auto-generated from Precedences.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Precedences:
    def Precedences(self, pre, t, G):
        self.pre = pre
        self.t = t
        self.G = G
        self.n = len(self.pre)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if (not (self.G[j] >= max(((self.G[i] + self.t[i]) for i in self.pre[j]), default=-2147483647))):
                    self.G[j] = max(((self.G[i] + self.t[i]) for i in self.pre[j]), default=-2147483647)
                    _changed = True
        return self.G


