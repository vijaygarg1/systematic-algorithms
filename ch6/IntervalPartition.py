"""IntervalPartition (bxx-greedy.tex, algo:interval-partition): for each
interval i (sorted by start time), reuse the earliest-free room via a
min-priority-queue Q of (finishTime, roomNumber) pairs -- extract-min
when Q is non-empty and its minimum finish time is <= s[i], else open
a new room -- matching the book's binary-heap algorithm exactly, not

Auto-generated from IntervalPartition.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""
import heapq


class IntervalPartition:
    def partition(self, s, f):
        self.s = s
        self.f = f
        self.n = len(self.s)
        self.G = [0] * (self.n)
        self.Q = []
        self.numRooms = 0
        for j in range(0, ((self.n - 1)) + 1):
            r = (0 - 1)
            if ((not (not self.Q)) and (self.Q[0][0] <= self.s[j])):
                r = self.Q[0][1]
                heapq.heappop(self.Q)
            else:
                r = self.numRooms
                self.numRooms = (self.numRooms + 1)
            self.G[j] = (r + 1)
            heapq.heappush(self.Q, (self.f[j], r))
        return self.G


if __name__ == '__main__':
    s = [0, 0, 1, 2]
    f = [0, 0, 1, 1]
    prog = IntervalPartition()
    result = prog.partition(s, f)
    print(result)

