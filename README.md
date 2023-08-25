# GitHub Snap API
Simple API providing two endpoints to get user's github repos. 

1. /api/repos?username={github user login}
2. /api/repos-auth?username={github user login}

To send authenticated requests (higher request per minute threshold) add accessToken to request body as follows:
"accessToken":${YOUR-PERSONAL-ACCESS-TOKEN}. 
More info about obtaining token in [Github reference document](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens)    

Both endpoints accept requests with "Accept": "application/json" header.
406 response status is returned when "Accept" header is not specified.
404 response status is returned when user does not exist.

Easiest way to run application on your machine is to clone repo and run program on dedicated IDE like Intellij IDEA.

Postman collection to test this API on localhost:8080/ available in project root folder.