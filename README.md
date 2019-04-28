
# Google Hash Code Problem 2019 

This solution will get around 717698 points (712155 in the compentition; #851 Worldwide / #39 in UK)

## Qualification Problem

The problem statement is 
```
Given a list of photos and the tags associated with each photo, arrange the photos into
a slideshow that is as interesting as possible.
```
They score a slideshow by considering each transition between slides. The score will *minimum* of these three values 
  * the number of common tags between two consecutive slides or
  * the number of tags that only appears in the first slide or
  * the number of tags that only appears in the next slide
 
 Each slide could consist of
  * one horizontal photo
  * two vertical photos

*** the detail version please go to [official website](https://hashcodejudge.withgoogle.com/#/home)

### (tiny) Analysing Problem 

The idea behide this is a simple greedy algorithm.

To maximize score; we found that the optimal solution is that the consecutive slides A, B should have these properties.
  * n(A) = n(B)
  * n(A intersect B) = n(A-B) = n(B-A)
  * so only about half of tags in A should be found in B
  
** n(A) = a number of tags in photo A

So we knew that a number of tags is quite useful.
