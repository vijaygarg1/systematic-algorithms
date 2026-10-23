// LLP-JobScheduling with an explicit `fixed` array: a job is advanced only
// once every prerequisite has been fixed, so each job is settled exactly
// once, in dependency order. That is the same schedule the dependList /
// topological lowering gives the other variants -- here it is written out
// in the program itself.

#include <iostream>
#include <vector>
#include <climits>

std::vector<int> llpJobSchedulingFixed(const std::vector<int>& t,
                                       const std::vector<std::vector<int>>& pre) {
    int n = (int) t.size();
    std::vector<int> G(t);
    std::vector<bool> fixedFlag(n, false);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (fixedFlag[j]) continue;
            bool allFixed = true;
            for (size_t k = 0; k < pre[j].size() && allFixed; ++k)
                if (!fixedFlag[pre[j][k]]) allFixed = false;
            if (!allFixed) continue;
            long long best = (long long) t[j];
            for (size_t k = 0; k < pre[j].size(); ++k) {
                long long cand = (long long) G[pre[j][k]] + t[j];
                if (cand > best) best = cand;
            }
            G[j] = (int) best;
            fixedFlag[j] = true;
            changed = true;
        }
    }
    return G;
}

int main() {
    std::vector<int> t = {3, 2, 4, 1};
    std::vector<std::vector<int>> pre = {{}, {0}, {0}, {1, 2}};
    std::vector<int> G = llpJobSchedulingFixed(t, pre);
    std::cout << "completion times:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
