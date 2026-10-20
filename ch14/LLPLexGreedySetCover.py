"""LLP parallel H_n-approximation for Set Cover: pick every set that maximises coverage among neighbours and is lex-minimal among ties."""


def is_covered(e, S, G):
    return any(G[s] and S[s][e] == 1 for s in range(len(S)))


def coverage(j, S, G):
    return sum(1 for e in range(len(S[j])) if S[j][e] == 1 and not is_covered(e, S, G))


def share_uncovered(j, k, S, G):
    return any(S[j][e] == 1 and S[k][e] == 1 and not is_covered(e, S, G)
               for e in range(len(S[j])))


def is_lex_max_cov(j, S, G):
    if G[j]:
        return False
    cov_j = coverage(j, S, G)
    if cov_j == 0:
        return False
    for k in range(len(S)):
        if k == j or G[k]:
            continue
        if not share_uncovered(j, k, S, G):
            continue
        cov_k = coverage(k, S, G)
        if cov_k > cov_j:
            return False
        if cov_k == cov_j and k < j:
            return False
    return True


def llp_lex_first_set_cover(S):
    m = len(S)
    G = [False] * m
    while True:
        changed = False
        for j in range(m):
            if is_lex_max_cov(j, S, G):
                G[j] = True
                changed = True
        if not changed:
            return G


if __name__ == "__main__":
    # Universe {0..5}; sets S_1={0,1,2}, S_2={0,3,4}, S_3={1,4,5}, S_4={2,5}.
    S = [[1, 1, 1, 0, 0, 0],
         [1, 0, 0, 1, 1, 0],
         [0, 1, 0, 0, 1, 1],
         [0, 0, 1, 0, 0, 1]]
    print(llp_lex_first_set_cover(S))
