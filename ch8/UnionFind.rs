// Disjoint-set with path compression in find and union-by-rank.

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

fn main() {
    let n = 6;
    let mut parent: Vec<usize> = (0..n).collect();
    let mut rank = vec![0usize; n];
    let pairs = [[0usize, 1], [2, 3], [1, 2]];
    for p in pairs.iter() {
        let merged = union_sets(&mut parent, &mut rank, p[0], p[1]);
        println!("union({}, {}) -> merged={}", p[0], p[1], merged);
    }
    let roots: Vec<usize> = (0..n).map(|i| find(&mut parent, i)).collect();
    println!("roots: {:?}", roots);
}
