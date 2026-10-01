// LLP assignment: minimum clearing price vector. When no perfect
// matching exists in the current tight-edge graph, find an
// inclusion-minimal overdemanded set J via alternating-path
// reachability from an unmatched bidder, then raise every item in J by
// ONE SHARED amount delta = min over bidders demanding into J of
// [bidder's best surplus - bidder's best surplus using an item
// outside J].

#include <iostream>
#include <vector>
#include <climits>

bool tryMatch(int b, const std::vector<std::vector<int>>& v,
              const std::vector<int>& C, std::vector<int>& partner,
              std::vector<bool>& seen) {
    int n = (int)C.size();
    int bestSurplus = INT_MIN;
    for (int i = 0; i < n; ++i)
        if (v[b][i] - C[i] > bestSurplus) bestSurplus = v[b][i] - C[i];
    for (int i = 0; i < n; ++i) {
        if (v[b][i] - C[i] != bestSurplus || seen[i]) continue;
        seen[i] = true;
        if (partner[i] == -1 || tryMatch(partner[i], v, C, partner, seen)) {
            partner[i] = b;
            return true;
        }
    }
    return false;
}

int bestSurplus(int b, const std::vector<std::vector<int>>& v, const std::vector<int>& C) {
    int n = (int)C.size();
    int best = INT_MIN;
    for (int i = 0; i < n; ++i) if (v[b][i] - C[i] > best) best = v[b][i] - C[i];
    return best;
}

int bestSurplusOutside(int b, const std::vector<std::vector<int>>& v,
                       const std::vector<int>& C, const std::vector<bool>& itemInJ) {
    int n = (int)C.size();
    int best = INT_MIN;
    for (int i = 0; i < n; ++i)
        if (!itemInJ[i] && v[b][i] - C[i] > best) best = v[b][i] - C[i];
    return best;
}

void reach(int b, const std::vector<std::vector<int>>& v, const std::vector<int>& C,
          const std::vector<int>& partner, std::vector<bool>& itemInJ, std::vector<bool>& bidderInB) {
    if (bidderInB[b]) return;
    bidderInB[b] = true;
    int n = (int)C.size();
    int best = bestSurplus(b, v, C);
    for (int i = 0; i < n; ++i) {
        if (v[b][i] - C[i] == best && !itemInJ[i]) {
            itemInJ[i] = true;
            if (partner[i] != -1) reach(partner[i], v, C, partner, itemInJ, bidderInB);
        }
    }
}

std::vector<int> llpAssignment(const std::vector<std::vector<int>>& v) {
    int n = (int)v[0].size();
    int m = (int)v.size();
    std::vector<int> C(n, 0);
    while (true) {
        std::vector<int> partner(n, -1);
        for (int b = 0; b < m; ++b) {
            std::vector<bool> seen(n, false);
            tryMatch(b, v, C, partner, seen);
        }
        std::vector<bool> bidderMatched(m, false);
        for (int i = 0; i < n; ++i) if (partner[i] != -1) bidderMatched[partner[i]] = true;
        int unmatched = -1;
        for (int b = 0; b < m; ++b) if (!bidderMatched[b]) { unmatched = b; break; }
        if (unmatched == -1) return C;

        std::vector<bool> itemInJ(n, false), bidderInB(m, false);
        reach(unmatched, v, C, partner, itemInJ, bidderInB);
        int delta = INT_MAX;
        for (int b = 0; b < m; ++b) {
            if (bidderInB[b]) {
                int gap = bestSurplus(b, v, C) - bestSurplusOutside(b, v, C, itemInJ);
                if (gap < delta) delta = gap;
            }
        }
        for (int j = 0; j < n; ++j) if (itemInJ[j]) C[j] += delta;
    }
}

int main() {
    std::vector<std::vector<int>> v = {
        {5, 3, 1},
        {4, 4, 2},
        {1, 2, 5}
    };
    auto C = llpAssignment(v);
    std::cout << "prices:";
    for (int x : C) std::cout << ' ' << x;
    std::cout << '\n';

    // Tie case: 3 bidders tied on items {0,1}, item 2 undesired.
    std::vector<std::vector<int>> v2 = {
        {20, 20, 0},
        {20, 20, 0},
        {20, 20, 0}
    };
    auto C2 = llpAssignment(v2);
    std::cout << "tie case:";
    for (int x : C2) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
