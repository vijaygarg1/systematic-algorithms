// Sum of products i*j over a nested loop -- a Theta(n^2) toy.

#include <iostream>

long long nestedSum(int n) {
    long long total = 0;
    for (int i = 0; i < n; ++i)
        for (int j = 0; j < n; ++j)
            total += (long long)i * j;
    return total;
}

int main() {
    for (int n : {0, 1, 5, 10}) {
        std::cout << "nestedSum(" << n << ") = " << nestedSum(n) << '\n';
    }
    return 0;
}
