// Closest pair of points by divide-and-conquer (bxx-divideConquer.tex,
// algo:closest-pair). Returns [best squared distance, one endpoint, the
// other], naming the closest PAIR as the book's KwOut does.
//
// PRECONDITION -- the book's "presort once" line, extended to BOTH
// coordinates so that the combine step never sorts: px is non-decreasing
// with py[k] the y-coordinate of the same point, and ys lists the indices
// 0..n-1 in non-decreasing y-order. Both presorts are done once by the
// caller, with the host language's own sort.
//
// Every call receives its range's indices in y-order, splits that order
// between the halves in O(size), and reads the strip off it already
// y-sorted, so T(n) = 2T(n/2) + O(n) = O(n log n) -- the classical
// refinement named at the end of the book's analysis, in place of the
// O(n log^2 n) version that re-sorted the strip at every level.

// Keep the pair (i, j) when it beats the best seen so far.
fn check(i: usize, j: usize, px: &[f64], py: &[f64], g: &mut [f64]) {
    let dx = px[i] - px[j];
    let dy = py[i] - py[j];
    let d = dx * dx + dy * dy;
    if d < g[0] { g[0] = d; g[1] = i as f64; g[2] = j as f64; }
}

fn closest_pair(lo: usize, hi: usize, px: &[f64], py: &[f64], ys: &[usize], g: &mut [f64]) {
    if hi <= lo { return; }
    if hi - lo <= 2 {
        for i in lo..hi {
            for j in (i + 1)..=hi { check(i, j, px, py, g); }
        }
        return;
    }
    let mid = (lo + hi) / 2;
    let mid_x = px[mid];
    // Divide: split the inherited y-order between the two halves, each
    // half keeping its points in y-order.
    let ls: Vec<usize> = ys.iter().copied().filter(|&p| p <= mid).collect();
    let rs: Vec<usize> = ys.iter().copied().filter(|&p| p > mid).collect();
    closest_pair(lo, mid, px, py, &ls, g);
    closest_pair(mid + 1, hi, px, py, &rs, g);
    // Combine: the same y-order, filtered to the points within
    // sqrt(best) of the dividing line -- already sorted by y.
    let strip: Vec<usize> = ys.iter().copied()
        .filter(|&p| { let dx = px[p] - mid_x; dx * dx < g[0] })
        .collect();
    // Each strip point need only be checked against the next 15 points
    // in y-order (Lemma: at most 15 positions apart).
    for a in 0..strip.len() {
        let end = (a + 16).min(strip.len());
        for b in (a + 1)..end { check(strip[a], strip[b], px, py, g); }
    }
}

fn find(lo: usize, hi: usize, px: &[f64], py: &[f64], ys: &[usize]) -> Vec<f64> {
    let mut g = vec![f64::INFINITY, -1.0, -1.0];
    closest_pair(lo, hi, px, py, ys, &mut g);
    g
}

fn main() {
    // The book's worked example (bxx-divideConquer.tex).
    let mut pts = [(1.0, 2.0), (3.0, 7.0), (5.0, 4.0), (8.0, 9.0), (9.0, 6.0), (10.0, 2.0)];
    pts.sort_by(|a, b| a.0.partial_cmp(&b.0).unwrap());          // presort by x, once
    let px: Vec<f64> = pts.iter().map(|p| p.0).collect();
    let py: Vec<f64> = pts.iter().map(|p| p.1).collect();
    let mut ys: Vec<usize> = (0..pts.len()).collect();
    ys.sort_by(|&a, &b| py[a].partial_cmp(&py[b]).unwrap());     // presort by y, once
    let g = find(0, px.len() - 1, &px, &py, &ys);
    let (i, j) = (g[1] as usize, g[2] as usize);
    println!("closest pair: ({}, {}) and ({}, {}), squared distance {} (distance {:.3})",
             px[i], py[i], px[j], py[j], g[0], g[0].sqrt());
}
