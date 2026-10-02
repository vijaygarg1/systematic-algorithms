// GCD by repeated subtraction.

#include <iostream>

int gcd(int a, int b) {
    while (a != b) {
        if (a > b) a -= b;
        else       b -= a;
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
