// Minimise max lateness on a single processor: earliest-deadline first.
// Inputs t, d are pre-sorted by deadline.

#include <iostream>
#include <vector>
#include <utility>

std::pair<std::vector<int>, int> schedule(const std::vector<int>& t,
                                          const std::vector<int>& d) {
    int n = (int)t.size();
    std::vector<int> G(n, 0);
    int last = 0, maxLate = 0;
    for (int i = 0; i < n; ++i) {
        G[i] = last;
        last += t[i];
        int late = last - d[i];
        if (late > maxLate) maxLate = late;
    }
    return {G, maxLate};
}

int main() {
    std::vector<int> t = {3, 2, 1, 4, 3, 2};
    std::vector<int> d = {6, 8, 9, 9, 14, 15};
    auto [G, mx] = schedule(t, d);
    std::cout << "start times:"; for (int x : G) std::cout << ' ' << x; std::cout << '\n';
    std::cout << "max lateness: " << mx << '\n';
    return 0;
}
