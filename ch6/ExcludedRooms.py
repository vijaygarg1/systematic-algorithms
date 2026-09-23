"""ExcludedRooms: composition program enforcing R[j] = excluded rooms.
G[j] is the room assigned to course j; advance picks the smallest
non-excluded room not used by any overlapping earlier course."""


def excluded_rooms(R, pre, G):
    n = len(G)
    changed = True
    while changed:
        changed = False
        for j in range(n):
            if G[j] in R[j]:
                for r in range(1, n + 1):
                    if r in R[j]:
                        continue
                    if any(G[k] == r for k in pre[j]):
                        continue
                    if G[j] != r:
                        G[j] = r
                        changed = True
                    break
    return G


if __name__ == "__main__":
    R = [{1}, {2}, set()]
    pre = [set(), {0}, {0, 1}]
    G = [1, 2, 1]
    print(excluded_rooms(R, pre, G))
