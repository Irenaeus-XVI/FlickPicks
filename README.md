# FlickPicks

FlickPicks is an Android app that uses Retrofit, Glide, jBCrypt, and SQLite to fetch data from the Movie DB API and display trending movies and TV shows from the last week.

## Installation

To install FlickPicks, you can clone this repository and open the project in Android Studio. Then, you can build and run the app on an emulator or a physical Android device.

## Usage

When you open FlickPicks, you will be presented with a list of trending movies and TV shows from the last week, including its title, release date, rating, overview, and poster image.

You can also search for movies and TV shows by typing in the search bar at the top of the screen. The app will display a list of results that match your search query, and you can click on a result to see more details about it.

You can create your own user account.

To use FlickPicks, you'll need to obtain an API key from The Movie Database. Once you have your API key, you can add it to the local.properties file in your project directory like so:

    apiKey=YOUR_API_KEY_HERE




## Development

To develop FlickPicks further, you can add more features such as:

- User authentication and registration using jBCrypt for password hashing
- Saving user information and encrypted password  to a local SQLite database
- Implementing NavBar to display more results from the Movie DB API
- Adding a watchlist feature to keep track of movies and TV shows you want to watch


## Technologies Used
- Retrofit: for making API calls to the Movie Database API
- BCrypt: for securely hashing and storing user passwords
- SQLite: for local data storage and retrieval
- Glide: for loading and displaying images from the API
- Android Studio: for development and testing


Contributing
------------
Contributions to FlickPicks are welcome and encouraged! If you find a bug or have a feature request, please open an issue or submit a pull request. Before contributing.

## Credits

- Ehab Tarek
- Marwan Ayman
- Ahmed Abd Elsalam 
- Ahmed Khaled 
- Youssef Barsom 
 


