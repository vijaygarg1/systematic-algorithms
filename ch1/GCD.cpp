// GCD using the mod operation.

#include <iostream>

int gcd(int a, int b) {
    while (a != b) {
        if (a > b) {
            if (a % b == 0) a = b;
            else            a = a % b;
        } else {
            if (b % a == 0) b = a;
            else            b = b % a;
        }
    }
    return a;
}

int main() {
    int pairs[][2] = {{48, 18}, {100, 75}, {17, 5}, {12, 12}};
    for (auto& p : pairs) {
        std::cout << "gcd(" << p[0] << ", " << p[1] << ") = "
                  << gcd(p[0], p[1]) << '\n';
    }
    return 0;
}
