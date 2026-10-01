"""LLP Horn SAT: head[c] is the consequent variable of clause c, or -1
if clause c is a pure negative (goal) clause (antecedents => false).
Rule 1: a definite clause with all antecedents true and a false
consequent forces that consequent true. Rule 2: a goal clause with all
antecedents true proves unsatisfiability."""


def all_true(variables, G):
    return all(G[x] for x in variables)


def horn_sat_llp(body, head, n):
    G = [False] * n
    changed = True
    while changed:
        changed = False
        for c in range(len(body)):
            if not all_true(body[c], G):
                continue
            if head[c] == -1:
                return None
            if not G[head[c]]:
                G[head[c]] = True
                changed = True
    return G


if __name__ == "__main__":
    body = [[], [0], [1]]
    head = [0, 1, 2]
    result = horn_sat_llp(body, head, 3)
    print(f"least model = {result}")

    # Unsatisfiable: x0 forced true, then x0 => false.
    body_unsat = [[], [0]]
    head_unsat = [0, -1]
    print("UNSAT case:", horn_sat_llp(body_unsat, head_unsat, 1))
