# PokedexGraphQL

An app to retrieve and display Pokemons from the https://graphql-pokeapi.vercel.app/ API.

This is a GraphQL version of my other Pokedex app:

https://github.com/herrbert74/Pokedex

## Tech

* Application entirely written in [Kotlin](https://kotlinlang.org)
* UI developed in [Jetpack Compose](https://developer.android.com/jetpack/androidx/releases/compose)
* Following the [Material 3](https://m3.material.io/) guidelines
* Asynchronous processing using [Coroutines](https://kotlin.github.io/kotlinx.coroutines/)
* Dependency injection with [Dagger](https://github.com/google/dagger) and [Hilt](https://dagger.dev/hilt/)
* Database using [realm-kotlin](https://github.com/realm/realm-kotlin)

## Structure

* The three main sections (module groups in a larger project) are **data**, **domain**, and **presentation**.
* **Domain** does not depend on anything and contains the api interfaces, and the model classes. I do not use use 
  cases at the moment, otherwise I follow clean architecture.
* **Data** implements the domain interfaces (repos) through the network, local and db packages or modules, and does not
  depend on anything else, apart from platform and third party libraries. Repository implementations are placed here 
  as well.
* **Presentation** layer uses the data implementations through dependency injection and the domain entities. It also contains the *navigation*, the *design* and the *ui* packages.
* **Dependency Injection** through Dagger and Hilt. The modules are placed in their implementation packages.
