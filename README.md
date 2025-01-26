# How to run

- Make sure docker daemon is running
- `docker build -t fetch-challenge .`
- `docker run -p 8080:8080 fetch-challenge`
- Use `curl` on `localhost:8080` to send requests