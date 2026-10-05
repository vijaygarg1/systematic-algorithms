// Closest pair of points by divide-and-conquer (bxx-divideConquer.tex,
// algo:closest-pair). Px sorted by x. Returns the smallest squared
// distance (G[0]). Combine step: gather the strip of points within
// `best` of the dividing line, sort the strip BY Y-COORDINATE, and
// check each strip point only against the next 15 points in that
// y-order -- the book's O(n log^2 n) bound, not an O((hi-lo)^2)
// brute-force scan over every pair in range.

fn closest_pair(lo: usize, hi: usize, px: &[f64], py: &[f64], g: &mut [f64]) {
    if hi <= lo { return; }
    if hi - lo <= 2 {
        for i in lo..hi {
            for j in (i + 1)..=hi {
                let dx = px[i] - px[j];
                let dy = py[i] - py[j];
                let d = dx * dx + dy * dy;
                if d < g[0] { g[0] = d; }
            }
        }
        return;
    }
    let mid = (lo + hi) / 2;
    let mid_x = px[mid];
    closest_pair(lo, mid, px, py, g);
    closest_pair(mid + 1, hi, px, py, g);
    let mut strip: Vec<usize> = (lo..=hi)
        .filter(|&k| { let dx = px[k] - mid_x; dx * dx < g[0] })
        .collect();
    strip.sort_by(|&a, &b| py[a].partial_cmp(&py[b]).unwrap());
    for a in 0..strip.len() {
        let end = (a + 16).min(strip.len());
        for b in (a + 1)..end {
            let (pi, pj) = (strip[a], strip[b]);
            let dx = px[pi] - px[pj];
            let dy = py[pi] - py[pj];
            let d = dx * dx + dy * dy;
            if d < g[0] { g[0] = d; }
        }
    }
}

fn find(lo: usize, hi: usize, px: &[f64], py: &[f64]) -> Vec<f64> {
    let mut g = vec![f64::INFINITY];
    closest_pair(lo, hi, px, py, &mut g);
    g
}

fn main() {
    let px = [0.0, 1.0, 3.0, 4.0, 7.0];
    let py = [0.0, 5.0, 2.0, 1.0, 6.0];
    let g = find(0, px.len() - 1, &px, &py);
    println!("min squared distance: {}", g[0]);
}
