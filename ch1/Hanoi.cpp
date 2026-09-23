// Towers of Hanoi.

#include <iostream>

void hanoi(int n, char source, char aux, char target) {
    if (n == 1) {
        std::cout << "move disk 1 from " << source << " to " << target << '\n';
    } else {
        hanoi(n - 1, source, target, aux);
        std::cout << "move disk " << n << " from " << source
                  << " to " << target << '\n';
        hanoi(n - 1, aux, source, target);
    }
}

int main() {
    hanoi(3, 'A', 'B', 'C');
    return 0;
}
