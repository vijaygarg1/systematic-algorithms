// Gale-Shapley algorithm for the man-optimal stable marriage.
// Returns G[1..n] where G[i] is the number of proposals made by man i.
//
// Written as bxx-stableMatching.tex's Algorithm Gale--Shapley states it:
// the free men are held in a list (mList), so "pick a free man" is O(1).
// That is what the book's analysis requires: the while loop runs O(n^2)
// times because each G[i] rises at most n times, and the book states
// "Each iteration of the while loop takes O(1) time", giving O(n^2)
// overall. Scanning an array of free flags instead costs O(n) per
// proposal and makes the whole loop O(n^3).

#include <deque>
#include <iostream>
#include <vector>

std::vector<int> GaleShapley(const std::vector<std::vector<int>>& mpref,
                             const std::vector<std::vector<int>>& rank) {
    int n = (int)mpref.size() - 1;          // 1-based; slot 0 is padding
    std::deque<int> mList;                  // men that are free
    std::vector<int> partner(n + 1, 0);     // current fiancee for woman i
    std::vector<int> G(n + 1, 0);           // proposals made by man i
    for (int i = 1; i <= n; ++i) mList.push_back(i);

    while (!mList.empty()) {
        int i = mList.front();
        mList.pop_front();
        G[i] = G[i] + 1;                    // next top choice for man i
        int z = mpref[i][G[i]];             // woman corresponding to it
        if (partner[z] == 0) {
            partner[z] = i;
        } else {
            if (rank[z][i] < rank[z][partner[z]]) {
                mList.push_back(partner[z]);   // displaced, free again
                partner[z] = i;
            } else {
                mList.push_back(i);            // rejected, stays free
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
    auto G = GaleShapley(mpref, rank);
    for (int i = 1; i < (int)G.size(); ++i) {
        std::cout << "man " << i << ": proposed " << G[i] << " time(s), "
                  << "engaged to woman " << mpref[i][G[i]] << '\n';
    }
    return 0;
}
