## Image functionality

#### Image retireval
* **URL:** `/image/:resource/:id`
* **Method:** `GET`
* **URL paramteres:** `resource = ['user','wish','story']`, `id = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        * `String` for `resource = user`, single URL to image associated with resource
        * `[String]` for `resource = ['wish','story']`, array of URLs to images associated with resource
* **Error response:** will be elaborated at a later time

#### Image upload
* **URL:** `/image/:resource/:id`
* **Method:** `PUT`
* **URL paramteres:** `resource = ['user','wish','story']`, `id = String`
* **Data parameters:** Header key: `image`, header value associated  with aformentioned key: `MultipartFile` - file for ulpoad
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** `String`, URL to image uploaded
* **Error response:** will be elaborated at a later time

## User functionality
#### User creation
* **URL:** `/user`
* **Method:** `POST`
* **URL paramteres:** not utilized here
* **Data parameters:**
    ```json
    {
        "userId" : null,
        "email" : "john.doe@email.com",
        "password" : "strong_password",
        "firstName" : "John",
        "lastName" : "Doe",
        "country" : "USA",
        "acity" : "New York",
        "gender" : "M",
        "dateOfBirth" : 1490117226,
        "profilePicture" : "url/to/profile/picture",
        "contactNumber" : "00385991122333",
        "contactFacebook" : "link/to/user/profile",
        "dateOfRegistration" : 1490117226,
        "profileConfirmed" : false,
        "coins" : 0
    }
    ```
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        {
            "userId" : 123234,
            "email" : "john.doe@email.com",
            "password" : "strong_password",
            "firstName" : "John",
            "lastName" : "Doe",
            "country" : "USA",
            "city" : "New York",
            "gender" : "M",
            "dateOfBirth" : 1490117226,
            "profilePicture" : "url/to/profile/picture",
            "contactNumber" : "00385991122333",
            "contactFacebook" : "link/to/user/profile",
            "dateOfRegistration" : 1490117226,
            "profileConfirmed" : false,
            "coins" : 0
        }
        ```
* **Error response:** will be elaborated at a later time

#### User retrieval
* **URL:** `/user/:id`
* **Method:** `GET`
* **URL parameters:** `id = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        {
            "userId" : 123234,
            "email" : "john.doe@email.com",
            "password" : "strong_password",
            "firstName" : "John",
            "lastName" : "Doe",
            "country" : "USA",
            "city" : "New York",
            "gender" : "M",
            "dateOfBirth" : 1490117226,
            "profilePicture" : "url/to/profile/picture",
            "contactNumber" : "00385991122333",
            "contactFacebook" : "url/to/user/profile",
            "dateOfRegistration" : 1490117226,
            "profileConfirmed" : false,
            "coins" : 0
        }
        ```
* **Error response:** will be elaborated at a later time

#### User update
* **Note:** In the following definition and example of user update, user is represented with whole JSON object that was earlier described. However, user update can be performed with partial JSON object. In that case, JSON object **MUST** have it's user id parameter set. Lastly, only fields which are provided will be updated.
* **URL:** `/user/:id`
* **Method:** `PUT`
* **URL paramteres:** `id = String`
* **Data parameters:**
    ```json
    {
        "userId" : 123234,
        "email" : "john.doe@email.com",
        "password" : "strong_password",
        "firstName" : "John",
        "lastName" : "Doe",
        "country" : "USA",
        "acity" : "New York",
        "gender" : "M",
        "dateOfBirth" : 1490117226,
        "profilePicture" : "url/to/profile/picture",
        "contactNumber" : "00385991122333",
        "contactFacebook" : "link/to/user/profile",
        "dateOfRegistration" : 1490117226,
        "profileConfirmed" : false,
        "coins" : 0
    }
    ```
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

#### User deletion
* **URL:** `/user/:id`
* **Method:** `DELETE`
* **URL paramteres:** `id = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

## Wish functionality
#### Wish creation
* **URL:** `/wish`
* **Method:** `POST`
* **URL parameters:** not utilized here
* **Data parameters:**  
    ```json
    {
        "wishId" : null,
        "userId" : 1980,
        "title" : "Wish title here",
        "description" : "Wish description here",
        "categories" : [ "travel", "music", "dancing" ],
        "created" : 1490117226,
        "offerId" : null,
        "state" : 1,
        "upvoteCount" : 0,
        "reportCount" : 0
    }
    ```
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        {
            "wishId" : 2550,
            "userId" : 1980,
            "title" : "Wish title here",
            "description" : "Wish description here",
            "categories" : [ "travel", "music", "dancing" ],
            "created" : 1490117226,
            "offerId" : null,
            "state" : 1,
            "upvoteCount" : 0,
            "reportCount" : 0
        }
        ```
* **Error response:** will be elaborated at a later time

#### Wish retrieval
* **URL:** `/wish/:wishId?lower=:lower&upper=:upper`
* **Method:** `GET`
* **URL parameters:** `wishId = String`, `lower = Int`, `upper = Int`
* **Data parameters:** not present
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        {
            "wish" :   {
                "wishId" : 1744,
                "userId" : 1980,
                "title" : "Wish title here",
                "description" : "Wish description here",
                "categories" : [ "travel", "music", "dancing" ],
                "created" : 1490117226,
                "offerId" : null,
                "state" : 1,
                "upvoteCount" : 0,
                "reportCount" : 0
            },
            "creator" : {
                "userId" : 1980,
                "firstName" : "Bill",
                "lastName" : "Kent",
                "profilePicture" : "profile/picture/url"
            },
            "offers" :    [
                {
                    "user" :  {
                        "userId" : 1876,
                        "firstName" : "John",
                        "lastName" : "Doe",
                        "profilePicture" : "profile/picture/url"
                    },
                    "offer" : {
                        "offerId" : 5677,
                        "wishId" : 1744,
                        "userId" : 1876,
                        "description" : "Offer description here",
                        "upvoteCount" : 15,
                        "reportCount" : 0
                    }
                },
                {
                    "user" :  {
                        "userId" : 1877,
                        "firstName" : "Jane",
                        "lastName" : "Doe",
                        "profilePicture" : "profile/picture/url"
                    },
                    "offer" : {
                        "offerId" : 5547,
                        "wishId" : 1744,
                        "userId" : 1877,
                        "description" : "Offer description here",
                        "upvoteCount" : 10,
                        "reportCount" : 0
                    }
                }
            ]
        }
        ```
* **Error response:** will be elaborated at a later time

#### Wish deletion
* **URL:** `/wish/:wishId`
* **Method:** `DELETE`
* **URL parameters:** `wishId = String`
* **Data parameters:** not present
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not present
* **Error response:** will be elaborated at a later time

## Story functionality
#### Story creation
* **URL:** `/story`
* **Method:** `POST`
* **URL parameters:** not utilized here
* **Data parameters:**
    ```json
    {
        "storyId" : null,
        "wishId" : 148842,
        "creatorId" : 15561,
        "sponsorId" : 27888,
        "description" : "Story description here",
        "created" : 1490117226,
        "pictures" : [ "url/to/first/picture", "url/to/last/picture" ],
        "reportCount" : 0
    }
    ```
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        {
            "storyId" : 6731,
            "wishId" : 148842,
            "creatorId" : 15561,
            "sponsorId" : 27888,
            "description" : "Story description here",
            "created" : 1490117226,
            "pictures" : [ "url/to/first/picture", "url/to/last/picture" ],
            "reportCount" : 0
        }
        ```
* **Error response:** will be elaborated at a later time

#### Story retrieval
* **URL:** `/story/:id`
* **Method:** `GET`
* **URL parameters:** `id = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        {
            "story" : {
                "storyId" : 6731,
                "wishId" : 148842,
                "creatorId" : 15561,
                "sponsorId" : 27888,
                "description" : "Story description here",
                "created" : 1490117226,
                "pictures" : [ "url/to/first/picture", "url/to/last/picture" ],
                "reportCount" : 0
            },
            "creator" : {
                "userId" : 15561,
                "firstName" : "Jane",
                "lastName" : "Doe",
                "profilePicture" : "profile/picture/url"
            },
            "sponsor" : {
                "userId" : 27888,
                "firstName" : "John",
                "lastName" : "Doe",
                "profilePicture" : "profile/picture/url"
            }
        }
        ```
* **Error response:** will be elaborated at a later time

## Offer functionality
#### Offer creation
* **URL:** `/offer`
* **Method:** `POST`
* **URL parameters:** not utilized here
* **Data parameters:**
    ```json
    {
        "offerId" : null,
        "wishId" : 1990,
        "userId" : 6742,
        "description" : "Offer description here",
        "upvoteCount" : 0,
        "reportCount" : 0
    }
    ```
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        {
            "offerId" : 190995,
            "wishId" : 1990,
            "userId" : 6742,
            "description" : "Offer description here",
            "upvoteCount" : 0,
            "reportCount" : 0
        }
        ```
* **Error response:** will be elaborated at a later time

#### Offer retrieval
`Offer` retrieval is closely tied to `Wish` retrieval. For more information consult `Wish` retrieval functionality.

#### Offer deletion
* **URL:** `/offer/:offerId`
* **Method:** `DELETE`
* **URL parameters:** `offerId = String`
* **Data parameters:** not present
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not present
* **Error response:** will be elaborated at a later time

## Upvote functionality
Upvote functionality behaves the same accross resources that support upvoting. Only difference is present in first query parameter which identifies specific resource on which upvote is being performed.
* **Wish URL:** `/upvote?wish=:wishId&user=:userId`
* **Offer URL:** `/upvote?offer=:offerId&user=:userId`
* **Method:** `PUT`
* **URL parameters:** `resourceId = String`, `userId = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

## Report functionality
Report functionality behaves the same accross resources that support reporting. Only difference is present in first query parameter which identifies specific resource on which report is being performed.
* **Wish URL:** `/report?wish=:wishId&user=:userId`
* **Offer URL:** `/report?offer=:offerId&user=:userId`
* **Story URL:** `/report?story=:storyId&user=:userId`
* **Method:** `PUT`
* **URL parameters:** `resourceId = String`, `userId = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

## Search functionality
**Note:** In the following definitions, query array of `String` values represents user's search input splitted into words.

#### Wish search functionality
* **URL:** `/search/wish/?lower=:lower&upper=:upper&query=:query`
* **Method:** `GET`
* **URL parameters:** `lower = Int`, `upper = Int`, `query = [String]`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        [
            {
                "wish" :   {
                    "wishId" : 1744,
                    "userId" : 1980,
                    "title" : "Wish title here",
                    "description" : "Wish description here",
                    "categories" : [ "travel", "music", "dancing" ],
                    "global" : true,
                    "created" : 1490117226,
                    "offerId" : null,
                    "state" : 1,
                    "upvoteCount" : 0,
                    "reportCount" : 0
                },
                "creator" : {
                    "userId" : 1980,
                    "firstName" : "Bill",
                    "lastName" : "Kent",
                    "profilePicture" : "profile/picture/url"
                }
            }
        ]
        ```
* **Error response:** will be elaborated at a later time

#### Story search functionality
* **URL:** `/search/story/?lower=:lower&upper=:upper&query=:query`
* **Method:** `GET`
* **URL parameters:** `lower = Int`, `upper = Int`, `query = [String]`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        [
            {
                "story" : {
                    "storyId" : 6731,
                    "wishId" : 148842,
                    "creatorId" : 15561,
                    "sponsorId" : 27888,
                    "description" : "Story description here",
                    "created" : 1490117226,
                    "pictures" : [ "url/to/first/picture", "url/to/last/picture" ],
                    "reportCount" : 0
                },
                "creator" : {
                    "userId" : 15561,
                    "firstName" : "Jane",
                    "lastName" : "Doe",
                    "profilePicture" : "profile/picture/url"
                },
                "sponsor" : {
                    "userId" : 27888,
                    "firstName" : "John",
                    "lastName" : "Doe",
                    "profilePicture" : "profile/picture/url"
                }
            }
        ]
        ```
* **Error response:** will be elaborated at a later time

## Front page functionality
#### Wish front page functionality
* **URL:** `/frontpage/wish/?ranking=:ranking&lower=:lower&upper=:upper`
* **Method:** `GET`
* **URL parameters:** `ranking = ['new', 'top', 'pending']`,`lower = Int`, `upper = Int`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        [
            {
                "wish" :   {
                    "wishId" : 1744,
                    "userId" : 1980,
                    "title" : "Wish title here",
                    "description" : "Wish description here",
                    "categories" : [ "travel", "music", "dancing" ],
                    "global" : true,
                    "created" : 1490117226,
                    "offerId" : null,
                    "state" : 1,
                    "upvoteCount" : 0,
                    "reportCount" : 0
                },
                "creator" : {
                    "userId" : 1980,
                    "firstName" : "Bill",
                    "lastName" : "Kent",
                    "profilePicture" : "profile/picture/url"
                }
            }
        ]
        ```
* **Error response:** will be elaborated at a later time

#### Story front page functionality
* **Note:** Stories retrieved as part of front page functionality are by deafult sorted in descending order by time created.
* **URL:** `/frontpage/story/?lower=:lower&upper=:upper`
* **Method:** `GET`
* **URL parameters:** `lower = Int`, `upper = Int`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
            [
                {
                    "story" : {
                        "storyId" : 6731,
                        "wishId" : 148842,
                        "creatorId" : 15561,
                        "sponsorId" : 27888,
                        "description" : "Story description here",
                        "created" : 1490117226,
                        "pictures" : [ "url/to/first/picture", "url/to/last/picture" ],
                        "reportCount" : 0
                    },
                    "creator" : {
                        "userId" : 15561,
                        "firstName" : "Jane",
                        "lastName" : "Doe",
                        "profilePicture" : "profile/picture/url"
                    },
                    "sponsor" : {
                        "userId" : 27888,
                        "firstName" : "John",
                        "lastName" : "Doe",
                        "profilePicture" : "profile/picture/url"
                    }
                }
            ]
        ```
* **Error response:** will be elaborated at a later time
