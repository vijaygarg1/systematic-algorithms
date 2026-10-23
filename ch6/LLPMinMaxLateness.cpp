// LLP-MinMaxLate: schedule jobs (sorted by deadline) by raising each start
// time G[j] to the prefix sum of earlier processing times.
//
// The targets S[j] = t[0] + ... + t[j-1] are built once in a left-to-right
// scan, as in the .llp: writing the reduction inline would make the guard
// an O(j) loop re-run for every j, which is O(n^2) for a bound the book
// states as O(n).

#include <iostream>
#include <vector>

std::vector<int> llpMinMaxLateness(const std::vector<int>& t,
                                   const std::vector<int>& d) {
    (void) d;                       // the sort order of the input, not read here
    int n = (int) t.size();
    std::vector<int> S(n, 0), G(n, 0);
    for (int j = 1; j < n; ++j) S[j] = S[j - 1] + t[j - 1];
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < n; ++j) {
            if (G[j] < S[j]) { G[j] = S[j]; changed = true; }
        }
    }
    return G;
}

int main() {
    std::vector<int> t = {0, 0, 1, 2}, d = {1, 2, 4, 7};
    std::vector<int> G = llpMinMaxLateness(t, d);
    std::cout << "start times:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
