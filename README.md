# Iron-Track Back End

Iron-Track Back End is the service layer between Iron-Track and Iron-Track's database. This repo is intended to be used concurrently with the client-side repo for Iron Track which can be found [here](https://github.com/canasmh/iron-track).
## Installation

To Develop: Install JDK, run
```
 mvn install
```
in terminal. This will install the dependencies from the Maven Pom file.

To Run: Install JRE, go to command line, type
```
JavacIronTrackBE.java
```
this will compile the java program, then

 ```
java IronTrackBe
```
will run the program.

## Setting up Environment variables
In order to ensure that the dependencies are installed correctly, there are two crucial environment variables that you must define. These variables are necessary for the proper functioning of the application:

1. **API Key (API_KEY):** This key is required to connect to the API Ninjas service.
2. **Secret Key (SECRET_KEY):** This key is used for generating JSON Web Tokens (JWTs) and is essential for the application's security.

You have a choice when it comes to defining these environment variables:

**Option 1:** Define Environment Variables in Your IDE Configuration
- You can configure these environment variables directly in your integrated development environment (IDE) settings. This option is suitable for development and testing purposes.
**Option 2:** Define Environment Variables in Application Properties
- Alternatively, you can define these variables in the application properties. To do this, make sure you have a local copy of the application properties file.

### Steps to Create a Local `application.properties` File

To create a local application properties file, follow these steps:

1. Copy the sample application properties file to your local directory using the following command:

```
cp src/main/resources/application.properties.sample src/main/resources/application.properties
```

### Generating a Secret Key
The SECRET_KEY can be generated securely by following these steps:
1. Open your command line terminal.
2. Execute the following command to generate a secure secret key:

```
node -e "console.log(require('crypto').randomBytes(32).toString('hex'))"
```
3. Copy the output generated by the above command.
4. Set the value of SECRET_KEY in your chosen environment (IDE or application properties) to the copied output.

Please note that you must create an account with API Ninjas to obtain a valid API key for their service. This API key is required for proper interaction with their API.

By following these steps, you'll ensure that your application functions correctly and securely with the required environment variables.

### Database Configuration
The application uses a PostgreSQL database that runs locally on port 5432. While you can configure the database's host, port, and name in the application.properties file, it's essential to ensure that a compatible database is running and pointed to the location prescribed in the application.properties.

By following these steps, you'll ensure that your application functions correctly and securely with the required environment variables and a properly configured database.

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.
