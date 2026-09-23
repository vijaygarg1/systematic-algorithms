"""Interval partition: greedy by earliest start time -- assign each interval to any room whose previous interval has finished, or open a new room. Rooms used = max overlap depth."""


def partition(s, f):
    n = len(s)
    G = [0] * n
    room_finish = []
    for j in range(n):
        r = -1
        for i, end in enumerate(room_finish):
            if end <= s[j]:
                r = i
                break
        if r == -1:
            r = len(room_finish)
            room_finish.append(0)
        G[j] = r + 1
        room_finish[r] = f[j]
    return G


if __name__ == "__main__":
    # Five courses sorted by start time.
    s = [0, 1, 2, 3, 4]
    f = [3, 4, 5, 6, 7]
    print(partition(s, f))
