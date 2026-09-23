// LLP form of Gale-Shapley stable matching.

fn forbidden(j: usize, g: &[usize],
             mpref: &[Vec<usize>], rank: &[Vec<usize>]) -> bool {
    for i in 0..g.len() {
        for k in 0..=g[i] {
            let z = mpref[j][g[j]];
            if mpref[j][g[j]] == mpref[i][k] && rank[z][i] < rank[z][j] {
                return true;
            }
        }
    }
    false
}

fn stable_marriage(mpref: &[Vec<usize>], rank: &[Vec<usize>], i_init: &[usize]) -> Vec<usize> {
    let mut g: Vec<usize> = i_init.to_vec();
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..g.len() {
            if forbidden(j, &g, mpref, rank) { g[j] += 1; changed = true; }
        }
    }
    g
}

fn main() {
    let mpref: Vec<Vec<usize>> = vec![
        vec![0, 1, 2],
        vec![1, 0, 2],
        vec![0, 1, 2],
    ];
    let rank: Vec<Vec<usize>> = vec![
        vec![2, 1, 3],
        vec![1, 2, 3],
        vec![1, 2, 3],
    ];
    let g = stable_marriage(&mpref, &rank, &[0, 0, 0]);
    for j in 0..g.len() {
        println!("man {}: index {}, matched with woman {}", j, g[j], mpref[j][g[j]]);
    }
}
