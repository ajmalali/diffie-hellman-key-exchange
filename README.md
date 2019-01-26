# Diffie-Hellman key exchange
A java program that demonstrates this key exchange protocol. Diffie–Hellman key exchange
establishes a shared secret between two parties that can be used for secret communication
for exchanging data over a public network. The following is the simplest implementation
of the protocol.

## Example
Alice and Bob wish to communicate secretly over a public network. The following is how they do it.

1. They publicly agree to use a modulus `p = 23` (a prime number) and a base `g = 5` (which is a
      <a href="https://en.wikipedia.org/wiki/Primitive_root_modulo_n">primitive root modulo</a> 23).
2. Alice chooses a secret integer `a = 4`, then sends Bob `gᵃ mod p`.
   - `A = 5⁴ mod 23 = 4`
3. Bob chooses a secret integer `b = 3`, then sends Alice `gᵇ mod p`.
   - `B = 5³ mod 23 = 10`
4. Alice computes `s = Bᵃ mod p`.
   - `s = 10⁴ mod 23 = 18`
5. Bob computes `s = Aᵇ mod p`.
   - `s = 4³ mod 23 = 18`
6. Alice and Bob now share a secret key (18).

## What just happened?
Alice computed `(gᵃ mod p)ᵇ mod p` and Bob computed `(gᵇ mod p)ᵃ mod p` which are equivalent.

## How is this secure?
The encryption relies on the fact that the private numbers (`a` or `b`) can not be computed quickly, given only `g` and `p`, even by the fastest modern computers. Such a problem is called the <a href="https://en.wikipedia.org/wiki/Discrete_logarithm">discrete logarithm problem</a>.

To make the given example more secure, larger values (600+ digits) of `a`, `b`, and `p` need to be chosen.
<a href="http://compoasso.free.fr/primelistweb/page/prime/liste_online_en.php">Here is a list of prime numbers up to 1000 billion.</a> for you to try.
