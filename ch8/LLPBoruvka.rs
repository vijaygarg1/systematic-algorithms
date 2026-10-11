// LLP-Boruvka: the book's recursive Boruvka(V,E), written as a loop (tail
// recursion) so the pointer-jumping kernel stays a clean fixpoint step.
// Each round selects every active vertex's minimum-weight edge (breaking
// a mutual mwe tie toward the smaller-numbered vertex), pointer-jumps to
// rooted stars, records every active vertex's own mwe edge in the tree,
// keeps only the roots active, and repeats until one component remains.
// `progress` guards against a disconnected graph or an isolated vertex
// (no outgoing edge at all) -- outside the book's own stated
// connected-graph assumption -- so the loop cannot spin forever waiting
// for a spanning tree that cannot exist.

fn find(mut v: usize, g: &[usize]) -> usize {
    while g[v] != v {
        v = g[v];
    }
    v
}

fn other_endpoint(e: usize, u: &[usize], v: &[usize], x: usize) -> usize {
    if u[e] == x { v[e] } else { u[e] }
}

fn llp_boruvka(n: usize, u: Vec<usize>, v: Vec<usize>, w: Vec<i32>) -> Vec<bool> {
    let m = u.len();
    let mut t = vec![false; m];
    let mut g: Vec<usize> = (0..n).collect();
    let mut active = vec![true; n];

    let mut progress = true;
    while active.iter().filter(|&&a| a).count() > 1 && progress {
        let before = active.iter().filter(|&&a| a).count();
        let mut mwe_edge = vec![usize::MAX; n];
        let mut mwe_w = vec![i32::MAX; n];
        for e in 0..m {
            let ru = find(u[e], &g);
            let rv = find(v[e], &g);
            if active[ru] && active[rv] && ru != rv {
                if w[e] < mwe_w[ru] { mwe_w[ru] = w[e]; mwe_edge[ru] = e; }
                if w[e] < mwe_w[rv] { mwe_w[rv] = w[e]; mwe_edge[rv] = e; }
            }
        }

        for i in 0..n {
            if active[i] {
                if mwe_edge[i] == usize::MAX {
                    g[i] = i;
                } else {
                    let nb = other_endpoint(mwe_edge[i], &u, &v, i);
                    let nb_edge = mwe_edge[nb];
                    if nb_edge != usize::MAX && other_endpoint(nb_edge, &u, &v, nb) == i
                            && i < nb {
                        g[i] = i;
                    } else {
                        g[i] = nb;
                    }
                }
            }
        }

        let mut changed = true;
        while changed {
            changed = false;
            for j in 0..n {
                if active[j] && g[j] != g[g[j]] {
                    g[j] = g[g[j]];
                    changed = true;
                }
            }
        }

        for i in 0..n {
            if active[i] && mwe_edge[i] != usize::MAX { t[mwe_edge[i]] = true; }
        }
        for i in 0..n {
            if active[i] && g[i] != i { active[i] = false; }
        }
        progress = active.iter().filter(|&&a| a).count() < before;
    }
    t
}

fn main() {
    // Same 5-vertex graph as the Kruskal/Prim/Boruvka demos.
    let u = vec![1, 0, 0, 1, 2, 2, 3];
    let v = vec![2, 1, 2, 3, 3, 4, 4];
    let w = vec![1, 2, 3, 4, 5, 6, 7];
    let t = llp_boruvka(5, u, v, w);
    println!("{:?}", t);
}
