/*
 * Horn SAT forward chaining (Dowling-Gallier): unit propagation with counters.
 *
 * Auto-generated from HornSATFC.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class HornSATFC {
  HornSATFC(body, head, adj) {
    this.body = body;
    this.head = head;
    this.adj = adj;
    this.n = this.adj.length;
    this.m = this.body.length;
    this.A = new Array(this.n).fill(false);
    this.rem = new Array(this.m).fill(0);
    this.c = 0;
    while ((this.c < this.m)) {
        this.rem[this.c] = this.body[this.c].length;
        this.c = (this.c + 1);
    }
    let queue = new Array(this.n).fill(0);
    let front = 0;
    let back = 0;
    this.c = 0;
    while ((this.c < this.m)) {
        if (((this.rem[this.c] === 0) && (this.head[this.c] >= 0))) {
            if ((!this.A[this.head[this.c]])) {
                queue[back] = this.head[this.c];
                back = (back + 1);
            }
        }
        this.c = (this.c + 1);
    }
    let sat = true;
    while (((front < back) && sat)) {
        let x = queue[front];
        front = (front + 1);
        if ((!this.A[x])) {
            this.A[x] = true;
            for (let k = 0; k <= ((this.adj[x].length - 1)); k++) {
                let ci = this.adj[x][k];
                this.rem[ci] = (this.rem[ci] - 1);
                if ((this.rem[ci] === 0)) {
                    if ((this.head[ci] < 0)) {
                        sat = false;
                    } else {
                        if ((!this.A[this.head[ci]])) {
                            queue[back] = this.head[ci];
                            back = (back + 1);
                        }
                    }
                }
            }
        }
    }
    return this.A;
  }
}

if (typeof module !== 'undefined') module.exports = HornSATFC;

if (typeof require !== 'undefined' && require.main === module) {
    const body = [[0, 1, 2, 3, 4, 5, 6, 7], [1, 2, 3, 4, 5, 6, 7, 0], [2, 3, 4, 5, 6, 7, 0, 1], [3, 4, 5, 6, 7, 0, 1, 2], [4, 5, 6, 7, 0, 1, 2, 3], [5, 6, 7, 0, 1, 2, 3, 4], [6, 7, 0, 1, 2, 3, 4, 5], [7, 0, 1, 2, 3, 4, 5, 6]];
    const head = [3, 1, 6, 1, 6, 3, 6, 4];
    const adj = [[0, 7, 6, 5, 4, 3, 2, 1], [1, 0, 7, 6, 5, 4, 3, 2], [2, 1, 0, 7, 6, 5, 4, 3], [3, 2, 1, 0, 7, 6, 5, 4], [4, 3, 2, 1, 0, 7, 6, 5], [5, 4, 3, 2, 1, 0, 7, 6], [6, 5, 4, 3, 2, 1, 0, 7], [7, 6, 5, 4, 3, 2, 1, 0]];
    const prog = new HornSATFC();
    const result = prog.HornSATFC(body, head, adj);
    console.log(JSON.stringify(result));
}
