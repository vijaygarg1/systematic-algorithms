// Gale-Shapley algorithm for the man-optimal stable marriage.
// Returns G[1..n] where G[i] is the number of proposals made by man i.
//
// The free men are held on an explicit stack so that "pick any free man"
// is O(1). That is what the book's analysis requires: the while loop runs
// O(n^2) times because each G[i] rises at most n times, and
// bxx-stableMatching.tex:161 states "Each iteration of the while loop
// takes O(1) time", giving O(n^2) overall. Scanning the freeman[] array
// for a free man instead costs O(n) per proposal and makes the whole
// loop O(n^3).

#include <iostream>
#include <vector>

std::vector<int> match(const std::vector<std::vector<int>>& mpref,
                       const std::vector<std::vector<int>>& rank) {
    int n = (int)mpref.size() - 1;          // 1-based; slot 0 is padding
    std::vector<int> G(n + 1, 0);
    std::vector<int> partner(n + 1, 0);
    std::vector<int> freeMen;
    freeMen.reserve(n + 1);
    for (int i = 1; i <= n; ++i) freeMen.push_back(i);

    while (!freeMen.empty()) {
        int i = freeMen.back();
        freeMen.pop_back();
        ++G[i];
        int z = mpref[i][G[i]];
        if (partner[z] == 0) {
            partner[z] = i;
        } else {
            if (rank[z][i] < rank[z][partner[z]]) {
                freeMen.push_back(partner[z]);   // displaced, becomes free
                partner[z] = i;
            } else {
                freeMen.push_back(i);            // rejected, tries next
            }
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
