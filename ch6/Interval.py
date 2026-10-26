"""Maximum-cardinality interval scheduling: greedy by earliest finish time.

Auto-generated from Interval.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Interval:
    def schedule(self, s, f):
        self.s = s
        self.f = f
        self.n = len(self.s)
        self.G = [0] * (self.n)
        if (self.n > 0):
            self.G[0] = 1
            last = 0
            for i in range(1, ((self.n - 1)) + 1):
                if (self.s[i] >= self.f[last]):
                    self.G[i] = 1
                    last = i
        return self.G


if __name__ == '__main__':
    s = [0, 0, 1, 2]
    f = [0, 0, 1, 1]
    prog = Interval()
    result = prog.schedule(s, f)
    print(result)

