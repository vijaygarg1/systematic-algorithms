"""Reachability as a 1-bit LLP: forbidden = unreached vertex with reached predecessor.

Auto-generated from LLPTraversal.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPTraversal:
    def LLPTraversal(self, pre, G):
        self.pre = pre
        self.G = G
        self.n = len(self.pre)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if ((not self.G[j]) and any(self.G[i] for i in self.pre[j])):
                    self.G[j] = True
                    _changed = True


