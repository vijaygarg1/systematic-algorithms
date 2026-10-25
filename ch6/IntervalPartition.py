"""Interval partitioning, as bxx-greedy.tex's Algorithm IntervalPartition
states it:

var Q: min-priority queue of (finishTime, roomNumber), initially empty
numRooms: int, initially 0
for i := 1 to n:
if Q is not empty and Q.min().finishTime <= s[i]:
room[i] := Q.extract-min().roomNumber    // reuse earliest-free room
else:
numRooms := numRooms + 1                 // allocate a new room
room[i] := numRooms
Q.insert(f[i], room[i])                    // busy until f[i]
return room, numRooms

Intervals arrive sorted by start time. The heap is keyed by finish
time, so its minimum is the room that frees up earliest.

The loop is `for`, not `forall`: Q and numRooms both carry from one
iteration to the next.

The book returns the pair (room, numRooms). LL returns one value, so
room is returned; numRooms is max(room) and so is recoverable from it.
Rooms are numbered from 1, as the book numbers them.

Auto-generated from IntervalPartition.llp by lang/llcpy.py.
Do not edit by hand; edit the .llp source and rerun the build.
"""
import heapq


class IntervalPartition:
    def IntervalPartition(self, s, f):
        self.s = s
        self.f = f
        self.n = len(self.s)
        self.room = [0] * (self.n)
        self.Q = []
        self.numRooms = 0
        for i in range(0, ((self.n - 1)) + 1):
            if ((not (not self.Q)) and (self.Q[0][0] <= self.s[i])):
                self.room[i] = self.Q[0][1]
                heapq.heappop(self.Q)
            else:
                self.numRooms = (self.numRooms + 1)
                self.room[i] = self.numRooms
            heapq.heappush(self.Q, (self.f[i], self.room[i]))
        return self.room


if __name__ == '__main__':
    s = [0, 0, 1, 2]
    f = [0, 0, 1, 1]
    prog = IntervalPartition()
    result = prog.IntervalPartition(s, f)
    print(result)

