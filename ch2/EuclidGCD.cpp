// LLP-EuclidGCD: 2-element LLP that implements Euclid by repeated
// subtraction.  G is initialized to a copy of A.  Index j is forbidden
// when some i has G[j] > G[i]; advance subtracts G[i] from G[j].

#include <iostream>
#include <vector>

int forbidden(int j, const std::vector<int>& G) {
    for (int i = 0; i < (int)G.size(); ++i) {
        if (i != j && G[j] > G[i]) return i;
    }
    return -1;
}

void advance(int j, int i, std::vector<int>& G) {
    G[j] = G[j] - G[i];
}

std::vector<int> euclidGCD(std::vector<int> A) {
    std::vector<int> G = A;
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < (int)G.size(); ++j) {
            int i = forbidden(j, G);
            if (i >= 0) { advance(j, i, G); changed = true; }
        }
    }
    return G;
}

int main() {
    for (auto A : { std::vector<int>{48, 18},
                    std::vector<int>{100, 75},
                    std::vector<int>{60, 36, 24} }) {
        auto G = euclidGCD(A);
        std::cout << "euclidGCD(";
        for (size_t i = 0; i < A.size(); ++i) std::cout << (i ? ", " : "") << A[i];
        std::cout << ") = [";
        for (size_t i = 0; i < G.size(); ++i) std::cout << (i ? ", " : "") << G[i];
        std::cout << "]\n";
    }
    return 0;
}
