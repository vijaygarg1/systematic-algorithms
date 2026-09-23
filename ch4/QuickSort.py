"""Sequential quicksort with Lomuto partition (parallel form in progs-divideConquer/)."""

def sort(lo, hi, G):
    if lo < hi:
        p = partition(lo, hi, G)
        sort(lo, p - 1, G)
        sort(p + 1, hi, G)


def partition(lo, hi, G):
    pivot = G[hi]
    i = lo - 1
    j = lo
    while j <= hi - 1:
        if G[j] <= pivot:
            i += 1
            G[i], G[j] = G[j], G[i]
        j += 1
    G[i + 1], G[hi] = G[hi], G[i + 1]
    return i + 1


if __name__ == "__main__":
    G = [5, 2, 4, 6, 1, 3]
    sort(0, len(G) - 1, G)
    print(G)
