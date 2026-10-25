"""Traversal, as bxx-graph.tex's Algorithm Traversal states it:

var S: set of int                 // discovered but not yet explored
S.add(s); visited[s] := 1
while not S.empty():
j := S.removeAny(visited)
forall k in succ(j):
if visited[k] = 0: visited[k] := 1; S.add(k)
return visited

The book's S is a SET and removeAny may return any member -- that is the
point of this algorithm: BFS-Traversal and DFS-Traversal are the same
code with the order of removal pinned down. LL has no scalar set type
with a "remove any" operation, so S is a list<int> here and removeFirst
makes one concrete choice. Any other choice visits the same vertices;
only the order differs, and the result (the reachable set) is the same.

Auto-generated from Traversal.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class Traversal:
    def Traversal(self, succ, s):
        self.succ = succ
        self.s = s
        self.n = len(self.succ)
        self.visited = [0] * (self.n)
        self.S = []
        self.S.append(self.s)
        self.visited[self.s] = 1
        while (not (not self.S)):
            j = self.S.pop(0)
            for k in self.succ[j]:
                if (self.visited[k] == 0):
                    self.visited[k] = 1
                    self.S.append(k)
        return self.visited


