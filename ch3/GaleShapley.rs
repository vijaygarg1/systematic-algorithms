// Gale-Shapley algorithm for the man-optimal stable marriage.
// Returns G[0..=n] where G[i] (i>=1) is the number of proposals made by man i.

fn r#match(mpref: &[Vec<usize>], rank: &[Vec<usize>]) -> Vec<usize> {
    let n = mpref.len() - 1;
    let mut g       = vec![0usize; n + 1];
    let mut partner = vec![0usize; n + 1];
    let mut freeman = vec![false; n + 1];
    for i in 1..=n { freeman[i] = true; }
    loop {
        let i = (1..=n).find(|&i| freeman[i]);
        let i = match i { Some(x) => x, None => break };
        g[i] += 1;
        let z = mpref[i][g[i]];
        if partner[z] == 0 {
            partner[z] = i; freeman[i] = false;
        } else if rank[z][i] < rank[z][partner[z]] {
            freeman[partner[z]] = true;
            partner[z] = i; freeman[i] = false;
        }
    }
    g
}

fn main() {
    let mpref: Vec<Vec<usize>> = vec![
        vec![0, 0, 0, 0],
        vec![0, 1, 2, 3],
        vec![0, 2, 1, 3],
        vec![0, 1, 2, 3],
    ];
    let rank: Vec<Vec<usize>> = vec![
        vec![0, 0, 0, 0],
        vec![0, 3, 1, 2],
        vec![0, 2, 3, 1],
        vec![0, 1, 2, 3],
    ];
    let g = r#match(&mpref, &rank);
    for i in 1..g.len() {
        println!("man {}: proposed {} time(s), engaged to woman {}", i, g[i], mpref[i][g[i]]);
    }
}
