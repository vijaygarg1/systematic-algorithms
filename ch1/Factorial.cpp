// Recursive factorial.  factorial(n) = n * factorial(n-1) with base 1.

#include <iostream>

long long factorial(int n) {
    if (n == 0) return 1;
    return (long long)n * factorial(n - 1);
}

int main() {
    for (int n = 0; n < 7; ++n) {
        std::cout << n << "! = " << factorial(n) << '\n';
    }
    return 0;
}
