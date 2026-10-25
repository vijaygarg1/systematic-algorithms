"""BFS-Traversal, exactly as bxx-graph.tex's Algorithm BFS-Traversal
states it:

var S: queue of int               // discovered but not yet explored
S.enqueue(s); dist[s] := 0
while not S.empty():
j := S.dequeue()
forall k in succ(j):
if dist[k] > dist[j] + 1:
dist[k] := dist[j] + 1; parent[k] := j; S.enqueue(k)
return dist

The book's `queue of int` is a list<int> here: add is enqueue and
removeFirst is dequeue, so the list is used strictly FIFO.

parent is the BFS tree the book declares as a second output: parent[k]
is the vertex k was first reached from, and stays -1 for the source and
for anything unreachable. Only dist is returned, as the book returns.

Auto-generated from BFSTraversal.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class BFSTraversal:
    def BFSTraversal(self, succ, s):
        self.succ = succ
        self.s = s
        self.n = len(self.succ)
        self.dist = [0] * (self.n)
        self.parent = [0] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.dist[i] = 2147483647
        for i in range(0, ((self.n - 1)) + 1):
            self.parent[i] = (-1)
        S = []
        S.append(self.s)
        self.dist[self.s] = 0
        while (not (not S)):
            j = S.pop(0)
            for k in self.succ[j]:
                if (self.dist[k] > (self.dist[j] + 1)):
                    self.dist[k] = (self.dist[j] + 1)
                    self.parent[k] = j
                    S.append(k)
        return self.dist


