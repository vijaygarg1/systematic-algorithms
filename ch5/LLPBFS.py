"""LLP-BFS: forbidden when G[j] > min_{i in pre[j]} G[i] + 1.
style: fixpoint

Auto-generated from LLPBFS.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPBFS:
    def LLPBFS(self, pre, G):
        self.pre = pre
        self.G = G
        self.n = len(self.pre)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, (self.n - 1) + 1):
                if (self.G[j] > min(((self.G[i] + 1) for i in self.pre[j]), default=2147483647)):
                    self.G[j] = min(((self.G[i] + 1) for i in self.pre[j]), default=2147483647)
                    _changed = True


