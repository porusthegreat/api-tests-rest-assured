**Steps to Run:**
1. Install Java8 for the platform (https://www.oracle.com/java/technologies/downloads/)
2. Install Maven 3.6.3 (https://maven.apache.org/docs/3.6.3/release-notes.html)
3. Install git and setup ssh access in your local machine using https://docs.github.com/en/authentication/connecting-to-github-with-ssh
4. Clone the repo using `git clone git@github.com:purush087/wtiaTests.git`
5. Run command `mvn clean install`
6. Run command `mvn clean test`

**Issues Faced**:
* PositionsTest.java#36 :: Position Response returns valid response with Units as miles, when an invalid unit provided with units in response as miles.
    * API Improvement: Error Handling. The API should throw 400 error with message "Invalid Unit Provided".
    * Documentation Improvement: If no other units are handled, mention invalid unit will be defaulted to Kilometers
  
* Providing same timestamp multiple times, returns multiple objects for each timestamp.
    * Improvement: get unique timestamps from request and return response for only those time stamps. Duplicate objects need not be returned
