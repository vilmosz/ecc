# CO3326 Computer Security
## Coursework assignment 1
The aim of this coursework is to show evidence of an understanding of what Elliptic Curve Cryptography (ECC) is, how does it work and why is it considered secure.

Today, we can find elliptic curves cryptosystems in [TLS](https://tools.ietf.org/html/rfc4492), [PGP](https://tools.ietf.org/html/rfc6637) and [SSH](https://tools.ietf.org/html/rfc5656), which are just three of the main technologies on which the modern web and IT world are based. Not to mention [Bitcoin](https://en.bitcoin.it/wiki/Secp256k1) and other cryptocurrencies.

The web is full of information on the subject, so you will not find it difficult to do your research. You are not required to dive deeper into the mathematics of the subject than the math covered in your subject guide (modular arithmetics). Some simple math that were covered in high school (geometry and simple group theory) will be needed though. Your research should cover topics related to what an elliptic curve is, how to define a group law in order to do some math with the points of elliptic curves; how do you restrict elliptic curves to finite fields of integers modulo a prime; with this restriction, you'll see that the points of elliptic curves generate cyclic subgrups and you'll understand the terms: _base point_, _order_ and _cofactor_.

Please write a report using the following skeleton:

1. Elliptic curves over real numbers
  a. Define briefly what elliptic curves are and plot the elliptic curve based on the _a_ and _b_ parameters you were given.
  b. Explain in simple terms the group law for elliptic curves and demonstrate
    * geometric addition and
    * scalar multiplication
    with arbitrary points on your curve.
2. Elliptic curves over finite fields
  a. Explain in simple terms how are elliptic curves restricted to a finite field 𝔽k.
  b. Explain what _order_ of the field means and calculate the order of your field, using _k_ that you were given (𝔽k).
  c. Demonstrate
    * point addition, by calculating R = P + Q and
    * scalar multiplication, by calculating S = n * P
    over the field 𝔽k, with the values you were given: the points (P and Q) and _k_.    
    _Note:_ P and Q are the points that you were given. Please use _n = 3_ for the multplication.
3. Finally, in the ECC context explain what is the "easy" problem, and what seems to be "hard" problem; explain how how does it relate to the discrete logarithm problem; explain how can ECC be used for cryptography.

Show all your work. Anything that you used from your research should be included in the references section at the end of your coursework. Please note, that there is no need to copy-paste entire explanation. A simple sketch that demonstrates your understanding, mainly based around the calculations with your own numbers (the numbers you were given) will sufice.

In addition to your report, you will have to summarize the results of your calculations in a strictly specified JSON format.

## Submission requirements

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
    "modk-mul": {
      "exercise": "modk-mul",
      "n": 3,
      "p": {
        "x": 19,
        "y": 97
      },
      "q": {
        "x": 63,
        "y": 46
      }
    },
    "modk-add": {
      "exercise": "modk-add",
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
    }
  }
}
```
