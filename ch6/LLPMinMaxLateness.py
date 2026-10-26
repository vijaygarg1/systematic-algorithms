"""LLP-MinMaxLate: schedule jobs (sorted by deadline) by raising each start
time G[j] to the prefix sum of earlier processing times.

Auto-generated from LLPMinMaxLateness.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPMinMaxLateness:
    def LLPMinMaxLateness(self, t, d):
        self.t = t
        self.d = d
        self.n = len(self.t)
        self.S = [0] * (self.n)
        self.G = [0] * (self.n)
        for j in range(1, ((self.n - 1)) + 1):
            self.S[j] = (self.S[(j - 1)] + self.t[(j - 1)])
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if (not (self.G[j] >= self.S[j])):
                    self.G[j] = self.S[j]
                    _changed = True
        return self.G


if __name__ == '__main__':
    t = [0, 0, 1, 2]
    d = [0, 0, 1, 1]
    prog = LLPMinMaxLateness()
    result = prog.LLPMinMaxLateness(t, d)
    print(result)

