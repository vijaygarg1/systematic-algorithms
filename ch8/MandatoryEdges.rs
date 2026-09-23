// MandatoryEdges: composition program forcing the edges in subset M
// into the spanning tree.  Returns None on infeasibility.

fn find(parent: &mut [usize], x: usize) -> usize {
    let mut x = x;
    while parent[x] != x {
        parent[x] = parent[parent[x]];
        x = parent[x];
    }
    x
}

fn unite(parent: &mut [usize], a: usize, b: usize) {
    let ra = find(parent, a);
    let rb = find(parent, b);
    if ra != rb { parent[ra] = rb; }
}

fn mandatory_edges(u: &[usize], v: &[usize], m_set: &[bool], n: usize)
    -> Option<Vec<bool>>
{
    let m = u.len();
    let mut parent: Vec<usize> = (0..n).collect();
    let mut g = vec![false; m];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..m {
            if m_set[j] && !g[j] {
                if find(&mut parent, u[j]) == find(&mut parent, v[j]) {
                    return None;
                }
                g[j] = true;
                unite(&mut parent, u[j], v[j]);
                changed = true;
            }
        }
    }
    Some(g)
}

fn main() {
    let u = [0usize, 1, 0];
    let v = [1usize, 2, 2];
    let m_set = [true, true, true];
    match mandatory_edges(&u, &v, &m_set, 3) {
        Some(g) => println!("G: {:?}", g),
        None => println!("infeasible"),
    }
}
