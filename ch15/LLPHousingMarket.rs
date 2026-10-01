// LLP housing market: forbidden when agent j is not in S(G) (the
// largest submatching) but wishes for a house held by an agent who is;
// advance moves j to its next preference. S(G) is the set of agents
// lying on a cycle of the wish functional graph (i -> wish(i)).

fn wish(i: usize, g: &[usize], pref: &[Vec<usize>]) -> usize {
    pref[i][g[i]]
}

fn in_submatching(j: usize, g: &[usize], pref: &[Vec<usize>]) -> bool {
    let n = g.len();
    let mut cur = wish(j, g, pref);
    let mut steps = 1;
    while steps <= n {
        if cur == j { return true; }
        cur = wish(cur, g, pref);
        steps += 1;
    }
    false
}

fn llp_housing_market(pref: &[Vec<usize>]) -> Vec<usize> {
    let n = pref.len();
    let mut g = vec![0usize; n];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if !in_submatching(j, &g, pref) && in_submatching(wish(j, &g, pref), &g, pref) {
                g[j] += 1;
                changed = true;
            }
        }
    }
    g
}

fn main() {
    let pref: Vec<Vec<usize>> = vec![
        vec![1, 2, 0, 3],
        vec![0, 3, 1, 2],
        vec![0, 1, 3, 2],
        vec![1, 0, 2, 3],
    ];
    let g = llp_housing_market(&pref);
    println!("G: {:?}", g);
}
