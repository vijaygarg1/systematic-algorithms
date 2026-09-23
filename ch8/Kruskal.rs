// Classical Kruskal MST: edges already sorted by weight.

fn find(parent: &mut [usize], x: usize) -> usize {
    if parent[x] != x { parent[x] = find(parent, parent[x]); }
    parent[x]
}

fn union_sets(parent: &mut [usize], rank: &mut [usize], x: usize, y: usize) -> bool {
    let rx = find(parent, x);
    let ry = find(parent, y);
    if rx == ry { return false; }
    if rank[rx] < rank[ry] { parent[rx] = ry; }
    else if rank[rx] > rank[ry] { parent[ry] = rx; }
    else { parent[ry] = rx; rank[rx] += 1; }
    true
}

fn mst(n: usize, u: &[usize], v: &[usize], _w: &[i32]) -> Vec<bool> {
    let m = u.len();
    let mut in_tree = vec![false; m];
    let mut parent: Vec<usize> = (0..n).collect();
    let mut rank = vec![0usize; n];
    let mut chosen = 0;
    for e in 0..m {
        if chosen >= n - 1 { break; }
        if union_sets(&mut parent, &mut rank, u[e], v[e]) {
            in_tree[e] = true;
            chosen += 1;
        }
    }
    in_tree
}

fn main() {
    let n = 4;
    let u = [0usize, 1, 0, 1, 2];
    let v = [1usize, 2, 2, 3, 3];
    let w = [1i32, 2, 3, 4, 5];
    let in_tree = mst(n, &u, &v, &w);
    println!("inTree: {:?}", in_tree);
}
