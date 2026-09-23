"""Move n disks from source to target through aux (Towers of Hanoi)."""

def hanoi(n, source, aux, target):
    if n == 1:
        print(f"move disk 1 from {source} to {target}")
    else:
        hanoi(n - 1, source, target, aux)
        print(f"move disk {n} from {source} to {target}")
        hanoi(n - 1, aux, source, target)


if __name__ == "__main__":
    hanoi(3, "A", "B", "C")
