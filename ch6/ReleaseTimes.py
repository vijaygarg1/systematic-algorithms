"""ReleaseTimes: composition program enforcing that job j cannot start
before its release time r[j].  Composed onto LLP-MinMaxLateness via
predicate conjunction:
[ LLP-MinMaxLateness(t, d, G) && ReleaseTimes(r, G) ].

Auto-generated from ReleaseTimes.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class ReleaseTimes:
    def ReleaseTimes(self, r, G):
        self.r = r
        self.G = G
        self.n = len(self.r)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if (not (self.G[j] >= self.r[j])):
                    self.G[j] = self.r[j]
                    _changed = True
        return self.G


if __name__ == '__main__':
    r = [0, 0, 1, 2]
    G = [0, 0, 1, 1]
    prog = ReleaseTimes()
    result = prog.ReleaseTimes(r, G)
    print(result)

