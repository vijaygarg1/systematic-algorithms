"""ResourceBound: composition program enforcing rho[j] <= B."""


def resource_bound(rho, B, G):
    return None if any(r > B for r in rho) else G


if __name__ == "__main__":
    rho = [0, 2, 5, 9]
    G = [0, 4, 8, 15]
    print(resource_bound(rho, 7, G))
