// LLP assignment: minimum clearing price vector. When no perfect
// matching exists in the current tight-edge graph, find an
// inclusion-minimal overdemanded set J via alternating-path
// reachability from an unmatched bidder, then raise every item in J by
// ONE SHARED amount delta = min over bidders demanding into J of
// [bidder's best surplus - bidder's best surplus using an item
// outside J].

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

fn best_surplus(b: usize, v: &[Vec<i32>], c: &[i32]) -> i32 {
    (0..c.len()).map(|i| v[b][i] - c[i]).max().unwrap()
}

fn best_surplus_outside(b: usize, v: &[Vec<i32>], c: &[i32], item_in_j: &[bool]) -> i32 {
    (0..c.len()).filter(|&i| !item_in_j[i]).map(|i| v[b][i] - c[i]).max().unwrap()
}

fn reach(b: usize, v: &[Vec<i32>], c: &[i32], partner: &[i32], item_in_j: &mut [bool], bidder_in_b: &mut [bool]) {
    if bidder_in_b[b] { return; }
    bidder_in_b[b] = true;
    let best = best_surplus(b, v, c);
    for i in 0..c.len() {
        if v[b][i] - c[i] == best && !item_in_j[i] {
            item_in_j[i] = true;
            if partner[i] != -1 {
                reach(partner[i] as usize, v, c, partner, item_in_j, bidder_in_b);
            }
        }
    }
}

fn llp_assignment(v: &[Vec<i32>]) -> Vec<i32> {
    let n = v[0].len();
    let m = v.len();
    let mut c = vec![0i32; n];
    loop {
        let mut partner = vec![-1i32; n];
        for b in 0..m {
            let mut seen = vec![false; n];
            try_match(b, v, &c, &mut partner, &mut seen);
        }
        let mut bidder_matched = vec![false; m];
        for i in 0..n {
            if partner[i] != -1 { bidder_matched[partner[i] as usize] = true; }
        }
        let unmatched = (0..m).find(|&b| !bidder_matched[b]);
        let unmatched = match unmatched {
            None => return c,
            Some(b) => b,
        };

        let mut item_in_j = vec![false; n];
        let mut bidder_in_b = vec![false; m];
        reach(unmatched, v, &c, &partner, &mut item_in_j, &mut bidder_in_b);
        let delta = (0..m)
            .filter(|&b| bidder_in_b[b])
            .map(|b| best_surplus(b, v, &c) - best_surplus_outside(b, v, &c, &item_in_j))
            .min()
            .unwrap();
        for j in 0..n {
            if item_in_j[j] { c[j] += delta; }
        }
    }
}

fn main() {
    let v = vec![
        vec![5, 3, 1],
        vec![4, 4, 2],
        vec![1, 2, 5],
    ];
    let c = llp_assignment(&v);
    println!("prices: {:?}", c);

    // Tie case: 3 bidders tied on items {0,1}, item 2 undesired.
    let v2 = vec![
        vec![20, 20, 0],
        vec![20, 20, 0],
        vec![20, 20, 0],
    ];
    println!("tie case: {:?}", llp_assignment(&v2));
}
