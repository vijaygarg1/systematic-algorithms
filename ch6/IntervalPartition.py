"""Interval partition: for each interval (sorted by start time), reuse the
earliest-free room via a min-heap of (finishTime, roomNumber) pairs --
extract-min when its finish time is <= the current start time, else open
a new room. Rooms used = max overlap depth."""

import heapq


def partition(s, f):
    n = len(s)
    G = [0] * n
    heap = []  # (finishTime, roomNumber), min-heap
    num_rooms = 0
    for j in range(n):
        if heap and heap[0][0] <= s[j]:
            _, r = heapq.heappop(heap)
        else:
            r = num_rooms
            num_rooms += 1
        G[j] = r + 1
        heapq.heappush(heap, (f[j], r))
    return G


if __name__ == "__main__":
    # Five courses sorted by start time.
    s = [0, 1, 2, 3, 4]
    f = [3, 4, 5, 6, 7]
    print(partition(s, f))
