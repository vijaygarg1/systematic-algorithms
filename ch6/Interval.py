"""Maximum-cardinality interval scheduling: greedy by earliest finish time."""

def schedule(s, f):
    n = len(s)
    G = [0] * n
    if n == 0:
        return G
    G[0] = 1
    last = 0
    for i in range(1, n):
        if s[i] >= f[last]:
            G[i] = 1
            last = i
    return G


if __name__ == "__main__":
    s = [1, 3, 0, 5, 8, 5]
    f = [4, 5, 6, 7, 9, 9]
    print("selected:", schedule(s, f))
