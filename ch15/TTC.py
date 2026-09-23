"""Gale's Top Trading Cycle (TTC) algorithm for the housing market.
At each stage, build the top-choice graph on unassigned agents,
find a cycle in it, assign each agent in the cycle their current top
remaining house, and mark them fixed.  Iterate until everyone is fixed."""


def ttc(pref):
    n = len(pref)
    house = [0] * n
    fixed = [False] * n
    g = [0] * n  # g[i] = index into pref[i] for current top choice

    num_fixed = 0
    while num_fixed < n:
        # Step 1: advance past fixed agents' houses
        for i in range(n):
            if not fixed[i]:
                while fixed[pref[i][g[i]]]:
                    g[i] += 1

        # Step 2: walk from first unfixed agent until we find a cycle
        on_path = [False] * n
        start = 0
        while fixed[start]:
            start += 1
        cur = start
        on_path[cur] = True
        nxt = pref[cur][g[cur]]
        while not on_path[nxt]:
            cur = nxt
            on_path[cur] = True
            nxt = pref[cur][g[cur]]

        # Step 3: assign houses around the cycle
        cycle_start = nxt
        cur = cycle_start
        while True:
            wish = pref[cur][g[cur]]
            house[cur] = wish
            fixed[cur] = True
            num_fixed += 1
            if wish == cycle_start:
                break
            cur = wish

    return house


if __name__ == "__main__":
    pref = [[1, 2, 0, 3], [0, 3, 1, 2], [0, 1, 3, 2], [1, 0, 2, 3]]
    result = ttc(pref)
    print(f"allocation: {result}")
    for i, h in enumerate(result):
        print(f"  agent {i} -> house {h}")
