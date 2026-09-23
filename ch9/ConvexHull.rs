// Classical divide-and-conquer planar convex hull.
// Split by median x-coordinate, recursively hull each half, merge the two
// sub-hulls via their upper and lower common tangents.

#[derive(Clone, Copy, Debug, PartialEq)]
struct Pt { x: f64, y: f64 }

fn cross(o: Pt, a: Pt, b: Pt) -> f64 {
    (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x)
}

fn sorted_unique(input: &[Pt]) -> Vec<Pt> {
    let mut pts = input.to_vec();
    pts.sort_by(|a, b| a.x.partial_cmp(&b.x).unwrap().then(a.y.partial_cmp(&b.y).unwrap()));
    pts.dedup_by(|a, b| a.x == b.x && a.y == b.y);
    pts
}

// Convex hull of a small point set via Andrew's monotone chain. Used both
// as the recursion's base case and, on the union of two sub-hulls'
// vertices, as the merge step.
fn hull_of(input: &[Pt]) -> Vec<Pt> {
    let pts = sorted_unique(input);
    let n = pts.len();
    if n <= 2 { return pts; }
    let mut lower: Vec<Pt> = Vec::new();
    for &p in &pts {
        while lower.len() >= 2 && cross(lower[lower.len() - 2], lower[lower.len() - 1], p) <= 0.0 {
            lower.pop();
        }
        lower.push(p);
    }
    let mut upper: Vec<Pt> = Vec::new();
    for &p in pts.iter().rev() {
        while upper.len() >= 2 && cross(upper[upper.len() - 2], upper[upper.len() - 1], p) <= 0.0 {
            upper.pop();
        }
        upper.push(p);
    }
    lower.pop();
    upper.pop();
    lower.extend(upper);
    lower
}

fn convex_hull(input: &[Pt]) -> Vec<Pt> {
    let pts = sorted_unique(input);
    if pts.len() <= 3 { return hull_of(&pts); }
    let mid = (pts.len() + 1) / 2;
    let left_hull = convex_hull(&pts[..mid]);
    let right_hull = convex_hull(&pts[mid..]);
    let mut combined = left_hull;
    combined.extend(right_hull);
    hull_of(&combined)
}

fn main() {
    let points = vec![
        Pt { x: 0.0, y: 0.0 }, Pt { x: 2.0, y: 2.0 }, Pt { x: 2.3, y: 3.0 },
        Pt { x: 4.0, y: 0.5 }, Pt { x: 3.0, y: -1.0 }, Pt { x: 1.0, y: -2.0 },
        Pt { x: -1.0, y: -1.0 },
    ];
    for p in convex_hull(&points) {
        println!("({},{})", p.x, p.y);
    }
}
