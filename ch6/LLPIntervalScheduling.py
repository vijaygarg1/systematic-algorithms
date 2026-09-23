"""LLP-IntervalScheduling: G[j] = true when job j is selected; forbidden when j is unselected and compatible with every already-selected earlier job."""


def forbidden(j, G, s, f):
    if G[j]:
        return False
    for i in range(j):
        if G[i] and f[i] > s[j]:
            return False
    return True


def llp_interval_scheduling(s, f):
    n = len(s)
    G = [False] * n
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if forbidden(j, G, s, f):
                G[j] = True
                changed = True
    return G


if __name__ == "__main__":
    s = [1, 2, 4, 5]
    f = [4, 5, 5, 7]
    print(llp_interval_scheduling(s, f))
