FROM alpine:edge

RUN apk update && apk upgrade -a

RUN apk add \
    bash \
    coreutils \
    curl \
    git \
    grep \
    npm \
    openjdk11 \
    procps \
    python3 \
    which
