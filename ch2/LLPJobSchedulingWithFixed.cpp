// LLP-LLPJobSchedulingWithFixed: O(n + m) topological-sort variant.

#include <iostream>
#include <vector>

std::vector<int> jobSchedulingWithFixed(
        const std::vector<int>& t,
        const std::vector<std::vector<int>>& pre,
        const std::vector<std::vector<int>>& succ) {
    int n = (int)t.size();
    std::vector<int> G = t;
    std::vector<int> count(n);
    for (int k = 0; k < n; ++k) count[k] = (int)pre[k].size();
    std::vector<int> queue;
    for (int k = 0; k < n; ++k) if (count[k] == 0) queue.push_back(k);

    int head = 0;
    while (head < (int)queue.size()) {
        int j = queue[head++];
        if (!pre[j].empty()) {
            int rhs = G[j];
            for (int i : pre[j]) {
                int v = G[i] + t[j];
                if (v > rhs) rhs = v;
            }
            G[j] = rhs;
        }
        for (int k : succ[j]) {
            if (--count[k] == 0) queue.push_back(k);
        }
    }
    return G;
}

int main() {
    std::vector<int>              t    = {3, 2, 4, 1, 2, 3};
    std::vector<std::vector<int>> pre  = {{}, {0}, {0}, {1, 2}, {2}, {3, 4}};
    std::vector<std::vector<int>> succ = {{1, 2}, {3}, {3, 4}, {5}, {5}, {}};
    auto G = jobSchedulingWithFixed(t, pre, succ);
    std::cout << "G =";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
