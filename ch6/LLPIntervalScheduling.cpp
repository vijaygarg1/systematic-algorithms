// LLP-IntervalScheduling: jobs sorted by finish time, held in a doubly
// linked list (prevArr/nextArr). Job 0 is always fixed initially. The
// book's two rules share one fixpoint, so every job is tested against
// both on each sweep: rule 1 selects a job whose list-predecessor has
// already finished by s[j]; rule 2 deletes a job its selected predecessor
// still overlaps, in O(1), by unlinking the node.

#include <iostream>
#include <vector>

std::vector<bool> llpIntervalScheduling(const std::vector<int>& s,
                                        const std::vector<int>& f) {
    int n = (int) s.size();
    std::vector<bool> G(n, false), deleted(n, false);
    std::vector<int> prevArr(n), nextArr(n);
    for (int i = 0; i < n; ++i) { prevArr[i] = i - 1; nextArr[i] = i + 1; }
    if (n > 0) G[0] = true;
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (j > 0 && !deleted[j] && !G[j] && f[prevArr[j]] <= s[j]) {
                G[j] = true;
                changed = true;
            }
            if (j > 0 && !deleted[j] && G[prevArr[j]] && s[j] < f[prevArr[j]]) {
                deleted[j] = true;
                int p = prevArr[j];
                int nx = nextArr[j];
                nextArr[p] = nx;
                if (nx < n) prevArr[nx] = p;
                changed = true;
            }
        }
    }
    return G;
}

int main() {
    std::vector<int> s = {0, 2, 4, 5}, f = {3, 6, 7, 9};
    std::vector<bool> G = llpIntervalScheduling(s, f);
    std::cout << "selected:";
    for (int j = 0; j < (int) G.size(); ++j) if (G[j]) std::cout << ' ' << j;
    std::cout << '\n';
    return 0;
}
