// LLP-HousingMarket: Top Trading Cycles as a lattice-linear predicate.
// G[j] is how far agent j has advanced down its preference list; j is
// forbidden when it is not itself on a wish cycle but the agent it wishes
// for is, so that wish can never be granted and j must move on.
//
// S(G) is built for the whole market at once, which is what makes the
// round cost O(n) and the algorithm O(n^2): every agent wishes for exactly
// one house, so the wish graph is functional and its cycles are precisely
// what survives repeatedly discarding the agents nobody wishes for.
// inS[] is rebuilt only after an advance has moved a wish.

#include <iostream>
#include <vector>

static int wish(int i, const std::vector<int>& G,
                const std::vector<std::vector<int>>& pref) {
    return pref[i][G[i]];
}

// Rebuild S(G). An agent nobody wishes for cannot lie on a cycle;
// discarding it may leave its own wish with no one wishing for it, so the
// discards cascade. What remains is exactly the union of the cycles. Each
// agent enters the queue at most once, so this is O(n) for the market.
static void refresh(const std::vector<int>& G,
                    const std::vector<std::vector<int>>& pref,
                    std::vector<char>& inS, std::vector<int>& indeg,
                    std::vector<int>& q, bool& fresh) {
    if (fresh) return;
    int n = (int) G.size();
    for (int i = 0; i < n; ++i) { inS[i] = 1; indeg[i] = 0; }
    for (int i = 0; i < n; ++i) indeg[wish(i, G, pref)]++;
    int qt = 0;
    for (int i = 0; i < n; ++i) if (indeg[i] == 0) q[qt++] = i;
    int qh = 0;
    while (qh < qt) {
        int x = q[qh++];
        inS[x] = 0;
        int y = wish(x, G, pref);
        if (--indeg[y] == 0) q[qt++] = y;
    }
    fresh = true;
}

std::vector<int> llpHousingMarket(const std::vector<std::vector<int>>& pref) {
    int n = (int) pref.size();
    std::vector<int> G(n, 0);
    std::vector<char> inS(n, 0);
    std::vector<int> indeg(n, 0), q(n, 0);
    bool fresh = false;
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            refresh(G, pref, inS, indeg, q, fresh);
            if (!inS[j] && inS[wish(j, G, pref)]) {
                G[j] = G[j] + 1;
                fresh = false;
                changed = true;
            }
        }
    }
    return G;
}

int main() {
    std::vector<std::vector<int>> pref = {
        {1, 0, 2},
        {0, 1, 2},
        {2, 0, 1}};
    std::vector<int> G = llpHousingMarket(pref);
    std::cout << "assignment:";
    for (int j = 0; j < (int) G.size(); ++j) std::cout << ' ' << pref[j][G[j]];
    std::cout << '\n';
    return 0;
}
