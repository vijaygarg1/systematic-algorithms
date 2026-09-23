// Closest pair of points by divide-and-conquer.  Px sorted by x.
// Returns the smallest squared distance (G[0]).

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
    closest_pair(lo, mid, px, py, g);
    closest_pair(mid + 1, hi, px, py, g);
    for i in lo..hi {
        for j in (i + 1)..=hi {
            let a = px[i] - px[mid];
            let b = px[j] - px[mid];
            if a * a < g[0] && b * b < g[0] {
                let dx = px[i] - px[j];
                let dy = py[i] - py[j];
                let d = dx * dx + dy * dy;
                if d < g[0] { g[0] = d; }
            }
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
