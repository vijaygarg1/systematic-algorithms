/*
 * Move n disks from source to target through aux (Towers of Hanoi).
 *
 * Auto-generated from Hanoi.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Hanoi {
  Hanoi(n, source, aux, target) {
    if ((n === 1)) {
    } else {
        this.Hanoi((n - 1), source, target, aux);
        this.Hanoi((n - 1), aux, source, target);
    }
  }
}

if (typeof module !== 'undefined') module.exports = Hanoi;

if (typeof require !== 'undefined' && require.main === module) {
    const n = 3;
    const prog = new Hanoi();
    prog.Hanoi(n, 0, 1, 2);
}
