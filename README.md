# Notes application

Simple RESTful application for managing notes. 
Each note has a title and content, and also is marked with two dates: of creation and last modification.
Reading, creating, updating and deleting of notes are provided.

All changes (like updating and deleting) are kept in database and it is possible
to check whole history of all entries.

Response body formatting is application/hal+json. 
Every entry is followed by a link to a resource, following HATEOAS restrictions.

###Table of contents
1. [What is required for running the project](#running-project)
2. [Database setup for the project](#database-setup)
3. [Project build and run setup](#project-setup)
4. [Example usages](#example-usages)
    1. [Short summary of all possible methods and details of response](#summary-of-methods)
    2. [Notes service examples](#note-service)
    3. [Archive notes service examples](#archive-notes-service)

## What is required for running the project <a name="running-project"/>
* Java ver 1.8
* MySQL ver 8.0

## Database setup for the project <a name='database-setup'/>

First, create a database, which will be storing tables with data from application.
Log in to MySQL Client, i.e. MySQL8.0 Command Line Client and create database:

```MySql
CREATE DATABASE notesdb;
```

Next change connection properties in */src/main/resources/application.properties*,
and set your username and password, so the application would have access to your database.

```
spring.datasource.url=jdbc:mysql://localhost:3306/notesdb
spring.datasource.username=[SQL_DATABASE_USERNAME]
spring.datasource.password=[SQL_DATABASE_PASSWORD]
```

Table would be during first run of application. 

## Project build and run setup <a name="project-setup"/>

To run the application, run the following command in a terminal window of directory containing whole project.

```bash
./mvnw spring-boot:run
```

Application would be run by maven wrapper.

## Example usages <a name="example-usages"/>

Application provides two services:

* Notes service - for managing notes,
* Archive notes service - to view changes history.


### Short summary of all possible methods and details of response<a name="summary-of-methods"/>

**Notes service**

| Method | URI | Body | Content Type | Http Status | Response |
|:------:|:---:|------|:------------:|:-----------:|----------|
| GET | /notes | - | hal+json | 200 | list with all notes |
| GET | /notes/{id} | - | hal+json | 200 | single note with given *id* |
| POST | /notes | `{"title": "new title", "content": "new content"}` | hal+json | 201 | created note with *new title* and *new content* |
| PUT | /notes/{id} | `{"title": "new title", "content": "new content"}` | hal+json | 200 | updated note with given *id*, with *new title* and *new content* |
| DELETE | /notes/{id} | - | json | 204 | note with a given *id* was deleted |

**Archive notes service**

| Method | URI | Body | Content Type | Http Status | Response |
|:------:|:---:|------|:------------:|:-----------:|----------|
| GET | /archive | - | hal+json | 200 | list with all notes with all their changes, including deleted |
| GET | /archive/{id} | - | hal+json | 200 | list with all changes of single note with given *id* |


* Short summary of possible errors

**Notes service**

| Method | URI | Body | Content Type | Http Status | Response |
|:------:|:---:|------|:------------:|:-----------:|----------|
| GET | /notes/{id} | - | json | 404 | there is no note with given *id* to be displayed |
| POST | /notes | `{"title": "", "content": ""}` | json | 406 | title and content must not be empty, so error message is returned |
| PUT | /notes/{id} | `{"title": "", "content": ""}` | json | 406 | title and content must not be empty, so error message is returned |
| PUT | /notes/{id} | - | json | 404 | there is no note with given *id* to be updated |
| DELETE | /notes/{id} | - | json | 404 | there is no note with given *id* to be deleted |

**Archive notes service**

| Method | URI | Body | Content Type | Http Status | Response |
|:------:|:---:|------|:------------:|:-----------:|----------|
| GET | /archive/{id} | - | json | 404 | there is no note with given *id* to be displayed |

### Notes service examples <a name="note-service">

Manages basic CRUD operations on the database, providing reading, adding, updating and deleting notes.
All operations are based on the HTTP requests, which can be performed using curl, Postman, Httpie etc. 
In this tutorial example curl commands would be presented.

* Get all notes

For this example, at the beginning of work there are two notes in the database. 
After get request on the /notes endpoint list of all notes is returned.
Each note has a link to itself, and link to all notes is added at the end of response.
```bash
curl -XGET -i 'http://localhost:8080/notes'
```

```hal+json
HTTP/1.1 200
Content-Type: application/hal+json
{
    "_embedded": {
        "notes": [
            {
                "id": 1,
                "title": "title1",
                "content": "content 1",
                "created": "2020-10-03",
                "modified": "2020-10-03",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/notes/1"
                    }
                }
            },
            {
                "id": 2,
                "title": "title2",
                "content": "content 2",
                "created": "2020-10-03",
                "modified": "2020-10-03",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/notes/2"
                    }
                }
            }
        ]
    },
    "_links": {
        "all notes": {
            "href": "http://localhost:8080/notes"
        }
    }
}
```
* Get note

If we need to use only one specific note, we can add a path variable with id of requested note..
After get request on the /notes/1 endpoint note with id = 1 is returned, with link to itself.

```bash
curl -XGET -i 'http://localhost:8080/notes/1'
```

```hal+json
HTTP/1.1 200
Content-Type: application/hal+json
{
    "id": 1,
    "title": "title1",
    "content": "content 1",
    "created": "2020-10-03",
    "modified": "2020-10-03",
    "_links": {
        "self": {
            "href": "http://localhost:8080/notes/1"
        }
    }
}
```

* Add new note

Adding new notes is performed with POST request on basic "/notes" endpoint.
Request body must contain json object with defined title and content for new note. 
Creation and modification dates will be set automatically.
In case of success, newly created note would be returned in response body.

```bash
curl -XPOST -i -H "Content-type: application/json" -d '{"title": "new title", "content": "new content"}' 'http://localhost:8080/notes'
```

```hal+json
HTTP/1.1 201
Content-Type: application/hal+json
{
    "id": 3,
    "title": "new title",
    "content": "new content",
    "created": "2020-10-04",
    "modified": "2020-10-04",
    "_links": {
        "self": {
            "href": "http://localhost:8080/notes/3"
        }
    }
}
```

* Update note

Adding new notes is performed with PUT request on basic "/notes/{id}" endpoint.
Request body must contain json object with defined title and content to be changed in original note.
In this example we will change content of a note 1.
Because title must remain the same, we just copy original one to "title" json segment.

In response body is send updated note, with changed content. 
Update is also marked in a change of modification date.

```bash
curl -XPUT -i -H "Content-type: application/json" -d '{"title": "title1", "content": "updated content 1"}' 'http://localhost:8080/notes/1'
```
```hal+json
HTTP/1.1 200
Content-Type: application/hal+json
{
    "id": 1,
    "title": "title1",
    "content": "updated content 1",
    "created": "2020-10-03",
    "modified": "2020-10-04"
    "_links": {
        "self": {
            "href": "http://localhost:8080/notes/1"
        }
    }
}
```

* Delete note

Deleting notes is performed with DELETE request on basic "/notes/{id}" endpoint.
In this example we will delete a note 2.
Response do not contain body or messages, just No content Http status.

```bash
curl -XDELETE -i 'http://localhost:8080/notes/2'
```
```hal+json
HTTP/1.1 204
```

### Archive notes service examples <a name="archive-notes-service"/>

Gives access to whole history of changes, that were performed on the notes. 
Serves only GET requests, giving access to read only, not modify the data.

* Get whole history of all notes

After get request on the /archive endpoint list of changes of all notes is returned. 
Each change is presented as a note, with a link to itself, and link to all archived notes is added at the end of response.
More data is displayed about each note - there is added version of note (first one marked with zero),
and active field. 
Active describes, whether that note is still actual - if it was deleted or updated,
that field turns to false and is not displayed in Notes service.
That notes versions are kept in database just as a history record - like deleted note 2 or fist version of note 1.

```bash
curl -XGET -i 'http://localhost:8080/archive'
```

```hal+json
HTTP/1.1 200
Content-Type: application/hal+json
{
    "_embedded": {
        "archive notes": [
            {
                "id": 1,
                "version": 1,
                "active": true,
                "title": "title1",
                "content": "updated content 1",
                "created": "2020-10-03",
                "modified": "2020-10-04",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/archive/1"
                    }
                }
            },
            {
                "id": 1,
                "version": 0,
                "active": false,
                "title": "title1",
                "content": "content 1",
                "created": "2020-10-03",
                "modified": "2020-10-03",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/archive/1"
                    }
                }
            },
            {
                "id": 2,
                "version": 0,
                "active": false,
                "title": "title2",
                "content": "content 2",
                "created": "2020-10-03",
                "modified": "2020-10-03",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/archive/2"
                    }
                }
            },
            {
                "id": 3,
                "version": 0,
                "active": true,
                "title": "new title",
                "content": "new content",
                "created": "2020-10-04",
                "modified": "2020-10-04",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/archive/3"
                    }
                }
            }
        ]
    },
    "_links": {
        "all archived notes": {
            "href": "http://localhost:8080/notes"
        }
    }
}
```


* Get whole history of one note

After get request on the /archive/{id} endpoint list of changes of only one note is returned. 
Each change is presented as a note, with a link to itself, and link to all archived notes is added at the end of response.
First note is always an actual one (active - true), and then come all changes in historical order - from first (marked as 0 version) to the last one.
In this example we will check history of note 1.

```bash
curl -XGET -i 'http://localhost:8080/archive/1'
```

```hal+json
HTTP/1.1 200
Content-Type: application/hal+json
{
    "_embedded": {
        "archive notes": [
            {
                "id": 1,
                "version": 1,
                "active": true,
                "title": "title1",
                "content": "updated content 1",
                "created": "2020-10-03",
                "modified": "2020-10-04",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/archive/1"
                    }
                }
            },
            {
                "id": 1,
                "version": 0,
                "active": false,
                "title": "title1",
                "content": "content 1",
                "created": "2020-10-03",
                "modified": "2020-10-03",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/archive/1"
                    }
                }
            }
        ]
    },
    "_links": {
        "all archived notes": {
            "href": "http://localhost:8080/notes"
        }
    }
}
```