# GitHub Snap API
Simple API providing endpoint to get user's github repos. 

- /api/repos?username={github user login}

Api has 60 requests per hour limit. 

Both endpoints accept requests with "Accept": "application/json" header.
406 response status is returned when "Accept" header is not specified.
404 response status is returned when user does not exist.

Easiest way to run application on your machine is to clone repo and run program on dedicated IDE like Intellij IDEA.

Postman collection to test this API on localhost:8080/ available in project root folder.