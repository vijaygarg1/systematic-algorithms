// LLP parallel H_n-approximation for Set Cover: pick every set that
// maximises coverage among neighbours and is lex-minimal among ties.

#include <iostream>
#include <vector>

bool isCovered(int e, const std::vector<std::vector<int>>& S, const std::vector<bool>& G) {
    int m = (int)G.size();
    for (int s = 0; s < m; ++s)
        if (G[s] && S[s][e] == 1) return true;
    return false;
}

int coverage(int j, const std::vector<std::vector<int>>& S, const std::vector<bool>& G) {
    int u = (int)S[j].size();
    int count = 0;
    for (int e = 0; e < u; ++e)
        if (S[j][e] == 1 && !isCovered(e, S, G)) ++count;
    return count;
}

bool shareUncovered(int j, int k, const std::vector<std::vector<int>>& S,
                    const std::vector<bool>& G) {
    int u = (int)S[j].size();
    for (int e = 0; e < u; ++e)
        if (S[j][e] == 1 && S[k][e] == 1 && !isCovered(e, S, G)) return true;
    return false;
}

bool isLexMaxCov(int j, const std::vector<std::vector<int>>& S, const std::vector<bool>& G) {
    if (G[j]) return false;
    int m = (int)G.size();
    int cov_j = coverage(j, S, G);
    if (cov_j == 0) return false;
    for (int k = 0; k < m; ++k) {
        if (k == j || G[k]) continue;
        if (!shareUncovered(j, k, S, G)) continue;
        int cov_k = coverage(k, S, G);
        if (cov_k > cov_j) return false;
        if (cov_k == cov_j && k < j) return false;
    }
    return true;
}

std::vector<bool> llpLexicallyFirstSetCover(const std::vector<std::vector<int>>& S) {
    int m = (int)S.size();
    std::vector<bool> G(m, false);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < m; ++j)
            if (isLexMaxCov(j, S, G)) { G[j] = true; changed = true; }
    }
    return G;
}

int main() {
    std::vector<std::vector<int>> S = {
        {1, 1, 1, 0, 0, 0},
        {1, 0, 0, 1, 1, 0},
        {0, 1, 0, 0, 1, 1},
        {0, 0, 1, 0, 0, 1}
    };
    auto G = llpLexicallyFirstSetCover(S);
    std::cout << "picked:";
    for (size_t s = 0; s < G.size(); ++s) if (G[s]) std::cout << ' ' << s;
    std::cout << '\n';
    return 0;
}
