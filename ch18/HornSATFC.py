"""Horn SAT forward chaining (Dowling-Gallier): unit propagation with counters."""


def horn_sat_fc(body, head, n):
    A = [False] * n
    rem = [len(b) for b in body]
    adj = [[] for _ in range(n)]
    for c, b in enumerate(body):
        for x in b:
            adj[x].append(c)
    queue = []
    for c in range(len(body)):
        if rem[c] == 0 and head[c] >= 0:
            if not A[head[c]]:
                queue.append(head[c])
    front = 0
    sat = True
    while front < len(queue) and sat:
        x = queue[front]
        front += 1
        if A[x]:
            continue
        A[x] = True
        for ci in adj[x]:
            rem[ci] -= 1
            if rem[ci] == 0:
                if head[ci] < 0:
                    sat = False
                elif not A[head[ci]]:
                    queue.append(head[ci])
    return A if sat else None


if __name__ == "__main__":
    body = [[], [0], [1]]
    head = [0, 1, 2]
    result = horn_sat_fc(body, head, 3)
    print(f"assignment = {result}")
