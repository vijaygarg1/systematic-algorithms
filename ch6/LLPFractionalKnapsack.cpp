// LLP-FractionalKnapsack: items pre-sorted by v/w density; raise each
// fraction G[j] in [0,1] to its target G*[j] derived from the prefix
// sum of weights compared against capacity W.

#include <iostream>
#include <vector>

double prefixWeight(const std::vector<double>& w, int upto) {
    double s = 0.0;
    for (int i = 0; i <= upto; ++i) s += w[i];
    return s;
}

double target(const std::vector<double>& w, double W, int j) {
    double prev = prefixWeight(w, j - 1);
    double cur  = prev + w[j];
    if (cur <= W) return 1.0;
    if (prev >= W) return 0.0;
    return (W - prev) / w[j];
}

std::vector<double> llpFractionalKnapsack(const std::vector<double>& v,
                                          const std::vector<double>& w,
                                          double W) {
    int n = (int)v.size();
    std::vector<double> G(n, 0.0);
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            double t = target(w, W, j);
            if (G[j] < t) { G[j] = t; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<double> v = {1.0, 2.0, 3.0, 4.0};
    std::vector<double> w = {1.0, 2.0, 3.0, 4.0};
    double W = 5.0;
    auto G = llpFractionalKnapsack(v, w, W);
    std::cout << "G:";
    for (double x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
