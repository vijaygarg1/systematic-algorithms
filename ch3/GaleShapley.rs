// Gale-Shapley algorithm for the man-optimal stable marriage.
// Returns G[0..=n] where G[i] (i>=1) is the number of proposals made by man i.
//
// Written as bxx-stableMatching.tex's Algorithm Gale--Shapley states it: the
// free men are held in a list (m_list), so "pick a free man" is O(1). That is
// what the book's analysis requires: the loop runs O(n^2) times because each
// g[i] rises at most n times, and the book states "Each iteration of the while
// loop takes O(1) time", giving O(n^2) overall. Scanning an array of free
// flags instead costs O(n) per proposal and makes the whole loop O(n^3).

use std::collections::VecDeque;

fn gale_shapley(mpref: &[Vec<usize>], rank: &[Vec<usize>]) -> Vec<usize> {
    let n = mpref.len() - 1;
    let mut m_list: VecDeque<usize> = (1..=n).collect();  // men that are free
    let mut partner = vec![0usize; n + 1];                // fiancee for woman i
    let mut g       = vec![0usize; n + 1];                // proposals by man i
    while let Some(i) = m_list.pop_front() {
        g[i] += 1;                                        // next top choice
        let z = mpref[i][g[i]];                           // woman for it
        if partner[z] == 0 {
            partner[z] = i;
        } else if rank[z][i] < rank[z][partner[z]] {
            m_list.push_back(partner[z]);                 // displaced, free again
            partner[z] = i;
        } else {
            m_list.push_back(i);                          // rejected, stays free
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
    let g = gale_shapley(&mpref, &rank);
    for i in 1..g.len() {
        println!("man {}: proposed {} time(s), engaged to woman {}", i, g[i], mpref[i][g[i]]);
    }
}
