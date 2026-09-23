// Gale-Shapley algorithm for the man-optimal stable marriage.
// Returns G[1..n] where G[i] is the number of proposals made by man i.

#include <iostream>
#include <vector>

std::vector<int> match(const std::vector<std::vector<int>>& mpref,
                       const std::vector<std::vector<int>>& rank) {
    int n = (int)mpref.size() - 1;          // 1-based; slot 0 is padding
    std::vector<int> G(n + 1, 0);
    std::vector<int> partner(n + 1, 0);
    std::vector<bool> freeman(n + 1, false);
    for (int i = 1; i <= n; ++i) freeman[i] = true;

    while (true) {
        int i = 1;
        while (i <= n && !freeman[i]) ++i;
        if (i > n) break;
        ++G[i];
        int z = mpref[i][G[i]];
        if (partner[z] == 0) {
            partner[z] = i;
            freeman[i] = false;
        } else {
            if (rank[z][i] < rank[z][partner[z]]) {
                freeman[partner[z]] = true;
                partner[z] = i;
                freeman[i] = false;
            }
            // else: i stays free; tries next preference next round.
        }
    }
    return G;
}

int main() {
    // Three men, three women.  Slot 0 is padding throughout.
    std::vector<std::vector<int>> mpref = {
        {0, 0, 0, 0},
        {0, 1, 2, 3},
        {0, 2, 1, 3},
        {0, 1, 2, 3},
    };
    std::vector<std::vector<int>> rank = {
        {0, 0, 0, 0},
        {0, 3, 1, 2},
        {0, 2, 3, 1},
        {0, 1, 2, 3},
    };
    auto G = match(mpref, rank);
    for (int i = 1; i < (int)G.size(); ++i) {
        std::cout << "man " << i << ": proposed " << G[i] << " time(s), "
                  << "engaged to woman " << mpref[i][G[i]] << '\n';
    }
    return 0;
}
