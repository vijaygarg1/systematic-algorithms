// LLP-StableMarriage: Gale-Shapley via a cached partner[] vector and an
// explicit FIFO worklist of currently-forbidden men -- O(1) amortized
// per check, O(n^2) worst case. See the .llp of the same name for why
// this is procedural rather than the language's forbidden/advance
// sugar (the compiler's realizations of that sugar either rescan all n
// men every round -- measured O(n^3) here -- or need a PriorityQueue
// that cannot cheaply re-examine a displaced man whose own proposal
// count did not change).
//
// G[i] is a proposal count (1-based once nonzero), so mpref lookups are
// mpref[i][G[i]-1], not mpref[i][G[i]].
//
// The queue is a circular buffer of capacity n+1: inQueue[] stops a man
// being enqueued twice at once, so at most n distinct men are ever
// in-flight, regardless of the total number of times each is pushed.

#include <iostream>
#include <vector>

std::vector<int> llpStableMarriage(const std::vector<std::vector<int>>& mpref,
                                    const std::vector<std::vector<int>>& rank) {
    int n = (int)mpref.size();
    std::vector<int> G(n, 0);
    std::vector<int> partner(n, -1);

    int cap = n + 1;
    std::vector<int> queue(cap);
    std::vector<bool> inQueue(n, false);
    int qHead = 0, qTail = 0, qCount = 0;
    for (int i = 0; i < n; ++i) {
        queue[qTail] = i;
        qTail = (qTail + 1) % cap;
        ++qCount;
        inQueue[i] = true;
    }

    while (qCount > 0) {
        int i = queue[qHead];
        qHead = (qHead + 1) % cap;
        --qCount;
        inQueue[i] = false;

        bool isForbidden;
        if (G[i] == 0) {
            isForbidden = true;
        } else {
            int z0 = mpref[i][G[i] - 1];
            isForbidden = partner[z0] != -1 && rank[z0][partner[z0]] < rank[z0][i];
        }

        if (isForbidden) {
            ++G[i];
            int z = mpref[i][G[i] - 1];
            if (partner[z] == -1 || rank[z][i] < rank[z][partner[z]]) {
                int y = partner[z];
                partner[z] = i;
                if (y != -1 && !inQueue[y]) {
                    queue[qTail] = y;
                    qTail = (qTail + 1) % cap;
                    ++qCount;
                    inQueue[y] = true;
                }
            }
            if (!inQueue[i]) {
                queue[qTail] = i;
                qTail = (qTail + 1) % cap;
                ++qCount;
                inQueue[i] = true;
            }
        }
    }
    return G;
}

int main() {
    // Three men, three women, 0-based.
    std::vector<std::vector<int>> mpref = {
        {0, 1, 2},
        {1, 0, 2},
        {0, 1, 2},
    };
    std::vector<std::vector<int>> rank = {
        {2, 1, 3},
        {1, 2, 3},
        {1, 2, 3},
    };
    auto G = llpStableMarriage(mpref, rank);
    for (int i = 0; i < (int)G.size(); ++i) {
        std::cout << "man " << i << ": " << G[i] << " proposals, matched with woman "
                  << mpref[i][G[i] - 1] << '\n';
    }
    return 0;
}
