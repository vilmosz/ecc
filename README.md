# CO3326 Computer Security
## Coursework assignment 1
The aim of this coursework is to show evidence of an understanding of what Elliptic Curve Cryptography (ECC) is, how does it work and why is it considered secure.

Today, we can find elliptic curves cryptosystems in [TLS](https://tools.ietf.org/html/rfc4492), [PGP](https://tools.ietf.org/html/rfc6637) and [SSH](https://tools.ietf.org/html/rfc5656), which are just three of the main technologies on which the modern web and IT world are based. Not to mention [Bitcoin](https://en.bitcoin.it/wiki/Secp256k1) and other cryptocurrencies.

The web is full of information on the subject, so you will not find it difficult to do your research. You are not required to dive deeper into the mathematics of the subject than the math covered in your subject guide (modular arithmetics). Some simple math that were covered in high school (geometry and simple group theory) will be needed though. Your research should cover topics related to what an elliptic curve is, how to define a group law in order to do some math with the points of elliptic curves; how do you restrict elliptic curves to finite fields of integers modulo a prime; with this restriction, you'll see that the points of elliptic curves generate cyclic subgrups and you'll understand the terms: _base point_, _order_ and _cofactor_.

Please write a report using the following skeleton:

### Elliptic curves over real numbers
1. Define briefly what elliptic curves are and plot the elliptic curve based on the _a_ and _b_ parameters you were given.
2. Explain in simple terms the group law for elliptic curves and demonstrate
  * geometric addition and
  * scalar multiplication
  with arbitrary points on your curve.

### Elliptic curves over finite fields
1. Explain in simple terms how are elliptic curves restricted to a finite field 𝔽k.
2. Explain what _order_ of the field means and calculate the order of your field, using _k_ that you were given (𝔽k).
3. Demonstrate
  * point addition, by calculating R = P + Q and
  * scalar multiplication, by calculating S = n * P
  over the field 𝔽k, with the values you were given: the points (P and Q) and _k_.    

  _Note:_ P and Q are the points that you were given. Please use _n = 3_ for the multplication.

4. Finally, in the ECC context explain what is the _easy problem_, and what seems to be _hard problem_; explain how does it relate to the discrete logarithm problem.
5. Explain how can ECC be used for cryptography, and more specifically for key exchange.

Show all your work. Anything that you used from your research should be included in the references section at the end of your coursework. Please note, that there is no need to copy-paste entire explanations or mathematical proofs. A simple sketch that demonstrates your understanding is enough and it should be based around the calculations with your own numbers (the numbers you were given).

## Submission requirements
The report should be submitted as a PDF document, following a strict naming scheme: *StudentName_{srn}_CO3326_cw1.pdf*. For example, if your name is _Mark Zuckerberg_ and your SRN is _000000001_, your report submission will be *MarkZuckerberg_000000001_CO3326_cw1.pdf*. Your report will count as __60%__ of your CW1 mark.

In addition to your report, you will also submit a JSON file, which follows a _strict format_ and summarizes the results of your calculations. This will count as __40%__ of your CW1 and will be automatically checked, so particular attention to its format. The name of the file should be *StudentName_{srn}_CO3326_cw1.json*; for example, if your name is _Mark Zuckerberg_ and your SRN is _000000001_, your JSON submission will be *MarkZuckerberg_000000001_CO3326_cw1.json*.

You can use the following well-formed JSON and adapt it to your numbers and your calculation results. These numbers reflect a correct solution for a hypothetical student, _Mark Zuckerberg_, with SRN _000000001_, who received the following numbers:

| SRN       | Name            |  a  |  b  |  k  |  Px |  Py |  Qx |  Qy |  n  |
| --------- |:----------------|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| 000000001	| MARK ZUCKERBERG |  -2 |  13 | 103 |  19 |  97 |  27 |  81 |  3  |

would submit the following JSON:

```json
{
  "name": "MARK ZUCKERBERG",
  "srn": "000000001",
  "ecc": {
    "a": -2,
    "b": 13,
    "k": 103,
    "order": 109
  },
  "assignment": {
    "modk-add": {
      "p": {
        "x": 19,
        "y": 97
      },
      "q": {
        "x": 27,
        "y": 81
      },
      "s": {
        "x": 61,
        "y": 90
      }
    },
    "modk-mul": {
      "n": 3,
      "p": {
        "x": 19,
        "y": 97
      },
      "s": {
        "x": 63,
        "y": 46
      }
    }
  }
}
```

