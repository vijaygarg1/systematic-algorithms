"""Recursive DFS recording discovery and finish times."""

def dfs(dep):
    n = len(dep)
    visited    = [False] * n
    parent     = [-1] * n
    discovered = [0] * n
    finished   = [0] * n
    tick = [1]                    # rides on a list to allow nested mutation

    def visit(j):
        visited[j] = True
        discovered[j] = tick[0]
        tick[0] += 1
        for k in dep[j]:
            if not visited[k]:
                parent[k] = j
                visit(k)
        finished[j] = tick[0]
        tick[0] += 1

    visit(0)
    return discovered, parent, finished


if __name__ == "__main__":
    dep = [
        [1, 2],
        [3],
        [3, 4],
        [5],
        [5],
        [],
    ]
    discovered, parent, finished = dfs(dep)
    print("discovered:", discovered)
    print("parent:    ", parent)
    print("finished:  ", finished)
