// König's theorem: minimum vertex cover of size |M| from a maximum
// bipartite matching, via alternating reachability. Matches
// bxx-matchingReduced.tex Algorithm VertexCoverFromMatching: Z is the set
// of vertices reachable from an unmatched L-vertex by an alternating path
// (non-matching edge, then matching edge, ...); C := (L \ Z) union (R
// intersect Z).

fn vertex_cover_from_matching(adj: &[Vec<i32>], match_l: &[i32]) -> Vec<bool> {
    let l = adj.len();
    let r = adj[0].len();
    let mut in_z = vec![false; l + r];
    let mut partner = vec![-1i32; l + r];

    for u in 0..l {
        let v = match_l[u];
        if v != -1 {
            partner[u] = (l as i32) + v;
            partner[l + v as usize] = u as i32;
        }
    }

    // BFS queue of vertices whose incident edges are still unexplored,
    // seeded with every unmatched L-vertex.
    let mut q: Vec<usize> = Vec::new();
    for u in 0..l {
        if match_l[u] == -1 {
            in_z[u] = true;
            q.push(u);
        }
    }

    let mut head = 0;
    while head < q.len() {
        let w = q[head];
        head += 1;
        if w < l {
            // From an L-vertex, follow every non-matching edge.
            for v in 0..r {
                if adj[w][v] == 1 && partner[w] != (l + v) as i32 && !in_z[l + v] {
                    in_z[l + v] = true;
                    q.push(l + v);
                }
            }
        } else {
            // From an R-vertex, follow its matching edge (if any).
            let p = partner[w];
            if p != -1 && !in_z[p as usize] {
                in_z[p as usize] = true;
                q.push(p as usize);
            }
        }
    }

    let mut c = vec![false; l + r];
    for u in 0..l {
        c[u] = !in_z[u];
    }
    for v in 0..r {
        c[l + v] = in_z[l + v];
    }
    c
}

fn main() {
    let adj = vec![
        vec![1, 1, 0],
        vec![1, 0, 1],
        vec![0, 1, 1],
    ];
    let match_l = [0i32, 2, 1];
    let c = vertex_cover_from_matching(&adj, &match_l);
    print!("cover bits:");
    for b in &c { print!(" {}", if *b { 1 } else { 0 }); }
    println!();
}
