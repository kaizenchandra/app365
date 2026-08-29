# applepolitical-api-365

Account, user, notifications, news and events management.

### Builiding and running the project
- Create database table with SQL DDL and bootstrap with initial data.
- Install JDK version 8 or higher.
- Install maven.
- Install docker.
- Run the flowing commands from the server in the project folder

~~~bash
# Compile the project
mvn clean compile package -DENVIRONMENT=prod -DskipTests=true

# Build the project
docker build -t coreapi:latest .

# Create and run the service
docker run --name coreapi --env-file ./env.prod -d -p 80:8443 -it coreapi

# Docket environment variables file content 'env.prod'
ENVIRONMENT=[prod|dev]
DB_HOST=localhost
DB_PORT=3306
DB_USER=root
DB_PASS=secret
DB_NAME=coreapi
KEY_STORE_PASS=secret
SMTP_USER=info@domain.com
SMTP_PASS=secret
TWILIO_API_KEY=12345
OAUTH_CLIENT_ID=12345
OAUTH_CLIENT_SECRET=secret
OAUTH_URL=https:localhost:8090
STRIPE_API_KEY=12345
DO_API_KEY=12345
DO_API_SECRET=secret
FB_API_KEY=12345
FB_API_SECRET=secret
TWITTER_API_KEY=12345
TWITTER_API_SECRET=secret

# Check service logs
docker logs -f coreapi

# Clear service logs
echo "" > $(docker inspect --format='{{.LogPath}}' coreapi)
~~~


### API Docs how to

The API documentation is written in RAML 1.0 API specifications, to run it, please follow the following steps.

- Install [Atom IDE](https://atom.io)
- Install *API Workbench* plugin inside Atom IDE
- Open the *specs* directory into Atom

#### Online API Visualization & mocks
- Enter the online [API Workbench master branch](https://rawgit.com/mulesoft/api-designer/master/dist/index.html#/?xDisableProxy=true)
- Compress (Zip) the *specs* directory and then import it into the site. From here, you can have a clear view of all the API Endpoints, attributes, turn on the mock API option and use [Postman](https://www.getpostman.com/) to test it. Note that the mock URL will be changed between turning on and off.


### Helpful Resources
- [Convert JSON Schema to Java POJO](https://github.com/joelittlejohn/jsonschema2pojo/wiki/Getting-Started#the-command-line-interface) This one is available online [here](http://www.jsonschema2pojo.org/) to generate a download Java POJO and the first link provide a command line interface (CLI), maven and gradle plugin to generate all POJO from the schemas directory.
- [Convert JSON Schema to Java POJO](https://app.quicktype.io/) This one offer option to convert schemas to other languages like Java, Go, C++, C#, JavaScript, TypeScrypt, Swift, Objective C, Kotlin and others.
- [JSON to Json Schema generator](https://jsonschema.net/)
- [JSON Formatter](http://jsonviewer.stack.hu/)
- [JSON Generator Tool](https://www.json-generator.com/) This one help creating payload base on prebuilt custom functions.
- [JSON Handy Converter](http://convertjson.com/) This site allow converting JSON to a handfull list of other format like SQL, YAML, CSV, XML, HTML Table and vice versa.
- [Convert JSON to CSV](https://konklone.io/json/)
- [APIMATIC](https://apimatic.io/) Developer platform which allows to generate SDK, Code samples, test cases, docs in a wide range of languages like Java, Android, iOS, Angular, GO, Node and others. Tranform API Specifications from RAML and other format to Postman collections and other specs like Swagger.