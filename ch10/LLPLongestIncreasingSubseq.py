"""LLP-LIS: G[j] >= G[i] + 1 for every i in pre(j) (i < j with A[i] < A[j])."""

def llp_lis(A):
    n = len(A)
    pre = [[i for i in range(j) if A[i] < A[j]] for j in range(n)]
    G = [1] * n
    changed = True
    while changed:
        changed = False
        for j in range(n):
            best = G[j]
            for i in pre[j]:
                if G[i] + 1 > best:
                    best = G[i] + 1
            if best > G[j]:
                G[j] = best
                changed = True
    return G


if __name__ == "__main__":
    A = [3, 10, 2, 1, 20, 4]
    G = llp_lis(A)
    print(f"G = {G}, LIS length = {max(G)}")
