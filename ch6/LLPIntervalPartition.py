"""LLP-IntervalPartition: assign each course j to the least free room not used by any overlapping earlier course in pre[j]; advance fixes j once all of its pre-set is fixed."""


def least_free_room(j, G, pre):
    r = 1
    while True:
        conflict = any(G[i] == r for i in pre[j])
        if not conflict:
            return r
        r += 1


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
