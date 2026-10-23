"""LLP-StableMarriage: Gale-Shapley via a cached partner[] vector and an
explicit FIFO worklist of currently-forbidden men -- O(1) amortized per
check, O(n^2) worst case. See the .llp of the same name for why this is
procedural rather than the language's forbidden/advance sugar (the
compiler's realizations of that sugar either rescan all n men every
round -- measured O(n^3) here -- or need a PriorityQueue that cannot
cheaply re-examine a displaced man whose own proposal count did not
change).

G[i] is a proposal count (1-based once nonzero), so mpref lookups are
mpref[i][G[i] - 1], not mpref[i][G[i]].

The queue is a circular buffer of capacity n + 1: in_queue[] stops a
man being enqueued twice at once, so at most n distinct men are ever
in-flight, regardless of the total number of times each is pushed.
"""


def llp_stable_marriage(mpref, rank):
    n = len(mpref)
    G = [0] * n
    partner = [-1] * n

    cap = n + 1
    queue = [0] * cap
    in_queue = [False] * n
    q_head = q_tail = q_count = 0
    for i in range(n):
        queue[q_tail] = i
        q_tail = (q_tail + 1) % cap
        q_count += 1
        in_queue[i] = True

    while q_count > 0:
        i = queue[q_head]
        q_head = (q_head + 1) % cap
        q_count -= 1
        in_queue[i] = False

        if G[i] == 0:
            is_forbidden = True
        else:
            z0 = mpref[i][G[i] - 1]
            is_forbidden = partner[z0] != -1 and rank[z0][partner[z0]] < rank[z0][i]

        if is_forbidden:
            G[i] += 1
            z = mpref[i][G[i] - 1]
            if partner[z] == -1 or rank[z][i] < rank[z][partner[z]]:
                y = partner[z]
                partner[z] = i
                if y != -1 and not in_queue[y]:
                    queue[q_tail] = y
                    q_tail = (q_tail + 1) % cap
                    q_count += 1
                    in_queue[y] = True
            if not in_queue[i]:
                queue[q_tail] = i
                q_tail = (q_tail + 1) % cap
                q_count += 1
                in_queue[i] = True
    return G


if __name__ == "__main__":
    # Three men, three women, 0-based.
    mpref = [
        [0, 1, 2],
        [1, 0, 2],
        [0, 1, 2],
    ]
    rank = [
        [2, 1, 3],
        [1, 2, 3],
        [1, 2, 3],
    ]
    G = llp_stable_marriage(mpref, rank)
    for i in range(len(G)):
        print(f"man {i}: {G[i]} proposals, matched with woman {mpref[i][G[i] - 1]}")
