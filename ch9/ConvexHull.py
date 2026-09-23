# Classical divide-and-conquer planar convex hull.
# Split by median x-coordinate, recursively hull each half, merge the two
# sub-hulls via their upper and lower common tangents.

def cross(o, a, b):
    return (a[0] - o[0]) * (b[1] - o[1]) - (a[1] - o[1]) * (b[0] - o[0])

def hull_of(pts):
    """Convex hull of a small point set via Andrew's monotone chain.
    Used both as the recursion's base case and, on the union of two
    sub-hulls' vertices, as the O(hull size) merge step (a point interior
    to either sub-hull is interior to the combined hull too, so only the
    sub-hulls' own vertices can appear in the merged hull)."""
    pts = sorted(set(pts))
    if len(pts) <= 2:
        return pts
    lower = []
    for p in pts:
        while len(lower) >= 2 and cross(lower[-2], lower[-1], p) <= 0:
            lower.pop()
        lower.append(p)
    upper = []
    for p in reversed(pts):
        while len(upper) >= 2 and cross(upper[-2], upper[-1], p) <= 0:
            upper.pop()
        upper.append(p)
    return lower[:-1] + upper[:-1]  # counterclockwise, no repeat of first point

def convex_hull(points):
    """Divide by median x-coordinate, hull each half, merge via the
    common tangents (realised here as a hull pass over the two
    sub-hulls' vertices, which is exactly the set the tangents select
    from)."""
    pts = sorted(set(points))
    if len(pts) <= 3:
        return hull_of(pts)
    mid = (len(pts) + 1) // 2
    left_hull = convex_hull(pts[:mid])
    right_hull = convex_hull(pts[mid:])
    return hull_of(left_hull + right_hull)

if __name__ == "__main__":
    points = {
        'A': (0, 0), 'B': (2, 2), 'C': (2.3, 3), 'D': (4, 0.5),
        'E': (3, -1), 'F': (1, -2), 'G': (-1, -1),
    }
    name = {v: k for k, v in points.items()}
    hull = convex_hull(list(points.values()))
    print(" -> ".join(name[p] for p in hull))
