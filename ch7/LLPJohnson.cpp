// LLP-Johnson: compute non-negative price/potential vector p such that
// the reweighted edges w[i][j] + p[j] - p[i] are all >= 0.

#include <iostream>
#include <vector>
#include <climits>

using Matrix = std::vector<std::vector<int>>;
constexpr int INF = INT_MAX / 2;

bool forbidden(int j, const std::vector<int>& p,
               const std::vector<std::vector<int>>& pre,
               const Matrix& w) {
    for (int i : pre[j]) {
        if (p[j] < p[i] - w[i][j]) return true;
    }
    return false;
}

void advance(std::vector<int>& p,
             const std::vector<std::vector<int>>& pre,
             const Matrix& w, int n) {
    std::vector<int> np = p;
    for (int k = 0; k < n; ++k) {
        for (int i : pre[k]) {
            int cand = p[i] - w[i][k];
            if (cand > np[k]) np[k] = cand;
        }
    }
    p = np;
}

std::vector<int> llpJohnson(const std::vector<std::vector<int>>& pre,
                            const Matrix& w, int n) {
    std::vector<int> p(n, 0);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (forbidden(j, p, pre, w)) { advance(p, pre, w, n); changed = true; break; }
        }
    }
    return p;
}

int main() {
    int n = 4;
    std::vector<std::vector<int>> pre = { {}, {0}, {0, 1}, {2} };
    Matrix w(n, std::vector<int>(n, INF));
    w[0][1] = 1;  w[0][2] = 4;  w[1][2] = -3;  w[2][3] = 1;
    auto p = llpJohnson(pre, w, n);
    std::cout << "potentials:";
    for (int x : p) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
