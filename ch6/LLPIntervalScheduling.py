"""LLP-IntervalScheduling, as bxx-greedy.tex's Algorithm
LLP-IntervalScheduling states it. Jobs sit in a doubly linked list;
prev and next are the previous and next node.

Forbidden (j in [1..n]): !G[j] and f[prev[j]] <= s[j]
Advance                : G[j] := true
&&
Forbidden (j in [1..n]): (j>1) and (next[prev[j]] = j)
and G[prev[j]] and s[j] < f[prev[j]]
Advance                : next[prev[j]] := next[j]     // delete from
prev[next[j]] := prev[j]     // linked list

next[prev[j]] = j is the "j is still in the list" test. Without it the
second rule never falsifies its own guard -- deleting j leaves prev[j]
untouched, so G[prev[j]] and s[j] < f[prev[j]] both stay true and j is
forbidden forever. (Verified: the version without it does not
terminate.) It is also what makes the book's own claim true, that
every job is touched at most twice.

Rule 1 needs no index bound: G[0] is true from the start, so !G[j] is
false at j = 0 and the guard short-circuits before reaching f[prev[0]].

The advance guards prev[next[j]] because next[n-1] is n, one past the
last slot: the book's list has a tail sentinel there, this one does not.

Auto-generated from LLPIntervalScheduling.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""


class LLPIntervalScheduling:
    def LLPIntervalScheduling(self, s, f):
        self.s = s
        self.f = f
        self.n = len(self.s)
        self.G = [False] * (self.n)
        self.prev = [0] * (self.n)
        self.next_ = [0] * (self.n)
        for i in range(0, ((self.n - 1)) + 1):
            self.prev[i] = (i - 1)
        for i in range(0, ((self.n - 1)) + 1):
            self.next_[i] = (i + 1)
        self.G[0] = True
        _changed = True
        while _changed:
            _changed = False
            for j in range(0, ((self.n - 1)) + 1):
                if ((not self.G[j]) and (self.f[self.prev[j]] <= self.s[j])):
                    self.G[j] = True
                    _changed = True
                if ((((j > 0) and (self.next_[self.prev[j]] == j)) and self.G[self.prev[j]]) and (self.s[j] < self.f[self.prev[j]])):
                    self.next_[self.prev[j]] = self.next_[j]
                    if (self.next_[j] < self.n):
                        self.prev[self.next_[j]] = self.prev[j]
                    _changed = True
        return self.G


if __name__ == '__main__':
    s = [0, 0, 1, 2]
    f = [0, 0, 1, 1]
    prog = LLPIntervalScheduling()
    result = prog.LLPIntervalScheduling(s, f)
    print(result)

