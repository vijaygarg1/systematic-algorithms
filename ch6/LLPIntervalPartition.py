"""LLP-IntervalPartition: assign each course j to the least free room not
used by any overlapping earlier course in pre[j]; advance fixes j once
all of its pre-set is fixed. The least-free-room computation uses a
min-heap of the occupied room numbers in pre[j] (extract-min repeatedly
to find the smallest room number not present), matching the book's
heap-based advance step."""

import heapq


def least_free_room(j, G, pre):
    heap = [G[i] for i in pre[j]]
    heapq.heapify(heap)
    r = 1
    while heap and heap[0] == r:
        heapq.heappop(heap)
        r += 1
    return r


def llp_interval_partition(pre):
    n = len(pre)
    G = [1] * n
    fixed = [False] * n
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if fixed[j]:
                continue
            if all(fixed[i] for i in pre[j]):
                G[j] = least_free_room(j, G, pre)
                fixed[j] = True
                changed = True
    return G


if __name__ == "__main__":
    # 5 courses sorted by start time; pre[j] = earlier overlapping courses.
    pre = [
        set(),       # course 0 has no overlapping predecessors
        {0},         # course 1 overlaps with 0
        {0, 1},      # course 2 overlaps with 0 and 1
        {1, 2},      # course 3 overlaps with 1 and 2
        {2, 3},      # course 4 overlaps with 2 and 3
    ]
    print(llp_interval_partition(pre))
