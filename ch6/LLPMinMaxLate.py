"""LLP-MinMaxLate: schedule jobs (sorted by deadline) by raising each start time G[j] to the prefix sum of earlier processing times."""


def prefix_sum(j, t):
    return sum(t[:j])


def llp_min_max_late(t, d):
    n = len(t)
    G = [0] * n
    changed = True
    while changed:
        changed = False
        for j in range(n):
            target = prefix_sum(j, t)
            if G[j] < target:
                G[j] = target
                changed = True
    lateness = max(0, max(G[j] + t[j] - d[j] for j in range(n)))
    return G, lateness


if __name__ == "__main__":
    # Jobs sorted by deadline; processing times and deadlines.
    t = [3, 1, 2, 4]
    d = [4, 5, 6, 7]
    G, lateness = llp_min_max_late(t, d)
    print("starts:", G, "lateness:", lateness)
