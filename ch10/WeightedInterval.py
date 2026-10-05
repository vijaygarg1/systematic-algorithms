"""Classical sequential weighted-interval-scheduling DP given p[]."""

def schedule(s, f, w, p):
    n = len(s)
    opt = [0] * n
    G   = [0] * n
    # Phase 1: compute optimal values.
    for cur in range(1, n):
        if w[cur] + opt[p[cur]] >= opt[cur - 1]:
            opt[cur] = w[cur] + opt[p[cur]]
        else:
            opt[cur] = opt[cur - 1]
    # Phase 2: backtrack to find selected intervals.
    cur = n - 1
    while cur > 0:
        if w[cur] + opt[p[cur]] >= opt[cur - 1]:
            G[cur] = 1
            cur = p[cur]
        else:
            cur = cur - 1
    return G


if __name__ == "__main__":
    # Book's 5-interval example.  Index 0 is a 0-weight sentinel.
    s = [0, 1, 2, 4, 6, 5]
    f = [0, 3, 5, 6, 8, 9]
    w = [0, 4, 6, 5, 3, 7]
    p = [0, 0, 0, 1, 3, 2]
    print("selected:", schedule(s, f, w, p))
