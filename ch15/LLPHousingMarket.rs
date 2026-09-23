// LLP housing market: forbidden when agent j is not in the
// submatching but wishes for a house that is in the submatching;
// advance increments the proposal index.

fn in_submatching(j: usize, g: &[usize], pref: &[Vec<usize>]) -> bool {
    let n = g.len();
    let target = pref[j][g[j]];
    for i in 0..n {
        if i != j && pref[i][g[i]] == target { return false; }
    }
    true
}

fn wish_in_submatching(j: usize, g: &[usize], pref: &[Vec<usize>]) -> bool {
    let n = g.len();
    let wish = pref[j][g[j]];
    for i in 0..n {
        if pref[i][g[i]] == wish && in_submatching(i, g, pref) { return true; }
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
            if !in_submatching(j, &g, pref) && wish_in_submatching(j, &g, pref) {
                g[j] += 1;
                changed = true;
            }
        }
    }
    g
}

fn main() {
    let pref: Vec<Vec<usize>> = vec![
        vec![1, 0, 2, 3],
        vec![0, 1, 2, 3],
        vec![0, 1, 2, 3],
        vec![3, 1, 0, 2],
    ];
    let g = llp_housing_market(&pref);
    println!("G: {:?}", g);
}
