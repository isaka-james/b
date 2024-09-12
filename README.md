# Behaviour Management System

<p>
  <img src="https://komarev.com/ghpvc/?username=isaka-b&label=B%20System&color=0e75b6&style=flat" alt="since 04 Sep,2024" />
</p>

## How to run the System
### CREDIDENTIALS

```json
{
    "username":"isaka",
    "password":"admin",
    "first_name":"Isaka",
    "last_name":"James",
    "born":"2024-10-09",
    "gender":"Male",
    "email":"isaka@localhost.sys"
}
```
So you will use `username` as `isaka` and `password` as `admin`. Or you can create your own credentials.

### First Commands

It is better to run a docker in another session, don't just run the `./mvnm spring-boot:run` which automatically run the docker compose up to turn on the postgres.

```bash
docker compose up
```

To create the keypairs,

```bash
openssl genrsa -out keypair.pem 2048
```

And follows by public key,

```bash
openssl rsa -in keypair.pem -pubout -out public.pem
```

And generating the private key,

```bash
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem 
```

And you can delete the keypair.pem