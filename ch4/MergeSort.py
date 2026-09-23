"""Sequential merge sort (parallel companion in progs-divideConquer/)."""

def sort(lo, hi, G):
    if lo < hi:
        mid = (lo + hi) // 2
        sort(lo, mid, G)
        sort(mid + 1, hi, G)
        merge(lo, mid, hi, G)


def merge(lo, mid, hi, G):
    B = list(G)  # scratch copy
    i = lo
    j = mid + 1
    k = lo
    while i <= mid and j <= hi:
        if B[i] <= B[j]:
            G[k] = B[i]
            i += 1
        else:
            G[k] = B[j]
            j += 1
        k += 1
    while i <= mid:
        G[k] = B[i]
        i += 1
        k += 1
    while j <= hi:
        G[k] = B[j]
        j += 1
        k += 1


if __name__ == "__main__":
    G = [5, 2, 4, 6, 1, 3]
    sort(0, len(G) - 1, G)
    print(G)
