# Kogan Coding Challange 
An app that calculates average cubic weight given the list of products. 

# Libraries used: 
- Moshi – json parsing
- Ktor – network library
- Kodein - dependency injection
- Kotlin-Result - variant of `Either` monad

# Further improvements
- Average Cubic Weight is only calculated for the hardcoded category - Air Conditioners - consider adding a way to change the category
- As the app's dependencies grow, it would be a good idea to replace kodein with compile-time based solution 
- State in the viewModel contains list of products, but ui does not display it at the moment
