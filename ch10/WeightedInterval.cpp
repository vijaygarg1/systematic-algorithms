// Classical sequential weighted-interval-scheduling DP given p[].

#include <iostream>
#include <vector>

std::vector<int> schedule(const std::vector<int>& s,
                          const std::vector<int>& f,
                          const std::vector<int>& w,
                          const std::vector<int>& p) {
    (void)s; (void)f;
    int n = (int)s.size();
    std::vector<int> opt(n, 0), G(n, 0);
    for (int cur = 1; cur < n; ++cur) {
        opt[cur] = opt[cur - 1];
        if (w[cur] + opt[p[cur]] >= opt[cur - 1]) {
            opt[cur] = w[cur] + opt[p[cur]];
            G[cur] = 1;
        }
    }
    return G;
}

int main() {
    // Index 0 is a 0-weight sentinel.
    std::vector<int> s = {0, 1, 2, 4, 6, 5};
    std::vector<int> f = {0, 3, 5, 6, 8, 9};
    std::vector<int> w = {0, 4, 6, 5, 3, 7};
    std::vector<int> p = {0, 0, 0, 1, 3, 2};
    auto G = schedule(s, f, w, p);
    std::cout << "selected:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
