"""LLP-IntervalScheduling (bxx-greedy.tex, fig:activity): jobs sorted by
finish time, held in a doubly linked list (prevArr/nextArr). Job 0 is
always fixed initially. The book's two rules are written here as two
forbidden/advance clauses joined by `&&`, so they share one fixpoint:
every job is tested against both rules on each sweep. Rule 1 selects a
job whose list-predecessor has already finished by s[j]; rule 2 deletes
a job that its (selected) predecessor still overlaps, in O(1), via
next[prev[j]] := next[j]; prev[next[j]] := prev[j]. deleted[] records
the list removal that the book expresses by unlinking the node.

Auto-generated from LLPIntervalScheduling.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPIntervalScheduling:
    def LLPIntervalScheduling(self, s, f):
        self.s = s
        self.f = f
        self.n = len(self.s)
        self.G = [False] * (self.n)
        self.deleted = [False] * (self.n)
        self.prevArr = [0] * (self.n)
        self.nextArr = [0] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.prevArr[i] = (i - 1)
        for i in range(0, ((self.n - 1)) + 1):
            self.nextArr[i] = (i + 1)
        self.G[0] = True
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if ((((j > 0) and (not self.deleted[j])) and (not self.G[j])) and (self.f[self.prevArr[j]] <= self.s[j])):
                    self.G[j] = True
                    _changed = True
                if ((((j > 0) and (not self.deleted[j])) and self.G[self.prevArr[j]]) and (self.s[j] < self.f[self.prevArr[j]])):
                    self.deleted[j] = True
                    p = self.prevArr[j]
                    nx = self.nextArr[j]
                    self.nextArr[p] = nx
                    if (nx < self.n):
                        self.prevArr[nx] = p
                    _changed = True
        return self.G


if __name__ == '__main__':
    s = [0, 0, 1, 2]
    f = [0, 0, 1, 1]
    prog = LLPIntervalScheduling()
    result = prog.LLPIntervalScheduling(s, f)
    print(result)

