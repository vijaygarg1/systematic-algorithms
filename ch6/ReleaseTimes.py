"""ReleaseTimes: composition program enforcing G[j] >= r[j]."""


def release_times(r, G):
    n = len(G)
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if G[j] < r[j]:
                G[j] = r[j]
                changed = True
    return G


if __name__ == "__main__":
    r = [0, 3, 2, 7]
    G = [0, 0, 0, 0]
    print(release_times(r, G))
