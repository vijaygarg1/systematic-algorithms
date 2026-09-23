// Interval scheduling: maximum compatible subset, greedy by earliest finish.
// Inputs s, f are pre-sorted by finish time.

#include <iostream>
#include <vector>

std::vector<int> schedule(const std::vector<int>& s, const std::vector<int>& f) {
    int n = (int)s.size();
    std::vector<int> G(n, 0);
    if (n == 0) return G;
    G[0] = 1;
    int last = 0;
    for (int i = 1; i < n; ++i) {
        if (s[i] >= f[last]) { G[i] = 1; last = i; }
    }
    return G;
}

int main() {
    std::vector<int> s = {1, 3, 0, 5, 8, 5};
    std::vector<int> f = {4, 5, 6, 7, 9, 9};
    auto G = schedule(s, f);
    std::cout << "selected:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
