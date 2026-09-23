"""Min-max-lateness scheduling: greedy by earliest deadline."""

def schedule(t, d):
    n = len(t)
    G = [0] * n
    last = 0
    max_lateness = 0
    for i in range(n):
        G[i] = last
        last += t[i]
        max_lateness = max(max_lateness, last - d[i])
    return G, max_lateness


if __name__ == "__main__":
    # Jobs sorted by deadline.
    t = [3, 2, 1, 4, 3, 2]
    d = [6, 8, 9, 9, 14, 15]
    starts, mx = schedule(t, d)
    print("start times:", starts)
    print("max lateness:", mx)
