// LLP-Kruskal: edge-inclusion lattice driven by union-find.

fn find(mut x: usize, parent: &mut [usize]) -> usize {
    while parent[x] != x {
        parent[x] = parent[parent[x]];
        x = parent[x];
    }
    x
}

fn union_find(a: usize, b: usize, parent: &mut [usize]) {
    let ra = find(a, parent);
    let rb = find(b, parent);
    if ra != rb { parent[ra] = rb; }
}

fn llp_kruskal(u: &[usize], v: &[usize], parent: &mut [usize]) -> Vec<bool> {
    let m = u.len();
    let mut c = vec![false; m];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..m {
            if !c[j] && find(u[j], parent) != find(v[j], parent) {
                c[j] = true;
                union_find(u[j], v[j], parent);
                changed = true;
            }
        }
    }
    c
}

fn main() {
    let u = [0usize, 1, 0, 1, 2];
    let v = [1usize, 2, 2, 3, 3];
    let n = 4;
    let mut parent: Vec<usize> = (0..n).collect();
    let c = llp_kruskal(&u, &v, &mut parent);
    println!("C: {:?}", c);
}
