"""2-SAT via implication graph and SCC detection (Kosaraju's algorithm)."""


def two_sat(clause_a, clause_b):
    m = len(clause_a)
    n = 0
    for i in range(m):
        n = max(n, abs(clause_a[i]), abs(clause_b[i]))
    sz = 2 * n + 2

    adj_fwd = [[] for _ in range(sz)]
    adj_rev = [[] for _ in range(sz)]

    def lit_index(lit):
        return lit if lit > 0 else n - lit

    for i in range(m):
        a, b = clause_a[i], clause_b[i]
        u1, v1 = lit_index(-a), lit_index(b)
        adj_fwd[u1].append(v1)
        adj_rev[v1].append(u1)
        u2, v2 = lit_index(-b), lit_index(a)
        adj_fwd[u2].append(v2)
        adj_rev[v2].append(u2)

    visited = [False] * sz
    order = []

    def dfs1(u):
        stack = [(u, 0)]
        visited[u] = True
        while stack:
            node, idx = stack[-1]
            if idx < len(adj_fwd[node]):
                stack[-1] = (node, idx + 1)
                w = adj_fwd[node][idx]
                if not visited[w]:
                    visited[w] = True
                    stack.append((w, 0))
            else:
                stack.pop()
                order.append(node)

    for v in range(sz):
        if not visited[v]:
            dfs1(v)

    comp = [-1] * sz

    def dfs2(u, c):
        stack = [u]
        comp[u] = c
        while stack:
            node = stack.pop()
            for w in adj_rev[node]:
                if comp[w] < 0:
                    comp[w] = c
                    stack.append(w)

    num_comp = 0
    for u in reversed(order):
        if comp[u] < 0:
            dfs2(u, num_comp)
            num_comp += 1

    result = [False] * (n + 1)
    for xi in range(1, n + 1):
        pos = lit_index(xi)
        neg = lit_index(-xi)
        if comp[pos] == comp[neg]:
            return None
        result[xi] = comp[pos] > comp[neg]
    return result


if __name__ == "__main__":
    ca = [1, -1, 2]
    cb = [2, 3, -3]
    result = two_sat(ca, cb)
    if result is None:
        print("UNSATISFIABLE")
    else:
        print(f"assignment = {result[1:]}")
