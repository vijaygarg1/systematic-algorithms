// LLP-JobScheduling, written as an explicit forbidden/advance rule.
//
// G[j] is the completion time of job j: at least its own duration t[j],
// and at least t[j] after every prerequisite has finished. An empty
// prerequisite list leaves G[j] at t[j], since a max over an empty range
// is the identity and never raises anything.

#include <iostream>
#include <vector>
#include <climits>

std::vector<int> llpJobSchedulingForbidden(const std::vector<int>& t,
                     const std::vector<std::vector<int>>& pre) {
    int n = (int) t.size();
    std::vector<int> G(t);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            long long best = LLONG_MIN;              // max over an empty pre[j]
            for (size_t k = 0; k < pre[j].size(); ++k) {
                long long cand = (long long) G[pre[j][k]] + t[j];
                if (cand > best) best = cand;
            }
            if (best != LLONG_MIN && G[j] < best) {
                G[j] = (int) best;
                changed = true;
            }
        }
    }
    return G;
}

int main() {
    std::vector<int> t = {3, 2, 4, 1};
    std::vector<std::vector<int>> pre = {{}, {0}, {0}, {1, 2}};
    std::vector<int> G = llpJobSchedulingForbidden(t, pre);
    std::cout << "completion times:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
