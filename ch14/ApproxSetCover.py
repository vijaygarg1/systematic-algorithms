"""Greedy H_n-approximation for Set Cover: repeatedly pick the set covering the most uncovered elements."""


def approx_set_cover(S, n):
    m = len(S)
    C = [False] * m
    covered = [False] * n
    while True:
        best_idx = -1
        best_cover = 0
        for s in range(m):
            if C[s]:
                continue
            count = sum(1 for e in range(n) if S[s][e] == 1 and not covered[e])
            if count > best_cover:
                best_cover = count
                best_idx = s
        if best_idx == -1:
            return C
        C[best_idx] = True
        for e in range(n):
            if S[best_idx][e] == 1:
                covered[e] = True


if __name__ == "__main__":
    # Universe {0..5}; sets S_1={0,1,2}, S_2={0,3,4}, S_3={1,4,5}, S_4={2,5}.
    S = [[1, 1, 1, 0, 0, 0],
         [1, 0, 0, 1, 1, 0],
         [0, 1, 0, 0, 1, 1],
         [0, 0, 1, 0, 0, 1]]
    print(approx_set_cover(S, 6))
