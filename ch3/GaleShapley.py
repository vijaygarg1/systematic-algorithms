"""Classical Gale-Shapley man-optimal stable-matching loop."""

def match(mpref, rank):
    n = len(mpref) - 1
    G = [0] * (n + 1)
    partner = [0] * (n + 1)
    free = [False] * (n + 1)
    for i in range(1, n + 1):
        free[i] = True

    done = False
    while not done:
        i = 1
        while i <= n and not free[i]:
            i += 1
        if i > n:
            done = True
        else:
            G[i] += 1
            z = mpref[i][G[i]]
            if partner[z] == 0:
                partner[z] = i
                free[i] = False
            else:
                if rank[z][i] < rank[z][partner[z]]:
                    free[partner[z]] = True
                    partner[z] = i
                    free[i] = False
                # else: i stays free; he will try his next preference next round.
    return G


if __name__ == "__main__":
    # Three men, three women.  Slot 0 is padding.
    mpref = [
        [0, 0, 0, 0],
        [0, 1, 2, 3],
        [0, 2, 1, 3],
        [0, 1, 2, 3],
    ]
    rank = [
        [0, 0, 0, 0],
        [0, 3, 1, 2],
        [0, 2, 3, 1],
        [0, 1, 2, 3],
    ]
    G = match(mpref, rank)
    for i in range(1, len(G)):
        print(f"man {i}: proposed {G[i]} time(s), engaged to woman {mpref[i][G[i]]}")
