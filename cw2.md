# University of London International Programmes<br/>CO3326 Computer Security 2016-17

## Coursework assignment 2

__IMPORTANT:__ This coursework builds on the first coursework and assumes that you have an understanding of what Elliptic Curve Cryptography (ECC) is and how it works. Furthermore, it reuses the curve you were given in coursework 1 and the point you had to use for modular multiplication. So, please refer to the email you received that contains the unique set of numbers you used for coursework 1.

The aim of this coursework is to show evidence of understanding of the Elliptic curve Diffie-Hellman (ECDH) key exchange protocol, which allows two parties, each having an elliptic curve public–private key pair, to establish a shared secret over an insecure channel. This shared secret may be directly used as a key, or to derive another key which can then be used to encrypt subsequent communications using a symmetric key cipher. It is a variant of the Diffie-Hellman protocol using elliptic curve cryptography.

### Protocol

The following example will illustrate how a key establishment is made. Suppose Alice wants to establish a shared key with Bob, but the only channel available for them may be eavesdropped by a third party. Initially, the domain parameters, which are _k, a, b,_ and _G_, must be agreed upon. _a_ and _b_ deteremine the curve; _k_ restricts the curve to a prime field 𝔽<sub>k</sub>. _G_ is a generator, a point in the field.

Also, each party must have a key pair suitable for elliptic curve cryptography, consisting of a private key _d_ (a randomly selected integer in the interval _(1, n-1]_, where _n_ is the order of the field) and a public key _Q_ (where _Q = dG_, that is, the result of adding _G_ together _d_ times). Let Alice's key pair be _(d<sub>A</sub>, Q<sub>A</sub>)_ and Bob's key pair be _(d<sub>B</sub>, Q<sub>B</sub>)_. Each party must know the other party's public key prior to execution of the protocol.

Alice computes _(x<sub>k</sub>, y<sub>k</sub>) = d<sub>A</sub>Q<sub>B</sub>_. Bob computes _(x<sub>k</sub>, y<sub>k</sub>)=d<sub>B</sub>Q<sub>A</sub>_. The shared secret is x<sub>k</sub> (the _x_ coordinate of the point).  

### Parameters

For the elliptic curve please use the same _a_ and _b_ values that you were given in coursework 1. Furthermore, please use the _P_ point that you used in coursework 1 for the modular multiplication exercise for the generator (_G_) in coursework 2.

### Report

Please write a report using the following skeleton:

1. Show in detail all the steps of the key exchange protocol, with the calculation expanded using the numbers you were given. For the private keys (_d<sub>A</sub>_ and _d<sub>B</sub>_) you may choose any number in the _[11, n-1]_, where _n_ is the order of the field.
2. How do Alice and Bob arrive to the same shared secret?
3. If Carol is intercepting the communication and captures _Q<sub>A</sub>_ and _Q<sub>B</sub>_, can she compute Alice's and Bob's private key?
4. A more sophisticated attack by Carol involves generating _(d<sub>C</sub>, Q<sub>C</sub>)_ for use as a reset value, using the same values of _a_, _b_, _k_ and _G_ that Alice and Bob are using. Explain how would this work.
5. Write a brief disccussion (2 paragraphs) on the comparison, focusing on the advantages and disadvantages, of ECC (Elliptic Curve Cryptography and RSA).
6. Include key snippets of your code. _NOTE:_ as you are doing modulo multiplications with figures greater than 10 on an elliptic curve, your work will most probably involve some programming. You may choose a programming language of your liking, whatever you're the most comfortable with. The snippet, that youought toinclude in you report, should be the fragment dealing with modular multiplication on the elliptic curve.

Show all your work. You must include in-text citation and provide a detailed references section at the end of your coursework (see [How to avoid plagiarism](https://computing.elearning.london.ac.uk/mod/page/view.php?id=5176) in Study Support on the VLE.) Please note, that there is no need to copy-paste entire explanations or mathematical proofs. A simple sketch that demonstrates your understanding is enough and it should be based around the calculations with your own numbers (the numbers you were given).

### Submission requirements
The report should be submitted as a PDF document, following a _strict naming scheme_: *StudentName_{srn}_CO3326_cw2.pdf*. For example, if your name is _Mark Zuckerberg_ and your SRN is _000000001_, your report submission will be *MarkZuckerberg_000000001_CO3326_cw2.pdf*. Your report will count as __60%__ of your CW2 mark.

In addition to your report, you will also submit a JSON file, which also follows a _strict format_ and _naming scheme_ and summarizes the results of your calculations. This will count as __40%__ of your CW1 and will be automatically checked by an algorithm, so pay particular attention to its format. 

The name of the file should be *StudentName_{srn}_CO3326_cw2.json*; for example, if your name is _Mark Zuckerberg_ and your SRN is _000000001_, your JSON submission will be *MarkZuckerberg_000000001_CO3326_cw2.json*. You can use the following well-formed JSON and adapt it for your numbers and your calculation results. 

A hypothetical student, _Mark Zuckerberg_, with SRN _000000001_, received the following numbers:

| SRN       | Name            |  a  |  b  |  k  |  Px |  Py |  Qx |  Qy |  n  |
| --------- |:----------------|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| 000000001	| MARK ZUCKERBERG |  -2 |  13 | 103 |  19 |  97 |  27 |  81 |  3  |

He would submit the following JSON, which reflects a correct solution:

```json
{
  "name": "MARK ZUCKERBERG",
  "srn": "000000001",
  "ecc": {
    "a": -2,
    "b": 13,
    "k": 103,
    "order": 109,
    "g": {
      "x": 19,
      "y": 97
    },
  },
  "assignment": {
    "key_exchange": {
      "alice" : {
        "da" : 13,
        "qa" : {
          "x": 58,
          "y": 37
        }
      },
      "bob" : {
        "db" : 17,
        "qb" : {
          "x": 22,
          "y": 76
        }
      },
      "key": {
        "x": 63,
        "y": 46
      }
    }
  }
}
```
Using his _a_ and _b_ values, the generic elliptic curve, defined by _y<sup>2</sup> = x<sup>3</sup> + ax + b_, becomes _y<sup>2</sup> = x<sup>3</sup> - 2x + 13_. This is the same curve he's been allocated in coursework 1. 

He uses _k = 103_ to restrict the curve onto the 𝔽<sub>k</sub> = 𝔽<sub>103</sub> prime field. He determines the _order_ of the field, by working out how many points the discrete curve has (including the point at infinity). In this case it is 109 (observe `"order": 109` in the JSON result). 

Then, using his point, _P(19, 97)_ as a generator _g(x, y) = P(x, y) = (19, 97)_ and choosing randomly Alice's and Bob's private key, _d<sub>a</sub> = 13_ and _d<sub>b</sub> = 17_, he computes the corresponding public keys _Q<sub>a</sub>(x, y)_ and _Q<sub>b</sub>(x, y)_, and the shared key between Alice and Bob _key(x, y)_. Note the results in the JSON output.

`
NOTE: Most programming languages have very good support for JSON parsing and output (including Java, C#, Python and JavaScript), so you can even rely on your programme to produce the required JSON output. 
`

<p align="center"><b>[END OF COURSEWORK ASSIGNMENT 2]</b></p>

