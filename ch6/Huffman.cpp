// Huffman coding: repeatedly merge the two least-probable nodes into a binary tree.

#include <iostream>
#include <vector>
#include <queue>
#include <string>

struct Node {
    double prob;
    int symbol;
    Node* left;
    Node* right;
    Node(double prob, int symbol) : prob(prob), symbol(symbol), left(nullptr), right(nullptr) {}
    Node(double prob, Node* left, Node* right) : prob(prob), symbol(-1), left(left), right(right) {}
};

struct Compare {
    bool operator()(const Node* a, const Node* b) const { return a->prob > b->prob; }
};

void assignCodes(Node* node, const std::string& prefix, std::vector<std::string>& codes) {
    if (node->symbol != -1) {
        codes[node->symbol] = prefix.empty() ? "0" : prefix;
        return;
    }
    assignCodes(node->left, prefix + "0", codes);
    assignCodes(node->right, prefix + "1", codes);
}

std::vector<std::string> huffmanCodes(const std::vector<double>& p) {
    int n = (int)p.size();
    std::priority_queue<Node*, std::vector<Node*>, Compare> Q;
    for (int i = 0; i < n; ++i) Q.push(new Node(p[i], i));
    for (int i = 1; i <= n - 1; ++i) {
        Node* x = Q.top(); Q.pop();
        Node* y = Q.top(); Q.pop();
        Q.push(new Node(x->prob + y->prob, x, y));
    }
    Node* root = Q.top();
    std::vector<std::string> codes(n);
    assignCodes(root, "", codes);
    return codes;
}

int main() {
    std::vector<double> p = {0.45, 0.13, 0.12, 0.16, 0.09, 0.05};
    auto codes = huffmanCodes(p);
    std::cout << "codes:";
    for (auto& c : codes) std::cout << ' ' << c;
    std::cout << '\n';
    return 0;
}
