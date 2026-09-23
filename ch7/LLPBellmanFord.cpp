// LLP-BellmanFord shortest paths.  Edge weights may be negative.

#include <iostream>
#include <vector>
#include <climits>

using Matrix = std::vector<std::vector<int>>;
constexpr int INF = INT_MAX / 2;             // half-INF to avoid overflow

bool forbidden(int j, const std::vector<int>& d,
               const std::vector<std::vector<int>>& pre,
               const Matrix& w) {
    for (int i : pre[j]) {
        if (d[i] != INF && d[i] + w[i][j] < d[j]) return true;
    }
    return false;
}

void advance(std::vector<int>& d,
             const std::vector<std::vector<int>>& pre,
             const Matrix& w, int n) {
    std::vector<int> nd = d;
    for (int k = 0; k < n; ++k) {
        for (int i : pre[k]) {
            if (d[i] != INF && d[i] + w[i][k] < nd[k]) nd[k] = d[i] + w[i][k];
        }
    }
    d = nd;
}

std::vector<int> llpBellmanFord(const std::vector<std::vector<int>>& pre,
                                const Matrix& w, int n) {
    std::vector<int> d(n, INF);
    d[0] = 0;
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (forbidden(j, d, pre, w)) { advance(d, pre, w, n); changed = true; break; }
        }
    }
    return d;
}

int main() {
    int n = 4;
    std::vector<std::vector<int>> pre = { {}, {0}, {0, 1}, {2} };
    Matrix w(n, std::vector<int>(n, INF));
    w[0][1] = 1;  w[0][2] = 4;  w[1][2] = -3;  w[2][3] = 1;
    auto d = llpBellmanFord(pre, w, n);
    std::cout << "d =";
    for (int x : d) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
