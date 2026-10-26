"""Min-max-lateness scheduling: greedy by earliest deadline.

Auto-generated from MinMaxLateness.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class MinMaxLateness:
    def schedule(self, t, d):
        self.t = t
        self.d = d
        self.n = len(self.t)
        self.G = [0] * (self.n)
        self.last = 0
        for i in range(0, ((self.n - 1)) + 1):
            self.G[i] = self.last
            self.last = (self.last + self.t[i])
        return self.G


if __name__ == '__main__':
    t = [0, 0, 1, 2]
    d = [0, 0, 1, 1]
    prog = MinMaxLateness()
    result = prog.schedule(t, d)
    print(result)

