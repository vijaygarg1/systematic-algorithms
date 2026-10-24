// Gale-Shapley algorithm for the man-optimal stable marriage.
// Returns G[0..=n] where G[i] (i>=1) is the number of proposals made by man i.
//
// The free men are held on an explicit stack so that "pick any free man" is
// O(1). That is what the book's analysis requires: the loop runs O(n^2) times
// because each g[i] rises at most n times, and bxx-stableMatching.tex:161
// states "Each iteration of the while loop takes O(1) time", giving O(n^2)
// overall. Scanning for a free man instead costs O(n) per proposal and makes
// the whole loop O(n^3).

fn r#match(mpref: &[Vec<usize>], rank: &[Vec<usize>]) -> Vec<usize> {
    let n = mpref.len() - 1;
    let mut g       = vec![0usize; n + 1];
    let mut partner = vec![0usize; n + 1];
    let mut free_men: Vec<usize> = (1..=n).collect();
    while let Some(i) = free_men.pop() {
        g[i] += 1;
        let z = mpref[i][g[i]];
        if partner[z] == 0 {
            partner[z] = i;
        } else if rank[z][i] < rank[z][partner[z]] {
            free_men.push(partner[z]);   // displaced, becomes free again
            partner[z] = i;
        } else {
            free_men.push(i);            // rejected; tries his next preference
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
