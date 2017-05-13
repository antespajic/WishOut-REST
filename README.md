# WishOut REST API
###### Version 1.0.2

## User functionality

#### User creation
* **URL:** `/user`
* **Method:** `POST`
* **URL paramteres:** not utilized here
* **Data parameters:**
    ```json
    {
        "email" : "john.doe@email.com",
        "password" : "strong_password",
        "firstName" : "John",
        "lastName" : "Doe",
        "city" : "New York",
        "country" : "USA",
        "gender" : "M",
        "dateOfBirth" : 1490117226,
        "dateOfRegistration" : 1490117226,
        "contactNumber" : "00385991122333",
        "contactFacebook" : "link/to/user/profile",
        "profileConfirmed" : false,
        "coins" : 0
    }
    ```
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        {
            "id" : "neki123user456id",
            "email" : "john.doe@email.com",
            "password" : "strong_password",
            "firstName" : "John",
            "lastName" : "Doe",
            "city" : "New York",
            "country" : "USA",
            "gender" : "M",
            "dateOfBirth" : 1490117226,
            "dateOfRegistration" : 1490117226,
            "profilePicture" : null,
            "contactNumber" : "00385991122333",
            "contactFacebook" : "link/to/user/profile",
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
            "id" : 123234,
            "email" : "john.doe@email.com",
            "password" : "strong_password",
            "firstName" : "John",
            "lastName" : "Doe",
            "city" : "New York",
            "country" : "USA",
            "gender" : "M",
            "dateOfBirth" : 1490117226,
            "dateOfRegistration" : 1490117226,
            "profilePicture" : "url/to/profile/picture",
            "contactNumber" : "00385991122333",
            "contactFacebook" : "url/to/user/profile",
            "profileConfirmed" : true,
            "coins" : 50
        }
        ```
* **Error response:** will be elaborated at a later time

#### User update
* **Note:** In the following definition and example of user update, user is represented with whole JSON update object. However, user update can be performed with partial JSON update object. Only fields which are provided will be updated. Following parameters of `User` object can be updated (all other parameters given throught JSON update object will be discarded, if they are not 'updateable'):
    * firstName
    * lastName
    * city
    * country
    * gender
    * dateOfBirth
    * contactNumber
    * contactFacebook
    * profileConfirmed
    * coins
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

#### Retrieve wishes for user
* **URL:** `/user/:id/wishes`
* **Method:** `GET`
* **URL paramteres:** `id = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        [
            {
                "id" : "123hjkl123",
                "userId" : "asdf9521dasdf",
                "title" : "Wish title here",
                "description" : "Wish description here",
                "categories" : [ "travel", "music", "dancing" ],
                "pictures" : [ "/first/path/here", "/second/path/here", "/third/path/here" ],
                "created" : 1490117226,
                "state" : 1,
                "upvoteCount" : 0,
                "reportCount" : 0
            }
        ]
        ```
* **Error response:** will be elaborated at a later time

#### Retrieve offers for user
* **URL:** `/user/:id/offers`
* **Method:** `GET`
* **URL paramteres:** `id = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** 
        ```json
        [
            {
                "id" : "ne4st8jkl9",
                "userId" : "asdf9521dasdf",
                "wishId" : "123hjkl123",
                "description" : "Offer description here",
                "created" : 1490117226,
                "chosen" : false,
                "upvoteCount" : 121,
                "reportCount" : 0
            }
        ]
        ```
* **Error response:** will be elaborated at a later time

#### Retrieve stories for user
* **URL:** `/user/:id/stories`
* **Method:** `GET`
* **URL paramteres:** `id = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        [
            {
                "id" : "jkaa8719s9",
                "wishId" : "asd1122",
                "creatorId" : "ask28ja",
                "sponsorId" : "p121alk",
                "description" : "Story description here",
                "created" : 1490117226,
                "pictures" : [ "/first/path/here", "/second/path/here", "/third/path/here" ],
                "reportCount" : 0
            }
        ]
        ```
* **Error response:** will be elaborated at a later time

## Wish functionality

#### Wish creation
* **URL:** `/wish`
* **Method:** `POST`
* **URL parameters:** not utilized here
* **Data parameters:**  
    ```json
    {
        "userId" : "neki358user988id",
        "title" : "Wish title here",
        "description" : "Wish description here",
        "categories" : [ "travel", "music", "dancing" ],
        "created" : 1490117226,
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
            "id" : "neki358wish988id",
            "userId" : "neki358user988id",
            "title" : "Wish title here",
            "description" : "Wish description here",
            "categories" : [ "travel", "music", "dancing" ],
            "pictures" : [],
            "created" : 1490117226,
            "state" : 1,
            "upvoteCount" : 0,
            "reportCount" : 0
        }
        ```
* **Error response:** will be elaborated at a later time

#### Wish retrieval
* **URL:** `/wish/:wishId?index=:index&size=:size`
* **Method:** `GET`
* **URL parameters:** `wishId = String`, `index = Int`, `size = Int`
* **Data parameters:** not present
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        {
            "creator" : {
                "id" : "billov23neki45id",
                "firstName" : "Bill",
                "lastName" : "Kent",
                "profilePicture" : "profile/picture/url"
            },
            "wish" :   {
                "id" : "neki5wish6id",
                "userId" : "neki8creator9id",
                "title" : "Wish title here",
                "description" : "Wish description here",
                "categories" : [ "travel", "music", "dancing" ],
                "pictures" :  [ "/first/path/here", "/second/path/here", "/third/path/here" ],
                "created" : 1490117226,
                "state" : 1,
                "upvoteCount" : 0,
                "reportCount" : 0
            },
            "chosenOffer" : {
                "user" :  {
                    "id" : "neki1odabrani2id",
                    "firstName" : "John",
                    "lastName" : "Doe",
                    "profilePicture" : "profile/picture/url"
                },
                "offer" : {
                    "id" : "neki2odabrani3offer4id",
                    "userId" : "neki1odabrani2id",
                    "wishId" : "neki5wish6id",
                    "description" : "Offer description here",
                    "created" : 1490117226, 
                    "chosen" : true,
                    "upvoteCount" : 28,
                    "reportCount" : 0
                },
                "interaction" : {
                    "upvoted" : true,
                    "reported" : false
                }
            },
            "myOffer" : {
                "user" :  {
                    "id" : "neki1moj2id",
                    "firstName" : "John",
                    "lastName" : "Doe",
                    "profilePicture" : "profile/picture/url"
                },
                "offer" : {
                    "id" : "neki2moj3offer4id",
                    "userId" : "neki1moj2id",
                    "wishId" : "neki5wish6id",
                    "description" : "Offer description here",
                    "created" : 1490117226, 
                    "chosen" : false,
                    "upvoteCount" : 2,
                    "reportCount" : 0
                },
                "interaction" : {
                    "upvoted" : false,
                    "reported" : false
                }
            },
            "interaction" : {
                "upvoted" : true,
                "reported" : false
            },
            "offers" :    [
                {
                    "user" :  {
                        "id" : "neki2prvi3user4id",
                        "firstName" : "John",
                        "lastName" : "Doe",
                        "profilePicture" : "profile/picture/url"
                    },
                    "offer" : {
                        "id" : "neki2prvi3offer4id",
                        "userId" : "neki2prvi3user4id",
                        "wishId" : "neki5wish6id",
                        "description" : "Offer description here",
                        "created" : 1490117226, 
                        "chosen" : false,
                        "upvoteCount" : 15,
                        "reportCount" : 0
                    },
                    "interaction" : {
                        "upvoted" : false,
                        "reported" : false
                    }
                },
                {
                    "user" :  {
                        "id" : "neki2drugi3user4id",
                        "firstName" : "Jane",
                        "lastName" : "Doe",
                        "profilePicture" : "profile/picture/url"
                    },
                    "offer" : {
                        "id" : "neki2drugi3offer4id",
                        "userId" : "neki2drugi3user4id",
                        "wishId" : "neki5wish6id",
                        "description" : "Offer description here",
                        "created" : 1490117226, 
                        "chosen" : false,
                        "upvoteCount" : 10,
                        "reportCount" : 0
                    },
                    "interaction" : {
                        "upvoted" : false,
                        "reported" : false
                    }
                }
            ]
        }
        ```
* **Error response:** will be elaborated at a later time

#### Wish update
* **Note:** In the following definition and example of wish update, wish is represented with whole JSON update object. However, wish update can be performed with partial JSON update object, where only fields which are provided will be updated. Following parameters of `Wish` object can be updated (all other parameters given throught JSON update object will be discarded, if they are present):
    * title
    * description
    * state
* **URL:** `/wish/:id`
* **Method:** `PUT`
* **URL parameters:** `id = String`
* **Data parameters:**  
    ```json
    {
        "title" : "Another wish title here",
        "description" : "Another wish description here",
        "state" : 2
    }
    ```
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

#### Wish categories update
* **URL:** `/wish/categories/:id`
* **Method:** `PUT`
* **URL parameters:** `id = String`
* **Data parameters:**  
    ```json
    [ "travel", "music", "dancing", "skiing" ]
    ```
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

#### Assigning offer to wish
* **Note:** This functionality can be used to confirm new and decline existing offer for wish. Trying to assign
offer to wish that already has confirmed offer assigned to it will result in error.
* **URL:** `/wish/:wishId/:offerId?confirmed=:confirmed`
* **Method:** `PUT`
* **URL parameters:** `wishId = String`, `offerId = String`, `confirmed = Bool`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

## Offer functionality

#### Offer creation
* **URL:** `/offer`
* **Method:** `POST`
* **URL parameters:** not utilized here
* **Data parameters:**
    ```json
    {
        "userId" : "user123id123neki",
        "wishId" : "neki987id654wish",
        "description" : "Offer description here",
        "created" : 1490117226,
        "chosen" : false,
        "upvoteCount" : 0,
        "reportCount" : 0
    }
    ```
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        {
            "id" : "offer775neki912id",
            "userId" : "user123id123neki",
            "wishId" : "neki987id654wish",
            "description" : "Offer description here",
            "created" : 1490117226,
            "chosen" : false,
            "upvoteCount" : 0,
            "reportCount" : 0
        }
        ```
* **Error response:** will be elaborated at a later time

#### Offer retrieval
`Offer` object retrieval is closely tied to `Wish` object retrieval. For more information consult `Wish` object retrieval functionality.

#### Offer update
* **Note:** In the following definition and example of offer update, offer is represented with whole JSON update object. Following parameters of `Offer` object can be updated (all other parameters given throught JSON update object will be discarded, if they are present):
    * description
* **URL:** `/offer/:id`
* **Method:** `PUT`
* **URL parameters:** `id = String`
* **Data parameters:**  
    ```json
    {
        "description" : "Another offer description here"
    }
    ```
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

## Story functionality

#### Story creation
* **URL:** `/story`
* **Method:** `POST`
* **URL parameters:** not utilized here
* **Data parameters:**
    ```json
    {
        "wishId" : "neki123string456id",
        "creatorId" : "neki987drugi654id",
        "sponsorId" : "neki456treci123id",
        "created" : 1490117226,
        "description" : "Story description here",
        "reportCount" : 0
    }
    ```
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        {
            "id" : "neki357sasvim864novi",
            "wishId" : "neki123string456id",
            "creatorId" : "neki987drugi654id",
            "sponsorId" : "neki456treci123id",
            "created" : 1490117226,
            "description" : "Story description here",
            "pictures" : [],
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
                "id" : "story777id123neki",
                "wishId" : "wish123id999neki",
                "creatorId" : "neki987prvi654id",
                "sponsorId" : "neki987drugi654id",
                "created" : 1490117226,
                "description" : "Story description here",
                "pictures" : [ "/first/path/here", "/second/path/here", "/third/path/here" ],
                "reportCount" : 0
            },
            "creator" : {
                "id" : "neki987prvi654id",
                "firstName" : "Jane",
                "lastName" : "Doe",
                "profilePicture" : "profile/picture/url"
            },
            "sponsor" : {
                "id" : "neki987drugi654id",
                "firstName" : "John",
                "lastName" : "Doe",
                "profilePicture" : "profile/picture/url"
            },
            "interaction" : {
                "upvoted" : false,
                "reported" : false
            }
        }
        ```
* **Error response:** will be elaborated at a later time

#### Story update
* **Note:** In the following definition and example of story update, story is represented with whole JSON update object. Following parameters of `Story` object can be updated (all other parameters given throught JSON update object will be discarded, if they are present):
    * description
* **URL:** `/story/:id`
* **Method:** `PUT`
* **URL parameters:** `id = String`
* **Data parameters:**  
    ```json
    {
        "description" : "Another story description here"
    }
    ```
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

## Upvote functionality

#### Wish upvote
* **URL:** `/upvote/wish?wish=:wishId&user=:userId`
* **Method:** `PUT`
* **URL parameters:** `wishId = String`, `userId = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

#### Offer upvote
* **URL:** `/upvote/offer?offer=:offerId&user=:userId`
* **Method:** `PUT`
* **URL parameters:** `offerId = String`, `userId = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

## Report functionality

#### Wish report
* **URL:** `/report/wish?wish=:wishId&user=:userId`
* **Method:** `PUT`
* **URL parameters:** `wishId = String`, `userId = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

#### Offer report
* **URL:** `/report/offer?offer=:offerId&user=:userId`
* **Method:** `PUT`
* **URL parameters:** `offerId = String`, `userId = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

#### Story report
* **URL:** `/report/story?story=:storyId&user=:userId`
* **Method:** `PUT`
* **URL parameters:** `storyId = String`, `userId = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** not utilized here
* **Error response:** will be elaborated at a later time

## Image functionality

#### Image upload
* **URL:** `/image/:resource/:id`
* **Method:** `PUT`
* **URL paramteres:** `resource = ['user','wish','story']`, `id = String`
* **Data parameters:** Header key: `image`, header value associated  with aformentioned key: `MultipartFile` - file for ulpoad
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** In case of user, 'paths' list will be singleton list with path to his profile picture.
        ```json
        {
            "id" : "abc3def6",
            "paths" : [ "/first/path/here", "/second/path/here", "/third/path/here" ]
        }
        ```
* **Error response:** will be elaborated at a later time

#### Image retireval
* **URL:** `/image/:resource/:id`
* **Method:** `GET`
* **URL paramteres:** `resource = ['user','wish','story']`, `id = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** In case of user, 'paths' list will be singleton list with path to his profile picture.
        ```json
        {
            "id" : "abc3def6",
            "paths" : [ "/first/path/here", "/second/path/here", "/third/path/here" ]
        }
        ```
* **Error response:** will be elaborated at a later time

#### Image removal
* **Note:** Image name parameter is not needed when removing user's profile picture.
* **URL:** `/image/:resource/:id/:imageName`
* **Method:** `DELETE`
* **URL paramteres:** `resource = ['user','wish','story']`, `id = String`, `imageName = String`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:** 
        * In case of user, no content is returned.
        * In case of wishes and stories, model with paths to remaining pictures is returned:
            ```json
            {
                "id" : "abc3def6",
                "paths" : [ "/first/path/here", "/second/path/here", "/third/path/here" ]
            }
            ```
* **Error response:** will be elaborated at a later time

## Search functionality
**Note:** In the following definitions, query array of `String` values represents user's search input splitted into words.

#### Wish search functionality
* **URL:** `/search/wish/?index=:index&size=:size&query=:query`
* **Method:** `GET`
* **URL parameters:** `index = Integer`, `size = Integer`, `query = [String]`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        [
            {
                "wish" :   {
                    "id" : "1744",
                    "creator" : {
                        "userId" : "1980",
                        "firstName" : "Bill",
                        "lastName" : "Kent",
                        "profilePicture" : "profile/picture/url"
                    },
                    "title" : "Wish title here",
                    "description" : "Wish description here",
                    "categories" : [ "travel", "music", "dancing" ],
                    "global" : true,
                    "created" : 1490117226,
                    "offerId" : null,
                    "state" : 1,
                    "upvoteCount" : 0
                }
            }
        ]
        ```
* **Error response:** will be elaborated at a later time

#### Story search functionality
* **URL:** `/search/story/?index=:index&size=:size&query=:query`
* **Method:** `GET`
* **URL parameters:** `index = Integer`, `size = Integer`, `query = [String]`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        [
            {
                "story" : {
                    "id" : "6731da",
                    "wishId" : "12sd312",
                    "creator" : {
                        "id" : "d112312d",
                        "firstName" : "Jane",
                        "lastName" : "Doe",
                        "profilePicture" : "profile/picture/url"
                    },
                    "sponsor" : {
                        "id" : "d2e2123",
                        "firstName" : "John",
                        "lastName" : "Doe",
                        "profilePicture" : "profile/picture/url"
                    },
                    "description" : "Story description here",
                    "created" : 1490117226,
                    "pictures" : [ "url/to/first/picture", "url/to/last/picture" ],
                    "reportCount" : 0
                }
            }
        ]
        ```
* **Error response:** will be elaborated at a later time

## Front page functionality
#### Wish front page functionality
* **URL:** `/frontpage/wish/?ranking=:ranking&index=:index&size=:size`
* **Method:** `GET`
* **URL parameters:** `ranking = ['new', 'top', 'pending']`,`index = Integer`, `size = Integer`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
        [
            {
                "wish" :   {
                    "id" : "1744",
                    "creator" : {
                        "userId" : "1980",
                        "firstName" : "Bill",
                        "lastName" : "Kent",
                        "profilePicture" : "profile/picture/url"
                    },
                    "title" : "Wish title here",
                    "description" : "Wish description here",
                    "categories" : [ "travel", "music", "dancing" ],
                    "global" : true,
                    "created" : 1490117226,
                    "offerId" : null,
                    "state" : 1,
                    "upvoteCount" : 0,
                    "interaction" : {
                        "upvoted" : false,
                        "reported" : false
                    }
                }
            }
        ]
        ```
* **Error response:** will be elaborated at a later time

#### Story front page functionality
* **Note:** Stories retrieved as part of front page functionality are by deafult sorted in descending order by time created.
* **URL:** `/frontpage/story/?index=:index&size=:size`
* **Method:** `GET`
* **URL parameters:** `index = Integer`, `size = Integer`
* **Data parameters:** not utilized here
* **Success response:**
    * **Code:** `200 OK`
    * **Content:**
        ```json
            [
                {
                    "story" : {
                        "id" : "6731da",
                        "wishId" : "12sd312",
                        "creator" : {
                            "id" : "d112312d",
                            "firstName" : "Jane",
                            "lastName" : "Doe",
                            "profilePicture" : "profile/picture/url"
                        },
                        "sponsor" : {
                            "id" : "d2e2123",
                            "firstName" : "John",
                            "lastName" : "Doe",
                            "profilePicture" : "profile/picture/url"
                        },
                        "description" : "Story description here",
                        "created" : 1490117226,
                        "pictures" : [ "url/to/first/picture", "url/to/last/picture" ],
                        "reportCount" : 0,
                        "interaction" : {
                            "upvoted" : false,
                            "reported" : false
                        }
                    }
                }
            ]
        ```
* **Error response:** will be elaborated at a later time
