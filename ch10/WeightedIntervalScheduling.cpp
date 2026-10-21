// Classical sequential weighted-interval-scheduling DP. Intervals are
// presorted once by finish time, with index 0 a 0-weight sentinel, so
// p[j] -- the last interval that finishes no later than j starts -- is
// derived here rather than supplied.

#include <iostream>
#include <vector>

std::vector<int> schedule(const std::vector<int>& s,
                          const std::vector<int>& f,
                          const std::vector<int>& w) {
    int n = (int)s.size();
    std::vector<int> opt(n, 0), G(n, 0), p(n, 0);
    for (int j = 1; j < n; ++j)
        for (int i = 1; i < j; ++i)
            if (f[i] <= s[j]) p[j] = i;        // f is sorted, so the last fit wins
    // Phase 1: compute optimal values.
    for (int cur = 1; cur < n; ++cur) {
        if (w[cur] + opt[p[cur]] >= opt[cur - 1]) {
            opt[cur] = w[cur] + opt[p[cur]];
        } else {
            opt[cur] = opt[cur - 1];
        }
    }
    // Phase 2: backtrack to find selected intervals.
    int cur = n - 1;
    while (cur > 0) {
        if (w[cur] + opt[p[cur]] >= opt[cur - 1]) {
            G[cur] = 1;
            cur = p[cur];
        } else {
            cur = cur - 1;
        }
    }
    return G;
}

int main() {
    // Book's 5-interval example.  Index 0 is a 0-weight sentinel.
    std::vector<int> s = {0, 1, 2, 4, 6, 5};
    std::vector<int> f = {0, 3, 5, 6, 8, 9};
    std::vector<int> w = {0, 4, 6, 5, 3, 7};
    auto G = schedule(s, f, w);
    std::cout << "selected:";
    for (size_t i = 0; i < G.size(); ++i) std::cout << ' ' << G[i];
    std::cout << '\n';
    return 0;
}
