"""LLP assignment: minimum clearing price vector. When no perfect
matching exists in the current tight-edge graph, find an
inclusion-minimal overdemanded set J via alternating-path reachability
from an unmatched bidder, then raise every item in J by ONE SHARED
amount delta = min over bidders demanding into J of [bidder's best
surplus - bidder's best surplus using an item outside J]."""


def try_match(b, v, C, partner, seen):
    n = len(C)
    best_surplus = float("-inf")
    for i in range(n):
        s = v[b][i] - C[i]
        if s > best_surplus:
            best_surplus = s
    for i in range(n):
        if v[b][i] - C[i] == best_surplus and not seen[i]:
            seen[i] = True
            if partner[i] == -1 or try_match(partner[i], v, C, partner, seen):
                partner[i] = b
                return True
    return False


def best_surplus(b, v, C):
    return max(v[b][i] - C[i] for i in range(len(C)))


def best_surplus_outside(b, v, C, item_in_j):
    return max(v[b][i] - C[i] for i in range(len(C)) if not item_in_j[i])


def reach(b, v, C, partner, item_in_j, bidder_in_b):
    if bidder_in_b[b]:
        return
    bidder_in_b[b] = True
    best = best_surplus(b, v, C)
    for i in range(len(C)):
        if v[b][i] - C[i] == best and not item_in_j[i]:
            item_in_j[i] = True
            if partner[i] != -1:
                reach(partner[i], v, C, partner, item_in_j, bidder_in_b)


def llp_assignment(v):
    n = len(v[0])
    m = len(v)
    C = [0] * n
    while True:
        partner = [-1] * n
        for b in range(m):
            seen = [False] * n
            try_match(b, v, C, partner, seen)
        bidder_matched = [False] * m
        for i in range(n):
            if partner[i] != -1:
                bidder_matched[partner[i]] = True
        unmatched = next((b for b in range(m) if not bidder_matched[b]), None)
        if unmatched is None:
            return C
        item_in_j = [False] * n
        bidder_in_b = [False] * m
        reach(unmatched, v, C, partner, item_in_j, bidder_in_b)
        delta = min(
            best_surplus(b, v, C) - best_surplus_outside(b, v, C, item_in_j)
            for b in range(m) if bidder_in_b[b]
        )
        for j in range(n):
            if item_in_j[j]:
                C[j] += delta


if __name__ == "__main__":
    v = [[5, 3, 1], [4, 4, 2], [1, 2, 5]]
    result = llp_assignment(v)
    print(f"clearing prices = {result}")

    # 3 bidders all tied on items {0,1}, item 2 undesired: overdemanded
    # set J={0,1}, should raise both by a shared delta in one round.
    v2 = [[20, 20, 0], [20, 20, 0], [20, 20, 0]]
    print("tie case:", llp_assignment(v2))
