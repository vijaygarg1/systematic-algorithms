// LLP-FractionalKnapsack: items pre-sorted by v/w density; raise each
// fraction G[j] in [0,1] to its target, read in O(1) from one prefix scan
// of the weights.
//
// S is the EXCLUSIVE prefix sum, S[k] = w[0] + ... + w[k-1] with S[0] = 0,
// one slot longer than w. That removes target's j > 0 special case: the
// weight before item j is S[j] and the weight through it is S[j+1].

#include <iostream>
#include <vector>

static std::vector<double> S;

static double target(const std::vector<double>& w, double W, int j) {
    if (S[j + 1] <= W) return 1.0;
    if (S[j] >= W) return 0.0;
    return (W - S[j]) / w[j];
}

std::vector<double> llpFractionalKnapsack(const std::vector<double>& v,
                                          const std::vector<double>& w,
                                          double W) {
    (void) v;                       // the sort order of the input, not read here
    int n = (int) w.size();
    std::vector<double> G(n, 0.0);
    S.assign(n + 1, 0.0);
    for (int i = 0; i < n; ++i) S[i + 1] = S[i] + w[i];
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            double tg = target(w, W, j);
            if (G[j] < tg) { G[j] = tg; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<double> v = {60, 100, 120}, w = {10, 20, 30};
    std::vector<double> G = llpFractionalKnapsack(v, w, 50);
    std::cout << "fractions:";
    for (double x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
