// Horn SAT forward chaining (Dowling-Gallier): unit propagation with counters.

#include <iostream>
#include <vector>

std::vector<bool> hornSATFC(const std::vector<std::vector<int>>& body,
                            const std::vector<int>& head,
                            const std::vector<std::vector<int>>& adj) {
    int n = (int)adj.size();
    int m = (int)body.size();
    std::vector<bool> A(n, false);
    std::vector<int>  rem(m);
    for (int c = 0; c < m; ++c) rem[c] = (int)body[c].size();

    std::vector<int> queue;
    for (int c = 0; c < m; ++c)
        if (rem[c] == 0 && head[c] >= 0 && !A[head[c]]) queue.push_back(head[c]);

    bool sat = true;
    for (size_t front = 0; front < queue.size() && sat; ++front) {
        int x = queue[front];
        if (A[x]) continue;
        A[x] = true;
        for (int ci : adj[x]) {
            --rem[ci];
            if (rem[ci] == 0) {
                if (head[ci] < 0) { sat = false; break; }
                if (!A[head[ci]]) queue.push_back(head[ci]);
            }
        }
    }
    return A;
}

int main() {
    // 3 propositional variables: x0, x1, x2.
    // Clauses: x0 -> x1, x0 ∧ x1 -> x2, fact x0.
    std::vector<std::vector<int>> body = { {0}, {0, 1}, {} };
    std::vector<int> head = { 1, 2, 0 };
    std::vector<std::vector<int>> adj(3);
    for (int c = 0; c < 3; ++c) for (int x : body[c]) adj[x].push_back(c);
    auto A = hornSATFC(body, head, adj);
    std::cout << "A:";
    for (bool b : A) std::cout << ' ' << (b ? 1 : 0);
    std::cout << '\n';
    return 0;
}
