// Greedy H_n-approximation for Set Cover: repeatedly pick the set
// covering the most uncovered elements.

#include <iostream>
#include <vector>

std::vector<bool> approxSetCover(const std::vector<std::vector<int>>& S, int n) {
    int m = (int)S.size();
    std::vector<bool> C(m, false), covered(n, false);
    while (true) {
        int bestIdx = -1, bestCover = 0;
        for (int s = 0; s < m; ++s) {
            if (C[s]) continue;
            int count = 0;
            for (int e = 0; e < n; ++e)
                if (S[s][e] == 1 && !covered[e]) ++count;
            if (count > bestCover) { bestCover = count; bestIdx = s; }
        }
        if (bestIdx == -1) break;
        C[bestIdx] = true;
        for (int e = 0; e < n; ++e) if (S[bestIdx][e] == 1) covered[e] = true;
    }
    return C;
}

int main() {
    // 6-element universe, 4 sets.
    std::vector<std::vector<int>> S = {
        {1, 1, 1, 0, 0, 0},
        {1, 0, 0, 1, 1, 0},
        {0, 1, 0, 0, 1, 1},
        {0, 0, 1, 0, 0, 1}
    };
    auto C = approxSetCover(S, 6);
    std::cout << "picked:";
    for (size_t s = 0; s < C.size(); ++s) if (C[s]) std::cout << ' ' << s;
    std::cout << '\n';
    return 0;
}
