# android-task

Shortly Android App

## Description
This app has following basic functionality.
-   View the optimal layout for the mobile app depending on their device's screen size
-   Shorten any valid URL
-   See a list of their shortened links ("Link History"). Link History should be persisted ideally.
-   Copy the shortened link to their clipboard in a single click
-   Delete a shortened link from their Link History
-   Show error based on data validation or server errors


I have added support for different environments, also made text styles as per design guide and support of
different screen sizes in portrait mode only. Also unit test of all major classes are covered.

## App Architecture (MVVM)
- View: This part has view specific classes like Activity, fragments, Adapter, View holders
- ViewModel: Maintain UI state and provide data to UI (fragments), also get all inputs from user
- Model: This is actually brain of app and has all business and data logic along with data models
	- UseCase: Keep all business rules and logic. They apply rules and provide data to view model
	- Repository: It has all data fetching, saving logic. It also handle all types of network error or database errors. This layer has two major components
		- Service: Api that fetch data from server and in repository handle its all http types of errors
		- Dao: Has logic to fetch and save data in database. This is part of Repository


## Tech Stack
- Kotlin: Programming language
- Flow: Reactive stream to get live updates of data from room database
- Coroutines: For managing threads UI and background threads
- Hilt: Used for dependencies of classes
- Retrofit with Moshi: Parse http request/response of shortly API
- Room: for local cache to maintain links history
- MockK and Turbine: For unit testing
- View Model
- View Bindings


## Unit testing
- Unit tests of view model, use cases and repository classes


## Possible Improvements
- We can made for seperate design UIs for landscape mode (i did not pay attention to this because my focus is on support multiple screens sizes in portrait mode only)