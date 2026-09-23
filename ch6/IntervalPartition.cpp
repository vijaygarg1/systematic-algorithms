// Interval partition: greedy by earliest start time -- assign each
// interval to any room whose previous interval has finished, or open
// a new room.  Rooms used = max overlap depth.

#include <iostream>
#include <vector>

std::vector<int> partition(const std::vector<int>& s, const std::vector<int>& f) {
    int n = (int)s.size();
    std::vector<int> G(n, 0), roomFinish(n, 0);
    int numRooms = 0;
    for (int j = 0; j < n; ++j) {
        int r = -1;
        for (int i = 0; i < numRooms && r == -1; ++i)
            if (roomFinish[i] <= s[j]) r = i;
        if (r == -1) r = numRooms++;
        G[j] = r + 1;
        roomFinish[r] = f[j];
    }
    return G;
}

int main() {
    std::vector<int> s = {1, 4, 7};
    std::vector<int> f = {2, 5, 8};
    auto G = partition(s, f);
    std::cout << "rooms:";
    for (int x : G) std::cout << ' ' << x;
    std::cout << '\n';
    return 0;
}
