"""Mandatory: composition program forcing the intervals in subset S
into the schedule.  Returns None when two mandatory intervals overlap.
Composed with LLP-IntervalScheduling."""


def overlap(j, k, s, f):
    return s[j] < f[k] and s[k] < f[j]


def mandatory_intervals(s, f, S):
    n = len(s)
    G = [False] * n
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if S[j] and not G[j]:
                if any(G[k] and overlap(j, k, s, f) for k in range(n)):
                    return None
                G[j] = True
                changed = True
    return G


if __name__ == "__main__":
    s = [0, 3, 5]
    f = [2, 7, 9]
    S = [True, False, True]
    print(mandatory_intervals(s, f, S))
