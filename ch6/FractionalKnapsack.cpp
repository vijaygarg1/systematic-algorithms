// Fractional knapsack: highest value-density first.
// Items pre-sorted by v[i] / w[i] in non-increasing order.

#include <iostream>
#include <vector>

std::vector<double> solve(const std::vector<double>& v,
                          const std::vector<double>& w,
                          double W) {
    int n = (int)v.size();
    std::vector<double> x(n, 0.0);
    double rem = W;
    for (int i = 0; i < n; ++i) {
        if (w[i] <= rem) { x[i] = 1.0; rem -= w[i]; }
        else             { x[i] = rem / w[i]; break; }
    }
    return x;
}

int main() {
    std::vector<double> v = {60, 100, 120, 50};
    std::vector<double> w = {10, 20, 30, 40};
    double W = 50;
    auto x = solve(v, w, W);
    double total = 0;
    std::cout << "fractions:";
    for (size_t i = 0; i < x.size(); ++i) { std::cout << ' ' << x[i]; total += x[i] * v[i]; }
    std::cout << "\ntotal value: " << total << '\n';
    return 0;
}
