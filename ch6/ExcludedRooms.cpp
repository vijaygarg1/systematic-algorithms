// ExcludedRooms: composition program enforcing R[j] = excluded rooms.
// G[j] is the room assigned to course j; advance picks the smallest
// non-excluded room not used by any overlapping earlier course.

#include <iostream>
#include <vector>
#include <set>

std::vector<int> excludedRooms(const std::vector<std::set<int>>& R,
                               const std::vector<std::set<int>>& pre,
                               std::vector<int> G) {
    int n = (int)G.size();
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (R[j].count(G[j])) {
                for (int r = 1; r <= n; ++r) {
                    if (R[j].count(r)) continue;
                    bool clash = false;
                    for (int k : pre[j]) if (G[k] == r) { clash = true; break; }
                    if (clash) continue;
                    if (G[j] != r) { G[j] = r; changed = true; }
                    break;
                }
            }
        }
    }
    return G;
}

int main() {
    std::vector<std::set<int>> R = {{1}, {2}, {}};
    std::vector<std::set<int>> pre = {{}, {0}, {0, 1}};
    std::vector<int> G = {1, 2, 1};
    auto out = excludedRooms(R, pre, G);
    std::cout << "G:";
    for (int g : out) std::cout << ' ' << g;
    std::cout << '\n';
    return 0;
}
