/*
 * Compute n! via straight recursion.
 *
 * Auto-generated from Factorial.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Factorial {
  Factorial(n) {
    if ((n === 0)) {
        return 1;
    }
    return (n * this.Factorial((n - 1)));
  }
}

if (typeof module !== 'undefined') module.exports = Factorial;

if (typeof require !== 'undefined' && require.main === module) {
    const n = 3;
    const prog = new Factorial();
    const result = prog.Factorial(n);
    console.log(JSON.stringify(result));
}
