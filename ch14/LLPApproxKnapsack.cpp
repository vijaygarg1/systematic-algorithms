// LLP FPTAS for Knapsack: profit-indexed min-weight DP D[i][p] on a
// lattice of size (nf+1) x (V'+1), independent of the capacity W --
// this is what keeps the scheme fully polynomial (a capacity-indexed
// lattice would instead grow with W and remain merely
// pseudo-polynomial). D[i][p] = minimum total weight of a subset of
// the first i feasible items with scaled profit >= p. Returns
// p* = max{p : D[nf][p] <= W}.

#include <iostream>
#include <vector>
#include <algorithm>

int llpApproxKnapsack(const std::vector<int>& w, const std::vector<int>& v,
                      int W, int epsNum, int epsDen) {
    int n = (int)w.size();
    std::vector<int> fw, fv;
    for (int i = 0; i < n; ++i) if (w[i] <= W) { fw.push_back(w[i]); fv.push_back(v[i]); }
    int nf = (int)fw.size();
    if (nf == 0) return 0;
    int M = *std::max_element(fv.begin(), fv.end());

    std::vector<int> vPrime(nf);
    int Vp = 0;
    for (int i = 0; i < nf; ++i) {
        vPrime[i] = (long long)fv[i] * nf * epsDen / ((long long)epsNum * M);
        Vp += vPrime[i];
    }

    int INF = 1;
    for (int x : fw) INF += x;

    std::vector<std::vector<int>> D(nf + 1, std::vector<int>(Vp + 1, INF));
    for (int i = 0; i <= nf; ++i) D[i][0] = 0;

    bool changed = true;
    while (changed) {
        changed = false;
        for (int i = 1; i <= nf; ++i) {
            for (int p = 1; p <= Vp; ++p) {
                int target = D[i - 1][p];
                int prev = std::max(0, p - vPrime[i - 1]);
                int take = fw[i - 1] + D[i - 1][prev];
                if (take < target) target = take;
                if (target < D[i][p]) { D[i][p] = target; changed = true; }
            }
        }
    }

    int pStar = 0;
    for (int p = 0; p <= Vp; ++p) if (D[nf][p] <= W) pStar = p;
    return pStar;
}

int main() {
    std::vector<int> w = {2, 3, 4};
    std::vector<int> v = {30, 40, 50};
    int W = 6;
    int pStar = llpApproxKnapsack(w, v, W, /*epsNum=*/1, /*epsDen=*/5);
    std::cout << "p* = " << pStar << " (expect 24)\n";
    double scale = (1.0 * 50) / (5 * 3);
    std::cout << "approx profit = " << (scale * pStar) << " (expect ~80)\n";
    return 0;
}
