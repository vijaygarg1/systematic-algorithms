// LLP assignment: minimum clearing price vector via step-jump price increments.
// Each iteration: identify overdemanded items, raise each by step[j] = min slack
// to the next critical price (the smallest amount that lets some bidder become
// indifferent and break a tight edge). Strongly polynomial.

fn try_match(b: usize, v: &[Vec<i32>], c: &[i32], partner: &mut [i32], seen: &mut [bool]) -> bool {
    let n = c.len();
    let mut best_surplus = i32::MIN;
    for i in 0..n {
        if v[b][i] - c[i] > best_surplus { best_surplus = v[b][i] - c[i]; }
    }
    for i in 0..n {
        if v[b][i] - c[i] != best_surplus || seen[i] { continue; }
        seen[i] = true;
        if partner[i] == -1 || try_match(partner[i] as usize, v, c, partner, seen) {
            partner[i] = b as i32;
            return true;
        }
    }
    false
}

fn check_perfect_matching(v: &[Vec<i32>], c: &[i32]) -> bool {
    let n = c.len();
    let m = v.len();
    let mut partner = vec![-1i32; n];
    let mut matched = 0;
    for b in 0..m {
        let mut seen = vec![false; n];
        if try_match(b, v, c, &mut partner, &mut seen) { matched += 1; }
    }
    matched == m
}

fn raise_overdemanded_prices(v: &[Vec<i32>], c: &mut [i32]) {
    let n = c.len();
    let m = v.len();
    // Snapshot bestSurplus[b] before any prices change this round.
    let mut best_surplus = vec![i32::MIN; m];
    for b in 0..m {
        for i in 0..n {
            if v[b][i] - c[i] > best_surplus[b] { best_surplus[b] = v[b][i] - c[i]; }
        }
    }
    // step[j] = min slack across bidders whose top choice includes j.
    let mut step = vec![i32::MAX; n];
    let mut demand = vec![0i32; n];
    for j in 0..n {
        for b in 0..m {
            if v[b][j] - c[j] != best_surplus[b] { continue; }
            demand[j] += 1;
            let mut second_best = i32::MIN;
            for i in 0..n {
                if i == j { continue; }
                if v[b][i] - c[i] > second_best { second_best = v[b][i] - c[i]; }
            }
            let slack = (v[b][j] - c[j]) - second_best;
            if slack < step[j] { step[j] = slack; }
        }
        // Integer arithmetic: a tied bidder gives slack 0; raise by at least 1.
        if step[j] < 1 { step[j] = 1; }
    }
    for j in 0..n {
        if demand[j] > 1 { c[j] += step[j]; }
    }
}

fn llp_assignment(v: &[Vec<i32>]) -> Vec<i32> {
    let n = v[0].len();
    let mut c = vec![0i32; n];
    while !check_perfect_matching(v, &c) {
        raise_overdemanded_prices(v, &mut c);
    }
    c
}

fn main() {
    let v = vec![
        vec![5, 3, 1],
        vec![4, 4, 2],
        vec![1, 2, 5],
    ];
    let c = llp_assignment(&v);
    println!("prices: {:?}", c);
}
