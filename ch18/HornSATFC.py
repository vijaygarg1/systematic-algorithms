"""Horn SAT forward chaining (Dowling-Gallier): unit propagation with counters.

Auto-generated from HornSATFC.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class HornSATFC:
    def HornSATFC(self, body, head, adj):
        self.body = body
        self.head = head
        self.adj = adj
        self.n = len(self.adj)
        self.m = len(self.body)
        self.A = [False] * (self.n)
        self.rem = [0] * (self.m)
        self.c = 0
        while (self.c < self.m):
            self.rem[self.c] = len(self.body[self.c])
            self.c = (self.c + 1)
        queue = [0] * (self.n)
        front = 0
        back = 0
        self.c = 0
        while (self.c < self.m):
            if ((self.rem[self.c] == 0) and (self.head[self.c] >= 0)):
                if (not self.A[self.head[self.c]]):
                    queue[back] = self.head[self.c]
                    back = (back + 1)
            self.c = (self.c + 1)
        sat = True
        while ((front < back) and sat):
            x = queue[front]
            front = (front + 1)
            if (not self.A[x]):
                self.A[x] = True
                k = 0
                while (k < len(self.adj[x])):
                    ci = self.adj[x][k]
                    self.rem[ci] = (self.rem[ci] - 1)
                    if (self.rem[ci] == 0):
                        if (self.head[ci] < 0):
                            sat = False
                        else:
                            if (not self.A[self.head[ci]]):
                                queue[back] = self.head[ci]
                                back = (back + 1)
                    k = (k + 1)
        return self.A


if __name__ == '__main__':
    body = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]]
    head = [3, 1, 6, 1, 6, 3, 6, 4]
    adj = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]]
    prog = HornSATFC()
    result = prog.HornSATFC(body, head, adj)
    print(result)

