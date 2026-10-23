// LLP-GCD: greatest common divisor of two numbers via Euclidean
// reduction, matching bxx-numberTheory.tex Algorithm LLP-GCD. G is the
// two-element vector (a, b); forbidden picks an entry strictly larger
// than another, and the advance replaces it by its remainder mod the
// smaller (or by the smaller itself when it divides exactly, so entries
// stay at least 1). Each advance at least halves the larger entry, so
// this performs O(log(min(a,b))) advances.

#include <iostream>
#include <vector>

int llpGCD(int a, int b) {
    std::vector<int> G(2);
    G[0] = a;
    G[1] = b;
    bool changed = true;
    while (changed) {
        changed = false;
        for (int j = 0; j < 2; ++j) {
            // `pick i`: any witness will do, so take the first.
            for (int i = 0; i < 2; ++i) {
                if (G[j] > G[i]) {
                    if (G[j] % G[i] == 0) G[j] = G[i];
                    else G[j] = G[j] % G[i];
                    changed = true;
                    break;
                }
            }
        }
    }
    return G[0];
}

int main() {
    std::cout << "gcd(1071, 462) = " << llpGCD(1071, 462) << '\n';
    return 0;
}
