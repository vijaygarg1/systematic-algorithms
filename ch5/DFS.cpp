// Recursive DFS recording discovery and finish times.

#include <iostream>
#include <vector>
#include <tuple>

void visit(int j,
           const std::vector<std::vector<int>>& succ,
           std::vector<bool>& visited,
           std::vector<int>& parent,
           std::vector<int>& discovered,
           std::vector<int>& finished,
           int& tick) {
    visited[j] = true;
    discovered[j] = tick++;
    for (int k : succ[j]) {
        if (!visited[k]) { parent[k] = j; visit(k, succ, visited, parent, discovered, finished, tick); }
    }
    finished[j] = tick++;
}

void dfs(const std::vector<std::vector<int>>& succ,
         std::vector<int>& discovered,
         std::vector<int>& parent,
         std::vector<int>& finished) {
    int n = (int)succ.size();
    std::vector<bool> visited(n, false);
    parent.assign(n, -1);
    discovered.assign(n, 0);
    finished.assign(n, 0);
    int tick = 1;
    visit(0, succ, visited, parent, discovered, finished, tick);
}

int main() {
    std::vector<std::vector<int>> succ = {
        {1, 2}, {3}, {3, 4}, {5}, {5}, {}
    };
    std::vector<int> discovered, parent, finished;
    dfs(succ, discovered, parent, finished);
    std::cout << "discovered:"; for (int x : discovered) std::cout << ' ' << x; std::cout << '\n';
    std::cout << "parent:    "; for (int x : parent)     std::cout << ' ' << x; std::cout << '\n';
    std::cout << "finished:  "; for (int x : finished)   std::cout << ' ' << x; std::cout << '\n';
    return 0;
}
