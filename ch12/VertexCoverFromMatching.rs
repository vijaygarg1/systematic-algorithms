// König's theorem: minimum vertex cover of size |M| from a maximum
// bipartite matching, via alternating reachability. Matches
// bxx-matchingReduced.tex Algorithm VertexCoverFromMatching: Z is the set
// of vertices reachable from an unmatched L-vertex by an alternating path
// (non-matching edge, then matching edge, ...); C := (L \ Z) union (R
// intersect Z). The book treats the matching M as given directly as a
// partner lookup; here it is given as match_l[j] = R-partner of L-vertex
// j (or -1 if unmatched), with match_r (the same matching read from the
// R side -- the only direction the book's "partner[w]" needs, since w is
// always an R-vertex at that point in the algorithm) derived from it.

fn vertex_cover_from_matching(adj: &[Vec<i32>], match_l: &[i32]) -> Vec<bool> {
    let l = adj.len();
    let r = adj[0].len();
    let mut match_r = vec![-1i32; r];
    for j in 0..l {
        if match_l[j] != -1 {
            match_r[match_l[j] as usize] = j as i32;
        }
    }

    // Z := set of unmatched vertices in L; Q := Z.
    let mut in_z = vec![false; l + r];
    let mut q: Vec<usize> = Vec::new();
    for j in 0..l {
        if match_l[j] == -1 {
            in_z[j] = true;
            q.push(j);
        }
    }

    let mut head = 0;
    while head < q.len() {
        let w = q[head];
        head += 1;
        if w < l {
            // w in L: follow every (w, v) in E \ M with v not in Z.
            for v in 0..r {
                if adj[w][v] == 1 && match_l[w] != v as i32 && !in_z[l + v] {
                    in_z[l + v] = true;
                    q.push(l + v);
                }
            }
        } else {
            // w in R: if w is matched and partner[w] not in Z, add it.
            let rr = w - l;
            let p = match_r[rr];
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
