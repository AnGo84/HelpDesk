# Database
Used default: 
 - database name= helpdeskDB
 - username = helpdeskDB
 - password = helpdeskDB

## MySQL

### Docker issues
Create an image:
```sh
docker run --name helpdesk-mysql -e MYSQL_DATABASE=helpdeskDB -e MYSQL_USER=helpdeskDB-user -e MYSQL_PASSWORD=helpdeskDB-pass -e MYSQL_ROOT_PASSWORD=root_user_pass -d -p 3306:3306 mysql:latest
```

## Postgres

### Docker issues
Create an image:
```sh
docker run --name helpdesk-postgres -e POSTGRES_USER=helpdeskDB-user -e POSTGRES_PASSWORD=helpdeskDB-pass -d -p 5432:5432 postgres
```