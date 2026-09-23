// LLP-Prim with helper aux functions.

#include <iostream>
#include <vector>
#include <climits>

using Matrix = std::vector<std::vector<int>>;
constexpr int INF = INT_MAX / 2;

double minCrossCut(int j, const std::vector<bool>& fixedFlag,
                   const Matrix& W, int n) {
    double best = INF;
    for (int i = 0; i < n; ++i) {
        if (fixedFlag[i] && W[i][j] < best) best = W[i][j];
    }
    return best;
}

int argMinCrossCut(int j, const std::vector<bool>& fixedFlag,
                   const Matrix& W, int n) {
    int besti = -1;
    double best = INF;
    for (int i = 0; i < n; ++i) {
        if (fixedFlag[i] && W[i][j] < best) { best = W[i][j]; besti = i; }
    }
    return besti;
}

double globalMinCrossCut(const std::vector<bool>& fixedFlag,
                         const Matrix& W, int n) {
    double best = INF;
    for (int j = 0; j < n; ++j) {
        if (!fixedFlag[j]) {
            double m = minCrossCut(j, fixedFlag, W, n);
            if (m < best) best = m;
        }
    }
    return best;
}

void propagateFixed(const std::vector<int>& parent,
                    std::vector<bool>& fixedFlag, int n) {
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (!fixedFlag[j] && fixedFlag[parent[j]]) { fixedFlag[j] = true; changed = true; }
        }
    }
}

std::vector<double> llpPrim(std::vector<int>& parent,
                            std::vector<bool>& fixedFlag,
                            const Matrix& W,
                            std::vector<double>& C, int /*root*/) {
    int n = (int)parent.size();
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (fixedFlag[j]) continue;
            int arg = argMinCrossCut(j, fixedFlag, W, n);
            if (arg < 0) continue;
            double mcc = minCrossCut(j, fixedFlag, W, n);
            if (mcc <= globalMinCrossCut(fixedFlag, W, n) && C[j] < mcc) {
                parent[j] = arg;
                C[j] = W[arg][j];
                propagateFixed(parent, fixedFlag, n);
                changed = true;
                break;
            }
        }
    }
    return C;
}

int main() {
    Matrix W = {
        {0, 1, 3, INF, INF},
        {1, 0, 2, 6, INF},
        {3, 2, 0, 4, 5},
        {INF, 6, 4, 0, 7},
        {INF, INF, 5, 7, 0},
    };
    int n = (int)W.size(), root = 0;
    std::vector<bool>   fixedFlag(n, false); fixedFlag[root] = true;
    std::vector<int>    parent(n, root);
    std::vector<double> C(n, 0.0);
    for (int j = 0; j < n; ++j) {
        if (j == root) continue;
        double best = INF;  int besti = root;
        for (int i = 0; i < n; ++i)
            if (i != j && W[i][j] < best) { best = W[i][j]; besti = i; }
        parent[j] = besti;
        C[j] = W[besti][j];
    }
    llpPrim(parent, fixedFlag, W, C, root);
    std::cout << "parent:"; for (int x : parent) std::cout << ' ' << x; std::cout << '\n';
    std::cout << "C:";       for (double x : C)  std::cout << ' ' << x; std::cout << '\n';
    return 0;
}
