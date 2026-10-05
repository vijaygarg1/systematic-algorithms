"""LLP-IntervalScheduling: jobs sorted by finish time, held in a doubly
linked list (prev/next). Job 0 is always selected initially. A job j
(not yet selected or deleted) is forbidden when either (a) its current
list-predecessor has already finished by s[j] -- advance: select it --
or (b) its predecessor is selected but still overlaps j -- advance:
delete j from the list in O(1)."""


def llp_interval_scheduling(s, f):
    n = len(s)
    G = [False] * n
    deleted = [False] * n
    prev = list(range(-1, n - 1))
    nxt = list(range(1, n + 1))
    G[0] = True
    changed = True
    while changed:
        changed = False
        for j in range(1, n):
            if G[j] or deleted[j]:
                continue
            p = prev[j]
            if f[p] <= s[j]:
                G[j] = True
                changed = True
            elif G[p] and s[j] < f[p]:
                deleted[j] = True
                nx = nxt[j]
                nxt[p] = nx
                if nx < n:
                    prev[nx] = p
                changed = True
    return G


if __name__ == "__main__":
    s = [1, 2, 4, 5]
    f = [4, 5, 5, 7]
    print(llp_interval_scheduling(s, f))
