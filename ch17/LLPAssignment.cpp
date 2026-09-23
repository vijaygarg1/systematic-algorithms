// LLP assignment: minimum clearing price vector via step-jump price increments.
// Each iteration: identify overdemanded items, raise each by step[j] = min slack
// to the next critical price (the smallest amount that lets some bidder become
// indifferent and break a tight edge). Strongly polynomial.

#include <iostream>
#include <vector>
#include <climits>

bool tryMatch(int b, const std::vector<std::vector<int>>& v,
              const std::vector<int>& C, std::vector<int>& partner,
              std::vector<bool>& seen) {
    int n = (int)C.size();
    int bestSurplus = INT_MIN;
    for (int i = 0; i < n; ++i)
        if (v[b][i] - C[i] > bestSurplus) bestSurplus = v[b][i] - C[i];
    for (int i = 0; i < n; ++i) {
        if (v[b][i] - C[i] != bestSurplus || seen[i]) continue;
        seen[i] = true;
        if (partner[i] == -1 || tryMatch(partner[i], v, C, partner, seen)) {
            partner[i] = b;
            return true;
        }
    }
    return false;
}

bool checkPerfectMatching(const std::vector<std::vector<int>>& v, const std::vector<int>& C) {
    int n = (int)C.size();
    int m = (int)v.size();
    std::vector<int> partner(n, -1);
    int matched = 0;
    for (int b = 0; b < m; ++b) {
        std::vector<bool> seen(n, false);
        if (tryMatch(b, v, C, partner, seen)) ++matched;
    }
    return matched == m;
}

void raiseOverdemandedPrices(const std::vector<std::vector<int>>& v, std::vector<int>& C) {
    int n = (int)C.size();
    int m = (int)v.size();
    // Snapshot bestSurplus[b] before any prices change this round.
    std::vector<int> bestSurplus(m, INT_MIN);
    for (int b = 0; b < m; ++b)
        for (int i = 0; i < n; ++i)
            if (v[b][i] - C[i] > bestSurplus[b]) bestSurplus[b] = v[b][i] - C[i];
    // step[j] = min slack across bidders whose top choice includes j.
    std::vector<int> step(n, INT_MAX);
    std::vector<int> demand(n, 0);
    for (int j = 0; j < n; ++j) {
        for (int b = 0; b < m; ++b) {
            if (v[b][j] - C[j] != bestSurplus[b]) continue;
            ++demand[j];
            int secondBest = INT_MIN;
            for (int i = 0; i < n; ++i) {
                if (i == j) continue;
                if (v[b][i] - C[i] > secondBest) secondBest = v[b][i] - C[i];
            }
            int slack = (v[b][j] - C[j]) - secondBest;
            if (slack < step[j]) step[j] = slack;
        }
        // Integer arithmetic: a tied bidder gives slack 0; raise by at least 1.
        if (step[j] < 1) step[j] = 1;
    }
    for (int j = 0; j < n; ++j)
        if (demand[j] > 1) C[j] += step[j];
}

std::vector<int> llpAssignment(const std::vector<std::vector<int>>& v) {
    int n = (int)v[0].size();
    std::vector<int> C(n, 0);
    while (!checkPerfectMatching(v, C))
        raiseOverdemandedPrices(v, C);
    return C;
}

int main() {
    std::vector<std::vector<int>> v = {
        {5, 3, 1},
        {4, 4, 2},
        {1, 2, 5}
    };
    auto C = llpAssignment(v);
    std::cout << "prices:";
    for (int x : C) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
