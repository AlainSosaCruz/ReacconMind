# ReacconMind
ReacconMind is a social network designed to promote free communication among people of all ages and backgrounds. It offers a platform where users can share images, videos, and thoughts, promoting diversity of opinions and respect for freedom of expression.

## Database Configuration
To configure the database connection, copy the `application.properties.example` file to `application.properties` and edit it with your actual credentials.
## Database Configuration
To configure the firebase connection, create a file named `firebase-private-key.json` and `firebase-privateAlain-key.json` in the src/main/resources/ directory of your project and add your Firebase project credentials.

## CREDENTIALS
```bash
{
  "type": "service_account",
  "project_id": "reacconmind-e99ee",
  "private_key_id": "73e44819f14b0173e758b2ad109a3560ff49b094",
  "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCa6YLC4oAY5A46\n6SX/SOPse4I9mlbXmQvtZIYN1xWBBP2DswIxP6wD/LimMrZpKqbBbfYhlY5eHJFY\njv8WbDGdoX1miCi4fP13hgWQcgZ9NivGr1Vl0DlBJaHvBXlbaAWiz5kO4/Xr9IPH\nCR3qxwmzozwyTxaVw3ysXulSJvyC+7B8xI30bw7tw/Z3spP8SPBcogHtC8PRONZ8\nsY/QFn1Ddx8bG6eXwrYrZGuayAf6z1G65fSa9X9m5wAtjmgrx2OVDnu1YmHK5Uks\nwUYqBu/9ZMY24UW+zIYa1tBDVYnm+7Sqvlm3534TG5rDE/rcUAAkIJDApiOO50OY\nnGNeVQH7AgMBAAECggEAMVmejJM3l7ZfSikoL7F62sVl8lYOwFRDVncltZcFhLqg\nQKpwUlwkYojKEjgpoEs0IH71YDGSzyxuqTW00eHZ1IuztUw/HLbE0KTjdOa+Znkx\nbPRCUlMJSlXAxrKJvLLgyMM10WaR239j065112tdDds8vBIx8bf7KbZ6we8lllw1\nFt9jWzC8PCodLzJNtaaXvD323eV1/sdUTlOpA68cg9w1+4CFWSEu30jd/Qfik+28\nE4t9Kmagskx5ma1sd63ndutdMBDnyYwDY/TgwI7HC7pv6pD7+B7dtgjyftKOwmBs\nEVsMQBreNclt5GuFUpbO85FDCo4Wsx8tL8MQzjEXAQKBgQDP8UlW07CyTjUlEtUO\nkW99AyCFPcTSqW7e/7REqccQqs8pP9s2PUzf0ZwNZUkZ7OnGlUnrYAQIb4HzKfSD\nxEWkE3l9B5DZtQ9iQhedmvrUcCLebHjsPwa21W7zPrIxlasWv5d6A9KlRaQ2OcTM\nGxYgWO6nDVoQB4DwojOPzpzgOwKBgQC+trwZ/KskemaO9/BFPXo1ilXre7NJrpo7\nGoD3xy6u2MBc9NkXNaTl0brdUKtYu8ON1CwtD3Dy0qTvgrGH7//oNHhf69yRpNWF\nzeS6BsB7dOl6c/OWR3TosNv5qg9mexBV5WAK8tkBgOJX4lYmg3l4FF7Ihb5Kft34\nVkS4kfoJQQKBgBQcH52YB2SXD6iwM46fHgsW/FQmbxx4alKCpCpnyOqhujshA9Ax\nCXgzy0W4X+Nz9//OfEGZHYq7TfP4QL9lgd6mS3999dI4Im68OZByZoOY/KZKEz+1\nsK6J4ZsAmzcUZGFk0f9o0zxMid78oz2h+PfT4fCh5+3Y6+1Zombr8lv1AoGBAJ79\nIeUcMeSwMc2gPurKLJZz49rzfezAJY1+M+PcZjuXMQ6NhGmH7Bc3d+vGs4G+znDv\nX4CVLdkQ6DVPpAaqepGvJGb67x9v5h2tHrBCrrZ9KaNPBNPUcE8IGeqR7lLF50Fk\nfmPCPky1kHwpkR58XohPmxDaBmB+UTgVWybvcx3BAoGAS2w9JQuxqzNTu/KGLVds\n4bY/GwFIuXXmVHmX96fOgK34zAlYFa3cFA4SAJvWtIQPkI6bwL9Bqj7qI+vK24X8\nEhwWLTU4Cv6jdwtK1xDhZAIOfbDpJyBNwfmWjbwQZP4lrszjkbu/vBXjF7vK7kt6\ncQ4AoSZseKMO8xMSUkrYIDY=\n-----END PRIVATE KEY-----\n",
  "client_email": "firebase-adminsdk-fj8rj@reacconmind-e99ee.iam.gserviceaccount.com",
  "client_id": "105543304725207416726",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://oauth2.googleapis.com/token",
  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-fj8rj%40reacconmind-e99ee.iam.gserviceaccount.com",
  "universe_domain": "googleapis.com"
}
```

## SQL Database
The database is located in the `Bd` folder. To use it, import it into your favorite database management system (DBMS), making sure the engine is **MySQL**.
## Default User
To log in with an email, there is a default user created for this application:


```bash
mario.pv@teziutlan.tecnm.mx
```
Password:
```bash
mario
