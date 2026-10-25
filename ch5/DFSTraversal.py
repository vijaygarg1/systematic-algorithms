"""DFS-Traversal, as bxx-graph.tex's Algorithm DFS-Traversal states it:

var tick: int initially 1
function DFS(j):
visited[j] := 1
discovered[j] := tick; tick := tick + 1
for k in succ(j):
if visited[k] = 0: parent[k] := j; DFS(k)
finished[j] := tick; tick := tick + 1
DFS(s)

The book's DFS(j) closes over visited/parent/discovered/finished/tick;
LL has no closures, so DFS takes them as parameters.

tick is the book's `var tick: int initially 1`, a counter shared by every
DFS call. A plain `int tick` would be local to the entry method and
invisible to DFS (verified: the generated Java does not compile), so it
travels as a one-slot array -- the standard way to pass a mutable scalar.

The book declares four outputs (visited, parent, discovered, finished);
LL returns one value, so discovered is returned and the rest stay
internal, exactly as they are computed here.

Auto-generated from DFSTraversal.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class DFSTraversal:
    def DFSTraversal(self, succ, s):
        self.succ = succ
        self.s = s
        self.n = len(self.succ)
        self.visited = [0] * (self.n)
        self.parent = [0] * (self.n)
        self.discovered = [0] * (self.n)
        self.finished = [0] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.parent[i] = (-1)
        tick = [0] * (1)
        tick[0] = 1
        self.DFS(self.s, self.succ, self.visited, self.parent, self.discovered, self.finished, tick)
        return self.discovered

    def DFS(self, j, succ, visited, parent, discovered, finished, tick):
        visited[j] = 1
        discovered[j] = tick[0]
        tick[0] = (tick[0] + 1)
        for k in succ[j]:
            if (visited[k] == 0):
                parent[k] = j
                self.DFS(k, succ, visited, parent, discovered, finished, tick)
        finished[j] = tick[0]
        tick[0] = (tick[0] + 1)


