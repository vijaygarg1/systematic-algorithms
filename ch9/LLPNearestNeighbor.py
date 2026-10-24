"""LLP-NearestNeighbor: find nearest neighbor distance for each point.
px[j], py[j] are x,y coordinates of point j.

Auto-generated from LLPNearestNeighbor.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPNearestNeighbor:
    def LLPNearestNeighbor(self, px, py):
        self.px = px
        self.py = py
        self.n = len(self.px)
        self.G = [float('inf')] * (self.n)
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if (not (self.G[j] <= self.nearestDist(self.px, self.py, j))):
                    self.G[j] = self.nearestDist(self.px, self.py, j)
                    _changed = True

    def nearestDist(self, px, py, j):
        best = float('inf')
        for k in range(0, ((len(px) - 1)) + 1):
            if (k != j):
                d = self.dist(px, py, j, k)
                if (d < best):
                    best = d
        return best

    def dist(self, px, py, j, k):
        dx = (px[j] - px[k])
        dy = (py[j] - py[k])
        return ((dx * dx) + (dy * dy))


if __name__ == '__main__':
    px = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]
    py = [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0]
    prog = LLPNearestNeighbor()
    prog.LLPNearestNeighbor(px, py)
    print(px)
    print(py)

