# Why another Auth service?

As Web Developers, we often encounter the common barrier of auth (Authorization and Authentication) when building a product, service, etc. Thats expected, of course, since there are a lot of precious resources in your application that not any user can access, right? In most of these cases, the whole process of integrating an already existing auth service is okayish and painless.

> With existing auth services, I mean Firebase, Supabase, Kuzzle, Keycloak, etc. You got the point!

Our problems begin when your specific use case goes out of the boundaries secured by these providers, for example:

## 1. You want more control over the application logic

Most mainstream auth services are closed-source or has inner logic in private. They're sold as a product that you can plug into your existing application, without you having to worry about how the user specific data you're handing to them is being handled. That becomes a problem when the `handling` stuff matters to you.

We're not only talking specifically about data security. We're talking about every single process that the user data goes through; sometimes your inner logic doesn't match the way the data is processed, and that uncertainty is not a risk you want to take.

> Briefly, **you're paying for abstraction with the cost of not knowing what goes through that abstraction.**

## 2. You want more control over the entire service

This problem may not be universal, but since we've (Emerauthers 😄) perceived this, it may resonate with your experience as well. There are indeed free and/or open source auth services out there (supabase and keycloak for example). They work really well and have a wide acceptance from the Dev. community. However, even though the source code is open, its an infernal headache to get it compiled, running and self hosted.

Don't get us wrong, the code is good, the application is good, but the _SaaSification_ of **free and open source** software has become a hassle because all the attention goes towards the money-making part, and everything else is thrown away.

### 3. You don't want an overwhelmingly complex solution to a simple problem

As we've talked before, every software product that has an user base with more than 1 user has to come across auth at some point or another. In the same tangent of the foresaid, most of the times, all you want is just a user session handler, but when you come to the industrial standards, you're presented with an overpriced product that in fact does the session handler you asked for, but it comes with a load of solutions you don't even need like a database, a storage, edge functions, serverless functions, vector databases, and so forth and so forth.

---

Those problems, and a couple of others we'll tackle in more detail in the future, are a preview of situations we want to solve, or at least bring refreshing, simplistic and enough solutions.
